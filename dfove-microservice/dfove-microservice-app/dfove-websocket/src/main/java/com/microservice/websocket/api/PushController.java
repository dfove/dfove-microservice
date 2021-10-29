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
package com.microservice.websocket.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.websocket.WebSocketServer;

import common.entity.Constants;
import common.entity.OperateResult;
import common.tools.JsonUtil;
import common.tools.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "Websokect接口", tags = { "Websokect接口" })
@RestController
@RequestMapping("/dfove/v1/push")
public class PushController {

	@ApiOperation(value = "推送信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "consumerId", value = "客户标识", dataType = "String"),
			@ApiImplicitParam(paramType = "body", name = "message", value = "消息，Json形式，前端解析和判断", dataType = "Map") })
	@RequestMapping(value = "/{consumerId}", method = RequestMethod.POST)
	public OperateResult<Boolean> push(@PathVariable String consumerId, @RequestBody Map<String, Object> message) {
		try {
			String m = JsonUtil.objectToJson(message); 
			WebSocketServer.sendInfo(m, consumerId);
			return OperateResult.b().success(true);
		} catch (Exception e) {
			e.printStackTrace();
			return OperateResult.b().failed(e.getMessage());
		}
	}

	@ApiOperation(value = "获得用户标识(仅作测试)")
	@ApiImplicitParam(paramType = "path", name = "adminId", value = "用户ID", dataType = "Long")
	@RequestMapping(value = "/{adminId}", method = RequestMethod.GET)
	public OperateResult<String> push(@PathVariable Long adminId) {
		String customerId = MD5Util.stringToMD5(adminId.toString(), Constants.PASSWORD_SUFFIX);
		return OperateResult.s().success(customerId);
	}
}