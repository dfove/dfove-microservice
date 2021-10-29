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

@ApiModel(value = "RoleMenuModel", description = "角色菜单信息搜索参数")
public class RoleMenuModel{
    
    @ApiModelProperty(value = "角色菜单ID")
    private String roleMenuId;
    
    @ApiModelProperty(value = "开始角色菜单ID")
    private String minRoleMenuId;
    
    @ApiModelProperty(value = "截止角色菜单ID")
    private String maxRoleMenuId;

    @ApiModelProperty(value = "角色ID")
    private String roleId;

    @ApiModelProperty(value = "1-默认值，0-非默认值")
    private Integer restore;
    
    /**
	 * 获取角色菜单ID
	 */
    public String getRoleMenuId() {
        return this.roleMenuId;
    }
    
    /**
     * 设置角色菜单ID
     */
    public void setRoleMenuId(String roleMenuId) {
        this.roleMenuId = roleMenuId;
    }
    
    /**
	 * 获取开始角色菜单ID
	 */
    public String getMinRoleMenuId() {
        return this.minRoleMenuId;
    }
    
    /**
     * 设置开始角色菜单ID
     */
    public void setMinRoleMenuId(String minRoleMenuId) {
        this.minRoleMenuId = minRoleMenuId;
    }
    
    /**
	 * 获取截止角色菜单ID
	 */
    public String getMaxRoleMenuId() {
        return this.maxRoleMenuId;
    }
    
    /**
     * 设置截止角色菜单ID
     */
    public void setMaxRoleMenuId(String maxRoleMenuId) {
        this.maxRoleMenuId = maxRoleMenuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Integer getRestore() {
        return restore;
    }

    public void setRestore(Integer restore) {
        this.restore = restore;
    }
}
