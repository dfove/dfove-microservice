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

import java.net.InetSocketAddress;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import common.logs.Logs;
import reactor.core.publisher.Mono;

/**
 * Ip白名单过滤 优先级 1
 * 
 * @author Administrator
 *
 */
public class IpFilter extends BaseFilter {

	private final static int ORDER = 4;

	private static Logs _log = new Logs(IpFilter.class);

	public IpFilter() {
		super(_log);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		if (!ipWhiteCheck() && !ipBlockCheck()) {
            return chain.filter(exchange);
        }

		try {
			ServerHttpRequest request = exchange.getRequest();
			InetSocketAddress address = request.getRemoteAddress();

			String ip = address.toString();
			if (ip == null || ip.isEmpty()) {
				return accessFailed(exchange, "没有Ip信息");
			}

			ip = ip.replaceAll("/", "").split(":")[0];

			_log.info("来访Ip：", ip);

			if (ipWhiteCheck()) {
				_log.info("Ip白名单过滤开始");
				if (!ipWhiteList().contains(ip)) {
					return accessFailed(exchange, "请求不在白名单中");
				}
			}

			if (ipBlockCheck()) {
				_log.info("Ip黑名单过滤开始");
				if (ipBlockList().contains(ip)) {
					return accessFailed(exchange, "请求在黑名单中");
				}
			}
		} catch (Exception e) {
			return accessFailed(exchange, "Ip过滤失败：" + e.getMessage());
		}

		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return ORDER;
	}

	private Boolean ipWhiteCheck() {
		//return App.microservice().get("ip.white.check").toString().equals("1");
		return false;
	}

	private List<String> ipWhiteList() {
		//String s = App.microservice().get("ip.white.list").toString();
		//return StringUtil.stringToListString(s);
		return null;
	}

	private Boolean ipBlockCheck() {
		//return App.microservice().get("ip.block.check").toString().equals("1");
		return false;
	}

	private List<String> ipBlockList() {
		//String s = App.microservice().get("ip.block.list").toString();
		//return StringUtil.stringToListString(s);
		return null;
	}
}