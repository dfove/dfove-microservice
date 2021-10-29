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

import java.util.ArrayList;
import java.util.List;

import common.search.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Menu", description = "菜单信息")
public class MenuPermission extends BaseEntity<String> {

	@ApiModelProperty(value = "菜单ID，自增，添加时忽略")
	private String menuId;

	@ApiModelProperty(value = "父菜单ID")
	private String parentId;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "菜单地址")
	private String menuUrl;

	@ApiModelProperty(value = "备注")
	private String menuRemark;
	
	@ApiModelProperty(value = "菜单类型")
	private Integer menuType;

	@ApiModelProperty(value = "权限")
	private List<Permission> permission;// = new ArrayList<Permission>();

	@ApiModelProperty(value = "下级属性")
	private List<MenuPermission> children = new ArrayList<MenuPermission>();

	/**
	 * 无参菜单信息
	 */
	public MenuPermission() {

	}

	/**
	 * 有参菜单信息
	 */
	public MenuPermission(String parentId, String menuName, String menuUrl, String menuRemark) {
		this.setParentId(parentId);
		this.setMenuName(menuName);
		this.setMenuUrl(menuUrl);
		this.setMenuRemark(menuRemark);
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
	 * 获取父菜单ID
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置父菜单ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	/**
	 * 获取菜单地址
	 */
	public String getMenuUrl() {
		return this.menuUrl;
	}

	/**
	 * 设置菜单地址
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * 获取备注
	 */
	public String getMenuRemark() {
		return this.menuRemark;
	}

	/**
	 * 设置备注
	 */
	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}

	public List<MenuPermission> getChildren() {
		if (children==null) {
			return null;
		}
		return children;
	}

	public void setChildren(List<MenuPermission> children) {
		if (children.size() < 1) {
			this.children = null;
		} else {
			this.children = children;
		}
	}

	@Override
	public String primaryKey() {
		return this.getMenuId();
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}


	
}
