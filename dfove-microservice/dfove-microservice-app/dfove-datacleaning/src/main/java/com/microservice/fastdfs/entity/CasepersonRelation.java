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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CasepersonRelation", description = "涉烟人员关系")
public class CasepersonRelation {
	@ApiModelProperty(value = "涉烟人员关系ID,添加时后端赋值uuid，前端忽略")
	private Integer id;
	@ApiModelProperty(value = "涉案人员id")
	private String casepersonId;
	
	@ApiModelProperty(value = "涉案人员名称")
	private String casepersonName;
	
	@ApiModelProperty(value = "涉案人员电话")
	private String senderMobile;
	
	@ApiModelProperty(value = "关联人员id")
	private String receiverId;
	
	@ApiModelProperty(value = "关联人员名称")
	private String receiverName;
	
	@ApiModelProperty(value = "关联人员电话")
	private String receiverMobile;
	
	@ApiModelProperty(value = "行为名称")
	private String relationName;
	
	@ApiModelProperty(value = "详情id")
	private String detailedId;

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

	public String getSenderMobile() {
		return senderMobile;
	}

	public void setSenderMobile(String senderMobile) {
		this.senderMobile = senderMobile;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getDetailedId() {
		return detailedId;
	}

	public void setDetailedId(String detailedId) {
		this.detailedId = detailedId;
	}

}
