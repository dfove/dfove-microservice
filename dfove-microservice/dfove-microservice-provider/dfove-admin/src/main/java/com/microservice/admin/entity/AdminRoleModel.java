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

@ApiModel(value = "AdminRoleModel", description = "管理员角色信息搜索参数")
public class AdminRoleModel{
    
    @ApiModelProperty(value = "管理员角色ID")
    private String adminRoleId;
    
    @ApiModelProperty(value = "开始管理员角色ID")
    private String minAdminRoleId;
    
    @ApiModelProperty(value = "截止管理员角色ID")
    private String maxAdminRoleId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;
    
    
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
	 * 获取开始管理员角色ID
	 */
    public String getMinAdminRoleId() {
        return this.minAdminRoleId;
    }
    
    /**
     * 设置开始管理员角色ID
     */
    public void setMinAdminRoleId(String minAdminRoleId) {
        this.minAdminRoleId = minAdminRoleId;
    }
    
    /**
	 * 获取截止管理员角色ID
	 */
    public String getMaxAdminRoleId() {
        return this.maxAdminRoleId;
    }
    
    /**
     * 设置截止管理员角色ID
     */
    public void setMaxAdminRoleId(String maxAdminRoleId) {
        this.maxAdminRoleId = maxAdminRoleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
