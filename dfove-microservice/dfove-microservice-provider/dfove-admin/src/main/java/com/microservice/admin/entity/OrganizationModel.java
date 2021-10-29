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

import common.search.entity.FieldComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "OrganizationModel", description = "机构管理搜索参数")
public class OrganizationModel {

	@ApiModelProperty(value = "组织ID")
	private String organizationId;

	@ApiModelProperty(value = "组织ID")
	private List<String> organizationIds;

	@ApiModelProperty(value = "组织名称")
	private String organizationName;

	@ApiModelProperty(value = "父级ID")
	private String parentId;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "行政编码")
	private String addrCode;

	@ApiModelProperty(value = "机构类型 多个")
	private List<Integer> orgTypes;

	@ApiModelProperty(value = "状态 多个")
	private List<Integer> statuses;

	@ApiModelProperty(value = "按父机构Id进行分组，展示所属关系")
	@FieldComment(manual = true)
	private String groupParentId;

	/**
	 * 获取组织ID
	 */
	public String getOrganizationId() {
		return this.organizationId;
	}

	/**
	 * 设置组织ID
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * 获取组织ID
	 */
	public List<String> getOrganizationIds() {
		return organizationIds;
	}

	/**
	 * 设置组织ID
	 */
	public void setOrganizationIds(List<String> organizationIds) {
		this.organizationIds = organizationIds;
	}

	/**
	 * 获取组织名称
	 */
	public String getOrganizationName() {
		return this.organizationName;
	}

	/**
	 * 设置组织名称
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * 获取父级ID
	 */
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 设置父级ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取地址
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * 设置地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取行政编码
	 */
	public String getAddrCode() {
		return this.addrCode;
	}

	/**
	 * 设置行政编码
	 */
	public void setAddrCode(String addrCode) {
		this.addrCode = addrCode;
	}

	/**
	 * 获取机构类型 多个
	 */
	public List<Integer> getOrgTypes() {
		return this.orgTypes;
	}

	/**
	 * 设置机构类型 多个
	 */
	public void setOrgTypes(List<Integer> orgTypes) {
		this.orgTypes = orgTypes;
	}

	/**
	 * 获取 状态 多个
	 */
	public List<Integer> getStatuses() {
		return this.statuses;
	}

	/**
	 * 设置 状态 多个
	 */
	public void setStatuses(List<Integer> statuses) {
		this.statuses = statuses;
	}

	/**
	 * 按父机构Id进行分组
	 */
	public String getGroupParentId() {
		return groupParentId;
	}

	/**
	 * 按父机构Id进行分组
	 */
	public void setGroupParentId(String groupParentId) {
		this.groupParentId = groupParentId;
	}
}
