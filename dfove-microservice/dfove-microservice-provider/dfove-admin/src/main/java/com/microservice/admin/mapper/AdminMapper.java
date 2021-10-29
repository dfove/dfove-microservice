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

import common.search.entity.SearchResult;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import com.microservice.admin.entity.Admin;
import com.microservice.admin.entity.AdminModel;

import common.search.entity.BaseMapper;
import common.search.entity.PageRequest;

public interface AdminMapper extends BaseMapper<Admin, String, AdminModel> {

	Admin findByAdminName(String adminName);
	
	Admin findVerification(Admin admin);

	Admin findOthers(@Param("adminId") String adminId, @Param("name") String name, @Param("phone") String phone, @Param("email") String email);
	
	@Override
	public int updateByMap(@Param("params") Map<String, Object> params);
	
	public List<Admin> searchRequestSuperAdminRole(@Param("request")PageRequest<AdminModel> request,String adminId);
	
	public List<Long> findByOrgType(String orgType);

	List<Admin> getAdminList(List<String> ids);

	//根据机构id获取机构账户
	List<Admin> getOrganizationAdmin(@Param("organizationId") String organizationId);

}
