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
import com.microservice.admin.service.IAdminRoleService;
import com.microservice.admin.util.UuidUtils;
import com.microservice.core.App;
import common.annotation.RecordLog;
import common.constant.Log;
import common.constant.RoleType;
import common.entity.UserScope;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.admin.service.IRoleService;
import com.microservice.core.UserAnnotation;

import common.entity.OperateResult;
import common.logs.Logs;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "角色信息接口", tags = { "角色信息接口" })
@RestController
@RequestMapping("/dfove/v1/role")
public class RoleController {

	@Resource
	private IRoleService roleService;
	@Autowired
	private IAdminRoleService adminRoleService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(RoleController.class);

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_CREATE, content = "添加角色信息")
	@UserAnnotation
	@ApiOperation(value = "添加角色信息")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(Role role) {
		if (
		StringUtil.isEmpty(role.getRoleName())){
		return new	OperateResult<String>().failed("角色名称 不能为空！");
		}
		if (role.getType() == null || role.getSuperAdmin() == null) {
			return new OperateResult<String>().failed("角色用户类型不能为空");
		}
		if (role.getSuperAdmin() == RoleType.USER_SUPER_ADMIN
				|| role.getType() == RoleType.SYSTEM) {
			return new OperateResult<String>().failed("内置角色不给予添加");
		}
		role.setRoleId(UuidUtils.getUUID());
		OperateResult<String> result = roleService.add(role);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_DELETE, content = "删除角色信息")
	@ApiOperation(value = "删除角色信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "roleId", value = "角色ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{roleId}")
	public OperateResult<Boolean> delete(@PathVariable("roleId") String roleId) {
		Role admin = roleService.find(roleId);
		if (admin.getSuperAdmin().equals(1)) {
			return new OperateResult<Boolean>().failed("内置权限不给予删除");
		}
		if (admin.getType() == RoleType.SYSTEM) {
			return new OperateResult<Boolean>().failed("内置角色不给予删除");
		}
		SearchRequest<AdminRoleModel> request = new SearchRequest<>();
		AdminRoleModel adminRoleModel = new AdminRoleModel();
		adminRoleModel.setRoleId(roleId);
		request.setData(adminRoleModel);
		List<AdminRole> adminRoles = adminRoleService.searchRequest(request);
		if (!adminRoles.isEmpty()) {
			return new OperateResult<Boolean>().failed("有用户正在使用该角色，不给予删除");
		}
		OperateResult<Boolean> result = roleService.delete(roleId);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_UPDATE, content = "更新角色信息")
	@UserAnnotation
	@ApiOperation(value = "更新角色信息")
	@RequestMapping(method = RequestMethod.PUT)
	public OperateResult<Integer> update(Role role) {
		if (
		StringUtil.isEmpty(role.getRoleName())){
		return new	OperateResult<Integer>().failed("角色名称 不能为空！");
		}
		if (role.getSuperAdmin() == RoleType.USER_SUPER_ADMIN
				|| role.getType() == RoleType.SYSTEM) {
			return new OperateResult<Integer>().failed("内置角色不给予更新");
		}
		OperateResult<Integer> result = roleService.update(role);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_QUERY, content = "查找角色信息")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找角色信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "roleId", value = "角色ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{roleId}")
	public OperateResult<Role> find(@PathVariable("roleId") String roleId) {
		Role result = roleService.find(roleId);
		return new OperateResult<Role>().success(result);
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_QUERY, content = "不分页搜索角色信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索角色信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«RoleModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<Role>> searchRequest(@RequestBody SearchRequest<RoleModel> request) {
		List<Role> result = roleService.searchRequest(request);
		return new SearchResult<List<Role>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_QUERY, content = "不分页搜索机构管理用户角色列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索机构管理~用户角色列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«OrganizationRoleModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/roleuser")
	public SearchResult<List<OrganizationRole>> searchRequestRole(@RequestBody SearchRequest<OrganizationRoleModel> request) {

		List<OrganizationRole> result = null;
		result = roleService.searchRequestRole(request);
		return new SearchResult<List<OrganizationRole>>(result);
	}
	@ApiOperation(value = "不分页搜索机构管理~用户角色ID查询",hidden=true)
	/*@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)")*//*, dataType = "SearchRequest«OrganizationRoleModel»"*/
	@RequestMapping(method = RequestMethod.GET, value = "/search/roleuser1/{roleId}")
	public SearchResult<List<OrganizationRole>> searchRequestRoleuser(@PathVariable("roleId") String roleId) {
		OrganizationRoleModel orm=new OrganizationRoleModel();
		orm.setRoleId(roleId);
		List<OrganizationRole> result  = roleService.searchRequestRole(new SearchRequest<OrganizationRoleModel>(orm));
		return new SearchResult<List<OrganizationRole>>(result);
	}
	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_QUERY, content = "不分页搜索机构管理用户角色查询")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索机构管理~用户角色ID查询")
	/*@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)")*//*, dataType = "SearchRequest«OrganizationRoleModel»"*/
	@RequestMapping(method = RequestMethod.GET, value = "/search/roleuser/{roleId}")
	public SearchResult<List<Admin>> searchRequestRoleAdmin(@PathVariable("roleId") String roleId) {
		if (roleId==null) {
			return new SearchResult<List<Admin>>().failed("roleId不能为空!");
		}
		OrganizationRoleModel orm=new OrganizationRoleModel();
		orm.setRoleId(roleId);
		List<Admin> result = null;
		result = roleService.searchRequestRoleAdmin(new SearchRequest<OrganizationRoleModel>(orm));
		return new SearchResult<List<Admin>>(result);
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_QUERY, content = "分页搜索角色信息列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索角色信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«RoleModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<Role>> pageRequest(@RequestBody PageRequest<RoleModel> request) {
		PageResult<List<Role>> result = roleService.pageRequest(request);
		return result;
	}

	@ApiOperation(value = "根据用户id返回角色")
	@RequestMapping(method = RequestMethod.POST, value = "/find/adminId")
	public OperateResult<List<Role>> pageRequest(String adminId) {
		List<Role> result = roleService.findByAdminId(adminId);
		return new OperateResult<List<Role>>().success(result);
	}

	@RecordLog(module = Log.MODULE_ROLE, type = Log.OPERATE_UPDATE, content = "恢复角色默认值")
	@UserAnnotation
	@ApiOperation(value = "恢复角色默认值")
	@ApiImplicitParam(paramType = "query", name = "roleId", value = "角色id", dataType = "String")
	@RequestMapping(method = RequestMethod.PUT, value = "/restore")
	public OperateResult<Boolean> restoreDefaultValue(String roleId) {
		if (roleId == null) {
			return new SearchResult<Boolean>().failed("参数不能为空!");
		}
		if (App.userScope() == null) {
			return new SearchResult<Boolean>().failed("没有权限");
		}
		if (!roleService.isSuperAdmin(App.userScope().getUserId())) {
			return new SearchResult<Boolean>().failed("没有权限");
		}
		boolean result = roleService.restoreDefaultValue(roleId);
		return new OperateResult<Boolean>().success(result);
	}

}
