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

import com.microservice.admin.entity.AdminRole;
import com.microservice.admin.entity.AdminRoleExtends;
import com.microservice.admin.entity.AdminRoleExtendsModel;
import com.microservice.admin.entity.AdminRoleModel;

import common.entity.OperateResult;
import common.search.entity.IBaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

public interface IAdminRoleService extends IBaseService<AdminRole, String, AdminRoleModel> {

	public OperateResult<Boolean> update(List<AdminRole> adminRoles);

	public List<AdminRoleExtends> searchRequestExtends(SearchRequest<AdminRoleExtendsModel> request);

	public int countExtends(SearchRequest<AdminRoleExtendsModel> request);

	public PageResult<List<AdminRoleExtends>> pageRequestExtends(PageRequest<AdminRoleExtendsModel> request);

	public OperateResult<Integer> deleteAdminAndRole(AdminRole adminrole);

	int deleteByRoleId(String roleId);

}
