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

import java.util.List;
import java.util.Map;

import common.entity.OperateResult;

public interface IBaseService<T1, T2, T3> {

	public OperateResult<T2> add(T1 invest);

	public OperateResult<Boolean> delete(T2 id);

	public OperateResult<Integer> update(T1 invest);

	public OperateResult<Integer> update(Map<String, Object> params);

	public T1 find(T2 id);

	public List<T1> searchRequest(SearchRequest<T3> request);

	public int count(SearchRequest<T3> request);

	public PageResult<List<T1>> pageRequest(PageRequest<T3> request);
}
