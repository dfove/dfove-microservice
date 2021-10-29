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
package com.microservice.admin.service;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.microservice.admin.entity.*;
import com.microservice.admin.mapper.AdminRoleMapper;
import com.microservice.admin.util.UuidUtils;
import common.constant.RoleType;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.vo.AdminVo;
import common.vo.OrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.admin.mapper.AdminMapper;
import com.microservice.admin.mapper.OrganizationMapper;

import common.constant.RedisKey;
import common.entity.Constants;
import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.redis.IRedis;
import common.search.entity.BaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.tools.MD5Util;

@Service("adminService")
public class AdminService extends BaseService<Admin, String, AdminModel> implements IAdminService {

	@Resource
	private AdminMapper adminMapper;
	@Resource
	private OrganizationMapper organizationMapper;

	@Resource
	private AdminRoleMapper adminroleMapper;

	private final static String ITEM_NAME = "管理员信息";

	@Resource
	private IRoleService roleService;
	@Resource
	private IRedis redis;

	@Autowired
	private IAdminRoleService adminRoleService;
	@Autowired
	private IOrganizationService organizationService;

	@Override
	public AdminMapper mapper() {
		return adminMapper;
	}

	@Override
	public String itemName() {
		return ITEM_NAME;
	}

	@Override
	public OperateResult<String> add(Admin admin) {

		// 判断是否已存在
		Admin _admin = adminMapper.findVerification(admin);
		if (_admin != null && _admin.getAdminId() !=null) {
			return new OperateResult<String>().failed("用户名/手机/Email已存在", ErrorCode.USER_NAME_EXISTS);
		}

		// 加密密码
		admin.setPassword(MD5Util.stringToMD5(admin.getPassword(), Constants.PASSWORD_SUFFIX));
		OperateResult<String> result=new OperateResult<>();
		admin.setAdminId(UuidUtils.getUUID());
		admin.setCreateTime(new Date());
		admin.setUpdateTime(new Date());
		result=super.add(admin);
		if (result.ok()) {
			setRedisAdmin(admin);
			//赋予角色
			AdminRole adminRole=new AdminRole();
			adminRole.setAdminRoleId(UuidUtils.getUUID());
			adminRole.setAdminId(admin.getAdminId());
			adminRole.setRoleId(admin.getRoleId());
			result=adminRoleService.add(adminRole);
		}
		return result;
	}

	@Override
    public PageResult<List<Admin>> pageRequestSuperAdmin(PageRequest<AdminModel> request, String superAdminId) {
		if (request.getData().getOrganizationId() == null) { // 没指定机构id
			if (roleService.isRoleGtLevel(superAdminId, RoleType.USER_BUREAU_ADMIN)) { // 县级管理员只能看自己机构及子机构的用户
				Admin admin = findByAdminId(superAdminId);
				if (admin == null) {
					return new PageResult<List<Admin>>().failed("用户不存在");
				}
				SearchRequest<OrganizationModel> searchRequest = new SearchRequest<OrganizationModel>();
				List<String> organizationIds = organizationMapper.searchRequestBelow(searchRequest, admin.getOrganizationId()).stream()
						.map(item -> item.getOrganizationId()).collect(Collectors.toList());
				request.getData().setOrganizationIds(organizationIds);
			}
		} else { // 指定机构id
			SearchRequest<OrganizationModel> searchRequest = new SearchRequest<OrganizationModel>();
			List<String> organizationIds = organizationMapper.searchRequestBelow(searchRequest, request.getData().getOrganizationId()).stream()
					.map(item -> item.getOrganizationId()).collect(Collectors.toList());
			if (roleService.isRoleGtLevel(superAdminId, RoleType.USER_BUREAU_ADMIN)) { // 县级管理员只能看自己机构及子机构的用户
				Admin admin = findByAdminId(superAdminId);
				if (admin == null) {
					return new PageResult<List<Admin>>().failed("用户不存在");
				}
				SearchRequest<OrganizationModel> bureauAdminSearchRequest = new SearchRequest<OrganizationModel>();
				List<String> bureauAdminOrganizationIds = organizationMapper.searchRequestBelow(bureauAdminSearchRequest, admin.getOrganizationId()).stream()
						.map(item -> item.getOrganizationId()).collect(Collectors.toList());
				if (bureauAdminOrganizationIds.contains(request.getData().getOrganizationId())) { // 指定机构id在县级管理员机构下级
					request.getData().setOrganizationIds(organizationIds);
				} else { // 指定机构id在县级管理员机构上级
					request.getData().setOrganizationIds(bureauAdminOrganizationIds);
				}
			} else { // 指定机构及子机构
				request.getData().setOrganizationIds(organizationIds);
			}
			request.getData().setOrganizationId(null);
		}
		if (!roleService.isSuperAdmin(superAdminId)) { // 不需要返回超级管理员
			Admin superAdmin = getSuperAdmin();
			if (superAdmin != null) {
				request.getData().setSuperAdminId(superAdmin.getAdminId());
			}
		}

		PageResult<List<Admin>>  result=super.pageRequest(request);
		setSuperAdmins(result.getData());
		for (Admin admin:result.getData()){
			//获取角色id
			admin.setRoleId(adminroleMapper.findByAdminId(admin.getAdminId()));
		}

//		List<Admin> list=searchRequestSuperAdminRole(superAdminId,request);
//		if (list!=null && list.size()>0 && request.getCurrentPage()==1) {
//				result.getData().addAll(0,list);
//		}
		return result;
	}

