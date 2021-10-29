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

import com.microservice.admin.entity.AdminRole;
import com.microservice.admin.entity.AdminRoleExtends;
import com.microservice.admin.entity.AdminRoleExtendsModel;
import com.microservice.admin.entity.AdminRoleModel;

import common.search.entity.BaseMapper;
import common.search.entity.PageRequest;
import common.search.entity.SearchRequest;

public interface AdminRoleMapper extends BaseMapper<AdminRole, String, AdminRoleModel> {

	@Override
	public int updateByMap(@Param("params") Map<String, Object> params);

	public int deleteNotExists(@Param("adminId") String adminId, @Param("roleIds") List<String> roleIds);

	public int batchAdd(@Param("adminId") String adminId, @Param("roleIds") List<String> roleIds);

	public AdminRole findByAdminAndRole(@Param("adminId") String adminId, @Param("roleId") String roleId);

	public int countExtends(SearchRequest<AdminRoleExtendsModel> request);

	public List<AdminRoleExtends> searchRequestExtends(SearchRequest<AdminRoleExtendsModel> request);

	public List<AdminRoleExtends> pageRequestExtends(PageRequest<AdminRoleExtendsModel> request);
	
	public int deleteAdminAndRole(AdminRole adminrole);

	int deleteByRoleId(String roleId);

	/**
	 * 根据 用户id获取角色id
	 * @param adminId
	 * @return
	 */
	public String findByAdminId(@Param("adminId")String adminId);

}
