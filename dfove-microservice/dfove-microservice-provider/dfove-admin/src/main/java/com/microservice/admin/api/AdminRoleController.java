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
package com.microservice.admin.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.microservice.admin.entity.*;
import com.microservice.admin.service.IRoleService;
import common.annotation.RecordLog;
import common.constant.Log;

import common.constant.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.admin.service.IAdminRoleService;
import com.microservice.core.UserAnnotation;

import common.entity.OperateResult;
import common.enums.OperateState;
import common.logs.Logs;
import common.redis.IRedis;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "管理员~角色信息接口", tags = { "管理员~角色信息接口" })
@RestController
@RequestMapping("/dfove/v1/adminrole")
public class AdminRoleController {
	
	@Autowired
	private IRedis redis;
	
	@Resource
	private IAdminRoleService adminroleService;
	@Autowired
	private IRoleService roleService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(AdminRoleController.class);

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_CREATE, content = "添加管理员角色信息")
	@UserAnnotation
	@ApiOperation(value = "添加管理员~角色信息")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(AdminRole adminrole) {
		OperateResult<String> result = adminroleService.add(adminrole);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_DELETE, content = "删除管理员角色信息")
	@UserAnnotation
	@SuppressWarnings("unlikely-arg-type")
	@ApiOperation(value = "删除管理员~角色信息")
	@RequestMapping(method = RequestMethod.DELETE)
	public OperateResult<Integer> delete(AdminRole adminrole) {
		if (adminrole.getAdminId()==null|| "".equals(adminrole.getAdminId())||adminrole.getRoleId()==null|| "".equals(adminrole.getRoleId())) {
			return new OperateResult<Integer>().failed("用户id和角色id不能为空");
		}
		if (roleService.isSuperAdmin(adminrole.getAdminId())) {
			return new OperateResult<Integer>().failed("不能删除超级管理员用户角色");
		}
		OperateResult<Integer> result = adminroleService.deleteAdminAndRole(adminrole);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_DELETE, content = "删除管理员角色信息")
	@ApiOperation(value = "删除管理员~角色信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminRoleId", value = "管理员~角色ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{adminRoleId}")
	public OperateResult<Boolean> delete(@PathVariable("adminRoleId") String adminRoleId) {
		if (roleService.isSuperAdmin(adminRoleId)) {
			return new OperateResult<Boolean>().failed("不能删除超级管理员用户角色");
		}
		OperateResult<Boolean> result = adminroleService.delete(adminRoleId);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_UPDATE, content = "批量更新管理员角色")
	@UserAnnotation
	@ApiOperation(value = "批量更新管理员~角色")
	@RequestMapping(value = "/batch", method = RequestMethod.PUT)
	public OperateResult<Boolean> update(@RequestBody List<AdminRole> adminRoles) {
		if (adminRoles == null || adminRoles.isEmpty()) {
			return new OperateResult<Boolean>().failed("参数不正确");
		}
		for (AdminRole adminRole : adminRoles) {
			if (roleService.isSuperAdmin(adminRole.getAdminId())) {
				return new OperateResult<Boolean>().failed("不能修改超级管理员用户角色");
			}
		}
		OperateResult<Boolean> result = adminroleService.update(adminRoles);
		try {
			String keys="jnyc.admin.permissions.*";
			redis.dels(keys);
		} catch (Exception e) {
		}
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_QUERY, content = "查找管理员角色信息")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找管理员~角色信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminRoleId", value = "管理员~角色ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{adminRoleId}")
	public OperateResult<AdminRole> find(@PathVariable("adminRoleId") String adminRoleId) {
		AdminRole result = adminroleService.find(adminRoleId);
		return new OperateResult<AdminRole>().success(result);
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_QUERY, content = "分页搜索管理员角色信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索管理员~角色信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«AdminRoleModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<AdminRole>> searchRequest(@RequestBody SearchRequest<AdminRoleModel> request) {
		List<AdminRole> result = adminroleService.searchRequest(request);
		return new SearchResult<List<AdminRole>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_QUERY, content = "分页搜索管理员角色信息列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索管理员~角色信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«AdminRoleModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<AdminRole>> pageRequest(@RequestBody PageRequest<AdminRoleModel> request) {
		PageResult<List<AdminRole>> result = adminroleService.pageRequest(request);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_QUERY, content = "不分页搜索管理员角色扩展列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索管理员~角色扩展列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«AdminRoleExtendsModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/extends")
	public SearchResult<List<AdminRoleExtends>> searchRequestExtends(
			@RequestBody SearchRequest<AdminRoleExtendsModel> request) {
		List<AdminRoleExtends> result = adminroleService.searchRequestExtends(request);
		return new SearchResult<List<AdminRoleExtends>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ADMIN_ROLE, type = Log.OPERATE_QUERY, content = "分页搜索管理员角色扩展列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索管理员~角色扩展列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«AdminRoleExtendsModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/extends")
	public PageResult<List<AdminRoleExtends>> pageRequestExtends(
			@RequestBody PageRequest<AdminRoleExtendsModel> request) {
		PageResult<List<AdminRoleExtends>> result = adminroleService.pageRequestExtends(request);
		return result;
	}
}
