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
package com.microservice.fastdfs.entity;

import java.util.List;

import com.microservice.fastdfs.service.DataMessage;

public class DataCleaningResult {
	// 错误类型
	private List<DataMessage> failedData;
	// 返回进度
	private String schedule;
	// 表名
	private String tab;
	// id
	private String id;
	// 文件名称
	private String originalFileName;
	
	
	public List<DataMessage> getFailedData() {
		return failedData;
	}
	public void setFailedData(List<DataMessage> failedData) {
		this.failedData = failedData;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	@Override
	public String toString() {
		return "DataCleaningResult [failedData=" + failedData + ", schedule=" + schedule + ", tab=" + tab + ", id=" + id + ", originalFileName=" + originalFileName + "]";
	}

}
