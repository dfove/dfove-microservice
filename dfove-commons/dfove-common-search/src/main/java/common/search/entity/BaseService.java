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
package common.search.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.enums.OperateState;

public abstract class BaseService<T1 extends BaseEntity<T2>, T2, T3> implements IBaseService<T1, T2, T3> {

	/**
	 * 操作对象名称
	 * 
	 * @return
	 */
	public abstract String itemName();

	/**
	 * 具体mapper
	 * 
	 * @return
	 */
	public abstract BaseMapper<T1, T2, T3> mapper();

	@Override
	public OperateResult<T2> add(T1 entity) {
		if (entity == null){
			return new OperateResult<T2>().failed("添加对象不能为空", ErrorCode.INPUT_NOT_ALLOWED);
		}
		int result = mapper().add(entity);
		Boolean success = result > 0;
		@SuppressWarnings("unchecked")
		T2 id = success ? entity.primaryKey() : (T2) (Object) 0;
		OperateState operateState = success ? OperateState.Success : OperateState.Failed;
		String message = success ? "添加" + itemName() + "成功" : "添加" + itemName() + "失败";

		return new OperateResult<T2>(id, operateState, message);
	}

	@Override
	public OperateResult<Boolean> delete(T2 id) {
		int result = mapper().delete(id);

		Boolean success = result > 0;
		OperateState operateState = success ? OperateState.Success : OperateState.Failed;
		String message = success ? "删除" + itemName() + "成功" : "删除" + itemName() + "失败";

		return new OperateResult<Boolean>(success, operateState, message);
	}

	@Override
	public OperateResult<Integer> update(T1 entity) {
		if (entity == null){
			return OperateResult.i().failed("修改对象不能为空", ErrorCode.INPUT_NOT_ALLOWED);
		}

		int result = mapper().update(entity);

		return OperateResult.i().success(result);
	}

	@Override
	public OperateResult<Integer> update(Map<String, Object> params) {
		if (params == null || params.size() < 2) {
			return OperateResult.i().failed("无效参数");
		}
		if (!params.containsKey("where")) {
			return OperateResult.i().failed("更新操作必须带where条件");
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> where = (Map<String, Object>) params.get("where");
		if (where == null || where.size() < 1) {
			return OperateResult.i().failed("更新操作必须带where条件");
		}
		int result = mapper().updateByMap(params);
		return OperateResult.i().success(result);
	}

	@Override
	public T1 find(T2 id) {
		T1 result = mapper().find(id);
		return result;
	}

	@Override
	public List<T1> searchRequest(SearchRequest<T3> request) {
		if (request == null || request.getData() == null) {
			return null;
		}
		List<T1> result = mapper().searchRequest(request);
		return result;
	}

	@Override
	public int count(SearchRequest<T3> request) {
		if (request == null || request.getData() == null) {
			return 0;
		}
		int result = mapper().count(request);
		return result;
	}

	@Override
	public PageResult<List<T1>> pageRequest(PageRequest<T3> request) {
		if (request == null || request.getData() == null) {
			return new PageResult<List<T1>>().failed("搜索参数不能为空");
		}
		
		int totalCount = this.count(request);
		request.check(totalCount);

		List<T1> result = mapper().pageRequest(request);

		return new PageResult<List<T1>>(result, totalCount, request.getPageSize(), request.getCurrentPage());
	}

	/**
	 * 条件参数
	 * 
	 * @param objects key1,value1,key2,value2...
	 * @return map<String, Object>
	 */
	public Map<String, Object> where(Object... objects) {
		return this.params(objects);
	}

	/**
	 * 更新参数
	 * 
	 * @param objects objects key1,value1,key2,value2...
	 * @return map<String, Object>
	 */
	public Map<String, Object> params(Object... objects) {
		Map<String, Object> map = new HashMap<>();

		for (int i = 0; i < objects.length; i += 2) {
			map.put((String) objects[i], objects[i + 1]);
		}
		return map;
	}

	public String autoId() {
		return (UUID.randomUUID().toString()).replaceAll("-", "");
	}
}
