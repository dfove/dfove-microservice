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
package com.microservice.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "GroupResult", description = "分组汇总结果")
public class GroupResult {

	@ApiModelProperty(value = "分组项")
	private String item;

	@ApiModelProperty(value = "记录数")
	private String count;

	/**
	 * 获取日期格式
	 */
	public String getItem() {
		return this.item;
	}

	/**
	 * 设置日期格式
	 */
	public void setItem(String item) {
		this.item = item;
	}

	/**
	 * 获取记录数
	 */
	public String getCount() {
		return this.count;
	}

	/**
	 * 设置记录数
	 */
	public void setCount(String count) {
		this.count = count;
	}
}
