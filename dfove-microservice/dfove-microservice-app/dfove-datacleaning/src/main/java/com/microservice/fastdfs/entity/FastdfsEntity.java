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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.microservice.fastdfs.service.DataMessage;

import common.search.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Title: FastdfsEntity
 * @Description: FastDFS文件信息实体
 * @author sunwuzhao
 * @date 2019-02-28
 * @version V1.0
 */
@ApiModel(value = "FastdfsEntity", description = "FastDFS文件信息")
public class FastdfsEntity extends BaseEntity<String> {

	@ApiModelProperty(value = "id", hidden = true)
	private String id;

	@ApiModelProperty(value = "原文件名")
	private String originalFileName;

	@ApiModelProperty(value = "文件名")
	private String fileName;

	@ApiModelProperty(value = "文件类型" ,hidden=true)
	private String contentType;

	@ApiModelProperty(value = "文件后缀",hidden=true)
	private String ext;

	@ApiModelProperty(value = "文件大小")
	private long fileSize;

	@ApiModelProperty(value = "服务器-文件分组",hidden=true)
	private String group;

	@ApiModelProperty(value = "服务器-文件全路径")
	private String fullPath;

	@ApiModelProperty(value = "服务器-文件路径")
	private String path;

	@ApiModelProperty(value = "状态：success || failed")
	private String state = "success";

	@ApiModelProperty(value = "消息")
	private String message = "成功";
	
	@ApiModelProperty(value = "入库指定表" ,hidden=true)
	private String tab ;
	
	@ApiModelProperty(value = "创建时间" ,hidden=true)
	private Date createDate ;
	
	@ApiModelProperty(value = "解析失败数据" )
	private List<DataMessage> failedData= new ArrayList<DataMessage>();
	
	@ApiModelProperty(value = "成功解析数据量" )
	private Integer successNumber; 
	
	@ApiModelProperty(value = "数据总量" )
	private Integer count; 
	
	
	
	/**
	 * @Desc 无参构造-文件信息
	 */
	public FastdfsEntity() {

	}

	/**
	 * @Desc 无参构造-文件信息
	 */
	public FastdfsEntity(String originalFileName, String fileName, String contentType, String ext, long fileSize, String group, String fullPath, String path) {
		super();
		this.originalFileName = originalFileName;
		this.fileName = fileName;
		this.contentType = contentType;
		this.ext = ext;
		this.fileSize = fileSize;
		this.group = group;
		this.fullPath = fullPath;
		this.path = path;
	}

	public FastdfsEntity(String id, String originalFileName, String fileName, String contentType, String ext, long fileSize, String group, String fullPath, String path, String state, String message, String tab, Date createDate, List<DataMessage> failedData, Integer successNumber, Integer count) {
		super();
		this.id = id;
		this.originalFileName = originalFileName;
		this.fileName = fileName;
		this.contentType = contentType;
		this.ext = ext;
		this.fileSize = fileSize;
		this.group = group;
		this.fullPath = fullPath;
		this.path = path;
		this.state = state;
		this.message = message;
		this.tab = tab;
		this.createDate = createDate;
		this.failedData = failedData;
		this.successNumber = successNumber;
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @Desc 原文件名originalFileName
	 * @return 原文件名originalFileName，String
	 */
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	 * @Desc 设置原文件名originalFileName
	 * @param originalFileName
	 *            原文件名originalFileName
	 */
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	/**
	 * @Desc 文件名fileName
	 * @return 文件名fileName，String
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @Desc 设置文件名fileName
	 * @param fileName
	 *            文件名fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @Desc 文件类型contentType
	 * @return 文件类型contentType，String
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @Desc 设置文件类型contentType
	 * @param contentType
	 *            文件类型contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @Desc 文件后缀ext
	 * @return 文件后缀ext，String
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @Desc 设置文件后缀ext
	 * @param ext
	 *            文件后缀ext
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @Desc 文件大小fileSize
	 * @return 文件大小fileSize，long
	 */
	public long getFileSize() {
		return fileSize;
	}

	/**
	 * @Desc 设置文件大小fileSize
	 * @param fileSize
	 *            文件大小fileSize
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @Desc 服务器-文件分组fileSize
	 * @return 服务器-文件分组fileSize，String
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @Desc 设置服务器-文件分组group
	 * @param group
	 *            服务器-文件分组group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @Desc 服务器-文件全路径fullPath
	 * @return 服务器-文件全路径fullPath，String
	 */
	public String getFullPath() {
		return fullPath;
	}

	/**
	 * @Desc 设置服务器-文件全路径fullPath
	 * @param fullPath
	 *            服务器-文件全路径fullPath
	 */
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	/**
	 * @Desc 服务器-文件路径path
	 * @return 服务器-文件路径path，String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @Desc 设置服务器-文件路径path
	 * @param path
	 *            服务器-文件路径path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @Desc 状态 state
	 * @return 状态，String
	 */
	public String getState() {
		return state;
	}

	/**
	 * @Desc 状态 state
	 * @param state
	 *            状态
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @Desc 消息message
	 * @return 消息，String
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @Desc 消息message
	 * @param message
	 *            消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String primaryKey() {
		return id;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<DataMessage> getFailedData() {
		return failedData;
	}

	public void setFailedData(List<DataMessage> failedData) {
		this.failedData = failedData;
	}

	public Integer getSuccessNumber() {
		return successNumber;
	}

	public void setSuccessNumber(Integer successNumber) {
		this.successNumber = successNumber;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}




	
}
