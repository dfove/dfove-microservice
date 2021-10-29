package com.microservice.filestorage.entity;

import common.search.entity.BaseEntity;

public class FileStateDetail extends BaseEntity<String> {
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
        this.fileStateId = fileStateId == null ? null : fileStateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab == null ? null : tab.trim();
    }

	@Override
	public String primaryKey() {
		// TODO Auto-generated method stub
		return id;
	}
}