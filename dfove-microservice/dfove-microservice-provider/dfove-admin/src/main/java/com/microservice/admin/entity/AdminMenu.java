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

@ApiModel(value = "AdminMenu", description = "管理员菜单")
public class AdminMenu {

	@ApiModelProperty(value = "管理员Id")
	private String adminId;

	@ApiModelProperty(value = "管理员登录名")
	private String adminName;

	@ApiModelProperty(value = "管理员真实姓名")
	private String realName;

	@ApiModelProperty(value = "菜单Id，自增，添加时忽略")
	private String menuId;

	@ApiModelProperty(value = "父菜单Id，0表示最顶层")
	private String parentId;

	@ApiModelProperty(value = "菜单名称")
	private String menuName;

	@ApiModelProperty(value = "菜单连接")
	private String menuUrl;

	@ApiModelProperty(value = "排序，越大越排在前面")
	private Integer menuOrder;

	@ApiModelProperty(value = "菜单类型")
	private Long menuType;

	@ApiModelProperty(value = "菜单图标")
	private String menuImg;

	@ApiModelProperty(value = "页面组件路径")
	private String componentUrl;

	@ApiModelProperty(value = "权限标识")
	private List<String> permissionIdents;


	@ApiModelProperty(value = "子菜单")
	private List<AdminMenu> children;

	/**
	 * 获取管理员Id
	 * 
	 * @return 管理员Id，Integer
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * 设置管理员Id
	 * 
	 * @param adminId 管理员Id
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * 获取管理员登录名
	 * 
	 * @return 管理员登录名，String
	 */
	public String getAdminName() {
		return this.adminName;
	}

	/**
	 * 设置管理员登录名
	 * 
	 * @param adminName 管理员登录名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * 获取管理员真实姓名
	 * 
	 * @return 管理员真实姓名，String
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * 设置管理员真实姓名
	 * 
	 * @param realName 管理员真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取菜单Id
	 * 
	 * @return 菜单Id，Integer
	 */
	public String getMenuId() {
		return this.menuId;
	}

	/**
	 * 设置菜单Id
	 * 
	 * @param menuId 菜单Id
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取父菜单Id，0表示最顶层
	 * 
	 * @return 父菜单Id，0表示最顶层，Integer
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置父菜单Id，0表示最顶层
	 * 
	 * @param parentId 父菜单Id，0表示最顶层
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取菜单名称
	 * 
	 * @return 菜单名称，String
	 */
	public String getMenuName() {
		return this.menuName;
	}

	/**
	 * 设置菜单名称
	 * 
	 * @param menuName 菜单名称
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * 获取菜单连接
	 * 
	 * @return 菜单连接，String
	 */
	public String getMenuUrl() {
		return this.menuUrl;
	}

	/**
	 * 设置菜单连接
	 * 
	 * @param menuUrl 菜单连接
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * 获取排序，越大越排在前面
	 * 
	 * @return 排序，越大越排在前面，Integer
	 */
	public Integer getMenuOrder() {
		return this.menuOrder;
	}

	/**
	 * 设置排序，越大越排在前面
	 * 
	 * @param menuOrder 排序，越大越排在前面
	 */
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}


	public List<String> getPermissionIdents() {
		return permissionIdents;
	}

	public void setPermissionIdents(List<String> permissionIdents) {
		this.permissionIdents = permissionIdents;
	}

	/**
	 * 获取子菜单
	 * 
	 * @return 子菜单，List<AdminMenus>
	 */
	public List<AdminMenu> getChildren() {
		return this.children;
	}


	/**
	 * 设置子菜单
	 * 
	 * @param children 子菜单
	 */
	public void setChildren(List<AdminMenu> children) {
		this.children = children;
	}

	public Long getMenuType() {
		return menuType;
	}

	public void setMenuType(Long menuType) {
		this.menuType = menuType;
	}


	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	public String getComponentUrl() {
		return componentUrl;
	}

	public void setComponentUrl(String componentUrl) {
		this.componentUrl = componentUrl;
	}
}
