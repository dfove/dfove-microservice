package com.microservice.admin.entity.topic;



import com.microservice.admin.TopicSearch;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "TopicPermissionModel", description = "组合查询-涉案人员参数")
public class TopicPermissionModel  extends TopicModel{
	
	public String where(TopicSearch topicSearch) {

		final String tableName = "TB_PERMISSION";
		StringBuffer sb = new StringBuffer();


		String where = super.topicWhere(sb, tableName, topicSearch);

		return where;
	}
}
