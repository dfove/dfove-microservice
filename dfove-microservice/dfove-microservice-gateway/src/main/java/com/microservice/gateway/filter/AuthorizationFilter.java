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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest.Builder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import com.microservice.gateway.Gateways;
import com.microservice.gateway.Service;

import common.entity.Constants;
import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.enums.ScopeType;
import common.logs.Logs;
import common.tools.MD5Util;
import reactor.core.publisher.Mono;

/**
 * 过滤器 在这里可以实现记录日志和访问权限校验等
 * 
 * @author Administrator
 *
 */

@Configuration
public class AuthorizationFilter extends BaseFilter {

	@Autowired
	@Qualifier("RestTemplateEureka")
	private RestTemplate restTemplate;

	@Autowired
	private Gateways gateways;

	private static Logs _log = new Logs(IpFilter.class);

	private final static String BEARER = Constants.BEARER;
	private final static String AUTHORIZATION = Constants.AUTHORIZATION;
	private final static String WEBSOCKET = Constants.WEB_SOCKET;

	private final static String NONE = Constants.NONE;
	private final static int ORDER = 2;

	public AuthorizationFilter() {
		super(_log);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		ServerHttpRequest request = exchange.getRequest();

		String fullUrl = request.getPath().pathWithinApplication().value();
		if (fullUrl == null || fullUrl.isEmpty() || "/".equals(fullUrl)) {
			return chain.filter(exchange);
		}

		String url = (fullUrl.split("\\?"))[0].toLowerCase();

		String method = request.getMethod().name().toLowerCase();
		// exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR)

		// 允许直接访问的地址不过滤
		List<Service> none = gateways.getList(NONE);
		if (none == null || none.isEmpty() || none.size() == 0) {
			return accessFailed(exchange, "没有可匿名访问的资源", ErrorCode.NO_RESOURCES);
		}
		for (Service s : none) {
			if (s.validUrl(url)) {
				if (s.validMethod(method)) {
					return chain.filter(exchange);
				}
				break;
			}
		}

		_log.info("Jwt服务鉴权开始");

		try {

			// 获取用户token
			Boolean websocket = false;
			String token = request.getHeaders().getFirst(AUTHORIZATION);
			if ((token == null || token.isEmpty()) && url.indexOf("endpoint") > -1) {
				token = websokectToken(request);
				websocket = true;
			}

			// token不存在、不正确、篡改
			if (token == null || !token.startsWith(BEARER)) {
				return accessFailed(exchange, "Jwt服务鉴权失败：token不存在、不正确、篡改", ErrorCode.TOKEN_NOT_CORRECT);
			}
			String jwt = token.substring(BEARER.length());

			// 验证token
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("token", jwt);
			map.add("api", url);
			map.add("method", method);
			HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(map,
					null);
			String checkUrl = String.format("%s/check", gateways.getAuthorization());
			OperateResult<UserScope> result = restTemplate.exchange(checkUrl, HttpMethod.POST, httpEntity,
					new ParameterizedTypeReference<OperateResult<UserScope>>() {
					}).getBody();

			if (!result.ok()) {
				return accessFailed(exchange, result.getMessage(), result.getCode());
			}

			// 验证角色权限
			UserScope userScope = result.getData();
			int scopeType = userScope.getScopeType();
			String code = scopeType == ScopeType.anonymous.getValue() ? ErrorCode.LOGIN_REQUIRED
					: ErrorCode.EXCEED_AUTHORITY;

			if (scopeType != ScopeType.system.getValue()) {
				List<Service> resource = gateways.getList(ScopeType.find(scopeType).name());
				if (resource == null || resource.isEmpty() || resource.size() < 1) {
					return accessFailed(exchange, "无匹配的资源可以访问", ErrorCode.NO_RESOURCES);
				}
				Boolean ok = false;
				for (Service s : resource) {
					if (s.validUrl(url)) {
						if (s.validMethod(method)) {
							ok = true;
						}
						break;
					}
				}
				if (!ok) {
					return accessFailed(exchange, "权限不足，无法访问", code);
				}
			}

			Builder builder = exchange.getRequest().mutate();
			if (websocket) {
				String customerId = MD5Util.stringToMD5(String.valueOf(userScope.getUserId()),
						Constants.PASSWORD_SUFFIX);
				String newUrl = String.format("%s/%s", url, customerId);
				builder.uri(new URI(newUrl));
				// builder.header(_websocket, _bearer + token);
			} else {
				builder.header(AUTHORIZATION, BEARER + token).header("UserScope",
						java.net.URLEncoder.encode(userScope.toJson(), "UTF-8"));
			}

			ServerWebExchange newExchange = exchange.mutate().request(builder.build()).build();
			return chain.filter(newExchange);

		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
			return accessFailed(exchange, "Jwt服务鉴权失败", ErrorCode.SYSTEM_ERROR);
		}
	}

	@Override
	public int getOrder() {
		return ORDER;
	}

	private String websokectToken(ServerHttpRequest request) {

		String[] temp = request.getURI().toString().split("token=");
		if (temp.length != 2){
			return null;
		}
		String token = temp[1]; // request.getHeaders().getFirst(_websocket);
		if (token != null){
			token = token.replaceFirst("%20", " ");
		}
		return token;
	}
}
