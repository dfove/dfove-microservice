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

@ApiModel(value = "AdminPermissions", description = "管理员接口访问配置")
public class AdminPermission {

	@ApiModelProperty(value = "管理员Id")
	private String adminId;

	@ApiModelProperty(value = "管理员登录名")
	private String adminName;

	@ApiModelProperty(value = "管理员真实姓名")
	private String realName;

	@ApiModelProperty(value = "接口Id")
    private String permissionId;
   
    @ApiModelProperty(value = "接口名称")
    private String permissionName;
   
    @ApiModelProperty(value = "接口地址")
    private String apiUrl;
    
    @ApiModelProperty(value = "添加的权限")
    private Integer postPermission;
   
    @ApiModelProperty(value = "删除的权限")
    private Integer deletePermission;
   
    @ApiModelProperty(value = "修改的权限")
    private Integer putPermission;
   
    @ApiModelProperty(value = "查询的权限")
    private Integer getPermission;
    
    @ApiModelProperty(value = "菜单id")
    private String menuId;
    
    
	/**
	 * 获取管理员Id
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * 设置管理员Id
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * 获取管理员登录名
	 */
	public String getAdminName() {
		return this.adminName;
	}

	/**
	 * 设置管理员登录名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * 获取管理员真实姓名
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * 设置管理员真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	/**
     * 获取接口Id
     */
    public String getPermissionId() {
        return this.permissionId;
    }
    
    /**
     * 设置接口Id
     * @param permissionId 接口Id
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    
    /**
     * 获取接口名称
     */
    public String getPermissionName() {
        return this.permissionName;
    }
    
    /**
     * 设置接口名称
     * @param permissionName 接口名称
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
    
    /**
     * 获取接口地址
     */
    public String getApiUrl() {
        return this.apiUrl;
    }
    
    /**
     * 设置接口地址
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
    
    /**
     * 获取添加的权限
     */
    public Integer getPostPermission() {
        return this.postPermission;
    }
    
    /**
     * 设置添加的权限
     */
    public void setPostPermission(Integer postPermission) {
        this.postPermission = postPermission;
    }
    
    /**
     * 获取删除的权限
     */
    public Integer getDeletePermission() {
        return this.deletePermission;
    }
    
    /**
     * 设置删除的权限
     */
    public void setDeletePermission(Integer deletePermission) {
        this.deletePermission = deletePermission;
    }
    
    /**
     * 获取修改的权限
     */
    public Integer getPutPermission() {
        return this.putPermission;
    }
    
    /**
     * 设置修改的权限
     */
    public void setPutPermission(Integer putPermission) {
        this.putPermission = putPermission;
    }
    
    /**
     * 获取查询的权限
     */
    public Integer getGetPermission() {
        return this.getPermission;
    }
    
    public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
     * 设置查询的权限
     */
    public void setGetPermission(Integer getPermission) {
        this.getPermission = getPermission;
    }
}
