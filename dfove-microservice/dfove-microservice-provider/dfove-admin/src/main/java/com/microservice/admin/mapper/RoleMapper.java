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
package com.microservice.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.microservice.admin.entity.Admin;
import com.microservice.admin.entity.OrganizationRole;
import com.microservice.admin.entity.OrganizationRoleModel;
import com.microservice.admin.entity.Role;
import com.microservice.admin.entity.RoleModel;

import common.search.entity.BaseMapper;
import common.search.entity.SearchRequest;

public interface RoleMapper extends BaseMapper<Role, String, RoleModel> {

    @Override
	public int updateByMap(@Param("params") Map<String, Object> params);

	public List<OrganizationRole> searchRequestRole(SearchRequest<OrganizationRoleModel> request);
	
	public List<Admin> searchRequestRoleAdmin(SearchRequest<OrganizationRoleModel> request);
    
	public List<Role> searchRequestAdmin(SearchRequest<RoleModel> request);

	public List<Role> findByAdminId(@Param("adminId") String adminId);

	List<Role> findBySuperAdmins(List<Integer> superAdmins);

	//根据角色id获取角色信息
	Role getRole(@Param("id")String id);

}
