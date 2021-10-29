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

import java.util.Date;
import java.util.List;

import common.search.entity.FieldComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "AdminModel", description = "管理员信息搜索参数")
public class AdminModel {

	@ApiModelProperty(value = "管理员ID")
	private String adminId;

	@ApiModelProperty(value = "开始管理员ID")
	private String minAdminId;

	@ApiModelProperty(value = "截止管理员ID")
	private String maxAdminId;

	@ApiModelProperty(value = "管理员用户名")
	private String adminName;

	@ApiModelProperty(value = "真实姓名")
	private String realName;

	@ApiModelProperty(value = "状态 (0：禁用，1：正常，8：锁定，88：删除) 多选项")
	private List<Integer> statuses;

	@ApiModelProperty(value = "组织机构ID")
	private String organizationId;
	
	@ApiModelProperty(value = "组织机构ID~批量")
	private List<String> organizationIds;

	@ApiModelProperty(value = "组织机构ID")
	private String organizationName;

	@ApiModelProperty(value = "开始创建日期")
	private Date minCreateTime;

	@ApiModelProperty(value = "截止创建日期")
	private Date maxCreateTime;

	@ApiModelProperty(value = "性别（0:未知1:男，2:女）")
	private Integer gender;

	@FieldComment(manual = true)
	private String superAdminId;

	/**
	 * 获取管理员ID
	 */
	public String getAdminId() {
		return this.adminId;
	}

	/**
	 * 设置管理员ID
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * 获取开始管理员ID
	 */
	public String getMinAdminId() {
		return this.minAdminId;
	}

	/**
	 * 设置开始管理员ID
	 */
	public void setMinAdminId(String minAdminId) {
		this.minAdminId = minAdminId;
	}

	/**
	 * 获取截止管理员ID
	 */
	public String getMaxAdminId() {
		return this.maxAdminId;
	}

	/**
	 * 设置截止管理员ID
	 */
	public void setMaxAdminId(String maxAdminId) {
		this.maxAdminId = maxAdminId;
	}

	/**
	 * 获取管理员用户名
	 */
	public String getAdminName() {
		return this.adminName;
	}

	/**
	 * 设置管理员用户名
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * 获取真实姓名
	 */
	public String getRealName() {
		return this.realName;
	}

	/**
	 * 设置真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 获取状态 (0：禁用，1：正常，8：锁定，88：删除) 多选项
	 */
	public List<Integer> getStatuses() {
		return this.statuses;
	}

	/**
	 * 设置状态 (0：禁用，1：正常，8：锁定，88：删除) 多选项
	 */
	public void setStatuses(List<Integer> statuses) {
		this.statuses = statuses;
	}

	/**
	 * 获取组织机构ID
	 */
	public String getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 设置组织机构ID
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * 获取组织机构名称
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * 设置组织机构名称
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * 获取开始创建日期
	 */
	public Date getMinCreateTime() {
		return this.minCreateTime;
	}

	/**
	 * 设置开始创建日期
	 */
	public void setMinCreateTime(Date minCreateTime) {
		this.minCreateTime = minCreateTime;
	}

	/**
	 * 获取截止创建日期
	 */
	public Date getMaxCreateTime() {
		return this.maxCreateTime;
	}

	/**
	 * 设置截止创建日期
	 */
	public void setMaxCreateTime(Date maxCreateTime) {
		this.maxCreateTime = maxCreateTime;
	}

	/**
	 * 获取性别（0:未知1:男，2:女）
	 */
	public Integer getGender() {
		return this.gender;
	}

	/**
	 * 设置性别（0:未知1:男，2:女）
	 */
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public List<String> getOrganizationIds() {
		return organizationIds;
	}

	public void setOrganizationIds(List<String> organizationIds) {
		this.organizationIds = organizationIds;
	}

	public String getSuperAdminId() {
		return superAdminId;
	}

	public void setSuperAdminId(String superAdminId) {
		this.superAdminId = superAdminId;
	}

}
