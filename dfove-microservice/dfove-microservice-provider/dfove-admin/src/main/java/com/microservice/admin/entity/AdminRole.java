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

@ApiModel(value = "AdminRole", description = "管理员角色信息")
public class AdminRole extends BaseEntity<String> {
   
    @ApiModelProperty(value = "管理员角色ID，添加时忽略")
    private String adminRoleId;
   
    @ApiModelProperty(value = "管理员ID")
    private String adminId;
   
    @ApiModelProperty(value = "角色ID")
    private String roleId;
    
    /**
    无参管理员角色信息
    */
    public AdminRole() {

	}
    
    /**
    有参管理员角色信息
    */
    public AdminRole(String adminId, String roleId) {
        this.setAdminId(adminId);
        this.setRoleId(roleId);
	}
    
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
    
    @Override
	public String primaryKey() {
		return this.getAdminRoleId();
	}
}
