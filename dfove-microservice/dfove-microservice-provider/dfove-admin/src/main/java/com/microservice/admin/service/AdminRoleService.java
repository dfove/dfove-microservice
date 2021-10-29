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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.microservice.admin.entity.*;
import com.microservice.admin.util.UuidUtils;
import common.constant.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.admin.mapper.AdminRoleMapper;

import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.search.entity.BaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

@Service("adminroleService")
public class AdminRoleService extends BaseService<AdminRole, String, AdminRoleModel> implements IAdminRoleService {

	@Resource
	private AdminRoleMapper adminroleMapper;

	@Autowired
	private IRoleService roleService;

	private final static String ITEM_NAME = "管理员角色信息";

	@Override
	public AdminRoleMapper mapper() {
		return adminroleMapper;
	}

	@Override
	public String itemName() {
		return ITEM_NAME;
	}

	@Override
	public OperateResult<String> add(AdminRole adminRole) {

		AdminRole _adminRole = adminroleMapper.findByAdminAndRole(adminRole.getAdminId(), adminRole.getRoleId());
		if (_adminRole != null){
			return OperateResult.s().success(_adminRole.getAdminRoleId(), "相同用户相同角色已存在");
		}
		adminRole.setAdminRoleId(UuidUtils.getUUID());
		return super.add(adminRole);
	}

	@Override
	public OperateResult<Boolean> update(List<AdminRole> adminRoles) {

		if (adminRoles == null || adminRoles.size() < 1) {
			return OperateResult.b().failed("至少要包括一个管理权限", ErrorCode.INPUT_NOT_ALLOWED);
		}

		String adminId = adminRoles.get(0).getAdminId();
		if (adminId == null) {
			return OperateResult.b().failed("用户Id不合法", ErrorCode.INPUT_NOT_ALLOWED);
		}

		List<String> roleIds = new ArrayList<>();
		for (AdminRole e : adminRoles) {
			if (!adminId.equals(e.getAdminId())) {
				return OperateResult.b().failed("批量更新角色必须是相同的用户", ErrorCode.INPUT_NOT_ALLOWED);
			}
			String roleId = e.getRoleId();
			if (roleId == null ) {
				return OperateResult.b().failed("角色Id不合法", ErrorCode.INPUT_NOT_ALLOWED);
			}
			Role role = roleService.find(e.getRoleId());
			if (RoleType.isSuperAdmin(role.getSuperAdmin())) {
				return OperateResult.b().failed("不能分配超级管理员角色", ErrorCode.INPUT_NOT_ALLOWED);
			}
			roleIds.add(roleId);
		}

		adminroleMapper.deleteNotExists(adminId, roleIds);
		adminroleMapper.batchAdd(adminId, roleIds);
		roleService.updateUserRoleCacheByAdminId(adminId);
		return OperateResult.b().success(true);
	}

	@Override
	public int countExtends(SearchRequest<AdminRoleExtendsModel> request) {
		int result = adminroleMapper.countExtends(request);
		return result;
	}
	
	@Override
	public List<AdminRoleExtends> searchRequestExtends(SearchRequest<AdminRoleExtendsModel> request) {
		List<AdminRoleExtends> result = adminroleMapper.searchRequestExtends(request);
		return result;
	}

	@Override
	public PageResult<List<AdminRoleExtends>> pageRequestExtends(PageRequest<AdminRoleExtendsModel> request) {
		int totalCount = this.countExtends(request);
		request.check(totalCount);

		List<AdminRoleExtends> result = adminroleMapper.pageRequestExtends(request);

		return new PageResult<List<AdminRoleExtends>>(result, totalCount, request.getPageSize(), request.getCurrentPage());
	}

	@Override
	public OperateResult<Integer> deleteAdminAndRole(AdminRole adminrole) {
		return new OperateResult<Integer>().success(adminroleMapper.deleteAdminAndRole(adminrole));
	}

	@Override
	public int deleteByRoleId(String roleId) {
		if (roleId == null) {
			return 0;
		}
		return adminroleMapper.deleteByRoleId(roleId);
	}

}
