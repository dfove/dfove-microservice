package com.microservice.fastdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import common.redis.*;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "redis")
public class RedisConfig extends Config  {
	
	@Bean
	@RefreshScope
	public IRedis redis() {
		return new Redis(this);
	}
}
