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

import com.microservice.admin.entity.RolePermissionExtends;
import com.microservice.admin.entity.RolePermissionExtendsModel;
import com.microservice.admin.entity.RolePermission;
import com.microservice.admin.entity.RolePermissionModel;

import common.search.entity.BaseMapper;
import common.search.entity.PageRequest;
import common.search.entity.SearchRequest;

public interface RolePermissionMapper extends BaseMapper<RolePermission, String, RolePermissionModel> {

	@Override
	public int updateByMap(@Param("params") Map<String, Object> params);

	public void deleteNotExists(@Param("roleId") String roleId, @Param("permissionIds") List<String> permissionIds, @Param("restore") Integer restore);

	public int batchUpdate(@Param("roleId") String roleId, @Param("permissions") List<RolePermission> permissions);
	
	public int batchAdd(@Param("roleId") String roleId, @Param("permissions") List<RolePermission> permissions);
	
	public RolePermission findByRoleAndPermission( @Param("roleId") String roleId, @Param("permissionId") String permissionId, @Param("restore") Integer restore);
    
	public int countExtends(SearchRequest<RolePermissionExtendsModel> request);

	public List<RolePermissionExtends> searchRequestExtends(SearchRequest<RolePermissionExtendsModel> request);

	public List<RolePermissionExtends> pageRequestExtends(PageRequest<RolePermissionExtendsModel> request);

	public int addSuperAdmin(RolePermission invest);
	
	public int deleteSuperAdmin(RolePermission invest);
	
	public int updateRoleId(RolePermission invest);

	int deleteByRoleId(String roleId, Integer restore);

}
