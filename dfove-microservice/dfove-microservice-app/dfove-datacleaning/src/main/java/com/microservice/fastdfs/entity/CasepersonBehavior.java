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

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CasepersonBehavior", description = "涉烟人员行为")
public class CasepersonBehavior {
	@ApiModelProperty(value = "涉烟人员行为ID,添加时后端赋值uuid，前端忽略")
	private Integer id;

	@ApiModelProperty(value = "人员id")
	private String casepersonId;

	@ApiModelProperty(value = "人员名称")
	private String casepersonName;

	@ApiModelProperty(value = "涉案地址")
	private String caseAddress;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;

	@ApiModelProperty(value = "涉案类型")
	private String caseType;

	@ApiModelProperty(value = "涉案描述")
	private String caseDescription;

	@ApiModelProperty(value = "涉案详情id")
	private String caseDetailsid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCasepersonId() {
		return casepersonId;
	}

	public void setCasepersonId(String casepersonId) {
		this.casepersonId = casepersonId;
	}

	public String getCasepersonName() {
		return casepersonName;
	}

	public void setCasepersonName(String casepersonName) {
		this.casepersonName = casepersonName;
	}

	public String getCaseAddress() {
		return caseAddress;
	}

	public void setCaseAddress(String caseAddress) {
		this.caseAddress = caseAddress;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseDescription() {
		return caseDescription;
	}

	public void setCaseDescription(String caseDescription) {
		this.caseDescription = caseDescription;
	}

	public String getCaseDetailsid() {
		return caseDetailsid;
	}

	public void setCaseDetailsid(String caseDetailsid) {
		this.caseDetailsid = caseDetailsid;
	}

}
