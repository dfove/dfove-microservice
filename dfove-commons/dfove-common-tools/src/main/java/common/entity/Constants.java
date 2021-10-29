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
package common.entity;

/**
 * 常量 
 * @author YangTianhua
 *
 */
public class Constants {
	
	/**
	 * 匿名且无token
	 */
	public final static String NONE = "none";
	
	/**
	 * 匿名token
	 */
	public final static String ANONYMOUS = "anonymous";
	
	/**
	 * 登录用户
	 */
	public final static String USER = "user";
	
	/**
	 * 管理员
	 */
	public final static String ADMIN = "admin";
	
	/**
	 * 系统，最高权限，可以访问任何资源
	 */
	public final static String SYSTEM = "system";
	
	/**
	 * token默认前缀
	 */
	public final static String BEARER = "Bearer ";
	
	/**
	 * header携带token的名称
	 */
	public final static String AUTHORIZATION = "Authorization";
	
	/**
	 * websocket子协议
	 */
	public static final String WEB_SOCKET = null;
	
	/**
	 * header携带token的名称
	 */
	public final static String USER_SCOPE = "UserScope";
	
	/**
	 * header携带token的名称
	 */
	public final static String TARGET_SCOPE = "TargetScope";
	
	/**
	 * 全系统密码后缀
	 */
	public final static String PASSWORD_SUFFIX = "E5562F6C2BDE3E56";
	
	/**
	 * 只保留日期
	 */
	public final static String DAY_ONLY = "yyyy-MM-dd";
	
	/**
	 * 短时间格式
	 */
	public final static String SHORT_TIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 长时间格式
	 */
	public final static String LONG_TIME = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * 日期的整数格式
	 */
	public final static String DAY_INTEGER = "yyyyMMdd";
	
	/**
	 * 短时间的整数格式
	 */
	public final static String SHORT_TIME_INTEGER = "yyyyMMddHHmmss";
	
	/**
	 * 长时间的整数格式
	 */
	public final static String LONG_TIME_INTEGER = "yyyyMMddHHmmssSSS";
}
