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
 * 全系统的错误代码
 */
public class ErrorCode {
	
	/**
	 * 未知错误
	 */
	public static final String UNKNOWN_ERROR ="1000";
	
	/**
	 * 系统错误
	 */
	public static final String SYSTEM_ERROR ="1001";
	
	/**
	 * 对象不存在
	 */
	public static final String OBJECT_NOT_EXISTS ="1002";
	
	/**
	 * 资源/类型不明确
	 */
	public static final String RESOURCE_AMBIGUITY ="1003";
	
	/**
	 * 输入不合法
	 */
	public static final String INPUT_NOT_ALLOWED ="1004";
	
	/**
	 * 对象已存在，重复
	 */
	public static final String OBJECT_EXISTS ="1005";
	
	/**
	 * 对象不可用
	 */
	public static final String OBJECT_INVALID ="1006";
	
	/**
	 * 重复操作
	 */
	public static final String REPETITIVE_OPERATION ="1007";
	
	
	/**
	 *  无匹配的资源可以访问
	 */
	public static final String NO_RESOURCES ="4001";
	
	/**
	 * 需要登录
	 */
	public static final String LOGIN_REQUIRED ="4002";
	
	/**
	 * 无权限访问该资源，超出权限范围
	 */
	public static final String EXCEED_AUTHORITY ="4003";
	
	/**
	 * token不存在、不正确、篡改
	 */
	public static final String TOKEN_NOT_CORRECT ="4004";
	
	/**
	 * token超时
	 */
	public static final String TOKEN_TIMEOUT ="4005";
	
	/**
	 * 用户不存在
	 */
	public static final String USER_NOT_EXISTS ="8000";
	
	/**
	 * 用户名不存在
	 */
	public static final String USER_NAME_NOT_EXISTS ="8001";
	
	/**
	 * 密码不正确
	 */
	public static final String PASSWORD_NOT_CORRECT ="8002";
	
	/**
	 * 无法获取用户信息
	 */
	public static final String USER_SCOPE_MISSED ="8003";
	
	/**
	 *  用户名或手机号码或Email已存在
	 */
	public static final String USER_NAME_EXISTS ="8011";
	
	/**
	 *  用户名已修改过
	 */
	public static final String USER_NAME_MODIFIED ="8012";
	
	/**
	 *  短信验证码不正确
	 */
	public static final String MOBILE_CODE_NOT_CORRECT ="9001";
	
	/**
	 *  用户状态异常
	 */
	public static final String USER_STATUS_ERROR ="9002";
	
	/**
	 *  推荐人已修改过
	 */
	public static final String BROKER_MODIFIED ="9003";
	
	/**
	 *  资金账户不存在
	 */
	public static final String ACCOUNT_NOT_EXIST ="10001";
	
	/**
	 *  资金账户已存在
	 */
	public static final String ACCOUNT_EXIST ="10002";
	
	/**
	 *  可用余额不足
	 */
	public static final String BALANCE_NOT_ENOUGH ="10003";
	
	/**
	 *  插入请求记录失败
	 */
	public static final String INSERT_REQUEST_FAILED = "20001";
	
	
	
}
