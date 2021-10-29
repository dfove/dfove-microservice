package com.microservice.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
public class AuthApplication {

	@Bean(name="RestTemplate")
	public RestTemplate getRestTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean(name="RestTemplateEureka")
	@LoadBalanced
	public RestTemplate getRestTemplateEureka()
	{
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
