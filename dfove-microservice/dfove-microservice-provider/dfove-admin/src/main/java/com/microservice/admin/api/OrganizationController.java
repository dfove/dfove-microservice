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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.microservice.admin.service.IRoleService;
import com.microservice.admin.util.UuidUtils;
import com.microservice.core.App;
import common.annotation.RecordLog;
import common.constant.Log;
import common.constant.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.microservice.admin.entity.Organization;
import com.microservice.admin.entity.OrganizationModel;
import com.microservice.admin.service.IOrganizationService;
import com.microservice.core.UserAnnotation;

import common.entity.OperateResult;
import common.logs.Logs;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;
import common.search.entity.SearchResult;
import common.tools.RegExpValidatorUtils;
import common.tools.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "机构管理接口", tags = { "机构管理接口" })
@RestController
@RequestMapping("/dfove/v1/organization")
public class OrganizationController {

	@Resource
	private IOrganizationService organizationService;

	@Autowired
	private IRoleService roleService;

	@SuppressWarnings("unused")
	private static Logs _log = new Logs(OrganizationController.class);

	@RecordLog(module = Log.MODULE_ORGANIZATION, type = Log.OPERATE_CREATE, content = "添加机构管理")
	@UserAnnotation
	@ApiOperation(value = "添加机构管理")
	@RequestMapping(method = RequestMethod.POST)
	public OperateResult<String> add(Organization organization) {
		if (roleService.isRoleGtLevel(App.userScope().getUserId(), RoleType.USER_BUREAU_ADMIN)) { // 县局管理员
			return new OperateResult<String>().failed("无权限添加机构或子机构");
		}
		if (
		StringUtil.isEmpty(organization.getOrganizationName())||
		StringUtil.isEmpty(organization.getAddress())||
		organization.getParentId()==null ){		
			return new	OperateResult<String>().failed("组织名称、父级ID、地址 不能为空！");
		}
		if (!StringUtil.isEmpty(organization.getPhone())) {	
			if (!(RegExpValidatorUtils.isTelePhone(organization.getPhone())||RegExpValidatorUtils.isHandset(organization.getPhone()))) {
				return new	OperateResult<String>().failed("请输入合法的手机号码");
			}
		}
	
		if (organization.getCreateDate() == null) {
            organization.setCreateDate(new Date());
        }
		if (organization.getUpdateDate() == null) {
            organization.setUpdateDate(new Date());
        }
		if (organization.getOrderNum() == null) {
            organization.setOrderNum(0);
        }
		organization.setOrganizationId(UuidUtils.getUUID());
		organization.setCreator(App.userScope().getUserId());
		OperateResult<String> result = organizationService.add(organization);
		return result;
	}

	@RecordLog(module = Log.MODULE_ORGANIZATION, type = Log.OPERATE_DELETE, content = "删除机构管理")
	@ApiOperation(value = "删除机构管理")
	@ApiImplicitParam(paramType = "path", required = true, name = "organizationId", value = "组织ID", dataType = "String")
	@UserAnnotation()
	@RequestMapping(method = RequestMethod.DELETE, value = "/{organizationId}")
	public OperateResult<Boolean> delete(@PathVariable("organizationId") String organizationId) {
		OperateResult<Boolean> result = organizationService.delete(organizationId);
		return result;
	}

	@RecordLog(module = Log.MODULE_ORGANIZATION, type = Log.OPERATE_UPDATE, content = "更新机构管理")
	@UserAnnotation
	@ApiOperation(value = "更新机构管理")
	@RequestMapping(method = RequestMethod.PUT)
	public OperateResult<Integer> update(Organization organization) {
		if (
		StringUtil.isEmpty(organization.getOrganizationName())||
		StringUtil.isEmpty(organization.getAddress())||
		organization.getParentId()==null ){		
			return new	OperateResult<Integer>().failed("组织名称、父级ID、地址 不能为空！");
		}
		if (!StringUtil.isEmpty(organization.getPhone())) {	
			if (!(RegExpValidatorUtils.isTelePhone(organization.getPhone())||RegExpValidatorUtils.isHandset(organization.getPhone()))) {
				return new	OperateResult<Integer>().failed("请输入合法的手机号码");
			}
		}
		organization.setUpdator(App.userScope().getUserId());
		OperateResult<Integer> result = organizationService.update(organization);
		return result;
	}

	@RecordLog(module = Log.MODULE_ORGANIZATION, type = Log.OPERATE_QUERY, content = "查找机构管理")
	@UserAnnotation
	@ApiOperation(value = "根据主键查找机构管理")
	@ApiImplicitParam(paramType = "path", required = true, name = "organizationId", value = "组织ID", dataType = "String")
	@RequestMapping(method = RequestMethod.GET, value = "/{organizationId}")
	public OperateResult<Organization> find(@PathVariable("organizationId") String organizationId) {
		Organization result = organizationService.find(organizationId);
		return new OperateResult<Organization>().success(result);
	}

	@RecordLog(module = Log.MODULE_ORGANIZATION, type = Log.OPERATE_QUERY, content = "不分页搜索机构管理列表")
	@UserAnnotation
	@ApiOperation(value = "不分页搜索机构管理列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "SearchRequest«OrganizationModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search")
	public SearchResult<List<Organization>> searchRequest(@RequestBody SearchRequest<OrganizationModel> request) {
		try {
			
			List<Organization> result = organizationService.searchRequest(request);
			if (result==null) {				
				return new SearchResult<List<Organization>>(result, 0);
			}
			return new SearchResult<List<Organization>>(result, result.size());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@RecordLog(module = Log.MODULE_ORGANIZATION, type = Log.OPERATE_QUERY, content = "分页搜索机构管理列表")
	@UserAnnotation
	@ApiOperation(value = "分页搜索机构管理列表")
	@ApiImplicitParam(paramType = "body", name = "request", value = "请求参数(Json字符串)", dataType = "PageRequest«OrganizationModel»")
	@RequestMapping(method = RequestMethod.POST, value = "/search/page")
	public PageResult<List<Organization>> pageRequest(@RequestBody PageRequest<OrganizationModel> request) {
		PageResult<List<Organization>> result = organizationService.pageRequest(request);
		return result;
	}

	@ApiOperation(value = "查询用户所属机构及子机构")
	@ApiImplicitParam(paramType = "query", name = "adminId", value = "用户id", dataType = "String")
	@GetMapping(value = "/below")
	public OperateResult<List<Organization>> getBelowOrganization(String adminId) {
		List<Organization> belowOrganizations = organizationService.getBelowOrganization(adminId);
		return new OperateResult<List<Organization>>().success(belowOrganizations);
	}

}
