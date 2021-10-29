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

import com.microservice.admin.entity.AdminPermission;
import com.microservice.admin.entity.Permission;
import com.microservice.admin.entity.PermissionModel;

import common.search.entity.PageRequest;
import common.search.entity.SearchRequest;

public interface PermissionMapper extends TopicMapper<Permission, String, PermissionModel> {

    @Override
	public int updateByMap(@Param("params") Map<String, Object> params);

	public List<AdminPermission> findAdminPermissions(String adminId);

	public List<Permission> pageRequestMenuId(PageRequest<PermissionModel> request);
	
	public int countMenuId(SearchRequest<PermissionModel> request);
	
	public int topicCount(@Param("where") String where);
	public List<Permission> topicPageRequest(PageRequest<String> request);

	/**
	 * 根据用户id获取权限标识
	 * @param adminId
	 * @return
	 */
	public  List<String> getPermissionIdent(@Param("adminId") String adminId,@Param("menuId")String menuId);

	/**
	 * 根据用户id和父菜单id获取子类权限标识
	 * @param adminId
	 * @return
	 */
	public  List<String> getSubClassIdent(@Param("adminId") String adminId,@Param("menuId")String menuId);

	/**
	 * 根据菜单id和角色id获取已有权限
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermission(@Param("menuId") String menuId,@Param("roleId") String roleId);
}
