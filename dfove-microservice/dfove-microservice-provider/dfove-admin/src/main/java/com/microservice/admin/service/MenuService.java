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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.microservice.admin.entity.*;
import com.microservice.admin.mapper.PermissionMapper;
import com.microservice.admin.mapper.RoleMapper;
import com.microservice.core.App;
import common.entity.UserScope;
import org.springframework.stereotype.Service;

import com.microservice.admin.mapper.AdminMapper;
import com.microservice.admin.mapper.MenuMapper;

import common.search.entity.BaseService;
import common.search.entity.SearchRequest;

@Service("menuService")
public class MenuService extends BaseService<Menu, String, MenuModel> implements IMenuService {

	@Resource
	private MenuMapper menuMapper;

	@Resource
	private AdminMapper adminMapper;

	@Resource
	private PermissionMapper permissionMapper;

	@Resource
	private RoleMapper roleMapper;

	private final static String ITEM_NAME = "菜单信息";

	@Override
	public MenuMapper mapper() {
		return menuMapper;
	}

	@Override
	public String itemName() {
		return ITEM_NAME;
	}



	@Override
	public List<AdminMenu> findAdminMenus(String adminId) {
		 List<AdminMenu> result = null;
		List<Admin>  admins=	adminMapper.searchRequestSuperAdminRole(null,adminId);
		if (admins!=null && admins.size()>0) {
			result = menuMapper.findSuperAdminMenus(adminId);
		}else {
			result = menuMapper.findAdminMenus(adminId);
		}
		//获取角色
		Role role =roleMapper.getRole(App.userScope().getUserId());
		//获取权限标识
//		for (AdminMenu menu:result){
//			if (role.getSuperAdmin()==1){
//				menu.setPermissionIdents(permissionMapper.getSubClassIdent(null,menu.getMenuId()));
//			}else {
//				menu.setPermissionIdents(permissionMapper.getSubClassIdent(role.getRoleId(),menu.getMenuId()));
//			}
//			if (menu.getChildren()!=null && menu.getChildren().size()>=1){
//				for (AdminMenu children:menu.getChildren()){
//					if (role.getSuperAdmin()==1){
//						children.setPermissionIdents(permissionMapper.getPermissionIdent(null,children.getMenuId()));
//					}else {
//						children.setPermissionIdents(permissionMapper.getPermissionIdent(role.getRoleId(),children.getMenuId()));
//					}
//				}
//			}
//		}
		getIndex(result,role);
		return result;
//		return setChildren(result);
	}


	//获取权限标识
	public void getIndex(List<AdminMenu> result,Role role){
		for (AdminMenu menu:result){
			menu.setPermissionIdents(new ArrayList<>());
			//判断是否有子菜单
			if (menu.getChildren()!=null && menu.getChildren().size()>=1){
				//获取子菜单标识列表
//				if (role.getSuperAdmin()==1){
//					menu.setPermissionIdents(permissionMapper.getSubClassIdent(null,menu.getMenuId()));
//				}else {
//					menu.setPermissionIdents(permissionMapper.getSubClassIdent(role.getRoleId(),menu.getMenuId()));
//				}
				for (AdminMenu children:menu.getChildren()){
					//还有子权限时获取子菜单标识在将子菜单递归
					if (children.getChildren().size()>=1){
						if (role.getSuperAdmin()==1){
							children.setPermissionIdents(permissionMapper.getSubClassIdent(null,children.getMenuId()));
							menu.getPermissionIdents().addAll(children.getPermissionIdents());
						}else {
							children.setPermissionIdents(permissionMapper.getSubClassIdent(role.getRoleId(),children.getMenuId()));
							menu.getPermissionIdents().addAll(children.getPermissionIdents());
						}
						getIndex(children.getChildren(),role);
					}else {
						//没有子菜单获取自身标识
						if (role.getSuperAdmin()==1){
							children.setPermissionIdents(permissionMapper.getPermissionIdent(null,children.getMenuId()));
							menu.getPermissionIdents().addAll(children.getPermissionIdents());
						}else {
							children.setPermissionIdents(permissionMapper.getPermissionIdent(role.getRoleId(),children.getMenuId()));
							menu.getPermissionIdents().addAll(children.getPermissionIdents());
						}
					}
				}
			}else {
				//没有子菜单，获取自身权限
				if (role.getSuperAdmin()==1){
					menu.setPermissionIdents(permissionMapper.getPermissionIdent(null,menu.getMenuId()));
				}else {
					menu.setPermissionIdents(permissionMapper.getPermissionIdent(role.getRoleId(),menu.getMenuId()));
				}
			}
		}
	}


	@SuppressWarnings("unused")
	private List<AdminMenu> setChildren(List<AdminMenu> list) {

		Map<String, List<AdminMenu>> map = new HashMap<>();

		for (AdminMenu menu : list) {
			String parentId = menu.getParentId();
			if (map.containsKey(parentId)) {
				map.get(parentId).add(menu);
			} else {
				List<AdminMenu> children = new ArrayList<AdminMenu>();
				children.add(menu);
				map.put(parentId, children);
			}
		}

		List<AdminMenu> newList = map.get(0);
		if (newList == null || newList.size() < 1) {
            return null;
        }

		for (AdminMenu menu : newList) {
			String menuId = menu.getMenuId();
			if (map.containsKey(menuId)) {
				List<AdminMenu> children = map.get(menuId);
				for (AdminMenu child : children) {
					child.setChildren(map.get(child.getMenuId()));
				}
				menu.setChildren(children);
			}
		}

		return newList;
	}

	@Override
	public List<Menu> searchRequests(SearchRequest<MenuModel> request) {

			return menuMapper.searchRequests(request);
	}

	@Override
	public List<MenuPermission> searchRequestPermission(SearchRequest<MenuModel> request) {
		return menuMapper.searchRequestPermission(request);
	}

	/**
	 * 校验是否有子集菜单
	 * @param parenId
	 * @return
	 */
	@Override
	public List<Menu> findChildrenMenus(String parenId) {
		return menuMapper.chekeMenusChildren(parenId);
	}
}
