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

import common.entity.OperateResult;
import common.enums.OperateState;
import common.tools.JsonUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "SearchResult", description = "通用搜索结果")
public class SearchResult<T> extends OperateResult<T> {

	@ApiModelProperty(value = "总记录数")
	private int totalCount;

	public SearchResult() {
		super(null, OperateState.Success);
	}

	public SearchResult(T data) {
		this(data, 0);
	}

	public SearchResult(T data, int totalCount) {
		super(data, OperateState.Success);
		this.totalCount = totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public String toJson() {
		return JsonUtil.objectToJson(this);
	}

	@Override
	public SearchResult<T> failed(String message) {
		return this.failed(message, null);
	}

	@Override
	public SearchResult<T> failed(String message, String code) {
		super.failed(message, code);
		return this;
	}

	@Override
	public SearchResult<T> success(T data) {
		return success(data, null);
	}

	@Override
	public SearchResult<T> success(T data, String message) {
		return success(data, message, null);
	}

	@Override
	public SearchResult<T> success(T data, String message, String code) {
		super.success(data, message, code);
		return this;
	}

}
