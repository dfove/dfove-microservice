package com.microservice.filestorage;

public class FileTemplate {
	private String scanPath;
	private String regx;
	private String dbinfo;
	private String table;
	private String split;
	private int readline;
	
	public String getScanPath() {
		return scanPath;
	}
	public void setScanPath(String scanPath) {
		this.scanPath = scanPath;
	}
	public String getRegx() {
		return regx;
	}
	public void setRegx(String regx) {
		this.regx = regx;
	}
	public String getDbinfo() {
		return dbinfo;
	}
	public void setDbinfo(String dbinfo) {
		this.dbinfo = dbinfo;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getSplit() {
		return split;
	}
	public void setSplit(String split) {
		this.split = split;
	}
	public int getReadline() {
		return readline;
	}
	public void setReadline(int readline) {
		this.readline = readline;
	}

}
