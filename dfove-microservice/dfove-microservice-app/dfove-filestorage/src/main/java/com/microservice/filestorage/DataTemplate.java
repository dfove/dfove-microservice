package com.microservice.filestorage;


public class DataTemplate {

	private String name = "数据";
	private String tableName = "table";
	private Integer exportWhereEmpty;

	private String[] exportTitles;
	private String[] exportColumns;

	private String[] importTitles;
	private String[] importColumns;
	private String[] importTypes;
	private String[] importDemos;
	private String importWhere;

	public String getName() {
		return name;
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

	public Integer getExportWhereEmpty() {
		return exportWhereEmpty;
	}

	public void setExportWhereEmpty(Integer exportWhereEmpty) {
		this.exportWhereEmpty = exportWhereEmpty;
	}

	public String[] getExportTitles() {
		return exportTitles;
	}

	public void setExportTitles(String[] exportTitles) {
		this.exportTitles = exportTitles;
	}

	public String[] getExportColumns() {
		return exportColumns;
	}

	public void setExportColumns(String[] exportColumns) {
		this.exportColumns = exportColumns;
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

	public String[] getImportTypes() {
		return importTypes;
	}

	public void setImportTypes(String[] importTypes) {
		this.importTypes = importTypes;
	}

	public String[] getImportDemos() {
		return importDemos;
	}

	public void setImportDemos(String[] importDemos) {
		this.importDemos = importDemos;
	}

	public String getImportWhere() {
		return importWhere;
	}

	public void setImportWhere(String importWhere) {
		this.importWhere = importWhere;
	}


}
