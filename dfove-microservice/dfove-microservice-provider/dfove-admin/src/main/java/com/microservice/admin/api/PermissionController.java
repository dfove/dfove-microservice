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

@Api(value = "??????????????????", tags = {"??????????????????"})
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

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_CREATE, content = "??????????????????")
	@UserAnnotation
	@ApiOperation(value = "??????????????????")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(Permission permission) {
		if (StringUtil.isEmpty(permission.getPermissionName()) || StringUtil.isEmpty(permission.getApiUrl())) {
			return new OperateResult<String>().failed("???????????????Api?????? ???????????????");
		}
		permission.setPermissionId(UuidUtils.getUUID());
		OperateResult<String> result = permissionService.add(permission);
		// ????????????????????????
		if (result.ok()) {
			RolePermission rolePermission = new RolePermission();
			rolePermission.setPermissionId(permission.getPermissionId());
			rolepermissionService.addSuperAdmin(rolePermission);
		}
		return result;
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_DELETE, content = "??????????????????")
	@ApiOperation(value = "??????????????????")
	@ApiImplicitParam(paramType = "path", required = true, name = "permissionId", value = "??????ID", dataType = "String")
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

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_UPDATE, content = "??????????????????")
	@UserAnnotation
	@ApiOperation(value = "??????????????????")
	@RequestMapping(method = RequestMethod.PUT)
    public OperateResult<Integer> update(Permission permission) {
		if (
		StringUtil.isEmpty(permission.getPermissionName())||
		StringUtil.isEmpty(permission.getApiUrl())){
		return new	OperateResult<Integer>().failed("???????????????Api?????? ???????????????");
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

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "??????????????????")
	@UserAnnotation
	@ApiOperation(value = "??????????????????????????????")
	@ApiImplicitParam(paramType = "path", required = true, name = "permissionId", value = "??????ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{permissionId}")
	public OperateResult<Permission> find(@PathVariable("permissionId") String permissionId) {
		Permission result = permissionService.find(permissionId);
		return new OperateResult<Permission>().success(result);
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "?????????????????????????????????")
	@UserAnnotation
	@ApiOperation(value = "?????????????????????????????????")
	@ApiImplicitParam(paramType = "body", name = "request", value = "????????????(Json?????????)", dataType = "SearchRequest??PermissionModel??")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<Permission>> searchRequest(@RequestBody SearchRequest<PermissionModel> request) {
		List<Permission> result = permissionService.searchRequest(request);
        return new SearchResult<List<Permission>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "??????????????????????????????")
	@UserAnnotation
	@ApiOperation(value = "??????????????????????????????")
	@ApiImplicitParam(paramType = "body", name = "request", value = "????????????(Json?????????)", dataType = "PageRequest??PermissionModel??")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<Permission>> pageRequest(@RequestBody PageRequest<PermissionModel> request) {
		PageResult<List<Permission>> result = permissionService.pageRequest(request);
		return result;
	}
	@ApiOperation(value = "??????????????????????????????~menuid")
	@ApiImplicitParam(paramType = "body", name = "request", value = "????????????(Json?????????)", dataType = "PageRequest??PermissionModel??")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/menuid")
	public PageResult<List<Permission>> pageRequestMenuId(@RequestBody PageRequest<PermissionModel> request) {
//		PageResult<List<Permission>> result = new PageResult<List<Permission>>(permissionService.pageRequestMenuId(request));
		
		return permissionService.pageRequestMenuId(request);
	}
	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????~")
	@ApiImplicitParam(paramType = "body", name = "request", value = "????????????(Json?????????)", dataType = "PageRequest??TopicPermissionModel??")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/topic")
	public PageResult<List<Permission>> topicRequest(@RequestBody PageRequest<TopicPermissionModel> request) {
		PageRequest<String> topicRequest = new PageRequest<String>(request.getData().where(topicSearch),
				request.getOrderBy(), request.getPageSize(), request.getCurrentPage());
		PageResult<List<Permission>> result = permissionService.topicPageRequest(topicRequest);
		return result;
	}

	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "???????????????????????????")
	@UserAnnotation
	@ApiOperation(value = "???????????????????????????")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminId", value = "?????????Id", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/admin/{adminId}")
	public OperateResult<List<AdminPermission>> findAdminPermissions(@PathVariable("adminId") String adminId) {
		List<AdminPermission> result = permissionService.findAdminPermissions(adminId);
		return new OperateResult<List<AdminPermission>>().success(result);
	}

	@ApiOperation(value = "??????????????????(T=A)")
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


	@RecordLog(module = Log.MODULE_PERMISSION, type = Log.OPERATE_QUERY, content = "????????????id?????????id??????????????????")
	@UserAnnotation
	@ApiOperation(value = "????????????id?????????id??????????????????")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", required = true, name = "menuId", value = "??????id", dataType = "String"),
			@ApiImplicitParam(paramType = "path", required = true, name = "roleId", value = "??????id", dataType = "String")
	})
	@RequestMapping(method = RequestMethod.GET, value = "/permission/{menuId}/{roleId}")
	public OperateResult<List<Permission>> getPermission(@PathVariable("menuId") String menuId,@PathVariable("roleId") String roleId) {
		List<Permission> result = permissionService.getPermission(menuId, roleId);
		return new OperateResult<List<Permission>>().success(result);
	}
}
