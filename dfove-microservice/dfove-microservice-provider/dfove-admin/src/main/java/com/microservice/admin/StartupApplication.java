package com.microservice.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableEurekaClient
@SpringBootApplication
@MapperScan(basePackages = "com.microservice.admin.mapper") // 不能缺少
public class StartupApplication {

	@Autowired
	private Microservices microservices;
	
	@Bean
	public SimpleClientHttpRequestFactory requestFactory() {
		int _timeout = microservices.getRestTimeout();
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(_timeout);
		factory.setReadTimeout(_timeout);
		return factory;
	}

	@Bean(name = "RestTemplate")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(requestFactory());
		return restTemplate;
	}

	@Bean(name = "RestTemplateEureka")
	@LoadBalanced
	public RestTemplate restTemplateEureka() {
		RestTemplate restTemplate = new RestTemplate(requestFactory());
		return restTemplate;
	}
	
	public static void main(String[] args) {
		try {
			SpringApplication.run(StartupApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
