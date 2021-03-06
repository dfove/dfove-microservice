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
package com.microservice.admin.api;

import javax.annotation.Resource;

import com.microservice.admin.entity.Role;
import com.microservice.admin.service.*;
import com.microservice.core.LogComponent;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import common.annotation.RecordLog;
import common.constant.Log;
import common.constant.RedisKey;
import common.entity.*;
import common.enums.TerminalSource;
import common.tools.JsonUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.admin.Microservices;
import com.microservice.admin.entity.Admin;
import com.microservice.core.App;
import com.microservice.core.RestTemplateUtil;
import com.microservice.core.UserAnnotation;

import common.enums.AccessType;
import common.enums.ScopeType;
import common.enums.UserStatus;
import common.redis.IRedis;
import common.tools.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "?????????????????????", tags = { "?????????????????????" })
@RestController
@RequestMapping("/dfove/v1/login")
public class LoginController {

	@Autowired
	@Qualifier("RestTemplateEureka")
	private RestTemplate restTemplate;

	@Autowired
	private Microservices microservices;

	@Resource
	private IAdminService adminService;

	@Resource
	private IRedis redis;

	@Autowired
	private LogComponent logComponent;

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IOrganizationService organizationService;

	@ApiOperation(value = "??????")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "adminName", value = "??????????????????", dataType = "string"),
			@ApiImplicitParam(paramType = "query", required = true, name = "password", value = "??????(??????)", dataType = "string"),
			@ApiImplicitParam(paramType = "query", required = true, name = "source", value = "???????????????11-PC???21-?????????31-IOS???41-?????????H5", dataType = "integer")
	})
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public OperateResult<String> login(String adminName, String password, Integer source) {

		if (adminName == null || adminName.isEmpty()) {
			return OperateResult.s().failed("?????????/??????????????????", ErrorCode.INPUT_NOT_ALLOWED);
		}
		if (source == null || !TerminalSource.check(source)) {
			return OperateResult.s().failed("??????????????????");
		}

		Admin admin = adminService.findName(adminName);
		if (admin == null ) {
			return OperateResult.s().failed("??????????????????/??????/Email??????", ErrorCode.USER_NAME_NOT_EXISTS);
		}

		if (admin.getStatus() == null || !admin.getStatus().equals(1)) {
			return OperateResult.s().failed("?????????????????????" + UserStatus.find(admin.getStatus()).getName(),
					ErrorCode.USER_STATUS_ERROR);
		}

		if (!MD5Util.valid(admin.getPassword(), password, Constants.PASSWORD_SUFFIX)) {
			return OperateResult.s().failed("???????????????", ErrorCode.PASSWORD_NOT_CORRECT);
		}

		UserScope userScope = new UserScope(ScopeType.admin.getValue(), admin.getAdminId(), admin.getAdminName(),
				admin.getRealName(), admin.getGrade(), admin.getStatus(), source);
		OperateResult<String> operateResult = this.getToken(userScope);
		if (operateResult.ok() && operateResult.getData() != null) {
			saveUserCache(admin);
			// ??????????????????
			sendLoginRecordLog(userScope, admin);
		}
		return operateResult;

	}

    private void saveUserCache(Admin admin) {
        Map<String, String> map = new HashMap<>();
        map.put(RedisKey.ADMIN, JsonUtil.objectToJson(admin));
        List<Role> roles = roleService.findByAdminId(admin.getAdminId());
        map.put(RedisKey.ROLE, JsonUtil.objectToJson(roles));
//		Organization organization = organizationService.find(admin.getOrganizationId());
//		map.put(RedisKey.ORGANIZATION, JsonUtil.objectToJson(organization));
        redis.setHash(RedisKey.getUserKey(admin.getAdminId()), map);
    }

	private void sendLoginRecordLog(UserScope userScope, Admin admin) {
		OperateLog log = logComponent.getOperateLog(userScope, Log.MODULE_ADMIN_LOGIN, Log.OPERATE_LOGIN, "??????");
		log.setAdminId(userScope.getUserId());
		log.setAdminName(userScope.getUserName());
		log.setAdminRealName(userScope.getRealName());
		log.setUserScopeType(userScope.getScopeType());
		log.setTerminalType(userScope.getSource());
		log.setAdminOrganizationId(admin.getOrganizationId());
		String serviceUrl = microservices.getValue("log", AccessType.eureka.toString());
		String url = String.format("%s/" + Log.PATH_OPERATE_ADD, serviceUrl);
		logComponent.sendOperateLog(restTemplate, url, log);
	}

	@ApiOperation(value = "??????TOKEN")
	@ApiImplicitParam(paramType = "query", required = false, name = "reload", value = "?????????????????????????????????????????????????????????????????????true", dataType = "Boolean")
	@RequestMapping(value = "/refresh", method = { RequestMethod.GET, RequestMethod.POST })
	@UserAnnotation(scopes = ScopeType.admin)
	public OperateResult<String> refresh(Boolean reload) {

		// ???token??????????????????
		UserScope userScope = App.userScope();

		// ????????????????????????????????????????????????????????????
		if (reload != null && reload) {
			Admin admin = adminService.find(userScope.getUserId());
			if (admin == null) {
                return OperateResult.s().failed("???????????????");
            }
			
			// ??????????????????
			Integer status = admin.getStatus();
			if (status == null || !status.equals(1)) {
				return OperateResult.s().failed("????????????????????????");
			}
			
			userScope.setRealName(admin.getRealName());
			userScope.setStatus(admin.getStatus());
		}
		
		return this.getToken(userScope);
	}

	private OperateResult<String> getToken(UserScope userScope) {
		String url = microservices.getValue("authorization", AccessType.eureka.toString()) + "/dfove/v1/authorised";

		OperateResult<OperateResult<String>> result = RestTemplateUtil.post(restTemplate, url, userScope, null,
				new ParameterizedTypeReference<OperateResult<String>>() {
				}, true);

		if (result.ok() && result.getData() != null) {
			return result.getData();
		} else {
			return OperateResult.s().failed(result.getMessage(), result.getCode());
		}
	}
	
	@ApiOperation(value = "test",hidden=true)
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public OperateResult<String> test(Boolean reload) {
		return getToken1(null);
	}
	private OperateResult<String> getToken1(UserScope userScope) {
		String url = microservices.getValue("authorization", AccessType.eureka.toString()) + "/dfove/v1/authorised/a";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", "ss");
		map.put("pass", "ss2");
		OperateResult<OperateResult<String>> result = RestTemplateUtil.post(restTemplate, url, map, null, new  ParameterizedTypeReference<OperateResult<String>>() {});
		
		if (result.ok() && result.getData() != null) {
			return result.getData();
		} else {
			return OperateResult.s().failed(result.getMessage(), result.getCode());
		}
	}

	@ApiOperation(value = "TOKEN??????")
	@ApiImplicitParam(paramType = "query", required = true, name = "token", value = "token", dataType = "String")
	@RequestMapping(method = RequestMethod.POST, value = "/decode")
	public OperateResult<String> decode(String token) {
		try {
			String[] temp = token.split("[.]");
			if (temp.length > 1) {
                token = temp[1];
            }
			String data = new String(Base64.decodeBase64(token), "utf-8");
			return OperateResult.s().success(data);
		} catch (Exception e) {
			return OperateResult.s().failed(e.getMessage());
		}
	}

	@ApiOperation(value = "????????????UserScope(??????swagger??????????????????????????????TOKEN)")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "userType", value = "????????????????????????????????????????????????????????????", allowableValues = "none, anonymous, user, admin, system", dataType = "String"),
			@ApiImplicitParam(paramType = "query", required = true, name = "adminId", value = "?????????Id", dataType = "String"),
			@ApiImplicitParam(paramType = "query", required = true, name = "adminName", value = "????????????", dataType = "String"),
			@ApiImplicitParam(paramType = "query", required = true, name = "realName", value = "????????????", dataType = "String"),
			@ApiImplicitParam(paramType = "query", required = true, name = "status", value = "?????? (0????????????1????????????8????????????88?????????)", allowableValues = "0, 1, 8, 88", dataType = "Integer"),
			@ApiImplicitParam(paramType = "query", required = true, name = "grade", value = "??????", dataType = "Integer") })
	@RequestMapping(method = RequestMethod.POST, value = "/test")
	public OperateResult<String> getScope(String userType, String adminId, String adminName, String realName,
			Integer status, Integer grade) {

		UserScope scope = new UserScope(ScopeType.find(userType).getValue(), adminId, adminName, realName, grade,
				status, 11);
		String json = null;
		try {
			json = java.net.URLEncoder.encode(scope.toJson(), "UTF-8");
			return OperateResult.s().success(json);
		} catch (Exception e) {
			return OperateResult.s().failed(e.getMessage(), ErrorCode.SYSTEM_ERROR);
		}
	}

	@RecordLog(module = Log.MODULE_ADMIN_LOGIN, type = Log.OPERATE_LOGOUT, content = "????????????")
	@ApiOperation(value = "???????????????????????????")
	@RequestMapping(method = RequestMethod.GET, value = "/quit")
	@UserAnnotation(scopes = ScopeType.admin)
	public OperateResult<Boolean> quit() {
		String tokenKey = App.userScope().getKey();
		if (tokenKey == null || tokenKey.isEmpty()) {
            return OperateResult.b().success(true);
        }

		tokenKey = String.format("jnyc.token.%s", tokenKey);
		Boolean result = redis.set(tokenKey, 1, 7200);
		redis.delete(RedisKey.getUserKey(App.userScope().getUserId()));

		return result ? OperateResult.b().success(true) : OperateResult.b().failed("????????????");
	}
}
