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

import cn.hutool.core.util.IdUtil;
import com.microservice.admin.entity.*;
import com.microservice.admin.util.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.admin.mapper.RolePermissionMapper;

import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.redis.IRedis;
import common.search.entity.BaseService;
import common.search.entity.PageRequest;
import common.search.entity.PageResult;
import common.search.entity.SearchRequest;

@Service("rolepermissionService")
public class RolePermissionService extends BaseService<RolePermission, String, RolePermissionModel> implements IRolePermissionService {
	@Autowired
	private IRedis redis;
	
	@Resource
	private RolePermissionMapper rolepermissionMapper;
	
	@Resource
	private IRoleService roleService;
	
    private final static String ITEM_NAME = "角色权限信息";

	@Override
	public RolePermissionMapper mapper() {
		return rolepermissionMapper;
	}
    
    @Override
	public String itemName() {
		return ITEM_NAME;
	}
    
    @Override
	public OperateResult<String> add(RolePermission rolePermission) {

		RolePermission _rolePermission = rolepermissionMapper.findByRoleAndPermission(rolePermission.getRoleId(), rolePermission.getPermissionId(), rolePermission.getRestore());
		if (_rolePermission != null) {
			rolePermission.setRolePermissionId(_rolePermission.getRolePermissionId());
			int update = rolepermissionMapper.update(rolePermission);
			if (update > 0) {
                return OperateResult.s().success(rolePermission.getRolePermissionId(), "相同角色相同权限已存在，更新成功");
            } else {
                return OperateResult.s().failed("相同用户相同权限已存在，但更新失败");
            }
		} else {
			rolePermission.setRolePermissionId(IdUtil.simpleUUID());
			return super.add(rolePermission);
		}

	}

	@Override
	public OperateResult<Boolean> update(List<RolePermission> rolePermission) {

		if (rolePermission == null || rolePermission.size() < 1) {
			return OperateResult.b().failed("至少要包括一个角色权限", ErrorCode.INPUT_NOT_ALLOWED);
		}

		String roleId = rolePermission.get(0).getRoleId();
		if (roleId == null  ) {
			return OperateResult.b().failed("角色Id不合法", ErrorCode.INPUT_NOT_ALLOWED);
		}

		List<String> permissionIds = new ArrayList<>();
		for (RolePermission e : rolePermission) {
			if (!roleId.equals(e.getRoleId())) {
				return OperateResult.b().failed("批量更新权限必须是相同的角色", ErrorCode.INPUT_NOT_ALLOWED);
			}
			String permissionId = e.getPermissionId();
			if (permissionId == null  ) {
				return OperateResult.b().failed("权限Id不合法", ErrorCode.INPUT_NOT_ALLOWED);
			}
			permissionIds.add(permissionId);
		}
		Integer restore = rolePermission.get(0).getRestore();
		rolepermissionMapper.deleteNotExists(roleId, permissionIds, restore);
		rolepermissionMapper.batchUpdate(roleId, rolePermission);
		for (RolePermission rp :rolePermission){
			rp.setRolePermissionId(IdUtil.simpleUUID());
		}
		rolepermissionMapper.batchAdd(roleId, rolePermission);

		return OperateResult.b().success(true);
	}
	
	@Override
	public int countExtends(SearchRequest<RolePermissionExtendsModel> request) {
		int result = rolepermissionMapper.countExtends(request);
		return result;
	}
	
	@Override
	public List<RolePermissionExtends> searchRequestExtends(SearchRequest<RolePermissionExtendsModel> request) {
		List<RolePermissionExtends> result = rolepermissionMapper.searchRequestExtends(request);
		return result;
	}

	@Override
	public PageResult<List<RolePermissionExtends>> pageRequestExtends(PageRequest<RolePermissionExtendsModel> request) {
		int totalCount = this.countExtends(request);
		request.check(totalCount);

		List<RolePermissionExtends> result = rolepermissionMapper.pageRequestExtends(request);

		return new PageResult<List<RolePermissionExtends>>(result, totalCount, request.getPageSize(), request.getCurrentPage());
	}

	@Override
	public OperateResult<Long> addSuperAdmin(RolePermission rolePermission) {
		//查询超级管理角色
//		RoleModel roleModel=new RoleModel();
//		roleModel.setSuperAdmin(1L);
//		SearchRequest<RoleModel> searchRequest=new SearchRequest<RoleModel>(roleModel);		
//		List<Role> roles=roleService.searchRequest(searchRequest);
		List<Role> roles=searchRequestSuperAdmin();
		int sum=0;
		for (int i = 0; i < roles.size(); i++) {
			rolePermission.setRoleId(roles.get(i).getRoleId());
			rolePermission.setRolePermissionId(IdUtil.simpleUUID());
			sum+=rolepermissionMapper.addSuperAdmin(rolePermission);
						
		}
		try {
			//刷新缓存
			String keys = "jnyc.admin.permissions.*";
			redis.dels(keys);
		} catch (Exception e) {
		}

		if (sum>0) {			
			return	new  OperateResult<Long>().success(Long.parseLong(sum+""));
		}
		return	new  OperateResult<Long>().failed("");
	}
	@Override
	public OperateResult<Long> deleteSuperAdmin(RolePermission rolePermission) {
		//查询超级管理角色
//		RoleModel roleModel=new RoleModel();
//		roleModel.setSuperAdmin(1L);
//		SearchRequest<RoleModel> searchRequest=new SearchRequest<RoleModel>(roleModel);		
//		List<Role> roles=roleService.searchRequest(searchRequest);
		List<Role> roles=searchRequestSuperAdmin();
		
		int sum=0;
		for (int i = 0; i < roles.size(); i++) {
			rolePermission.setRoleId(roles.get(i).getRoleId());
			sum+=rolepermissionMapper.deleteSuperAdmin(rolePermission);
		}
		if (sum>0) {			
			return	new  OperateResult<Long>().success(Long.parseLong(sum+""));
		}
		return	new  OperateResult<Long>().failed("");
	}
	
	private List<Role> searchRequestSuperAdmin(){
		RoleModel roleModel=new RoleModel();
		List<Integer> superAdmins = new ArrayList<>();
		superAdmins.add(1);
		roleModel.setSuperAdmins(superAdmins);
		SearchRequest<RoleModel> searchRequest=new SearchRequest<RoleModel>(roleModel);		
		return roleService.searchRequestAdmin(searchRequest);
	}

	@Override
	public OperateResult<Integer> updateRoleId(RolePermission rolePermission) {
		// TODO Auto-generated method stub
		return new OperateResult<Integer>().success(rolepermissionMapper.updateRoleId(rolePermission));
	}

	@Override
	public int deleteByRoleId(String roleId, Integer restore) {
		if (roleId == null || restore == null) {
			return 0;
		}
		return rolepermissionMapper.deleteByRoleId(roleId, restore);
	}

}
