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
import java.math.BigDecimal;

import common.search.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Organization", description = "机构管理")
public class Organization extends BaseEntity<String> {

	@ApiModelProperty(value = "组织ID，添加时忽略")
	private String organizationId;

	@ApiModelProperty(value = "组织名称",required=true)
	private String organizationName;

	@ApiModelProperty(value = "父级ID",required=true)
	private String parentId;

	@ApiModelProperty(value = "地址",required=true)
	private String address;

	@ApiModelProperty(value = "排序值")
	private Integer orderNum;

	@ApiModelProperty(value = "机构类型")
	private Integer orgType;

	@ApiModelProperty(value = "行政编码")
	private String addrCode;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "描述")
	private String info;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "创建时间")
	private Date createDate = new Date();

	@ApiModelProperty(value = "创建者")
	private String creator;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate = new Date();

	@ApiModelProperty(value = "更新者")
	private String updator;

	@ApiModelProperty(value = "经纬度")
	private BigDecimal gisX;

	@ApiModelProperty(value = "经纬度")
	private BigDecimal gisY;

	@ApiModelProperty(value = "子机构")
	private List<Organization> children;

	/**
	 * 无参机构管理
	 */
	public Organization() {

	}

	/**
	 * 有参机构管理
	 */
	public Organization(String organizationName, String parentId, String address, Integer orderNum, Integer orgType,
			String addrCode, String phone, String info, Integer status, Date createDate, String creator, Date updateDate,
						String updator, BigDecimal gisX, BigDecimal gisY) {
		this.setOrganizationName(organizationName);
		this.setParentId(parentId);
		this.setAddress(address);
		this.setOrderNum(orderNum);
		this.setOrgType(orgType);
		this.setAddrCode(addrCode);
		this.setPhone(phone);
		this.setInfo(info);
		this.setStatus(status);
		this.setCreateDate(createDate);
		this.setCreator(creator);
		this.setUpdateDate(updateDate);
		this.setUpdator(updator);
		this.setGisX(gisX);
		this.setGisY(gisY);
	}

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
	 * 获取排序值
	 */
	public Integer getOrderNum() {
		return this.orderNum;
	}

	/**
	 * 设置排序值
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取机构类型
	 */
	public Integer getOrgType() {
		return this.orgType;
	}

	/**
	 * 设置机构类型
	 */
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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
	 * 获取电话
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取描述
	 */
	public String getInfo() {
		return this.info;
	}

	/**
	 * 设置描述
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * 获取状态
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取创建时间
	 */
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取创建者
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * 设置创建者
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 获取更新时间
	 */
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 设置更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 获取更新者
	 */
	public String getUpdator() {
		return this.updator;
	}

	/**
	 * 设置更新者
	 */
	public void setUpdator(String updator) {
		this.updator = updator;
	}

	/**
	 * 获取经纬度
	 */
	public BigDecimal getGisX() {
		return this.gisX;
	}

	/**
	 * 设置经纬度
	 */
	public void setGisX(BigDecimal gisX) {
		this.gisX = gisX;
	}

	/**
	 * 获取经纬度
	 */
	public BigDecimal getGisY() {
		return this.gisY;
	}

	/**
	 * 设置经纬度
	 */
	public void setGisY(BigDecimal gisY) {
		this.gisY = gisY;
	}

	/**
	 * 获取子机构
	 */
	public List<Organization> getChildren() {
		return children;
	}

	/**
	 * 设置子机构
	 */
	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	@Override
	public String primaryKey() {
		return this.getOrganizationId();
	}
}