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

@ApiModel(value = "Role", description = "角色信息")
public class Role extends BaseEntity<String> {

	@ApiModelProperty(value = "角色ID，添加时忽略")
	private String roleId;

	@ApiModelProperty(value = "角色名称",required=true)
	private String roleName;

	@ApiModelProperty(value = "备注")
	private String roleRemark;
	
	@ApiModelProperty(value = "1-超级管理员，10-管理员，100-用户")
	private Integer superAdmin;

	@ApiModelProperty(value = "角色类型：1-系统角色，2-自定义角色")
	private Integer type;

	@ApiModelProperty(value = "数据权限类型：0、仅访问个人数据，1、访问机构数据")
	private Integer dataPermission;

	public Integer getDataPermission() {
		return dataPermission;
	}

	public void setDataPermission(Integer dataPermission) {
		this.dataPermission = dataPermission;
	}
	/**
	 * 无参角色信息
	 */
	public Role() {

	}

	/**
	 * 有参角色信息
	 */
	public Role(String roleName, String roleRemark) {
		this.setRoleName(roleName);
		this.setRoleRemark(roleRemark);
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
	 * 获取备注
	 */
	public String getRoleRemark() {
		return this.roleRemark;
	}

	/**
	 * 设置备注
	 */
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}

	public Integer getSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(Integer superAdmin) {
		this.superAdmin = superAdmin;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String primaryKey() {
		return this.getRoleId();
	}
}
