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

import java.util.List;

import javax.annotation.Resource;

import com.microservice.admin.entity.*;
import com.microservice.admin.service.IRoleService;
import com.microservice.core.App;
import common.annotation.RecordLog;
import common.constant.Log;
import common.constant.RoleType;
import common.entity.UserScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.admin.service.IRolePermissionService;
import com.microservice.core.UserAnnotation;

import common.entity.OperateResult;
import common.logs.Logs;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "角色~权限信息接口", tags = { "角色~权限信息接口" })
@RestController
@RequestMapping("/dfove/v1/rolepermission")
public class RolePermissionController {

	@Resource
	private IRolePermissionService rolepermissionService;
	@Autowired
	private IRoleService roleService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(RolePermissionController.class);

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_CREATE, content = "添加角色权限信息")
	@UserAnnotation
	@ApiOperation(value = "添加角色~权限信息")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(RolePermission rolepermission) {
		OperateResult<String> result = rolepermissionService.add(rolepermission);
		return result;
	}

	@ApiOperation(value = "添加角色~权限信息~超级管理员",hidden=true)
	@RequestMapping(value="/addSuperAdmin",method = RequestMethod.POST)
	public OperateResult<Long> addSuperAdmin(RolePermission rolepermission) {
		OperateResult<Long> result = rolepermissionService.addSuperAdmin(rolepermission);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_DELETE, content = "删除角色权限信息")
	@ApiOperation(value = "删除角色~权限信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "rolePermissionId", value = "角色~权限ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{rolePermissionId}")
	public OperateResult<Boolean> delete(@PathVariable("rolePermissionId") String rolePermissionId) {
		OperateResult<Boolean> result = rolepermissionService.delete(rolePermissionId);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_UPDATE, content = "更新角色权限信息")
	@UserAnnotation
	@ApiOperation(value = "更新角色~权限信息（仅post/delete/put/get权限）")
	@RequestMapping(method = RequestMethod.PUT)
	public OperateResult<Integer> update(RolePermission rolepermission) {
		
		Integer post = rolepermission.getPostPermission();
		Integer delete = rolepermission.getDeletePermission();
		Integer put = rolepermission.getDeletePermission();
		Integer get = rolepermission.getGetPermission();
		if (!validPermission(post) || !validPermission(delete) || !validPermission(put) || !validPermission(get)) {
			return OperateResult.i().failed("权限不能为空并且必须是0（禁止）或1（允许）");
		}
		
		OperateResult<Integer> result = rolepermissionService.update(rolepermission);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_UPDATE, content = "批量更新角色权限")
	@UserAnnotation
	@ApiOperation(value = "批量更新角色~权限")
	@RequestMapping(value = "/batch", method = RequestMethod.PUT)
	public OperateResult<Boolean> update(@RequestBody List<RolePermission> rolePermissions) {
		if (rolePermissions != null && !rolePermissions.isEmpty()) {
			if (!checkEditDefaultValue(rolePermissions)) {
				return new OperateResult<Boolean>().failed("无权限更新默认值");
			}
		}
		OperateResult<Boolean> result = rolepermissionService.update(rolePermissions);
		return result;
	}

	private boolean checkEditDefaultValue(List<RolePermission> rolePermissions){
		boolean isDefault = false;
		for (RolePermission rolePermission : rolePermissions) {
			if(rolePermission.getRestore() == RolePermission.RESTORE_DEFAULT){
				isDefault = true;
				break;
			}
		}
		if(isDefault){
			UserScope userScope = App.userScope();
			if(userScope != null){
				String userId = userScope.getUserId();
				List<Role> roles = roleService.findByAdminId(userId);
				for (Role role : roles) {
					if(RoleType.isSuperAdmin(role.getSuperAdmin())){
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_QUERY, content = "查找角色权限信息")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找角色~权限信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "rolePermissionId", value = "角色~权限ID", dataType = "Long")
	@RequestMapping(method = RequestMethod.GET, value = "/{rolePermissionId}")
	public OperateResult<RolePermission> find(@PathVariable("rolePermissionId") String rolePermissionId) {
		RolePermission result = rolepermissionService.find(rolePermissionId);
		return new OperateResult<RolePermission>().success(result);
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_QUERY, content = "不分页搜索角色权限信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索角色~权限信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«RolePermissionModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<RolePermission>> searchRequest(@RequestBody SearchRequest<RolePermissionModel> request) {
		List<RolePermission> result = rolepermissionService.searchRequest(request);
		return new SearchResult<List<RolePermission>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_QUERY, content = "分页搜索角色权限信息列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索角色~权限信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«RolePermissionModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<RolePermission>> pageRequest(@RequestBody PageRequest<RolePermissionModel> request) {
		try {
			PageResult<List<RolePermission>> result = rolepermissionService.pageRequest(request);
			return result;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_QUERY, content = "不分页搜索角色权限扩展列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索角色~权限扩展列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«RolePermissionExtendsModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/extends")
	public SearchResult<List<RolePermissionExtends>> searchRequestExtends(
			@RequestBody SearchRequest<RolePermissionExtendsModel> request) {
		List<RolePermissionExtends> result = rolepermissionService.searchRequestExtends(request);
		return new SearchResult<List<RolePermissionExtends>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ROLE_PERMISSION, type = Log.OPERATE_QUERY, content = "分页搜索角色权限扩展列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索角色~权限扩展列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«RolePermissionExtendsModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/extends")
	public PageResult<List<RolePermissionExtends>> pageRequestExtends(
			@RequestBody PageRequest<RolePermissionExtendsModel> request) {
		PageResult<List<RolePermissionExtends>> result = rolepermissionService.pageRequestExtends(request);
		return result;
	}
	
	private Boolean validPermission(Integer permission) {
		return permission != null && (permission.equals(1) || permission.equals(0));
	}
}
