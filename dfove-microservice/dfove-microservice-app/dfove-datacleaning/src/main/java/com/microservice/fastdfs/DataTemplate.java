package com.microservice.fastdfs;

import java.util.Map;

import com.microservice.fastdfs.entity.Ranges;

public class DataTemplate {

	private String name = "数据";
	private String tableName = "table";

	private String[] importTitles;
	private String[] importColumns;
	private int[] importColumnsLength;
	private String[] importTypes;
	private String[] importDateTypes;
	private String[][] importNotNull;
	private Map<String, Ranges> importRanges;

	private String disinctcolumn;
	private String where;
	private String split;

	public String getName() {
		return name;
	}

	public String getDisinctcolumn() {
		return disinctcolumn;
	}

	public void setDisinctcolumn(String disinctcolumn) {
		this.disinctcolumn = disinctcolumn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getImportTitles() {
		return importTitles;
	}

	public void setImportTitles(String[] importTitles) {
		this.importTitles = importTitles;
	}

	public String[] getImportColumns() {
		return importColumns;
	}

	public void setImportColumns(String[] importColumns) {
		this.importColumns = importColumns;
	}

	public int[] getImportColumnsLength() {
		return importColumnsLength;
	}

	public void setImportColumnsLength(int[] importColumnsLength) {
		this.importColumnsLength = importColumnsLength;
	}

	public String[] getImportTypes() {
		return importTypes;
	}

	public void setImportTypes(String[] importTypes) {
		this.importTypes = importTypes;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public String[] getImportDateTypes() {
		return importDateTypes;
	}

	public void setImportDateTypes(String[] importDateTypes) {
		this.importDateTypes = importDateTypes;
	}

	public String[][] getImportNotNull() {
		return importNotNull;
	}

	public void setImportNotNull(String[][] importNotNull) {
		this.importNotNull = importNotNull;
	}

	public Map<String, Ranges> getImportRanges() {
		return importRanges;
	}

	public void setImportRanges(Map<String, Ranges> importRanges) {
		this.importRanges = importRanges;
	}


}
