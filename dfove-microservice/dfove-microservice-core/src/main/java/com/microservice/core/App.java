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
package com.microservice.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.enums.ScopeType;
import common.tools.JsonUtil;

public class App implements HandlerInterceptor {

	private static final ThreadLocal<UserScope> USER_SCOPE = new ThreadLocal<>();
	private static final ThreadLocal<String> IP = new ThreadLocal<>();

	public App() {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		this.saveIp(request);

		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)){
			return true;
		}
		UserAnnotation userIntercept = ((HandlerMethod) handler).getMethodAnnotation(UserAnnotation.class);
		if (userIntercept != null) {
			return handle(userIntercept, request, response);
		}

		Class<?> clazz = ((HandlerMethod) handler).getBeanType();
		userIntercept = clazz.getAnnotation(UserAnnotation.class);
		if (userIntercept != null) {
			return handle(userIntercept, request, response);
		}

		return true;

	}

	private boolean handle(UserAnnotation userIntercept, HttpServletRequest request, HttpServletResponse response) {

		// 获取不到注解
		if (userIntercept == null){
			return refused(response);
		}
		// 登录用户
		UserScope userScope = null;
		if (request.getHeader("UserScope") != null) {
			try {
				String s = request.getHeader("UserScope");
				String json = java.net.URLDecoder.decode(s, "UTF-8");
				if (json != null && !json.isEmpty()) {
					userScope = JsonUtil.jsonToObject(json, UserScope.class);
					if (userScope != null) {
						USER_SCOPE.set(userScope);
					}
				}
			} catch (Exception e) {
				return refused(response, e.getMessage(), ErrorCode.SYSTEM_ERROR);
			}
		}

		ScopeType[] scopes = userIntercept.scopes();
		if (scopes != null && scopes.length > 0) {
			if (userScope == null || !userScope.contains(scopes)){
				return refused(response, "无权限访问该资源，超出权限范围", ErrorCode.EXCEED_AUTHORITY);
			}
		}

		if (userIntercept.MinGrade() > -1) {
			if (userScope == null || userScope.getGrade() < userIntercept.MinGrade()) {
				return refused(response, "无权限访问该资源，超出权限范围", ErrorCode.EXCEED_AUTHORITY);
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
		USER_SCOPE.remove();
		IP.remove();
	}

	/**
	 * 获取线程变量中登录用户信息
	 * 
	 * @return
	 */
	public static UserScope userScope() {
		return USER_SCOPE.get();
	}

	/**
	 * 判断是否管理员
	 * 
	 * @return
	 */
	public static Boolean isAdmin() {
		return isUserType(ScopeType.admin.getValue());
	}

	/**
	 * 判断是否系统
	 * 
	 * @return
	 */
	public static Boolean isSystem() {
		return isUserType(ScopeType.system.getValue());
	}

	/**
	 * 判断是否某种用户类型
	 * 
	 * @return
	 */
	private static Boolean isUserType(int userType) {
		UserScope userScope = userScope();
		if (userScope == null){
			return false;
		}
		return userScope.getScopeType() == (userType);
	}

	/**
	 * 获取线程变量中用户信息
	 * 
	 * @return
	 */
	public static String ip() {
		return IP.get();
	}

	private static boolean refused(HttpServletResponse response) {
		return refused(response, "未知错误", ErrorCode.UNKNOWN_ERROR);
	}

	private static boolean refused(HttpServletResponse response, String message, String code) {
		try {

			String location = String.format("/refuse?message=%s&code=%s", java.net.URLEncoder.encode(message, "UTF-8"),
					code);
			response.sendRedirect(location);
			// response.setHeader("content-type", "text/html;charset=UTF-8");
			// OutputStream out = response.getOutputStream();
			// OperateResult<?> result = new OperateResult<Object>().failed(message, code);
			// byte[] body=JsonUtil.objectToByte(result);
			// out.write(body);
		} catch (Exception e) {

		}

		return false;
	}

	/**
	 * 是否访问者本人，不涉及交易，默认是用户角色，如果是管理员角色需要指定ScopeType.admin
	 * 
	 * @param userId
	 * @return
	 */
	public static boolean isOwner(String userId) {
		return isOwner(userId, ScopeType.admin);
	}

	public static boolean isOwner(String userId, ScopeType scopeType) {
		UserScope scope = userScope();
		return (scope != null && scope.owner(userId, scopeType));
	}

	public static boolean isOwnerOrGrater(String adminId, int i) {
		UserScope scope = userScope();
		if (scope == null){
			return false;
		}
		return scope.owner(adminId, ScopeType.admin) || scope.getGrade() > i;
	}
	
	/**
	 * 是否更高级别
	 * 
	 * @return
	 */
	public static boolean isGrater(Integer grade) {
		UserScope scope = userScope();
		return (scope != null && scope.getGrade() > grade);
	}

	public static boolean isScopes(ScopeType... scopeTypes) {
		UserScope scope = userScope();
		return (scope != null && scope.contains(scopeTypes));
	}

	public static <T> OperateResult<T> loginRequired() {
		return refused("请登录", ErrorCode.LOGIN_REQUIRED);
	}

	public static <T> OperateResult<T> refused() {
		return refused("无权限访问该资源，或超出权限范围", ErrorCode.EXCEED_AUTHORITY);
	}

	public static <T> OperateResult<T> refused(String message, String code) {
		return new OperateResult<T>().failed(message, code);
	}

	private void saveIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		IP.set(ip);
	}

	public static String systemScope() {

		UserScope scope = new UserScope(ScopeType.system.getValue(),  "1", "system", "系统", 9999, 0, 0);
		String json = null;
		try {
			json = scope.toJson();
			return json;
		} catch (Exception e) {
			return null;
		}
	}
}