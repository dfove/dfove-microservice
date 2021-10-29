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

@ApiModel(value = "PermissionModel", description = "权限信息搜索参数")
public class PermissionModel{
    
    @ApiModelProperty(value = "权限ID")
    private String permissionId;
    
    @ApiModelProperty(value = "开始权限ID")
    private String minPermissionId;
    
    @ApiModelProperty(value = "截止权限ID")
    private String maxPermissionId;
    
    @ApiModelProperty(value = "权限名称")
    private String permissionName;
    
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
	 * 获取开始权限ID
	 */
    public String getMinPermissionId() {
        return this.minPermissionId;
    }
    
    /**
     * 设置开始权限ID
     */
    public void setMinPermissionId(String minPermissionId) {
        this.minPermissionId = minPermissionId;
    }
    
    /**
	 * 获取截止权限ID
	 */
    public String getMaxPermissionId() {
        return this.maxPermissionId;
    }
    
    /**
     * 设置截止权限ID
     */
    public void setMaxPermissionId(String maxPermissionId) {
        this.maxPermissionId = maxPermissionId;
    }
    
    /**
	 * 获取权限名称
	 */
    public String getPermissionName() {
        return this.permissionName;
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

	/**
     * 设置权限名称
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
