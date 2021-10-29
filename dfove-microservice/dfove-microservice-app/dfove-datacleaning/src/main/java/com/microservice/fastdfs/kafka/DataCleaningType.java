package com.microservice.fastdfs.kafka;

import java.util.List;
import java.util.Map;

public class DataCleaningType {
	private String type;//数据类型
	private List<Map<String, Object>> list;//数据

	
	public DataCleaningType() {
		super();
	}

	public DataCleaningType(String type, List<Map<String, Object>> list) {
		super();
		this.type = type;
		this.list = list;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Map<String, Object>> getList() {
		return list;
	}

	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}

}
