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

import common.search.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Permission", description = "权限信息")
public class Permission extends BaseEntity<String> {

	@ApiModelProperty(value = "权限ID，添加时忽略")
	private String permissionId;

	@ApiModelProperty(value = "权限名称",required=true)
	private String permissionName;

	@ApiModelProperty(value = "Api地址",required=true)
	private String apiUrl;

	@ApiModelProperty(value = "备注")
	private String permissionRemark;

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

	@ApiModelProperty(value = "权限标识")
	private String permissionIdent;


	/**
	 * 无参权限信息
	 */
	public Permission() {

	}

	/**
	 * 有参权限信息
	 */
	public Permission(String permissionName, String apiUrl, String permissionRemark) {
		this.setPermissionName(permissionName);
		this.setApiUrl(apiUrl);
		this.setPermissionRemark(permissionRemark);
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
	 * 获取备注
	 */
	public String getPermissionRemark() {
		return this.permissionRemark;
	}

	/**
	 * 设置备注
	 */
	public void setPermissionRemark(String permissionRemark) {
		this.permissionRemark = permissionRemark;
	}

	
	public Integer getPostPermission() {
		return postPermission;
	}

	public void setPostPermission(Integer postPermission) {
		this.postPermission = postPermission;
	}

	public Integer getDeletePermission() {
		return deletePermission;
	}

	public void setDeletePermission(Integer deletePermission) {
		this.deletePermission = deletePermission;
	}

	public Integer getPutPermission() {
		return putPermission;
	}

	public void setPutPermission(Integer putPermission) {
		this.putPermission = putPermission;
	}

	public Integer getGetPermission() {
		return getPermission;
	}

	public void setGetPermission(Integer getPermission) {
		this.getPermission = getPermission;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPermissionIdent() {
		return permissionIdent;
	}

	public void setPermissionIdent(String permissionIdent) {
		this.permissionIdent = permissionIdent;
	}

	@Override
	public String primaryKey() {
		return this.getPermissionId();
	}
	
}
