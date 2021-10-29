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

import com.microservice.admin.entity.AdminPermission;
import com.microservice.admin.entity.Permission;
import com.microservice.admin.entity.PermissionModel;

import common.search.entity.IBaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import org.apache.ibatis.annotations.Param;

public interface IPermissionService extends IBaseService<Permission, String, PermissionModel> {

	public List<AdminPermission> findAdminPermissions(String adminId);

	public PageResult<List<Permission>> pageRequestMenuId(PageRequest<PermissionModel> request);

	public PageResult<List<Permission>> topicPageRequest(PageRequest<String> topicRequest);
	public int topicCount(String where);


	/**
	 * 根据菜单id和角色id获取已有权限
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermission(String menuId, String roleId);
}
