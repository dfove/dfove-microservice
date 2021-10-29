/*
 * Copyright (c) 2021 dfove.com Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microservice.websocket;

//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@SpringBootApplication
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
		SpringApplication.run(StartupApplication.class, args);
	}
}
