package com.microservice.filestorage.entity;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.microservice.filestorage.service.DataMessage;

public class DataAnalysisModel {

	private Long id;
	private String ext;
	private String table;
	private InputStream inputStream;
	private List<Map<String, Object>> data;
	private List<DataMessage> datamessage;
	private String code;
	private String errmessage;
	private String[] columns;
	private String[] datetypes;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public List<DataMessage> getDatamessage() {
		return datamessage;
	}

	public void setDatamessage(List<DataMessage> datamessage) {
		this.datamessage = datamessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrmessage() {
		return errmessage;
	}

	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String[] getDatetypes() {
		return datetypes;
	}

	public void setDatetypes(String[] datetypes) {
		this.datetypes = datetypes;
	}

}
