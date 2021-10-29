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

import com.microservice.authorization.Jwt;
import com.microservice.authorization.jwts.JwtUtil;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.tools.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dfove/v1/authorised")
public class AuthorisedController {

	@Autowired
	private Jwt jwt;

	/**
	 * 有登录的请求
	 * 
	 * @param scopeJson 登录信息Json
	 * @return 获取token状态和token信息
	 */
	@RequestMapping(method = RequestMethod.GET)
	public OperateResult<String> authorised(String scopeJson) {

		if (scopeJson == null || scopeJson.isEmpty()) {
			return OperateResult.s().failed("缺少scopeJson信息", "401");
		}

		UserScope scope = JsonUtil.jsonToObject(scopeJson, UserScope.class);
		if (scope == null || !scope.valid()) {
			return OperateResult.s().failed("用户数据错误", "401");
		}

		return create(scope);
	}

	/**
	 * 有登录的请求
	 * 
	 * @param scopeJson 登录信息Json
	 * @return 获取token状态和token信息
	 */
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> authorisedPost(@RequestBody UserScope userScope) {
		if (userScope == null || !userScope.valid()) {
			return OperateResult.s().failed("用户数据错误", "401");
		}

		return create(userScope);
	}

	private OperateResult<String> create(UserScope userScope) {
		
		try {

			String secretKey = jwt.getSecretKey();

			if (secretKey == null || secretKey.isEmpty()) {
				return OperateResult.s().failed("无效secretKey", "401");
			}

			String token = JwtUtil.createToken(jwt, secretKey, userScope);
			if (token.isEmpty()) {
				return OperateResult.s().failed("生成token无效", "401");
			} else {
				return OperateResult.s().success(token);
			}

		} catch (Exception e) {
			return OperateResult.s().failed(e.getMessage());
		}
	}
}
