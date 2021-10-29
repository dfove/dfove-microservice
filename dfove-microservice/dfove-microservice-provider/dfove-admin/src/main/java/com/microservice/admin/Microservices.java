package com.microservice.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import common.entity.MapMap;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "microservices")
public class Microservices extends MapMap {
	private Integer restTimeout;
	
	public void setRestTimeout(Integer restTimeout) {
		this.restTimeout = restTimeout;
	}

	public Integer getRestTimeout() {
		return this.restTimeout;
	}
}
