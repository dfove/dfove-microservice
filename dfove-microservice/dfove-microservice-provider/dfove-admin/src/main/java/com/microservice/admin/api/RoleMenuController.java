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
import com.microservice.admin.service.IMenuService;
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

import com.microservice.admin.service.IRoleMenuService;
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

@Api(value = "角色~菜单信息接口", tags = { "角色~菜单信息接口" })
@RestController
@RequestMapping("/dfove/v1/rolemenu")
public class RoleMenuController {

	@Resource
	private IRoleMenuService rolemenuService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(RoleMenuController.class);

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_CREATE, content = "添加角色菜单信息")
	@UserAnnotation
	@ApiOperation(value = "添加角色~菜单信息")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(RoleMenu rolemenu) {
		if (rolemenu == null || rolemenu.getRoleId() == null || rolemenu.getMenuId() == null || rolemenu.getRestore() == null) {
			return new OperateResult<String>().failed("参数不能为空");
		}
		if (rolemenu.getRestore() == RoleMenu.RESTORE_DEFAULT && !isSuperAdmin()) {
			return new OperateResult<String>().failed("无权限更新默认值");
		}
		OperateResult<String> result = rolemenuService.add(rolemenu);
		return result;
	}
	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_DELETE, content = "删除角色菜单信息")
	@UserAnnotation
	@ApiOperation(value = "删除角色~菜单信息")
	@RequestMapping(method = RequestMethod.DELETE)
	public OperateResult<Integer> delete(RoleMenu rolemenu) {
		if (rolemenu == null || rolemenu.getMenuId() == null || rolemenu.getRoleId() == null || rolemenu.getRestore() == null) {
			return new OperateResult<Integer>().failed("参数不能为空");
		}
		if (rolemenu.getRestore() == RoleMenu.RESTORE_DEFAULT && !isSuperAdmin()) {
			return new OperateResult<Integer>().failed("无权限更新默认值");
		}
		OperateResult<Integer> result = rolemenuService.deleteRoleAndMemu(rolemenu);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_DELETE, content = "删除角色菜单信息")
	@ApiOperation(value = "删除角色~菜单信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "roleMenuId", value = "角色~菜单ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{roleMenuId}")
	public OperateResult<Boolean> delete(@PathVariable("roleMenuId") String roleMenuId) {
		OperateResult<Boolean> result = rolemenuService.delete(roleMenuId);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_UPDATE, content = "更新角色菜单排序信息")
	@UserAnnotation
	@ApiOperation(value = "更新角色~菜单信息（仅排序）")
	@RequestMapping(method = RequestMethod.PUT)
	public OperateResult<Integer> update(RoleMenu rolemenu) {
		Long order = rolemenu.getMenuOrder();
		if (order == null) {
			return OperateResult.i().failed("排序必须为整数");
		}

		OperateResult<Integer> result = rolemenuService.update(rolemenu);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_UPDATE, content = "批量更新角色菜单")
	@UserAnnotation
	@ApiOperation(value = "批量更新角色~菜单")
	@RequestMapping(value = "/batch", method = RequestMethod.PUT)
	public OperateResult<Boolean> update(@RequestBody List<RoleMenu> roleMenus) {
//		if (roleMenus != null && !roleMenus.isEmpty()) {
//			if (!checkEditDefaultValue(roleMenus)) {
//				return new OperateResult<Boolean>().failed("无权限更新默认值");
//			}
//		}
		OperateResult<Boolean> result = rolemenuService.update(roleMenus);
		return result;
	}

	private boolean checkEditDefaultValue(List<RoleMenu> roleMenus){
		boolean isDefault = false;
		for (RoleMenu roleMenu : roleMenus) {
			if(roleMenu.getRestore() == RoleMenu.RESTORE_DEFAULT){
				isDefault = true;
				break;
			}
		}
		if(isDefault){
			return isSuperAdmin();
		}
		return true;
	}

	private boolean isSuperAdmin(){
		UserScope userScope = App.userScope();
		if(userScope != null){
			String userId = userScope.getUserId();
			List<Role> roles = roleService.findByAdminId(userId);
			for (Role role : roles) {
				if (RoleType.isSuperAdmin(role.getSuperAdmin())) {
					return true;
				}
			}
		}
		return false;
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_QUERY, content = "查找角色菜单信息")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找角色~菜单信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "roleMenuId", value = "角色~菜单ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{roleMenuId}")
	public OperateResult<RoleMenu> find(@PathVariable("roleMenuId") String roleMenuId) {
		RoleMenu result = rolemenuService.find(roleMenuId);
		return new OperateResult<RoleMenu>().success(result);
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_QUERY, content = "不分页搜索角色菜单信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索角色~菜单信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«RoleMenuModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<RoleMenu>> searchRequest(@RequestBody SearchRequest<RoleMenuModel> request) {
		List<RoleMenu> result = rolemenuService.searchRequest(request);
		return new SearchResult<List<RoleMenu>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_QUERY, content = "分页搜索角色菜单信息列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索角色~菜单信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«RoleMenuModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<RoleMenu>> pageRequest(@RequestBody PageRequest<RoleMenuModel> request) {
		PageResult<List<RoleMenu>> result = rolemenuService.pageRequest(request);
		return result;
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_QUERY, content = "不分页搜索角色菜单扩展列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索角色~菜单扩展列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«RoleMenuExtendsModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/extends")
	public SearchResult<List<RoleMenuExtends>> searchRequestExtends(
			@RequestBody SearchRequest<RoleMenuExtendsModel> request) {
		if (request == null || request.getData() == null || request.getData().getRoleId() == null) {
			return new SearchResult<List<RoleMenuExtends>>().failed("参数不正确");
		}
		Role superAdminRole = null;
		Role role = roleService.find(request.getData().getRoleId());
		if (role != null && RoleType.isSuperAdmin(role.getSuperAdmin())) {
			superAdminRole = role;
		}
		List<RoleMenuExtends> result = null;
		if (superAdminRole == null) {
			result = rolemenuService.searchRequestExtends(request);
		} else {
			SearchRequest<MenuModel> menuRequest = new SearchRequest<>();
			menuRequest.setData(new MenuModel());
			List<Menu> menus = menuService.searchRequest(menuRequest);
			result = new ArrayList<>();
			for (Menu menu : menus) {
				RoleMenuExtends roleMenuExtend = new RoleMenuExtends();
				roleMenuExtend.setMenuId(menu.getMenuId());
				roleMenuExtend.setMenuName(menu.getMenuName());
				roleMenuExtend.setMenuUrl(menu.getMenuUrl());
				roleMenuExtend.setMenuType(menu.getMenuType() != null ? menu.getMenuType().intValue() : null);
				roleMenuExtend.setMenuOrder(menu.getMenuOrder());
				roleMenuExtend.setRoleId(superAdminRole.getRoleId());
				roleMenuExtend.setRoleName(superAdminRole.getRoleName());
				roleMenuExtend.setRestore(RoleMenu.RESTORE_NOT);
				result.add(roleMenuExtend);
			}
		}
		return new SearchResult<List<RoleMenuExtends>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ROLE_MENU, type = Log.OPERATE_QUERY, content = "分页搜索角色菜单扩展列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索角色~菜单扩展列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«RoleMenuExtendsModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page/extends")
	public PageResult<List<RoleMenuExtends>> pageRequestExtends(
			@RequestBody PageRequest<RoleMenuExtendsModel> request) {
		PageResult<List<RoleMenuExtends>> result = rolemenuService.pageRequestExtends(request);
		return result;
	}
}
