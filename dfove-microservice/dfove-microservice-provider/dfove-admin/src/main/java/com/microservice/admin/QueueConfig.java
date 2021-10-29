package com.microservice.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import common.mq.*;

@Component
@RefreshScope
@ConfigurationProperties(prefix = "mq")
public class QueueConfig extends Config {

	@Bean
	@RefreshScope
	public IQueue queue() {
		return new RabbitMQ(this);
	}
}
