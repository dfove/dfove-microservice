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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.admin.entity.AdminMenu;
import com.microservice.admin.entity.Menu;
import com.microservice.admin.entity.MenuModel;
import com.microservice.admin.entity.MenuPermission;
import com.microservice.admin.service.IMenuService;
import com.microservice.core.App;
import com.microservice.core.UserAnnotation;

import common.entity.OperateResult;
import common.enums.ScopeType;
import common.logs.Logs;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "菜单信息接口", tags = { "菜单信息接口" })
@RestController
@RequestMapping("/dfove/v1/menu")
public class MenuController {

	@Resource
	private IMenuService menuService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(MenuController.class);

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_CREATE, content = "添加菜单信息")
	@UserAnnotation
	@ApiOperation(value = "添加菜单信息")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(Menu menu) {
		if (
		StringUtil.isEmpty(menu.getMenuName())||
		menu.getParentId()==null){
		return new	OperateResult<String>().failed("父id、菜单名称 不能为空！");
		}
		menu.setMenuId(UuidUtils.getUUID());
		OperateResult<String> result = menuService.add(menu);
		return result;
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_DELETE, content = "删除菜单信息")
	@ApiOperation(value = "删除菜单信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "menuId", value = "菜单ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{menuId}")
	public OperateResult<Boolean> delete(@PathVariable("menuId") String menuId) {
		
			List<Menu> list=menuService.findChildrenMenus(menuId);
			if (list!=null&&list.size()>0) {
				return new OperateResult<Boolean>().failed("该菜单存在下级菜单无法删除!");
			}
			OperateResult<Boolean> result = menuService.delete(menuId);
			return result;
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_UPDATE, content = "更新菜单信息")
	@UserAnnotation
	@ApiOperation(value = "更新菜单信息")
	@RequestMapping(method = RequestMethod.PUT)
	public OperateResult<Integer> update(Menu menu) {
		if (
		StringUtil.isEmpty(menu.getMenuName())||
		menu.getParentId()==null){
		return new	OperateResult<Integer>().failed("父id、菜单名称 不能为空！");
		}	
		OperateResult<Integer> result = menuService.update(menu);
		return result;
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_QUERY, content = "查找菜单信息")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找菜单信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "menuId", value = "菜单ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{menuId}")
	public OperateResult<Menu> find(@PathVariable("menuId") String menuId) {
		Menu result = menuService.find(menuId);
		return new OperateResult<Menu>().success(result);
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_QUERY, content = "不分页搜索菜单信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索菜单信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«MenuModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<Menu>> searchRequest(@RequestBody SearchRequest<MenuModel> request) {
		List<Menu> result = menuService.searchRequest(request);
		return new SearchResult<List<Menu>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_QUERY, content = "不分页搜索级联菜单信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索菜单信息列表~级联")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«MenuModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/searchs")
	public SearchResult<List<Menu>> searchRequests(@RequestBody SearchRequest<MenuModel> request) {
		try {
			
			List<Menu> result = menuService.searchRequests(request);
			return new SearchResult<List<Menu>>(result, result.size());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_QUERY, content = "不分页搜权限索菜单信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索菜单信息列表~权限")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«MenuModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/permission")
	public SearchResult<List<MenuPermission>> searchRequestToPermission(@RequestBody SearchRequest<MenuModel> request) {
			List<MenuPermission> result = menuService.searchRequestPermission(request);
			return new SearchResult<List<MenuPermission>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_QUERY, content = "分页搜索菜单信息列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索菜单信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«MenuModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<Menu>> pageRequest(@RequestBody PageRequest<MenuModel> request) {
		PageResult<List<Menu>> result = menuService.pageRequest(request);
		return result;
	}

	@RecordLog(module = Log.MODULE_MENU, type = Log.OPERATE_QUERY, content = "获取用户对应的菜单")
	@ApiOperation(value = "获取某个用户对应的菜单（T=A）")
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	@UserAnnotation(scopes = { ScopeType.admin })
	public OperateResult<List<AdminMenu>> findAdminMenus() {
		try {
		List<AdminMenu> result = menuService.findAdminMenus(App.userScope().getUserId());
//			List<AdminMenu> result = menuService.findAdminMenus(1L);
			return new OperateResult<List<AdminMenu>>().success(result);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
}
