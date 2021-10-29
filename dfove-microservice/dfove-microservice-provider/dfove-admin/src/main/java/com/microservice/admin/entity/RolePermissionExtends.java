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
package com.microservice.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RolePermissionExtends", description = "角色权限扩展")
public class RolePermissionExtends {

	@ApiModelProperty(value = "角色权限ID")
	private String rolePermissionId;

	@ApiModelProperty(value = "角色ID")
	private String roleId;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "权限ID")
	private String permissionId;

	@ApiModelProperty(value = "权限名称")
	private String permissionName;

	@ApiModelProperty(value = "Api地址")
	private String apiUrl;

	@ApiModelProperty(value = "post 权限")
	private Integer postPermission;

	@ApiModelProperty(value = "delete 权限")
	private Integer deletePermission;

	@ApiModelProperty(value = "put 权限")
	private Integer putPermission;

	@ApiModelProperty(value = "get 权限")
	private Integer getPermission;

	@ApiModelProperty(value = "菜单Id")
	private String menuId;
	
	@ApiModelProperty(value = "菜单类型")
	private Integer menuType;

	@ApiModelProperty(value = "1-默认值，0-非默认值")
	private Integer restore;

	/**
	 * 无参角色权限信息
	 */
	public RolePermissionExtends() {

	}

	/**
	 * 有参角色权限信息
	 */
	public RolePermissionExtends(String roleId, String permissionId, Integer postPermission, Integer deletePermission, Integer putPermission, Integer getPermission) {
		this.setRoleId(roleId);
		this.setPermissionId(permissionId);
		this.setPostPermission(postPermission);
		this.setDeletePermission(deletePermission);
		this.setPutPermission(putPermission);
		this.setGetPermission(getPermission);
	}

	/**
	 * 获取角色权限ID
	 */
	public String getRolePermissionId() {
		return this.rolePermissionId;
	}

	/**
	 * 设置角色权限ID
	 */
	public void setRolePermissionId(String rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
	}

	/**
	 * 获取角色ID
	 */
	public String getRoleId() {
		return this.roleId;
	}

	/**
	 * 设置角色ID
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取角色名称
	 */
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * 设置角色名称
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取权限ID
	 */
	public String getPermissionId() {
		return this.permissionId;
	}

	/**
	 * 设置权限ID
	 */
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * 获取权限名称
	 */
	public String getPermissionName() {
		return this.permissionName;
	}

	/**
	 * 设置权限名称
	 */
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	/**
	 * 获取Api地址
	 */
	public String getApiUrl() {
		return this.apiUrl;
	}

	/**
	 * 设置Api地址
	 */
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	/**
	 * 获取post 权限
	 */
	public Integer getPostPermission() {
		return this.postPermission;
	}

	/**
	 * 设置post 权限
	 */
	public void setPostPermission(Integer postPermission) {
		this.postPermission = postPermission;
	}

	/**
	 * 获取delete 权限
	 */
	public Integer getDeletePermission() {
		return this.deletePermission;
	}

	/**
	 * 设置delete 权限
	 */
	public void setDeletePermission(Integer deletePermission) {
		this.deletePermission = deletePermission;
	}

	/**
	 * 获取put 权限
	 */
	public Integer getPutPermission() {
		return this.putPermission;
	}

	/**
	 * 设置put 权限
	 */
	public void setPutPermission(Integer putPermission) {
		this.putPermission = putPermission;
	}

	/**
	 * 获取get 权限
	 */
	public Integer getGetPermission() {
		return this.getPermission;
	}

	/**
	 * 设置get 权限
	 */
	public void setGetPermission(Integer getPermission) {
		this.getPermission = getPermission;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public Integer getRestore() {
		return restore;
	}

	public void setRestore(Integer restore) {
		this.restore = restore;
	}
}
