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

@ApiModel(value = "PageRequest", description = "分页搜索请求")
public class PageRequest<T> extends SearchRequest<T> {

	@ApiModelProperty(value = "当前页")
	private Integer currentPage;

	public PageRequest() {
		super();
	}

	public PageRequest(T data) {
		super(data);
	}

	public PageRequest(T data, String orderBy, Integer pageSize, Integer currentPage) {
		super(data, orderBy, pageSize);
		this.setCurrentPage(currentPage);
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getCurrentPage() {
		return this.currentPage;
	}

	public void check(int totalCount) {
		
		if (this.getPageSize()==null || this.getPageSize() < 1) {
			this.setPageSize(10);
		}

		int totalPage = (int) Math.ceil((double) totalCount / (double) this.getPageSize());
		if (this.getCurrentPage()==null) {
			this.setCurrentPage(1);
		}
		if (this.getCurrentPage() > totalPage) {
			//this.setCurrentPage(totalPage);
		}

		if (this.getCurrentPage() < 1) {
			this.setCurrentPage(1);
		}
	}

	public int getIndexFrom() {
		return (this.getCurrentPage() - 1) * this.getPageSize();
	}
}
