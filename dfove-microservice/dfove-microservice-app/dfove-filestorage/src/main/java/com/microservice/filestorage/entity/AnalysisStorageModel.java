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
@ApiModel(value = "AnalysisStorageModel", description = "数据清洗入库")
public class AnalysisStorageModel {

	@ApiModelProperty(value = "ID", required = true)
	private Long id;

	@ApiModelProperty(value = "入库类型")
	private String tab;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

}
