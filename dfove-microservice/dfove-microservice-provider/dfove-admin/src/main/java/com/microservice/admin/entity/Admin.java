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

import common.search.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Admin", description = "管理员信息")
public class Admin extends BaseEntity<String> {

	@ApiModelProperty(value = "管理员ID，添加时忽略")
	private String adminId;

//	   @Size(min=2, max=30,message = "请检查名字的长度是否有问题")
//    @NotBlank(message="用户不能为空")
	@ApiModelProperty(value = "管理员用户名",required=true)//
	private String adminName;

	@ApiModelProperty(value = "真实姓名",required=true)
	private String realName;

	@ApiModelProperty(value = "管理员密码",required=true)
	private String password;

	@ApiModelProperty(value = "邮件")
	private String email;

	@ApiModelProperty(value = "联系电话",required=true)
	private String phone;

	@ApiModelProperty(value = "状态 (0：禁用，1：正常，8：锁定，88：删除)",required=true)
	private Integer status;

	@ApiModelProperty(value = "创建者用户名",required=true)
	private String createAdminId;

	@ApiModelProperty(value = "组织机构ID",required=true)
	private String organizationId;
	
	@ApiModelProperty(value = "组织机构名称",required=true)
	private String organizationName;

	@ApiModelProperty(value = "创建日期")
	private Date createTime = new Date();

	@ApiModelProperty(value = "更新日期")
	private Date updateTime = new Date();

	@ApiModelProperty(value = "性别（0:未知1:男，2:女）",required=true)
	private Integer gender;

	@ApiModelProperty(value = "用户头像链接")
	private String adminLogo;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "等级",required=true)
	private Integer grade;

	@ApiModelProperty(value = "添加角色id")
	private String roleId;


	@ApiModelProperty(value = "角色类型：1-超级管理员，10-管理员，100-用户")
	private List<Integer> roleSuperAdmins;
	
//	@ApiModelProperty(value = "角色id对象")
//	private List<AdminRole> adminRoles;

	
	
	/**
	 * 无参管理员信息
	 */
	public Admin() {

	}

	/**
	 * 有参管理员信息
	 */
	public Admin(String adminName, String realName, String password, String email, String phone, Integer status,
				 String createAdminId, String organizationId, Date createTime, Date updateTime, Integer gender,
			String adminLogo) {
		this.setAdminName(adminName);
		this.setRealName(realName);
		this.setPassword(password);
		this.setEmail(email);
		this.setPhone(phone);
		this.setStatus(status);
		this.setCreateAdminId(createAdminId);
		this.setOrganizationId(organizationId);
		this.setCreateTime(createTime);
		this.setUpdateTime(updateTime);
		this.setGender(gender);
		this.setAdminLogo(adminLogo);
	}


	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

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
	 * 获取管理员密码
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设置管理员密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取邮件
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * 设置邮件
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取联系电话
	 */
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 设置联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取状态 (0：禁用，1：正常，8：锁定，88：删除)
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 设置状态 (0：禁用，1：正常，8：锁定，88：删除)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取创建者用户名
	 */
	public String getCreateAdminId() {
		return this.createAdminId;
	}

	/**
	 * 设置创建者用户名
	 */
	public void setCreateAdminId(String createAdminId) {
		this.createAdminId = createAdminId;
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
	 * 获取创建日期
	 */
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 设置创建日期
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取更新日期
	 */
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 设置更新日期
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	/**
	 * 获取用户头像链接
	 */
	public String getAdminLogo() {
		return this.adminLogo;
	}

	/**
	 * 设置用户头像链接
	 */
	public void setAdminLogo(String adminLogo) {
		this.adminLogo = adminLogo;
	}

	/**
	 * 获取备注
	 */
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 设置备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取等级
	 */
	public Integer getGrade() {
		return grade;
	}

	/**
	 * 设置等级
	 */
	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public List<Integer> getRoleSuperAdmins() {
		return roleSuperAdmins;
	}

	public void setRoleSuperAdmins(List<Integer> roleSuperAdmins) {
		this.roleSuperAdmins = roleSuperAdmins;
	}

//	public List<AdminRole> getAdminRoles() {
//		return adminRoles;
//	}
//
//	public void setAdminRoles(List<AdminRole> adminRoles) {
//		this.adminRoles = adminRoles;
//	}

	@Override
	public String primaryKey() {
		return this.getAdminId();
	}
}