	private Admin getSuperAdmin() {
		List<Integer> superAdmins = new ArrayList<>();
		superAdmins.add(RoleType.USER_SUPER_ADMIN);
		List<Role> roles = roleService.findBySuperAdmins(superAdmins);
		if (roles.isEmpty()) {
			return null;
		}
		SearchRequest<AdminRoleModel> adminRoleRequest = new SearchRequest<>();
		AdminRoleModel adminRoleModel = new AdminRoleModel();
		adminRoleModel.setRoleId(roles.get(0).getRoleId());
		adminRoleRequest.setData(adminRoleModel);
		List<AdminRole> adminRoles = adminRoleService.searchRequest(adminRoleRequest);
		if (adminRoles.isEmpty()) {
			return null;
		}
		return find(adminRoles.get(0).getAdminId());
	}

	@Override
	public Admin find(String id) {
		Admin admin = super.find(id);
		List<Admin> admins = new ArrayList<>();
		if (admin!=null){
			admins.add(admin);
			setSuperAdmins(admins);
		}
		return admin;
	}

	private void setSuperAdmins(List<Admin> admins) {
		if (admins == null || admins.isEmpty()) {
			return;
		}
		for (Admin admin : admins) {
			List<Role> roles = roleService.findByAdminId(admin.getAdminId());
			List<Integer> superAdmins = new ArrayList<>();
			if (!roles.isEmpty()) {
				for (Role role : roles) {
					superAdmins.add(role.getSuperAdmin());
				}
			}
			admin.setRoleSuperAdmins(superAdmins);
		}
	}

	@Override
	public Admin findName(String adminName) {
		Admin result = adminMapper.findByAdminName(adminName);
		return result;
	}

	@Override
	public OperateResult<Integer> update(Admin admin) {
		String adminId = admin.getAdminId();
		if (adminId == null  ) {
			return OperateResult.i().failed("用户Id不存在", ErrorCode.INPUT_NOT_ALLOWED);
		}

		Admin _admin = this.findOthers(adminId, admin.getAdminName(), admin.getPhone(), admin.getEmail());
		if (_admin != null) {
			return OperateResult.i().failed("用户名或手机号码或Email已存在，请换其他用户名或手机号码或Email", ErrorCode.INPUT_NOT_ALLOWED);
		}

		OperateResult<Integer> result = super.update(admin);
		
		if (result.ok()) {
			setRedisAdmin(admin);
		}
		return result;
	}

