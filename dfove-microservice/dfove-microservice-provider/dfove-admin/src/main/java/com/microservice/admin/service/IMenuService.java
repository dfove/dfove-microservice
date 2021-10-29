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

import java.util.List;

import com.microservice.admin.entity.AdminMenu;
import com.microservice.admin.entity.Menu;
import com.microservice.admin.entity.MenuModel;
import com.microservice.admin.entity.MenuPermission;

import common.search.entity.IBaseService;
import common.search.entity.SearchRequest;

public interface IMenuService extends IBaseService<Menu, String, MenuModel> {

	public List<AdminMenu> findAdminMenus(String adminId);
	
	public List<Menu> findChildrenMenus(String parenId);
	
	public List<Menu> searchRequests(SearchRequest<MenuModel> request);
	
	public List<MenuPermission> searchRequestPermission(SearchRequest<MenuModel> request);
	
	
}
