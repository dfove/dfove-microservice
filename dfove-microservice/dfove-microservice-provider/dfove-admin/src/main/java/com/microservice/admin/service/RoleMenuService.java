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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.microservice.admin.util.UuidUtils;
import common.tools.MD5Util;
import org.springframework.stereotype.Service;

import com.microservice.admin.entity.RoleMenuExtends;
import com.microservice.admin.entity.RoleMenuExtendsModel;
import com.microservice.admin.entity.RoleMenu;
import com.microservice.admin.entity.RoleMenuModel;
import com.microservice.admin.mapper.RoleMenuMapper;

import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.search.entity.BaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

@Service("rolemenuService")
public class RoleMenuService extends BaseService<RoleMenu, String, RoleMenuModel> implements IRoleMenuService {

	@Resource
	private RoleMenuMapper rolemenuMapper;

	private final static String ITEM_NAME = "角色菜单信息";

	@Override
	public RoleMenuMapper mapper() {
		return rolemenuMapper;
	}

	@Override
	public String itemName() {
		return ITEM_NAME;
	}

	@Override
	public OperateResult<String> add(RoleMenu roleMenu) {

		RoleMenu _roleMenu = rolemenuMapper.findByRoleAndMenu(roleMenu.getRoleId(), roleMenu.getMenuId(), roleMenu.getRestore());
		if (_roleMenu != null) {
			roleMenu.setRoleMenuId(_roleMenu.getRoleMenuId());
			int update = rolemenuMapper.update(roleMenu);
			if (update > 0) {
                return OperateResult.s().success(roleMenu.getRoleMenuId(), "相同角色相同菜单已存在，更新成功");
            } else {
                return OperateResult.s().failed("相同用户相同角色已存在，但更新失败");
            }
		} else {
			roleMenu.setRoleMenuId(UuidUtils.getUUID());
			return super.add(roleMenu);
		}

	}

	@Override
	public OperateResult<Boolean> update(List<RoleMenu> roleMenu) {

		if (roleMenu == null || roleMenu.size() < 1) {
			return OperateResult.b().failed("至少要包括一个角色菜单", ErrorCode.INPUT_NOT_ALLOWED);
		}

		String roleId = roleMenu.get(0).getRoleId();
		if (roleId == null ) {
			return OperateResult.b().failed("角色Id不合法", ErrorCode.INPUT_NOT_ALLOWED);
		}

		List<String> menuIds = new ArrayList<>();
		for (RoleMenu e : roleMenu) {
			if (!roleId.equals(e.getRoleId())) {
				return OperateResult.b().failed("批量更新菜单必须是相同的角色", ErrorCode.INPUT_NOT_ALLOWED);
			}
			String menuId = e.getMenuId();
			if (menuId == null ) {
				return OperateResult.b().failed("菜单Id不合法", ErrorCode.INPUT_NOT_ALLOWED);
			}
			menuIds.add(menuId);
		}
		Integer restore = roleMenu.get(0).getRestore();
		rolemenuMapper.deleteNotExists(roleId, menuIds, restore);
		rolemenuMapper.batchUpdate(roleId, roleMenu);
		for (RoleMenu rm :roleMenu){
			rm.setRoleMenuId(IdUtil.simpleUUID());
		}
		rolemenuMapper.batchAdd(roleId, roleMenu);

		return OperateResult.b().success(true);
	}
	
	@Override
	public int countExtends(SearchRequest<RoleMenuExtendsModel> request) {
		int result = rolemenuMapper.countExtends(request);
		return result;
	}
	
	@Override
	public List<RoleMenuExtends> searchRequestExtends(SearchRequest<RoleMenuExtendsModel> request) {
		List<RoleMenuExtends> result = rolemenuMapper.searchRequestExtends(request);
		return result;
	}

	@Override
	public PageResult<List<RoleMenuExtends>> pageRequestExtends(PageRequest<RoleMenuExtendsModel> request) {
		int totalCount = this.countExtends(request);
		request.check(totalCount);

		List<RoleMenuExtends> result = rolemenuMapper.pageRequestExtends(request);

		return new PageResult<List<RoleMenuExtends>>(result, totalCount, request.getPageSize(), request.getCurrentPage());
	}

	@Override
	public OperateResult<Integer> deleteRoleAndMemu(RoleMenu rolemenu) {
		// TODO Auto-generated method stub
		return new OperateResult<Integer>().success(rolemenuMapper.deleteRoleAndMemu(rolemenu));
	}

	@Override
	public int deleteByRoleId(String roleId, Integer restore) {
		if (roleId == null || restore == null) {
			return 0;
		}
		return rolemenuMapper.deleteByRoleId(roleId, restore);
	}

}
