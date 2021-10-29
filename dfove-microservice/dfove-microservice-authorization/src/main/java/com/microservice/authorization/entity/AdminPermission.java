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
package com.microservice.authorization.entity;

import common.tools.StringUtil;

public class AdminPermission {

	private String adminId;

	private String adminName;

	private String realName;

	private String permissionId;

	private String permissionName;

	private String apiUrl;

	private Integer postPermission;

	private Integer deletePermission;

	private Integer putPermission;

	private Integer getPermission;

	/**
	 * 获取管理员Id
	 * 
	 * @return 管理员Id，Integer
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * 设置管理员Id
	 * 
	 * @param adminId 管理员Id
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * 获取管理员登录名
	 * 
	 * @return 管理员登录名，String
	 */
	public String getAdminName() {
		return this.adminName;
	}

	/**
	 * 设置管理员登录名
	 * 
	 * @param adminName 管理员登录名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * 获取管理员真实姓名
	 * 
	 * @return 管理员真实姓名，String
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * 设置管理员真实姓名
	 * 
	 * @param realName 管理员真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取接口Id
	 * 
	 * @return 接口Id，Integer
	 */
	public String getPermissionId() {
		return this.permissionId;
	}

	/**
	 * 设置接口Id
	 * 
	 * @param permissionId 接口Id
	 */
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * 获取接口名称
	 * 
	 * @return 接口名称，String
	 */
	public String getPermissionName() {
		return this.permissionName;
	}

	/**
	 * 设置接口名称
	 * 
	 * @param permissionName 接口名称
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * 获取接口地址
	 * 
	 * @return 接口地址，String
	 */
	public String getApiUrl() {
		return this.apiUrl;
	}

	/**
	 * 设置接口地址
	 * 
	 * @param apiUrl 接口地址
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 * 获取添加的权限
	 * 
	 * @return 添加的权限，Integer
	 */
	public Integer getPostPermission() {
		return this.postPermission;
	}

	/**
	 * 设置添加的权限
	 * 
	 * @param postPermission 添加的权限
	 */
	public void setPostPermission(Integer postPermission) {
		this.postPermission = postPermission;
	}

	/**
	 * 获取删除的权限
	 * 
	 * @return 删除的权限，Integer
	 */
	public Integer getDeletePermission() {
		return this.deletePermission;
	}

	/**
	 * 设置删除的权限
	 * 
	 * @param deletePermission 删除的权限
	 */
	public void setDeletePermission(Integer deletePermission) {
		this.deletePermission = deletePermission;
	}

	/**
	 * 获取修改的权限
	 * 
	 * @return 修改的权限，Integer
	 */
	public Integer getPutPermission() {
		return this.putPermission;
	}

	/**
	 * 设置修改的权限
	 * 
	 * @param putPermission 修改的权限
	 */
	public void setPutPermission(Integer putPermission) {
		this.putPermission = putPermission;
	}

	/**
	 * 获取查询的权限
	 * 
	 * @return 查询的权限，Integer
	 */
	public Integer getGetPermission() {
		return this.getPermission;
	}

	/**
	 * 设置查询的权限
	 * 
	 * @param getPermission 查询的权限
	 */
	public void setGetPermission(Integer getPermission) {
		this.getPermission = getPermission;
	}

	public Boolean validApi(String api) {
		return StringUtil.match(this.getApiUrl(), api);
	}

	public Boolean validMethod(String method) {

		int permission = 0;

		switch (method.toLowerCase()) {
		case "post":
			permission = this.getPostPermission();
			break;
		case "delete":
			permission = this.getDeletePermission();
			break;
		case "put":
			permission = this.getPutPermission();
			break;
		case "get":
			permission = this.getGetPermission();
			break;
		default:
			break;
		}

		return permission == 1;
	}
}
