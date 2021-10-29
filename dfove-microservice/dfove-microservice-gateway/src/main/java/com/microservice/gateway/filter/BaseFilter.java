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

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import common.entity.OperateResult;
import common.logs.Logs;
import common.tools.JsonUtil;
import reactor.core.publisher.Mono;

public abstract class BaseFilter implements GlobalFilter, Ordered {

	private Logs _log = null;

	public BaseFilter(Logs _log) {
		this._log = _log;
	}

	public Mono<Void> accessFailed(ServerWebExchange exchange, String message) {

		return this.accessFailed(exchange, message, "401");
	}

	public Mono<Void> accessFailed(ServerWebExchange exchange, String message, String code) {
		return this.accessFailed(exchange, message, code, HttpStatus.UNAUTHORIZED);
	}

	public Mono<Void> accessFailed(ServerWebExchange exchange, String message, String code, HttpStatus status) {

		_log.error(message, code);

		OperateResult<?> result = OperateResult.s().failed(message, code);
		String json = JsonUtil.objectToJson(result);
		byte[] output = json.getBytes(StandardCharsets.UTF_8);

		ServerHttpResponse response = exchange.getResponse();
		DataBuffer buffer = response.bufferFactory().wrap(output);

		response.setStatusCode(status);
		response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		try {
			response.getHeaders().add("error", java.net.URLEncoder.encode(json, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		}
		
		return response.writeWith(Mono.just(buffer));
	}

}
