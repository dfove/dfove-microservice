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
public class FastdfsModel extends BaseEntity<Long> {

	@ApiModelProperty(value = "id", hidden = true)
	private Long id;

	@ApiModelProperty(value = "原文件名")
	private String originalFileName;

	@ApiModelProperty(value = "文件名")
	private String fileName;

	@ApiModelProperty(value = "文件类型")
	private String contentType;

	@ApiModelProperty(value = "文件后缀")
	private String ext;

	@ApiModelProperty(value = "文件大小")
	private long fileSize;

	@ApiModelProperty(value = "服务器-文件分组")
	private String group;

	@ApiModelProperty(value = "服务器-文件全路径")
	private String fullPath;

	@ApiModelProperty(value = "服务器-文件路径")
	private String path;

	@ApiModelProperty(value = "状态：success || failed")
	private String state = "success";

	@ApiModelProperty(value = "消息")
	private String message = "成功";
	
	@ApiModelProperty(value = "入库指定表")
	private String tab ;
	
	@ApiModelProperty(value = "处理类型：1入库、2实时流处理")
	private String type ;
	
	
	
	/**
	 * @Desc 无参构造-文件信息
	 */
	public FastdfsModel() {

	}

	/**
	 * @Desc 无参构造-文件信息
	 */
	public FastdfsModel(String originalFileName, String fileName, String contentType, String ext, long fileSize, String group, String fullPath, String path) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	 * @param fullPath
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
	public Long primaryKey() {
		return id;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
