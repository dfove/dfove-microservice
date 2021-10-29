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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import common.enums.ScopeType;

/**
 * 用户访问权限控制
 */
public class UserScope {

	// 本次token的唯一标识
	private String key;

	// 用户类型
	private int scopeType;

	// 用户Id
	private String userId;

	// 用户名
	private String userName;

	// 用户真实姓名
	private String realName;

	// 用户状态
	private int status;

	// 访问来源
	private int source;

	// 级别
	private int grade;

	public UserScope() {
		this.setKey(UUID.randomUUID().toString());
	}

	public UserScope(int scopeType, String userId, String userName, String realName, int grade, int status, int source) {

		this.setKey(UUID.randomUUID().toString());

		this.setScopeType(scopeType);

		this.setUserId(userId);
		this.setUserName(userName);
		this.setRealName(realName);

		this.setGrade(grade);
		this.setStatus(status);
		this.setSource(source);
	}

	public UserScope(Map<String, Object> map) {

		this.key = getFromMap(map, "key", "");

		this.scopeType = getFromMap(map, "scopeType", 0);

		this.userId = getFromMap(map, "userId", "");
		this.userName = getFromMap(map, "userName", "");
		this.realName = getFromMap(map, "realName", "");

		this.grade = getFromMap(map, "grade", 0);
		this.status = getFromMap(map, "status", 0);
		this.source = getFromMap(map, "source", 11);
	}

	/**
	 * 设置 本次token的唯一标志
	 * 
	 * @param key 本次token的唯一标志
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取 本次token的唯一标志
	 * 
	 * @return 本次token的唯一标志
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * 设置 角色
	 * 
	 * @param scopeType 角色，决定能访问哪些资源
	 */
	public void setScopeType(int scopeType) {
		this.scopeType = scopeType;
	}

	/**
	 * 获得 角色
	 * 
	 * @return 角色，决定能访问哪些资源
	 */
	public int getScopeType() {
		return this.scopeType;
	}

	/**
	 * 设置 用户ID
	 * 
	 * @param userId 用户ID，系统唯一标识
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获得 用户ID
	 * 
	 * @return 用户ID，系统唯一标识
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 设置 用户名
	 * 
	 * @param userName 用户名，系统唯一标识
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获得 用户名
	 * 
	 * @return 用户名，系统唯一标识
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 设置 用户姓名
	 * 
	 * @param realName 用户真实姓名，可以为企业
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return realName;
	}

	/**
	 * 获得 用户级别
	 * 
	 * @return 用户级别
	 */
	public int getGrade() {
		return this.grade;
	}

	/**
	 * 设置 用户级别
	 * 
	 * @param  grade 用户级别
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * 获得 用户状态
	 * 
	 * @return 用户状态
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * 设置 用户状态
	 * 
	 * @param status 用户状态
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 获得 访问来源
	 * 
	 * @return 访问来源
	 */
	public int getSource() {
		return this.source;
	}

	/**
	 * 设置 访问来源
	 * 
	 * @param source 访问来源
	 */
	public void setSource(int source) {
		this.source = source;
	}

	@SuppressWarnings("unchecked")
	private <T> T getFromMap(Map<?, ?> map, String key, T value) {
		if (map.containsKey(key)) {
            return (T) map.get(key);
        }
		return value;
	}

	/**
	 * 转换为map
	 * 
	 * @return map 键值对
	 */
	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("key", this.key);

		map.put("scopeType", this.scopeType);

		map.put("userId", this.userId);
		map.put("userName", this.userName);
		map.put("realName", this.realName);

		map.put("grade", this.grade);
		map.put("status", this.status);
		map.put("source", this.source);

		return map;
	}

	/**
	 * 是否有效
	 */
	public Boolean valid() {
		return ScopeType.find(scopeType) != null  && userName != null && !userName.isEmpty();
	}

	/**
	 * 是否匿名
	 */
	public Boolean anonymous() {
		return this.getScopeType() == ScopeType.anonymous.getValue();
	}

	/**
	 * 是否登录
	 */
	public Boolean login() {
		return this.getUserId() !=null && !anonymous();
	}

	/**
	 * 是否本人，不涉及交易
	 */
	public Boolean owner(String userId, ScopeType scopeType) {
		return  this.getUserId().equals(userId)  && scopeType.getValue() == this.getScopeType();
	}

	/**
	 * 是否包括对应的权限
	 */
	public Boolean contains(ScopeType... scopeTypes) {
		if (scopeTypes == null || scopeTypes.length == 0) {
            return true;
        }

		for (ScopeType scope : scopeTypes) {
			if (scope.getValue() == this.getScopeType()) {
				return true;
			}
		}

		return false;
	}

	public static UserScope system() {
		return new UserScope(ScopeType.system.getValue(),  "1", "system", "系统", 0, 0, 11);
	}


	/**
	 * 转换为Json
	 * 
	 * @return Json格式
	 */
	public String toJson() {
		return common.tools.JsonUtil.objectToJson(this);
	}
}
