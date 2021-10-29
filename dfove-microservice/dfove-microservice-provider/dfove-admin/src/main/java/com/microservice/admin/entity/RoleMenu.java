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

@ApiModel(value = "RoleMenu", description = "角色菜单信息")
public class RoleMenu extends BaseEntity<String> {

    /**
     * 默认值
     */
    public static final int RESTORE_DEFAULT = 1;
    /**
     * 非默认值
     */
    public static final int RESTORE_NOT = 0;
   
    @ApiModelProperty(value = "角色菜单ID，添加时忽略")
    private String roleMenuId;
   
    @ApiModelProperty(value = "角色ID")
    private String roleId;
   
    @ApiModelProperty(value = "菜单ID")
    private String menuId;
   
    @ApiModelProperty(value = "排序，大的在后面")
    private Long menuOrder;

    @ApiModelProperty(value = "1-默认值，0-非默认值")
    private Integer restore;
    
    /**
    无参角色菜单信息
    */
    public RoleMenu() {

	}
    
    /**
    有参角色菜单信息
    */
    public RoleMenu(String roleId, String menuId, Long menuOrder) {
        this.setRoleId(roleId);
        this.setMenuId(menuId);
        this.setMenuOrder(menuOrder);
	}
    
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
     * 获取菜单ID
     */
    public String getMenuId() {
        return this.menuId;
    }
    
    /**
     * 设置菜单ID
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    /**
     * 获取排序，大的在后面
     */
    public Long getMenuOrder() {
        return this.menuOrder;
    }
    
    /**
     * 设置排序，大的在后面
     */
    public void setMenuOrder(Long menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getRestore() {
        return restore;
    }

    public void setRestore(Integer restore) {
        this.restore = restore;
    }

    @Override
	public String primaryKey() {
		return this.getRoleMenuId();
	}
}
