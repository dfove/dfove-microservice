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

import com.microservice.admin.entity.GroupResult;
import com.microservice.admin.mapper.TopicMapper;

import common.entity.OperateResult;
import common.search.entity.BaseEntity;
import common.search.entity.BaseService;
import common.search.entity.SearchRequest;

public abstract class TopicService<T1 extends BaseEntity<T2>, T2, T3> extends BaseService<T1, T2, T3>
		implements ITopicService<T1, T2, T3> {

	@Override
	public abstract TopicMapper<T1, T2, T3> mapper();

	@Override
	public List<GroupResult> groupByDate(SearchRequest<T3> request) {
		List<GroupResult> result = mapper().groupByDate(request);
		return result;
	}

	@Override
	public List<GroupResult> groupByMonth(SearchRequest<T3> request) {
		List<GroupResult> result = mapper().groupByMonth(request);
		return result;
	}
	
	@Override
	public OperateResult<List<GroupResult>> groupByDateOrMonth(T3 model, String format) {
		if (format == null || (!"yyyyMMdd".equals(format) && !"yyyyMM".equals(format))) {
			return new OperateResult<List<GroupResult>>().failed("日期格式不正确，只支持按天yyyyMMdd或按月yyyyMM分组");
		}

		if ("yyyyMMdd".equals(format)) {
			List<GroupResult> data = groupByDate(new SearchRequest<T3>(model, null, null));
			return new OperateResult<List<GroupResult>>().success(data);
		} else {
			List<GroupResult> data =groupByMonth(new SearchRequest<T3>(model, null, null));
			return new OperateResult<List<GroupResult>>().success(data);
		}
	}
}
