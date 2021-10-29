package com.microservice.admin.entity.topic;

import java.util.Date;

import com.microservice.admin.TopicSearch;

import common.tools.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "TopicModel", description = "专题组合查询搜索参数")
public class TopicModel {

	@ApiModelProperty(value = "关键词")
	private String keyword;

	@ApiModelProperty(value = "开始时间，yyyy-MM-dd格式或时间戳")
	private Date beginTime;

	@ApiModelProperty(value = "截至时间，yyyy-MM-dd格式或时间戳")
	private Date endTime;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String topicWhere(StringBuffer sb, String type, TopicSearch topicSearch) {

		if (keyword != null && !keyword.isEmpty()) {
			String keywordsString = topicSearch.getValue(type, "keyword");
			if (keywordsString != null && !keywordsString.isEmpty()) {
				sb.append(" and (");
				String[] keywords = keywordsString.split(",");
				for (String k : keywords) {
					sb.append(String.format("%s like '%%%s%%' or ", k, keyword));
				}
				sb.append(")");
			}
		}

		if (beginTime != null || endTime != null) {
			String timeString = topicSearch.getValue(type, "time");
			if (timeString != null && !timeString.isEmpty()) {
				sb.append(" and (");
				String[] times = timeString.split(",");
				String and = "";
				for (String k : times) {
					and = "";
					sb.append(" ( ");
					if (beginTime != null) {
						sb.append(String.format("%s >=to_date('%s','yyyy-mm-dd hh24:mi:ss')", k,
								DateUtil.toShortTimeString(beginTime)));
						and = " and ";
					}
					if (endTime != null) {
						sb.append(String.format("%s%s <=to_date('%s','yyyy-mm-dd hh24:mi:ss')", and, k,
								DateUtil.toShortTimeString(endTime)));
					}
					sb.append(") or ");

				}
				sb.append(")");
			}
		}

		String where = sb.toString();
		if (where == null || where.isEmpty()) {
            where = "";
        } else {
            where = where.replaceFirst(" and ", " where ").replaceAll(" or \\)", "\\)").replaceAll("\\( and ", "\\(");
        }

		return where;
	}
}
