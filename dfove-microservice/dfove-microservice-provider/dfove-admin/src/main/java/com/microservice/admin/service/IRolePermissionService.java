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

import com.microservice.admin.entity.RolePermission;
import com.microservice.admin.entity.RolePermissionExtends;
import com.microservice.admin.entity.RolePermissionExtendsModel;
import com.microservice.admin.entity.RolePermissionModel;

import common.entity.OperateResult;
import common.search.entity.IBaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

public interface IRolePermissionService extends IBaseService<RolePermission, String, RolePermissionModel> {

	public OperateResult<Boolean> update(List<RolePermission> rolePermission);

	public int countExtends(SearchRequest<RolePermissionExtendsModel> request);

	public List<RolePermissionExtends> searchRequestExtends(SearchRequest<RolePermissionExtendsModel> request);

	public PageResult<List<RolePermissionExtends>> pageRequestExtends(PageRequest<RolePermissionExtendsModel> request);
	
	public OperateResult<Long> addSuperAdmin(RolePermission rolePermission);
	
	public OperateResult<Long> deleteSuperAdmin(RolePermission rolePermission);
	
	public OperateResult<Integer> updateRoleId(RolePermission rolePermission);

	int deleteByRoleId(String roleId, Integer restore);

}
