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
import com.microservice.admin.entity.AdminModel;

import common.entity.OperateResult;
import common.search.entity.IBaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.vo.AdminVo;

public interface IAdminService extends IBaseService<Admin, String, AdminModel> {

	public Admin findName(String adminName);

	public Admin findOthers(String adminId, String name, String phone, String email);

	public OperateResult<Integer> updatePassword(String adminId, String oldPassword, String newPassword);

	public OperateResult<Integer> resetPassword(String adminId, String newPassword);
	
	public List<Admin> searchRequestSuperAdminRole(String adminId,PageRequest<AdminModel> request);

	public PageResult<List<Admin>> pageRequestSuperAdmin(PageRequest<AdminModel> request, String adminId);
	
	public List<Long> findByOrgType(String orgType);

	Admin findByAdminId(String adminId);

	List<AdminVo> getAdminList(List<String> adminIds);

	SearchResult<List<Admin>> searchRequest(SearchRequest<AdminModel> request, String superAdminId);

	List<Admin> getOrganizationAdmin(String organizationId);

}
