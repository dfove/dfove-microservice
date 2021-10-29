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

import java.util.List;

@ApiModel(value = "RoleModel", description = "角色信息搜索参数")
public class RoleModel{
    
    @ApiModelProperty(value = "角色ID")
    private String roleId;
    
    @ApiModelProperty(value = "开始角色ID")
    private String minRoleId;
    
    @ApiModelProperty(value = "截止角色ID")
    private String maxRoleId;
    
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    
    @ApiModelProperty(value = "1-超级管理员，10-管理员，100-用户",hidden=true)
    private List<Integer> superAdmins;

    @ApiModelProperty(value = "开始角色用户类型")
    private Integer minequSuperAdmin;
    
    
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
	 * 获取开始角色ID
	 */
    public String getMinRoleId() {
        return this.minRoleId;
    }
    
    /**
     * 设置开始角色ID
     */
    public void setMinRoleId(String minRoleId) {
        this.minRoleId = minRoleId;
    }
    
    /**
	 * 获取截止角色ID
	 */
    public String getMaxRoleId() {
        return this.maxRoleId;
    }
    
    /**
     * 设置截止角色ID
     */
    public void setMaxRoleId(String maxRoleId) {
        this.maxRoleId = maxRoleId;
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

    public List<Integer> getSuperAdmins() {
        return superAdmins;
    }

    public void setSuperAdmins(List<Integer> superAdmins) {
        this.superAdmins = superAdmins;
    }

    public Integer getMinequSuperAdmin() {
        return minequSuperAdmin;
    }

    public void setMinequSuperAdmin(Integer minequSuperAdmin) {
        this.minequSuperAdmin = minequSuperAdmin;
    }

}