	@Override
	public Admin findOthers(String adminId, String name, String phone, String email) {
		if ((name == null || name.isEmpty()) && (phone == null || phone.isEmpty()) && (email == null || email.isEmpty())){
			return null;
		}
		Admin result = adminMapper.findOthers(adminId, name, phone, email);
		return result;
	}

	@Override
	public OperateResult<Integer> updatePassword(String adminId, String oldPassword, String newPassword) {
		if (adminId == null) {
			return OperateResult.i().failed("用户Id不存在", ErrorCode.INPUT_NOT_ALLOWED);
		}

		Admin admin = super.find(adminId);
		if (admin == null || !admin.getAdminId().equals(adminId)) {
			return OperateResult.i().failed("用户不存在", ErrorCode.OBJECT_NOT_EXISTS);
		}

		String password = admin.getPassword();
		if (password == null || password.length() != 32
				|| MD5Util.valid(password, oldPassword, Constants.PASSWORD_SUFFIX)) {
			return this.resetPassword(adminId, newPassword);
		} else {
			return OperateResult.i().failed("原密码不正确", ErrorCode.PASSWORD_NOT_CORRECT);
		}
	}
	
	@Override
	public OperateResult<Boolean> delete(String id) {
		OperateResult<Boolean> result=super.delete(id);
		if (result.ok()) {
			delRedisAdmin(id);
		}
		return result;
	}
	
	@Override
	public OperateResult<Integer> resetPassword(String adminId, String newPassword) {
		if (adminId == null ) {
			return OperateResult.i().failed("用户Id不存在", ErrorCode.INPUT_NOT_ALLOWED);
		}

		String password = MD5Util.stringToMD5(newPassword, Constants.PASSWORD_SUFFIX);
		int result = adminMapper.updateByMap(
				super.params("PASSWORD", password, "UPDATE_TIME", "now", "where", super.where("ADMIN_ID", adminId)));
		return OperateResult.i().success(result);
	}

	@Override
	public List<Admin> searchRequestSuperAdminRole(String adminId,PageRequest<AdminModel> request) {
		// TODO Auto-generated method stub
		return adminMapper.searchRequestSuperAdminRole(request,adminId);
	}

	@Override
	public List<Long> findByOrgType(String orgType) {
		// TODO Auto-generated method stub
		return adminMapper.findByOrgType(orgType);
	}

	/**
	 * 查缓存数据，没有则查数据库
	 *
	 * @param adminId
	 * @return
	 */
	@Override
	public Admin findByAdminId(String adminId) {
		if (adminId == null) {
			return null;
		}
		Admin admin = redis.getHashField(RedisKey.getUserKey(adminId), RedisKey.ADMIN, Admin.class);
		if (admin == null) {
			admin = find(adminId);
		}
		return admin;
	}

