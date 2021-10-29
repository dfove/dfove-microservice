/*
 * Copyright (c) 2021 dfove.com Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microservice.authorization.api;

import com.microservice.authorization.Gateways;
import com.microservice.authorization.Jwt;
import com.microservice.authorization.Microservices;
import com.microservice.authorization.entity.AdminPermission;
import com.microservice.authorization.entity.Service;
import com.microservice.authorization.jwts.JwtUtil;
import com.microservice.core.RestTemplateUtil;
import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.enums.AccessType;
import common.enums.CollectionType;
import common.enums.ScopeType;
import common.redis.IRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(value = "/dfove/v1/check")
public class CheckController {

	@Autowired
	private Jwt jwt;

	@Autowired
	private Gateways gateways;

	@Autowired
	private IRedis redis;

	@Autowired
	private Microservices microservices;

	@Autowired
	@Qualifier("RestTemplateEureka")
	private RestTemplate restTemplate;

	private final static String EUREKA = AccessType.eureka.toString();

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public OperateResult<UserScope> check(String token, String api, String method) {

		// token 为空
		if (token == null || token.isEmpty()) {
			return new OperateResult<UserScope>().failed("缺少token", ErrorCode.TOKEN_NOT_CORRECT);
		}

		// 解析token
		String secretKey = jwt.getSecretKey();
		OperateResult<UserScope> result = JwtUtil.parseToken(token, secretKey);

		// 如果失败或为空直接返回结果
		if (!result.ok() || result.getData() == null) {
            return result;
        }

		// token强制失效，包括主动退出或系统强制清除
		String tokenKey = result.getData().getKey();
		if (tokenKey == null || tokenKey.isEmpty()) {
            return new OperateResult<UserScope>().failed("该token已失效", ErrorCode.LOGIN_REQUIRED);
        }

		tokenKey = String.format("jnyc.token.%s", tokenKey);
		if (redis.exists(tokenKey)) {
            return new OperateResult<UserScope>().failed("该token已失效", ErrorCode.LOGIN_REQUIRED);
        }

		// 或者不是管理员直接返回结果
		if (result.getData().getScopeType() != ScopeType.admin.getValue()) {
            return result;
        }

		// 管理员开放Apis
		List<Service> services = gateways.getList("adminOpen");
		for (Service s : services) {
			if (s.validUrl(api)) {
				if (s.validMethod(method)) {
					return result;
				}
				break;
			}
		}

		// 验证管理员api权限，先根据角色id从缓存读取
		UserScope scope = result.getData();
		String adminId = scope.getUserId();
		String key = String.format("jnyc.admin.permissions.%s", adminId);
		List<AdminPermission> permissions = null;
		if (redis.exists(key)) {
			permissions = redis.get(key, AdminPermission.class, CollectionType.ListType);
		}

		// 如果不存在则从数据库读取
		if (permissions == null) {
			String adminUrl = microservices.getValue("admin", EUREKA);
			String apisApi = String.format("/permission/admin/%s", adminId);
			ParameterizedTypeReference<OperateResult<List<AdminPermission>>> returnType = new ParameterizedTypeReference<OperateResult<List<AdminPermission>>>() {
			};
			OperateResult<OperateResult<List<AdminPermission>>> apiResult = RestTemplateUtil.get(restTemplate,
					adminUrl + apisApi, null, null, returnType);
			if (!apiResult.ok() || apiResult.getData() == null) {
				return new OperateResult<UserScope>().failed("验证管理员权限发生错误：" + apiResult.getMessage(),
						apiResult.getCode());
			}
			permissions = apiResult.getData().getData();
			if (permissions != null && permissions.size() > 0) {
				redis.set(key, permissions, 86400);
			}
		}

		if (permissions == null || permissions.size() < 1) {
			return new OperateResult<UserScope>().failed("该管理员无关联任何权限", ErrorCode.NO_RESOURCES);
		}

		// 验证api和请求方法是否匹配
		for (AdminPermission a : permissions) {
			if (a.validApi(api)) {
				if (a.validMethod(method)) {
					return result;
				}
//				break;
			}
		}

		// 不满足则返回无权限提示
		return new OperateResult<UserScope>().failed(String.format("该管理员无访问%s的%s权限", api, method),
				ErrorCode.NO_RESOURCES);
	}
}
