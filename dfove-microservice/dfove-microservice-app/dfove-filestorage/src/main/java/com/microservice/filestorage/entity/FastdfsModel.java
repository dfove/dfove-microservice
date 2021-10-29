package com.microservice.filestorage.entity;

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
@ApiModel(value = "FastdfsModel", description = "Fast上传文件信息")
public class FastdfsModel  {

	@ApiModelProperty(value = "ID",required=true)
	private String id;
	
	@ApiModelProperty(value = "原文件名",hidden=true)
	private String originalFileName;

	@ApiModelProperty(value = "文件名",hidden=true)
	private String fileName;

	@ApiModelProperty(value = "文件类型")
	private String contentType;

	@ApiModelProperty(value = "文件后缀")
	private String ext;

	@ApiModelProperty(value = "文件大小",hidden=true)
	private long fileSize;

	@ApiModelProperty(value = "服务器-文件分组",hidden=true)
	private String group;

	@ApiModelProperty(value = "服务器-文件全路径",hidden=true)
	private String fullPath;

	@ApiModelProperty(value = "服务器-文件路径",hidden=true)
	private String path;
	
	@ApiModelProperty(value = "状态：",hidden=true)
	private String state="success";
	
	@ApiModelProperty(value = "消息",hidden=true)
	private String message="成功";
	
	@ApiModelProperty(value = "入库指定表")
	private String tab ;
	
	@ApiModelProperty(value = "分隔符")
	private String split ;

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}
	


}
