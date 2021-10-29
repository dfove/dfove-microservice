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
package com.microservice.websocket;

import com.microservice.core.LogComponent;
import common.constant.Log;
import common.enums.AccessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import common.entity.ErrorCode;
import common.entity.OperateResult;

@ControllerAdvice
public class ExceptionConfig {

	@Autowired
	private LogComponent logComponent;
	@Autowired
	private Microservices microservices;
	@Qualifier("RestTemplateEureka")
	@Autowired
	private RestTemplate restTemplate;

	@ExceptionHandler(value = { Exception.class })
	@ResponseBody
	public OperateResult<Object> handleException(final Exception ex, final WebRequest req) {
		String message = "系统错误";
		if (ex != null && ex.getMessage() != null && !ex.getMessage().isEmpty()) {
			message = ex.getMessage();
		}
		sendSystemLog(ex);
		return new OperateResult<Object>().failed(message, ErrorCode.SYSTEM_ERROR);
	}

	private void sendSystemLog(Exception ex) {
		if (ex == null) {
			return;
		}
		String content = null;
		if (ex.getStackTrace() != null && ex.getStackTrace().length > 0 && ex.getStackTrace()[0] != null) {
			content = ex.getStackTrace()[0].toString() + " : ";
		}
		content = content + ex.toString();
		String serviceUrl = microservices.getValue("log", AccessType.eureka.toString());
		String url = String.format("%s/" + Log.PATH_SYSTEM_ADD, serviceUrl);
		logComponent.sendSystemLog(restTemplate, url, Log.LEVEL_ERROR, content);
	}

}