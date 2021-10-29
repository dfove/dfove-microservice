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
package com.microservice.gateway;

public class RouteLocatorCustomized {
	
	/*
	@Bean
	@RefreshScope
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
	
		return builder.routes()
				.route("microservice-authorization", 
						r->r.uri("microservice-user").pre)
				.build();
		
	
		return builder.routes()
				.route(r -> r.path("/baidu").uri("http://baidu.com:80/"))
				
				.route("test")
		          .uri("http://httpbin.org:80")
		          .predicate(host("**.abc.org").and(path("/image/png")))
		          .addResponseHeader("X-TestHeader", "foobar")
		          .and()
		      .route("test2")
		          .uri("http://httpbin.org:80")
		          .predicate(path("/image/webp"))
		          .add(addResponseHeader("X-AnotherHeader", "baz"))
		          .and()
		         
		      .build();
		      */
}
