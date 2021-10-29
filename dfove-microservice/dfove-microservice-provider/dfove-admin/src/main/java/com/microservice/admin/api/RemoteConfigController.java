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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;

@Api(value = "刷新配置中心接口", tags = { "刷新配置中心接口" })
@RestController
@RequestMapping("/dfove/v1/remote/config")
public class RemoteConfigController {

	@Autowired
	@Qualifier("RestTemplate")
	private RestTemplate restTemplate;

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public Boolean refresh(String url) {

		String[] temp = url.split("\\?");
		url = temp[0].toLowerCase();
		if (!url.endsWith("actuator/refresh")) {
            url += "/actuator/refresh";
        }
		url = url.replace("//actuator/refresh", "/actuator/refresh");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
		System.out.print(result);

		return true;
	}
}
