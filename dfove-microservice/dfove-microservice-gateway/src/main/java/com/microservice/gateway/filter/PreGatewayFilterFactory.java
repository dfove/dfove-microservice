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

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

public class PreGatewayFilterFactory extends AbstractGatewayFilterFactory {

	/*
    public PreGatewayFilterFactory() {
        super(Config.class);
    }

    public GatewayFilter apply() {
        return apply(o -> {
        });
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling change.filter  ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            builder.header("GatewayFilter", "PreGatewayFilterFactory success");
            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }
      public static class Config {
          //Put the configuration properties for your filter here
    }
    */
	@Override
	public GatewayFilter apply(Object config) {
		// TODO Auto-generated method stub
		return null;
	}
}