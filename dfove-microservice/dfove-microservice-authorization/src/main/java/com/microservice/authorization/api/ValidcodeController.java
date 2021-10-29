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

import com.microservice.authorization.jwts.ValidateCodeUtil;
import com.microservice.authorization.jwts.ValidateCodeUtil.Validate;
import common.entity.OperateResult;
import common.redis.IRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/dfove/v1/validcode")
public class ValidcodeController {

	@Autowired
	private IRedis redis;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	@Deprecated
	public OperateResult<Map<String, String>> createCode(Integer size, Integer width, Integer height) {
		
		if (true) {
            return new OperateResult<Map<String, String>>().failed("该方法已过期，请调用message服务下的/dfove/v1/picture/code");
        }
		
		if (size == null || size < 4) {
            size = 4;
        }

		if (width == null || width < 80) {
            width = 80;
        }

		if (height == null || height < 34) {
            height = 34;
        }

		// 通过随机生成未完成
		String code = "";

		// 64base
		String encode = null;

		// 生成验证码和base64图片代码
		Validate v = ValidateCodeUtil.getRandomCode(size, width, height);

		if (v != null) {
			code = v.getValue();
			encode = v.getBase64Str();
		}

		// 获得随机值
		String uuid = UUID.randomUUID().toString();
		String key = String.format("dtph.validcode.%s", uuid);
		redis.set(key, code, 1800);

		Map<String, String> map = new HashMap<>();
		map.put("key", uuid);
		map.put("image", encode);

		return new OperateResult<Map<String, String>>().success(map);
	}

}
