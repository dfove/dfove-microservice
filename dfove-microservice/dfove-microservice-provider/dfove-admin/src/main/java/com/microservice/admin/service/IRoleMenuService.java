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

import com.microservice.admin.entity.RoleMenu;
import com.microservice.admin.entity.RoleMenuExtends;
import com.microservice.admin.entity.RoleMenuExtendsModel;
import com.microservice.admin.entity.RoleMenuModel;

import common.entity.OperateResult;
import common.search.entity.IBaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

public interface IRoleMenuService extends IBaseService<RoleMenu, String, RoleMenuModel> {

	public OperateResult<Boolean> update(List<RoleMenu> roleMenu);

	public int countExtends(SearchRequest<RoleMenuExtendsModel> request);

	public List<RoleMenuExtends> searchRequestExtends(SearchRequest<RoleMenuExtendsModel> request);

	public PageResult<List<RoleMenuExtends>> pageRequestExtends(PageRequest<RoleMenuExtendsModel> request);

	public OperateResult<Integer> deleteRoleAndMemu(RoleMenu rolemenu);

	int deleteByRoleId(String roleId, Integer restore);

}
