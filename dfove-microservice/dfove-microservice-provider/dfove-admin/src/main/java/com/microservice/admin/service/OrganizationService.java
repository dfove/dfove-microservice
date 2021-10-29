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

import java.util.*;

import javax.annotation.Resource;

import com.microservice.admin.entity.Admin;
import com.microservice.core.App;
import common.constant.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.admin.entity.Organization;
import com.microservice.admin.entity.OrganizationModel;
import com.microservice.admin.mapper.OrganizationMapper;

import common.search.entity.BaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

@Service("organizationService")
public class OrganizationService extends BaseService<Organization, String, OrganizationModel>
		implements IOrganizationService {

	@Resource
	private OrganizationMapper organizationMapper;

	private final static String ITEM_NAME = "机构管理";

	@Autowired
	private IAdminService adminService;
	@Autowired
	private IRoleService roleService;

	@Override
	public OrganizationMapper mapper() {
		return organizationMapper;
	}

	@Override
	public String itemName() {
		return ITEM_NAME;
	}

	@Override
	public List<Organization> searchRequest(SearchRequest<OrganizationModel> request) {
		if (request == null || request.getData() == null) {
			return null;
		}

		List<Organization> result = null;
		if (roleService.isRoleType(App.userScope().getUserId(), Arrays.asList(new Integer[]{RoleType.USER_SUPER_ADMIN, RoleType.USER_ADMIN}))) {
			if (request.getData().getOrganizationId()==null) {				
				result = super.searchRequest(request);
			}else {
				String organizationId=request.getData().getOrganizationId();
				Organization  organization =find(organizationId);
				if (organization!=null && organization.getParentId().equals("")) {
					request.getData().setOrganizationId(null);
					result = organizationMapper.searchRequestBelow(request, organizationId);				
				}else {					
					request.getData().setOrganizationId(null);
					result = organizationMapper.searchRequestAbove(request, organizationId);				
				}
			}
		} else { // 县局管理员只能看自己机构
			Admin admin = adminService.findByAdminId(App.userScope().getUserId());
			if (admin == null) {
				return new ArrayList<>();
			}
			request.getData().setOrganizationId(null);
			result = organizationMapper.searchRequestAbove(request, admin.getOrganizationId());
		}

		return this.setChildren(result, request.getData());
	}

	@Override
	public PageResult<List<Organization>> pageRequest(PageRequest<OrganizationModel> request) {
		if (request == null || request.getData() == null) {
			return null;
		}

		PageResult<List<Organization>> result = super.pageRequest(request);
		if (!result.ok()) {
            return result;
        }

		result.setData(this.setChildren(result.getData(), request.getData()));
		return result;
	}

	private List<Organization> setChildren(List<Organization> list, OrganizationModel model) {

		String parentId = model.getGroupParentId();
		
		if (list == null || list.size() < 1 || parentId == null) {
            return list;
        }

		Map<String, List<Organization>> map = new HashMap<>();

		for (Organization org : list) {
			String pid = org.getParentId();
			if (map.containsKey(pid)) {
				map.get(pid).add(org);
			} else {
				List<Organization> children = new ArrayList<Organization>();
				children.add(org);
				map.put(pid, children);
			}
		}

		List<Organization> newList = map.get(parentId);
		this.setChildren(newList, map);

		return newList;

	}

	private void setChildren(List<Organization> list, Map<String, List<Organization>> map) {
		if (list == null || list.size() < 1) {
            return;
        }

		for (Organization org : list) {
			String orgId = org.getOrganizationId();
			if (map.containsKey(orgId)) {
				List<Organization> children = map.get(orgId);
				if (children != null && children.size() > 0) {
					this.setChildren(children, map);
				}
				org.setChildren(children);
			}
		}
	}


	@Override
	public List<Organization> getBelowOrganization(String adminId) {
		if (adminId == null) {
			return new ArrayList<>();
		}
		Admin admin = adminService.find(adminId);
		if (admin == null) {
			return new ArrayList<>();
		}
		List<Organization> organizations = organizationMapper.searchRequestBelow(new SearchRequest<>(), admin.getOrganizationId());
		return organizations;
	}

	@Override
	public Map<String, Organization> getAboveOrganization(List<String> adminIds) {
		if (adminIds == null || adminIds.isEmpty()) {
			return null;
		}
		Map<String, Organization> organizationMap = new HashMap<>();
		for (String adminId : adminIds) {
			Admin admin = adminService.find(adminId);
			if (admin == null) {
				continue;
			}
			List<Organization> organizations = organizationMapper.searchRequestAbove(new SearchRequest<>(), admin.getOrganizationId());
			if (!organizations.isEmpty()) {
				OrganizationModel organizationModel = new OrganizationModel();
				organizationModel.setGroupParentId("0");
				organizations = setChildren(organizations, organizationModel);
				if (!organizations.isEmpty()) {
					organizationMap.put(adminId, organizations.get(0));
				}
			}
		}
		return organizationMap;
	}

}
