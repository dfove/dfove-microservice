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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.microservice.admin.entity.AdminPermission;
import com.microservice.admin.entity.Permission;
import com.microservice.admin.entity.PermissionModel;
import com.microservice.admin.mapper.PermissionMapper;

import common.search.entity.PageRequest;
import common.search.entity.PageResult;

@Service("permissionService")
public class PermissionService  extends TopicService<Permission, String, PermissionModel> implements IPermissionService {

	@Resource
	private PermissionMapper permissionMapper;

    private final static String ITEM_NAME = "权限信息";

	@Override
	public PermissionMapper mapper() {
		return permissionMapper;
	}
    
    @Override
	public String itemName() {
		return ITEM_NAME;
	}
    
    @Override
	public List<AdminPermission> findAdminPermissions(String adminId) {
		List<AdminPermission> result=permissionMapper.findAdminPermissions(adminId);
		return result;
	}

	@Override
	public PageResult<List<Permission>> pageRequestMenuId(PageRequest<PermissionModel> request) {
		
		int totalCount = permissionMapper.countMenuId(request);
//		request.check(totalCount);

		List<Permission> result = permissionMapper.pageRequestMenuId(request);

		return new PageResult<List<Permission>>(result, totalCount, request.getPageSize(), request.getCurrentPage());
//		return ;
//		return new  PageResult<List<Permission>>();
//		return null;
	}

	@Override
	public int topicCount(String where) {
		int result = mapper().topicCount(where);
		return result;
	}

	/**
	 * 根据菜单id和角色id获取已有权限
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	@Override
	public List<Permission> getPermission(String menuId, String roleId) {
		return permissionMapper.getPermission(menuId,roleId);
	}

	@Override
	public PageResult<List<Permission>> topicPageRequest(PageRequest<String> request) {
		String where = request.getData();

		int totalCount = this.topicCount(where);
		request.check(totalCount);

		List<Permission> result = mapper().topicPageRequest(request);

		return new PageResult<List<Permission>>(result, totalCount, request.getPageSize(),
				request.getCurrentPage());

	}
}
