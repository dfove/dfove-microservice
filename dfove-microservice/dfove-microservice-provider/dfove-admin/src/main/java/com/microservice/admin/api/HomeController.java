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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.OperateResult;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore("true")
@Controller
@RequestMapping("/")
public class HomeController {

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "OK";
	}
	
	@ResponseBody
	@RequestMapping(value = "/refuse")
	public OperateResult<?> refuse(String message, String code) {
		return new OperateResult<Object>().failed(message, code);
	}
	
}
