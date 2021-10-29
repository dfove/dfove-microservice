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

import javax.annotation.Resource;

import com.microservice.admin.entity.*;
import com.microservice.core.App;
import common.constant.RedisKey;
import common.constant.RoleType;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.enums.CollectionType;
import common.redis.IRedis;
import common.search.entity.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.admin.mapper.RoleMapper;

@Service("roleService")
public class RoleService extends BaseService<Role, String, RoleModel> implements IRoleService {

	@Resource
	private RoleMapper roleMapper;

	@Autowired
	private IRoleMenuService roleMenuService;
	@Autowired
	private IRolePermissionService rolePermissionService;

    private final static String ITEM_NAME = "角色信息";

	@Resource
	private IRedis redis;
	@Resource
	private IAdminRoleService adminRoleService;

	@Override
	public RoleMapper mapper() {
		return roleMapper;
	}

    @Override
	public String itemName() {
		return ITEM_NAME;
	}

	@Override
	public OperateResult<Integer> update(Role entity) {
		OperateResult<Integer> updateResult = super.update(entity);
		if (updateResult.ok() && updateResult.getData() != null) {
			updateUserRoleCacheByRoleId(entity.getRoleId());
		}
		return updateResult;
	}

	@Override
	public OperateResult<Boolean> delete(String id) {
		OperateResult<Boolean> deleteResult = super.delete(id);
		if (deleteResult.ok()) {
			int deleteByRoleId = adminRoleService.deleteByRoleId(id);
		}
		return deleteResult;
	}

	@Override
	public List<Role> searchRequest(SearchRequest<RoleModel> request) {
		request.getData().setMinequSuperAdmin(getMinRoleSuperAdmin());
		return super.searchRequest(request);
	}

	private Integer getMinRoleSuperAdmin() {
		UserScope userScope = App.userScope();
		int max = RoleType.USER_MEMBER;
		if (userScope != null) {
			List<Role> roles = getRoles(userScope.getUserId());
			for (Role role : roles) {
				if (role.getSuperAdmin() < max) {
					max = role.getSuperAdmin();
				}
			}
		}
		return max;
	}

	@Override
	public PageResult<List<Role>> pageRequest(PageRequest<RoleModel> request) {
		request.getData().setMinequSuperAdmin(getMinRoleSuperAdmin());
		return super.pageRequest(request);
	}

	@Override
	public List<OrganizationRole> searchRequestRole(SearchRequest<OrganizationRoleModel> request) {
		// TODO Auto-generated method stub
		return	roleMapper.searchRequestRole(request);
	}

	@Override
	public List<Admin> searchRequestRoleAdmin(SearchRequest<OrganizationRoleModel> request) {
		// TODO Auto-generated method stub
		return	roleMapper.searchRequestRoleAdmin(request);
	}

	@Override
	public List<Role> searchRequestAdmin(SearchRequest<RoleModel> request) {
		// TODO Auto-generated method stub
		return roleMapper.searchRequestAdmin(request);
	}

	@Override
	public List<Role> findByAdminId(String adminId) {
		return roleMapper.findByAdminId(adminId);
	}

