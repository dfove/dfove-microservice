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

import common.entity.OperateResult;
import common.search.entity.IBaseService;
import common.search.entity.SearchRequest;

public interface ITopicService<T1, T2, T3> extends IBaseService<T1, T2, T3> {

	public List<GroupResult> groupByDate(SearchRequest<T3> request);

	public List<GroupResult> groupByMonth(SearchRequest<T3> request);

	public OperateResult<List<GroupResult>> groupByDateOrMonth(T3 model, String format);
}
