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

@ApiModel(value = "MenuModel", description = "菜单信息搜索参数")
public class MenuModel{
    
    @ApiModelProperty(value = "菜单ID")
    private String menuId;
    
    @ApiModelProperty(value = "开始菜单ID")
    private String minMenuId;
    
    @ApiModelProperty(value = "截止菜单ID")
    private String maxMenuId;
    
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    
    @ApiModelProperty(value = "菜单类型")
    private Integer menuType;
    
	@ApiModelProperty(value = "父菜单ID")
	private String parentId;
	
    
    
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
	 * 获取开始菜单ID
	 */
    public String getMinMenuId() {
        return this.minMenuId;
    }
    
    /**
     * 设置开始菜单ID
     */
    public void setMinMenuId(String minMenuId) {
        this.minMenuId = minMenuId;
    }
    
    /**
	 * 获取截止菜单ID
	 */
    public String getMaxMenuId() {
        return this.maxMenuId;
    }
    
    /**
     * 设置截止菜单ID
     */
    public void setMaxMenuId(String maxMenuId) {
        this.maxMenuId = maxMenuId;
    }
    
    /**
	 * 获取菜单名称
	 */
    public String getMenuName() {
        return this.menuName;
    }
    
    /**
     * 设置菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}


    
}
