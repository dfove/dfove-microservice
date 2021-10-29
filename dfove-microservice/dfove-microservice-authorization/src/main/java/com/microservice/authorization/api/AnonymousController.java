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
import common.enums.TerminalSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/dfove/v1/anonymous")
public class AnonymousController {

	@Autowired
	private Jwt jwt;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public OperateResult<String> anonymous(String anonymous, String password, Integer source) {

		// 匿名用户名
		if (anonymous == null || anonymous.isEmpty() || password == null || password.isEmpty()) {
			return OperateResult.s().failed("缺少anonymous或password信息", "401");
		}

		if (!anonymous.equals(jwt.getAnonymous()) || !password.equals(jwt.getPassword())) {
			return OperateResult.s().failed("输入anonymous或password信息有误", "401");
		}

		String secretKey = jwt.getSecretKey();
		if (secretKey == null || secretKey.isEmpty()) {
			return OperateResult.s().failed("无效secretKey", "401");
		}

		if (source == null){
			source = TerminalSource.PC.getValue();
		}
		UserScope scope = new UserScope(1, "0", "", "",  0, 0, source);

		try {
			String token = JwtUtil.createToken(jwt, secretKey, scope);
			if (token.isEmpty()) {
				return OperateResult.s().failed("生成token出错", "401");
			} else {
				return OperateResult.s().success(token, "200");
			}

		} catch (Exception e) {
			return OperateResult.s().failed(e.getMessage());
		}
	}
}
