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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PageResult", description = "分页搜索结果")
public class PageResult<T> extends SearchResult<T> {

	@ApiModelProperty(value = "每页记录数")
	private int pageSize;

	@ApiModelProperty(value = "当前页")
	private int currentPage;

	@ApiModelProperty(value = "总页数")
	private int totalPage;

	public PageResult() {
		super();
	}

	public PageResult(T data, int totalCount, int pageSize, int currentPage) {
		super(data, totalCount);
		this.setPageSize(pageSize);
		this.setCurrentPage(currentPage);
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public int getTotalPage() {
		if (this.getPageSize() < 1) {
			this.setPageSize(10);
		}

		this.totalPage = (int) Math.ceil((double) this.getTotalCount() / (double) this.getPageSize());

		return this.totalPage;
	}
	
	@Override
	public PageResult<T> failed(String message) {
		return this.failed(message, null);
	}
	
	@Override
	public PageResult<T> failed(String message, String code) {
		super.failed(message, code);
		return this;
	}
}
