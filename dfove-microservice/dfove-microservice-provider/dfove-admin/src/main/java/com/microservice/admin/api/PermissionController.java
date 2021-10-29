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

import com.microservice.admin.util.UuidUtils;
import common.annotation.RecordLog;
import common.constant.Log;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.admin.TopicSearch;
import com.microservice.admin.entity.AdminPermission;
import com.microservice.admin.entity.Permission;
import com.microservice.admin.entity.PermissionModel;
import com.microservice.admin.entity.RolePermission;
import com.microservice.admin.entity.topic.TopicPermissionModel;
import com.microservice.admin.service.IPermissionService;
import com.microservice.admin.service.IRolePermissionService;
import com.microservice.core.UserAnnotation;

import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.enums.OperateState;
import common.enums.ScopeType;
import common.logs.Logs;
import common.redis.IRedis;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "权限信息接口", tags = {"权限信息接口"})
@RestController
@RequestMapping("/dfove/v1/permission")
public class PermissionController {

	@Autowired
	private IRedis redis;
	
	@Resource
	private IPermissionService permissionService;
	
	@Autowired
	private IRolePermissionService rolepermissionService;
	
	@Autowired
	private TopicSearch topicSearch;
	
	@SuppressWarnings("unused")
	private static Logs _log = new Logs(PermissionController.class);

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_CREATE, content = "添加权限信息")
	@UserAnnotation
	@ApiOperation(value = "添加权限信息")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(Permission permission) {
		if (StringUtil.isEmpty(permission.getPermissionName()) || StringUtil.isEmpty(permission.getApiUrl())) {
			return new OperateResult<String>().failed("权限名称、Api地址 不能为空！");
		}
		permission.setPermissionId(UuidUtils.getUUID());
		OperateResult<String> result = permissionService.add(permission);
		// 添加超级管理权限
		if (result.ok()) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(permission.getPermissionId());
			rolepermissionService.addSuperAdmin(rolePermission);
		}
		return result;
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_DELETE, content = "删除权限信息")
	@ApiOperation(value = "删除权限信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "permissionId", value = "权限ID", dataType = "String")
    @UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{permissionId}")
	public OperateResult<Boolean> delete(@PathVariable("permissionId") String permissionId) {
        OperateResult<Boolean> result = permissionService.delete(permissionId);
		if (result.ok()) {				
			RolePermission rolePermission=new RolePermission();
			rolePermission.setPermissionId(permissionId);
			rolepermissionService.deleteSuperAdmin(rolePermission);
		}
		return result;
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_UPDATE, content = "更新权限信息")
	@UserAnnotation
	@ApiOperation(value = "更新权限信息")
	@RequestMapping(method = RequestMethod.PUT)
    public OperateResult<Integer> update(Permission permission) {
		if (
		StringUtil.isEmpty(permission.getPermissionName())||
		StringUtil.isEmpty(permission.getApiUrl())){
		return new	OperateResult<Integer>().failed("权限名称、Api地址 不能为空！");
		}	
		OperateResult<Integer> result = permissionService.update(permission);
		if (result.ok()) {				
			RolePermission rolePermission=new RolePermission();
			rolePermission.setPermissionId(permission.getPermissionId());
			rolePermission.setGetPermission(permission.getGetPermission());
			rolePermission.setPostPermission(permission.getPostPermission());
			rolePermission.setDeletePermission(permission.getDeletePermission());
			rolePermission.setPutPermission(permission.getPutPermission());
			rolepermissionService.updateRoleId(rolePermission);//deleteSuperAdmin(rolePermission);
		}
		return result;
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "查找权限信息")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找权限信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "permissionId", value = "权限ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{permissionId}")
	public OperateResult<Permission> find(@PathVariable("permissionId") String permissionId) {
		Permission result = permissionService.find(permissionId);
		return new OperateResult<Permission>().success(result);
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "不分页搜索权限信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索权限信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«PermissionModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<Permission>> searchRequest(@RequestBody SearchRequest<PermissionModel> request) {
		List<Permission> result = permissionService.searchRequest(request);
        return new SearchResult<List<Permission>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "分页搜索权限信息列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索权限信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«PermissionModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<Permission>> pageRequest(@RequestBody PageRequest<PermissionModel> request) {
		PageResult<List<Permission>> result = permissionService.pageRequest(request);
		return result;
	}
	@ApiOperation(value = "分页搜索权限信息列表~menuid")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«PermissionModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/menuid")
	public PageResult<List<Permission>> pageRequestMenuId(@RequestBody PageRequest<PermissionModel> request) {
//		PageResult<List<Permission>> result = new PageResult<List<Permission>>(permissionService.pageRequestMenuId(request));
		
		return permissionService.pageRequestMenuId(request);
	}
	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "组合查询")
	@UserAnnotation
	@ApiOperation(value = "组合查询~")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«TopicPermissionModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/topic")
	public PageResult<List<Permission>> topicRequest(@RequestBody PageRequest<TopicPermissionModel> request) {
		PageRequest<String> topicRequest = new PageRequest<String>(request.getData().where(topicSearch),
				request.getOrderBy(), request.getPageSize(), request.getCurrentPage());
		PageResult<List<Permission>> result = permissionService.topicPageRequest(topicRequest);
		return result;
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "获取管理员权限列表")
	@UserAnnotation
	@ApiOperation(value = "获取管理员权限列表")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminId", value = "管理员Id", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/admin/{adminId}")
	public OperateResult<List<AdminPermission>> findAdminPermissions(@PathVariable("adminId") String adminId) {
		List<AdminPermission> result = permissionService.findAdminPermissions(adminId);
		return new OperateResult<List<AdminPermission>>().success(result);
	}

	@ApiOperation(value = "清空权限缓存(T=A)")
	@RequestMapping(method = RequestMethod.DELETE, value = "/redis")
	@UserAnnotation(scopes = { ScopeType.admin })
	public OperateResult<Long> redis() {
		try {
			String keys="jnyc.admin.permissions.*";
			Long result = redis.dels(keys);

			return new OperateResult<Long>(result,  OperateState.Success ,null,"200");
		} catch (Exception e) {
			e.printStackTrace();
			return new OperateResult<Long>().failed(e.getMessage());
		}
	}


	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "根据菜单id和角色id获取已有权限")
	@UserAnnotation
	@ApiOperation(value = "根据菜单id和角色id获取已有权限")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", required = true, name = "menuId", value = "菜单id", dataType = "String"),
			@ApiImplicitParam(paramType = "path", required = true, name = "roleId", value = "角色id", dataType = "String")
	})
	@RequestMapping(method = RequestMethod.GET, value = "/permission/{menuId}/{roleId}")
	public OperateResult<List<Permission>> getPermission(@PathVariable("menuId") String menuId,@PathVariable("roleId") String roleId) {
		List<Permission> result = permissionService.getPermission(menuId, roleId);
		return new OperateResult<List<Permission>>().success(result);
	}
}
