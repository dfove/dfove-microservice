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

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RolePermissionModel", description = "角色权限信息搜索参数")
public class RolePermissionModel{
    
    @ApiModelProperty(value = "角色权限ID")
    private String rolePermissionId;
    
    @ApiModelProperty(value = "开始角色权限ID")
    private String minRolePermissionId;
    
    @ApiModelProperty(value = "截止角色权限ID")
    private String maxRolePermissionId;
    
    @ApiModelProperty(value = "post 权限 多选项")
    private List<String> postPermissions;
    
    @ApiModelProperty(value = "delete 权限 多选项")
    private List<String> deletePermissions;
    
    @ApiModelProperty(value = "put 权限 多选项")
    private List<String> putPermissions;
    
    @ApiModelProperty(value = "get 权限 多选项")
    private List<String> getPermissions;
    
	@ApiModelProperty(value = "菜单Id")
	private String menuId;

	@ApiModelProperty(value = "角色Id")
	private String roleId;

    @ApiModelProperty(value = "1-默认值，0-非默认值")
    private Integer restore;
    
    
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
	 * 获取开始角色权限ID
	 */
    public String getMinRolePermissionId() {
        return this.minRolePermissionId;
    }
    
    /**
     * 设置开始角色权限ID
     */
    public void setMinRolePermissionId(String minRolePermissionId) {
        this.minRolePermissionId = minRolePermissionId;
    }
    
    /**
	 * 获取截止角色权限ID
	 */
    public String getMaxRolePermissionId() {
        return this.maxRolePermissionId;
    }
    
    /**
     * 设置截止角色权限ID
     */
    public void setMaxRolePermissionId(String maxRolePermissionId) {
        this.maxRolePermissionId = maxRolePermissionId;
    }
    
    /**
	 * 获取post 权限 多选项
	 */
    public List<String> getPostPermissions() {
        return this.postPermissions;
    }
    
    /**
     * 设置post 权限 多选项
     */
    public void setPostPermissions(List<String> postPermissions) {
        this.postPermissions = postPermissions;
    }
    
    /**
	 * 获取delete 权限 多选项
	 */
    public List<String> getDeletePermissions() {
        return this.deletePermissions;
    }
    
    /**
     * 设置delete 权限 多选项
     */
    public void setDeletePermissions(List<String> deletePermissions) {
        this.deletePermissions = deletePermissions;
    }
    
    /**
	 * 获取put 权限 多选项
	 */
    public List<String> getPutPermissions() {
        return this.putPermissions;
    }
    
    /**
     * 设置put 权限 多选项
     */
    public void setPutPermissions(List<String> putPermissions) {
        this.putPermissions = putPermissions;
    }
    
    /**
	 * 获取get 权限 多选项
	 */
    public List<String> getGetPermissions() {
        return this.getPermissions;
    }
    
    public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
     * 设置get 权限 多选项
     */
    public void setGetPermissions(List<String> getPermissions) {
        this.getPermissions = getPermissions;
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
