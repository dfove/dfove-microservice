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

import java.util.List;

import com.microservice.admin.entity.Admin;
import com.microservice.admin.entity.OrganizationRole;
import com.microservice.admin.entity.OrganizationRoleModel;
import com.microservice.admin.entity.Role;
import com.microservice.admin.entity.RoleModel;

import common.search.entity.IBaseService;
import common.search.entity.SearchRequest;
import org.apache.ibatis.annotations.Param;

public interface IRoleService extends IBaseService<Role, String, RoleModel> {
	List<OrganizationRole> searchRequestRole(SearchRequest<OrganizationRoleModel> request);

	List<Admin> searchRequestRoleAdmin(SearchRequest<OrganizationRoleModel> request);
	// 其他自行定义
	
	public List<Role> searchRequestAdmin(SearchRequest<RoleModel> request);

	public List<Role> findByAdminId(@Param("adminId") String adminId);

	boolean restoreDefaultValue(String roleId);

	void updateUserRoleCacheByAdminId(String roleId);

	void updateUserRoleCacheByRoleId(String roleId);

	List<Role> getRoles(String adminId);

	boolean isRoleGtLevel(String adminId, int level);

	boolean isRoleType(String adminId, int roleType);

	boolean isRoleType(String adminId, List<Integer> roleType);

	List<Role> findBySuperAdmins(List<Integer> superAdmins);

	boolean isSuperAdmin(String adminId);

}
