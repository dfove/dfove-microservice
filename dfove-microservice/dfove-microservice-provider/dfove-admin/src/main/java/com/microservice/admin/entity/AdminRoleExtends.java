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

@ApiModel(value = "AdminRoleExtends", description = "管理员角色扩展")
public class AdminRoleExtends {

	@ApiModelProperty(value = "管理员角色ID")
	private String adminRoleId;

	@ApiModelProperty(value = "管理员ID")
	private String adminId;

	@ApiModelProperty(value = "管理员用户名")
	private String adminName;

	@ApiModelProperty(value = "真实姓名")
	private String realName;
	
	@ApiModelProperty(value = "角色ID")
    private String roleId;
	
	@ApiModelProperty(value = "角色名称")
    private String roleName;
	
	@ApiModelProperty(value = "菜单ID")
	private String menuId;

	/**
	 * 获取管理员角色ID
	 */
	public String getAdminRoleId() {
		return this.adminRoleId;
	}

	/**
	 * 设置管理员角色ID
	 */
	public void setAdminRoleId(String adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	/**
	 * 获取管理员ID
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * 设置管理员ID
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * 获取管理员用户名
	 */
	public String getAdminName() {
		return this.adminName;
	}

	/**
	 * 设置管理员用户名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * 获取真实姓名
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * 设置真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
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
    
    public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
     * 设置角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
