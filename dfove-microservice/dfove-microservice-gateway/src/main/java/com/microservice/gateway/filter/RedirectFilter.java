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
package com.microservice.gateway.filter;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class RedirectFilter implements GlobalFilter, Ordered {

	private static Logger _log = LoggerFactory.getLogger(RedirectFilter.class);

	@Override
	public int getOrder() {
		return 10101;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		_log.error("=============RedirectFilter===============");

		try {
			// Filter中往下一个Filter中传递数据实用下面的方式：
			exchange.getAttributes().put("GATEWAY_REQUEST_URL_ATTR",
					new URI("http://192.168.31.245:8081/house/hello2"));
			// 获取方直接获取：
			// URI requestUrl = exchange.getRequiredAttribute("GATEWAY_REQUEST_URL_ATTR");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return chain.filter(exchange);
	}

}