	@Override
	public boolean restoreDefaultValue(String roleId) {
		if (roleId == null) {
			return false;
		}
		SearchRequest<RoleMenuModel> roleMenuModelSearchRequest = new SearchRequest<>();
		RoleMenuModel roleMenuModel = new RoleMenuModel();
		roleMenuModel.setRoleId(roleId);
		roleMenuModel.setRestore(RoleMenu.RESTORE_DEFAULT);
		roleMenuModelSearchRequest.setData(roleMenuModel);
		List<RoleMenu> roleMenus = roleMenuService.searchRequest(roleMenuModelSearchRequest);
		LoggerFactory.getLogger(getClass()).info("restoreDefaultValue() roleMenus.size():{}", roleMenus.size());
		if(!roleMenus.isEmpty()) {
			for (RoleMenu roleMenu : roleMenus) {
				roleMenu.setRestore(RoleMenu.RESTORE_NOT);
			}
			OperateResult<Boolean> updateRoleMenu = roleMenuService.update(roleMenus);
			LoggerFactory.getLogger(getClass()).info("restoreDefaultValue() updateRoleMenu:{}", updateRoleMenu);
		} else {
			int count = roleMenuService.deleteByRoleId(roleId, RoleMenu.RESTORE_NOT);
			LoggerFactory.getLogger(getClass()).info("restoreDefaultValue() deleteByRoleId:{}", count);
		}

		SearchRequest<RolePermissionModel> rolePermissionModelSearchRequest = new SearchRequest<>();
		RolePermissionModel rolePermissionModel = new RolePermissionModel();
		rolePermissionModel.setRoleId(roleId);
		rolePermissionModel.setRestore(RoleMenu.RESTORE_DEFAULT);
		rolePermissionModelSearchRequest.setData(rolePermissionModel);
		List<RolePermission> rolePermissions = rolePermissionService.searchRequest(rolePermissionModelSearchRequest);
		LoggerFactory.getLogger(getClass()).info("restoreDefaultValue() rolePermissions.size():{}", rolePermissions.size());
		if (!rolePermissions.isEmpty()) {
			for (RolePermission rolePermission : rolePermissions) {
				rolePermission.setRestore(RoleMenu.RESTORE_NOT);
			}
			OperateResult<Boolean> updateRolePermission = rolePermissionService.update(rolePermissions);
			LoggerFactory.getLogger(getClass()).info("restoreDefaultValue() updateRolePermission:{}", updateRolePermission);
		} else {
			int count = rolePermissionService.deleteByRoleId(roleId, RoleMenu.RESTORE_NOT);
			LoggerFactory.getLogger(getClass()).info("restoreDefaultValue() deleteByRoleId:{}", count);
		}
		return true;
	}

	@Override
	public void updateUserRoleCacheByAdminId(String adminId) {
		List<Role> roles = findByAdminId(adminId);
		String userKey = RedisKey.getUserKey(adminId);
		Boolean exists = redis.exists(userKey);
		if (exists) {
			redis.setHashField(userKey, RedisKey.ROLE, roles);
		}
	}

	@Override
	public void updateUserRoleCacheByRoleId(String roleId) {
		AdminRoleModel adminRoleModel = new AdminRoleModel();
		adminRoleModel.setRoleId(roleId);
		SearchRequest<AdminRoleModel> request = new SearchRequest<>(adminRoleModel);
		List<AdminRole> adminRoles = adminRoleService.searchRequest(request);
		for (AdminRole adminRole : adminRoles) {
			String adminId = adminRole.getAdminId();
			updateUserRoleCacheByAdminId(adminId);
		}
	}

	/**
	 * 获取缓存数据，没有则查数据库
	 *
	 * @param adminId
	 * @return
	 */
	@Override
	public List<Role> getRoles(String adminId) {
		if (adminId == null) {
			return new ArrayList<>();
		}
		String userKey = RedisKey.getUserKey(adminId);
		List<Role> roles = redis.getHashField(userKey, RedisKey.ROLE, Role.class, CollectionType.ListType);
		if (roles == null) {
			roles = findByAdminId(adminId);
		}
		return roles;
	}

	@Override
	public boolean isRoleGtLevel(String adminId, int level){
		if (adminId == null) {
			return false;
		}
		List<Role> roles = getRoles(adminId);
		for (Role role : roles) {
			if (role.getSuperAdmin() < level) {
				return false;  //一旦有一个角色等级小于LEVEL，即返回
			}
		}
		return true;
	}

	@Override
	public boolean isRoleType(String adminId, int roleType) {
		List<Integer> roleTypes = new ArrayList<>();
		roleTypes.add(roleType);
		return isRoleType(adminId, roleTypes);
	}

	/**
	 * 检查是否包含角色用户类型列表里的任意一个
	 *
	 * @param adminId
	 * @param roleTypes 角色用户类型列表
	 * @return
	 */
	@Override
	public boolean isRoleType(String adminId, List<Integer> roleTypes) {
		if (adminId == null) {
			return false;
		}
		List<Role> roles = getRoles(adminId);
		for (Role role : roles) {
			if (roleTypes.contains(role.getSuperAdmin())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Role> findBySuperAdmins(List<Integer> superAdmins) {
		if (superAdmins == null || superAdmins.isEmpty()) {
			return new ArrayList<>();
		}
		return roleMapper.findBySuperAdmins(superAdmins);
	}

	@Override
	public boolean isSuperAdmin(String adminId) {
		if (adminId == null) {
			return false;
		}
		return isRoleType(adminId, RoleType.USER_SUPER_ADMIN);
	}

}
