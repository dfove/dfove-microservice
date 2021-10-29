package com.microservice.filestorage.entity;

public class FileStateDetailModel{
	private String id;
	
    private String fileStateId;

    private String name;

    private String type;

    private String message;

    private String state;

    private String tab;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileStateId() {
		return fileStateId;
	}

	public void setFileStateId(String fileStateId) {
		this.fileStateId = fileStateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}


}