	@Override
	public List<AdminVo> getAdminList(List<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return new ArrayList<>();
		}
		List<Admin> admins = adminMapper.getAdminList(ids);
		List<AdminVo> adminVos = new ArrayList<>();
		if (!admins.isEmpty()) {
			Map<String, Organization> organizationMap = organizationService.getAboveOrganization(ids);
			for (Admin admin : admins) {
				AdminVo adminVo = new AdminVo();
				adminVo.setAdminId(admin.getAdminId());
				adminVo.setAdminName(admin.getAdminName());
				adminVo.setRealName(admin.getRealName());
				adminVo.setOrganizationId(admin.getOrganizationId());
				adminVo.setAdminLogo(admin.getAdminLogo());
				Organization organization = organizationMap.get(admin.getAdminId());
				if (organization != null) {
					OrganizationVo organizationVo = getOrganizationVo(organization);
					adminVo.setOrganization(organizationVo);
				}
				adminVos.add(adminVo);
			}
		}
		return adminVos;
	}

	private OrganizationVo getOrganizationVo(Organization organization) {
		OrganizationVo organizationVo = new OrganizationVo();
		organizationVo.setOrganizationId(organization.getOrganizationId());
		organizationVo.setOrganizationName(organization.getOrganizationName());
		List<Organization> children = organization.getChildren();
		if (children != null && !children.isEmpty()) {
			List<OrganizationVo> organizationVos = children.stream().map(item -> getOrganizationVo(item)).collect(Collectors.toList());
			organizationVo.setChildren(organizationVos);
		}
		return organizationVo;
	}

	@Override
	public SearchResult<List<Admin>> searchRequest(SearchRequest<AdminModel> request,String superAdminId) {
		
		if (request.getData().getOrganizationId() == null) { // 没指定机构id
			if (roleService.isRoleType(superAdminId, RoleType.USER_BUREAU_ADMIN)) { // 县级管理员只能看自己机构及子机构的用户
				Admin admin = findByAdminId(superAdminId);
				if (admin == null) {
					return new SearchResult<List<Admin>>().failed("用户不存在");
				}
				SearchRequest<OrganizationModel> searchRequest = new SearchRequest<OrganizationModel>();
				List<String> organizationIds = organizationMapper.searchRequestBelow(searchRequest, admin.getOrganizationId()).stream()
						.map(item -> item.getOrganizationId()).collect(Collectors.toList());
				request.getData().setOrganizationIds(organizationIds);
			}
		} else { // 指定机构id
			SearchRequest<OrganizationModel> searchRequest = new SearchRequest<OrganizationModel>();
			List<String> organizationIds = organizationMapper.searchRequestBelow(searchRequest, request.getData().getOrganizationId()).stream()
					.map(item -> item.getOrganizationId()).collect(Collectors.toList());
			if (roleService.isRoleType(superAdminId, RoleType.USER_BUREAU_ADMIN)) { // 县级管理员只能看自己机构及子机构的用户
				Admin admin = findByAdminId(superAdminId);
				if (admin == null) {
					return new PageResult<List<Admin>>().failed("用户不存在");
				}
				SearchRequest<OrganizationModel> bureauAdminSearchRequest = new SearchRequest<OrganizationModel>();
				List<String> bureauAdminOrganizationIds = organizationMapper.searchRequestBelow(bureauAdminSearchRequest, admin.getOrganizationId()).stream()
						.map(item -> item.getOrganizationId()).collect(Collectors.toList());
				if (bureauAdminOrganizationIds.contains(request.getData().getOrganizationId())) { // 指定机构id在县级管理员机构下级
					request.getData().setOrganizationIds(organizationIds);
				} else { // 指定机构id在县级管理员机构上级
					request.getData().setOrganizationIds(bureauAdminOrganizationIds);
				}
			} else { // 指定机构及子机构
				request.getData().setOrganizationIds(organizationIds);
			}
			request.getData().setOrganizationId(null);
		}
		if (!roleService.isSuperAdmin(superAdminId)) { // 不需要返回超级管理员
			Admin superAdmin = getSuperAdmin();
			if (superAdmin != null) {
				request.getData().setSuperAdminId(superAdmin.getAdminId());
			}
		}
		
		List<Admin> admins = super.searchRequest(request);
		setSuperAdmins(admins);
		
		SearchResult searchResult= new SearchResult<List<Admin>>().success(admins);
		searchResult.setTotalCount(admins.size());
		return searchResult;
	}

	//根据机构id获取机构账户
	@Override
	public List<Admin> getOrganizationAdmin(String organizationId) {
		return adminMapper.getOrganizationAdmin(organizationId);
	}

	@Override
	public PageResult<List<Admin>> pageRequest(PageRequest<AdminModel> request) {
		PageResult<List<Admin>> pageResult = super.pageRequest(request);
		setSuperAdmins(pageResult.getData());
		return pageResult;
	}
	
	private void setRedisAdmin(Admin admin) {
		try {			
			redis.setHashField(RedisKey.getUserKey(admin.getAdminId()), RedisKey.ADMIN, admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void delRedisAdmin(String adminId) {
		try {			
			redis.hdel(RedisKey.getUserKey(adminId), RedisKey.ADMIN);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
