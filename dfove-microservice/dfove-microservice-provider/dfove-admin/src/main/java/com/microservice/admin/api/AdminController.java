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
import com.microservice.admin.service.IRoleService;
import common.annotation.RecordLog;
import common.constant.Log;
import common.constant.RoleType;
import common.entity.UserScope;
import common.vo.AdminVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservice.admin.service.IAdminService;
import com.microservice.core.App;
import com.microservice.core.UserAnnotation;

import common.entity.OperateResult;
import common.enums.ScopeType;
import common.logs.Logs;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.tools.RegExpValidatorUtils;
import common.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "管理员信息接口", tags = { "管理员信息接口" })
@RestController
@RequestMapping("/dfove/v1/admin")
public class AdminController{

	@Resource
	private IAdminService adminService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAdminRoleService adminRoleService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(AdminController.class);

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_CREATE, content = "添加管理员信息")
	@ApiOperation(value = "添加管理员信息")
	@RequestMapping(method = RequestMethod.POST)
	@UserAnnotation()
	public OperateResult<String> add(Admin admin) {//@Valid
		try {
//			System.out.println(App.userScope());
			admin.setCreateAdminId(App.userScope().getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if (
//		StringUtil.isEmpty(admin.getAdminName())||
//		StringUtil.isEmpty(admin.getRealName())||
//		StringUtil.isEmpty(admin.getPassword())||
//		StringUtil.isEmpty(	admin.getPhone())||
//		admin.getStatus()==null||
//		admin.getCreateAdminId()==null||
//		admin.getOrganizationId()==null||
//		admin.getGender()== null){
//		return new	OperateResult<Long>().failed("管理员用户名、真实姓名、管理员密码、联系电话、状态、创建者用户名、组织机构ID、性别 不能为空！");
//		}
		if (StringUtil.isEmpty(admin.getAdminName())){
			return new	OperateResult<String>().failed("管理员用户名 不能为空！");
		}
		else if (StringUtil.isEmpty(admin.getRealName())){
			return new	OperateResult<String>().failed("管理员真实姓名 不能为空！");
		}
		else if (StringUtil.isEmpty(admin.getPassword())){
			return new	OperateResult<String>().failed("管理员密码 不能为空！");
		}
		else if (StringUtil.isEmpty(admin.getPhone())){
			return new	OperateResult<String>().failed("联系电话 不能为空！");
		}
		else if (admin.getStatus()==null){
			return new	OperateResult<String>().failed("状态 不能为空！");
		}		
		else if (admin.getCreateAdminId()==null){
			return new	OperateResult<String>().failed("创建者用户名id 不能为空！");
		}		
		else if (admin.getOrganizationId()==null){
			return new	OperateResult<String>().failed("组织机构ID 不能为空！");
		}		
		else if (admin.getGender()==null){
			return new	OperateResult<String>().failed("性别 不能为空！");
		}
		
		if (admin.getAdminName().length() != admin.getAdminName().trim().length()) {
			return new OperateResult<String>().failed("用户名不能有空格");
		}
//		if (!StringUtil.isEmpty(admin.getEmail())) {
//			if (!RegExpValidatorUtils.isEmail(admin.getEmail())) {
//				return new	OperateResult<String>().failed("请输入合法的邮箱");
//			}
//		}
//		if (!StringUtil.isEmpty(admin.getPhone())) {
//			if (!(RegExpValidatorUtils.isTelePhone(admin.getPhone())||RegExpValidatorUtils.isHandset(admin.getPhone()))) {
//				return new	OperateResult<String>().failed("请输入合法的手机号码");
//			}
//		}

		OperateResult<String> result = adminService.add(admin);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_DELETE, content = "删除管理员信息")
	@UserAnnotation
	@ApiOperation(value = "删除管理员信息")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminId", value = "管理员ID", dataType = "String")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{adminId}")
	public OperateResult<Boolean> delete(@PathVariable("adminId") String adminId) {
		OperateResult<Boolean> result = adminService.delete(adminId);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_UPDATE, content = "更新管理员信息")
	@ApiOperation(value = "更新管理员信息(Owner|100)")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.PUT)
	public OperateResult<Integer> update(Admin admin) {
		try {			
			admin.setCreateAdminId(App.userScope().getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (StringUtil.isEmpty(admin.getAdminName())){
			return new	OperateResult<Integer>().failed("管理员用户名 不能为空！");
		}
		else if (StringUtil.isEmpty(admin.getRealName())){
			return new	OperateResult<Integer>().failed("管理员真实姓名 不能为空！");
		}
		else if (StringUtil.isEmpty(admin.getPassword())){
			return new	OperateResult<Integer>().failed("管理员密码 不能为空！");
		}
		else if (StringUtil.isEmpty(admin.getPhone())){
			return new	OperateResult<Integer>().failed("联系电话 不能为空！");
		}
		else if (admin.getStatus()==null){
			return new	OperateResult<Integer>().failed("状态 不能为空！");
		}		
		else if (admin.getCreateAdminId()==null){
			return new	OperateResult<Integer>().failed("创建者用户名id 不能为空！");
		}		
		else if (admin.getOrganizationId()==null){
			return new	OperateResult<Integer>().failed("组织机构ID 不能为空！");
		}		
		else if (admin.getGender()==null){
			return new	OperateResult<Integer>().failed("性别 不能为空！");
		}		
		if (!StringUtil.isEmpty(admin.getEmail())) {			
			if (!RegExpValidatorUtils.isEmail(admin.getEmail())) {			
				return new	OperateResult<Integer>().failed("请输入合法的邮箱");
			}
		}
		if (!StringUtil.isEmpty(admin.getPhone())) {	
			if (!(RegExpValidatorUtils.isTelePhone(admin.getPhone())||RegExpValidatorUtils.isHandset(admin.getPhone()))) {
				return new	OperateResult<Integer>().failed("请输入合法的手机号码");
			}
		}
		
		if (!App.isOwnerOrGrater(admin.getAdminId(), 100)) {
			return App.refused();
		}

		OperateResult<Integer> result = adminService.update(admin);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_QUERY, content = "查找管理员信息")
	@ApiOperation(value = "根据主键查找管理员信息(Owner|100)")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminId", value = "管理员ID", dataType = "Long")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.GET, value = "/{adminId}")
	public OperateResult<Admin> find(@PathVariable("adminId") String adminId) {
		UserScope userScope = App.userScope();
		System.out.println(userScope);
		if (adminId == null || adminId.equals("0") ) {
			adminId = App.userScope().getUserId();
		}
		else if (!App.isOwnerOrGrater(adminId, 100)) {
			return App.refused();
		}

		Admin result = adminService.find(adminId);
		return new OperateResult<Admin>().success(result);
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_QUERY, content = "根据机构类型查找管理员信息")
	@UserAnnotation
	@ApiOperation(value = "根据机构类型查找管理员信息")
	@ApiImplicitParam(paramType = "path", name = "OrgType", value = "机构类型", dataType = "String")
	@RequestMapping(method = RequestMethod.POST, value = "/orgType")
	public SearchResult<List<Long>> findByOrganizationName(String orgType) {
		List<Long> result = adminService.findByOrgType(orgType);
		return new SearchResult<List<Long>>(result, result.size());
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_QUERY, content = "不分页搜索管理员信息列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索管理员信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«AdminModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<Admin>> searchRequest(@RequestBody SearchRequest<AdminModel> request) {
		try {
			SearchResult<List<Admin>> result= new SearchResult<>();
			result.setData(adminService.getOrganizationAdmin(request.getData().getOrganizationId()));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_QUERY, content = "分页搜索管理员信息列表")
	@ApiOperation(value = "分页搜索管理员信息列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«AdminModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	@UserAnnotation(scopes = ScopeType.admin)
	public PageResult<List<Admin>> pageRequest(@RequestBody PageRequest<AdminModel> request) {
			String adminId = App.userScope().getUserId();
			PageResult<List<Admin>> result = adminService.pageRequestSuperAdmin(request,adminId);
			return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_UPDATE, content = "修改密码")
	@ApiOperation(value = "修改自己的密码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "oldPassword", value = "旧密码", dataType = "String"),
			@ApiImplicitParam(paramType = "query", required = true, name = "newPassword", value = "新密码", dataType = "String") })
	@UserAnnotation(scopes = ScopeType.admin)
	@RequestMapping(value = "/password/update", method = RequestMethod.PUT)
	public OperateResult<Integer> update(String oldPassword, String newPassword) {
		String adminId = App.userScope().getUserId();
		OperateResult<Integer> result = adminService.updatePassword(adminId, oldPassword, newPassword);
		return result;
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_UPDATE, content = "重设自己或其他管理员的密码")
	@UserAnnotation
	@ApiOperation(value = "重设自己或其他管理员的密码")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", required = true, name = "adminId", value = "被修改者的用户Id", dataType = "String"),
			@ApiImplicitParam(paramType = "query", required = true, name = "newPassword", value = "新密码", dataType = "String") })
	@RequestMapping(value = "/password/reset", method = RequestMethod.PUT)
	public OperateResult<Integer> resetPassword(String adminId, String newPassword) {
		OperateResult<Integer> result = adminService.resetPassword(adminId, newPassword);
		return result;
	}

	@ApiOperation(value = "获取超级管理员", hidden = true)
	@GetMapping(value = "/super")
	public OperateResult<Admin> getSuperAdmin() {
		List<Integer> superAdmins = new ArrayList<>();
		superAdmins.add(RoleType.USER_SUPER_ADMIN);
		List<Role> roles = roleService.findBySuperAdmins(superAdmins);
		if (roles.isEmpty()) {
			return new OperateResult<Admin>().failed("找不到超级管理员角色");
		}
		Role role = roles.get(0);
		SearchRequest<AdminRoleModel> adminRoleRequest = new SearchRequest<>();
		AdminRoleModel adminRoleModel = new AdminRoleModel();
		adminRoleModel.setRoleId(role.getRoleId());
		adminRoleRequest.setData(adminRoleModel);
		List<AdminRole> adminRoles = adminRoleService.searchRequest(adminRoleRequest);
		if (adminRoles.isEmpty()) {
			return new OperateResult<Admin>().failed("找不到超级管理员角色对应的用户");
		}
		AdminRole adminRole = adminRoles.get(0);
		Admin admin = adminService.find(adminRole.getAdminId());
		return new OperateResult<Admin>().success(admin);
	}

	@ApiOperation(value = "根据用户id获取超级管理员", hidden = true)
	@PostMapping(value = "/list/ids")
	public OperateResult<List<AdminVo>> getAdminList(@RequestBody List<String> ids) {
		if (ids == null || ids.isEmpty()) {
			return new OperateResult<List<AdminVo>>().failed("参数不正确");
		}
		List<AdminVo> admins = adminService.getAdminList(ids);
		return new OperateResult<List<AdminVo>>().success(admins);
	}

	@RecordLog(module = Log.MODULE_ADMIN, type = Log.OPERATE_QUERY, content = "查找管理员信息")
	@ApiOperation(value = "根据主键查找管理员信息(Owner|100)")
	@ApiImplicitParam(paramType = "path", required = true, name = "adminId", value = "管理员ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.GET, value = "/findid/{adminId}")
	public OperateResult<Admin> findById(@PathVariable("adminId") String adminId) {
		Admin result = adminService.find(adminId);
		return new OperateResult<Admin>().success(result);
	}

}
