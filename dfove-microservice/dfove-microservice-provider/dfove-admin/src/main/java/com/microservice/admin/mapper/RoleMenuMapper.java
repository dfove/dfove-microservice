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

import com.microservice.admin.entity.RoleMenu;
import com.microservice.admin.entity.RoleMenuExtends;
import com.microservice.admin.entity.RoleMenuExtendsModel;
import com.microservice.admin.entity.RoleMenuModel;

import common.search.entity.BaseMapper;
import common.search.entity.PageRequest;
import common.search.entity.SearchRequest;

public interface RoleMenuMapper extends BaseMapper<RoleMenu, String, RoleMenuModel> {

	@Override
	public int updateByMap(@Param("params") Map<String, Object> params);

	public void deleteNotExists(@Param("roleId") String roleId, @Param("menuIds") List<String> menuIds, @Param("restore") Integer restore);

	public int batchUpdate(@Param("roleId") String roleId, @Param("menus") List<RoleMenu> menus);
	
	public int batchAdd(@Param("roleId") String roleId, @Param("menus") List<RoleMenu> menus);
	
	public RoleMenu findByRoleAndMenu( @Param("roleId") String roleId, @Param("menuId") String menuId, @Param("restore") Integer restore);
	
	public int countExtends(SearchRequest<RoleMenuExtendsModel> request);

	public List<RoleMenuExtends> searchRequestExtends(SearchRequest<RoleMenuExtendsModel> request);

	public List<RoleMenuExtends> pageRequestExtends(PageRequest<RoleMenuExtendsModel> request);

	public int deleteRoleAndMemu(RoleMenu rolemenu);

	int deleteByRoleId(String roleId, Integer restore);

}
