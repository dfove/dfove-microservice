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
package com.microservice.log.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jimmy on 2020/3/21.
 */
@Configuration
public class BeanConfig {

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

    @Bean(name = "RestTemplateEureka")
    @LoadBalanced
    public RestTemplate restTemplateEureka() {
        RestTemplate restTemplate = new RestTemplate(requestFactory());
        return restTemplate;
    }

}
