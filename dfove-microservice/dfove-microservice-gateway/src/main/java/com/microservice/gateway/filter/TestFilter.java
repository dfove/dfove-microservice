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
package com.microservice.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

public class TestFilter {
	
	private static Logger _log = LoggerFactory.getLogger(TestFilter.class);

    @Bean
    @Order(-1)
    public GlobalFilter test1() {
        return (exchange, chain) -> {
            _log.error("first pre filter=================");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                _log.error("first post filter==============");
            }));
        };
    }

    @Bean
    @Order(0)
    public GlobalFilter test2() {
        return (exchange, chain) -> {
            _log.error("second pre filter=====================");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                _log.error("second post filter=================");
            }));
        };
    }

    @Bean
    @Order(1)
    public GlobalFilter test3() {
        return (exchange, chain) -> {
            _log.error("third pre filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                _log.error("first post filter");
            }));
        };
    }
}