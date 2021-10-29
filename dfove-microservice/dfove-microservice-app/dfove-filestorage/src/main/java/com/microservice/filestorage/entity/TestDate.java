package com.microservice.filestorage.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TestDate {

//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createtime;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	private Date createtime1=new Date();
	
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getCreatetime1() {
		return createtime1;
	}
	public void setCreatetime1(Date createtime1) {
		this.createtime1 = createtime1;
	}
	
	
}
