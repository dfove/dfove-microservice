package com.microservice.gateway.filter;
/*
 * Copyright 2013-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/**
 * Sample throttling filter.
 * See https://github.com/bbeck/token-bucket
 */
public class ThrottleGatewayFilter{// implements GatewayFilter {
	/*
	private static final Logs log = new Logs(ThrottleGatewayFilter.class);

    int capacity;
    int refillTokens;
    int refillPeriod;
    TimeUnit refillUnit;

	public int getCapacity() {
		return capacity;
	}

	public ThrottleGatewayFilter setCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public int getRefillTokens() {
		return refillTokens;
	}

	public ThrottleGatewayFilter setRefillTokens(int refillTokens) {
		this.refillTokens = refillTokens;
		return this;
	}

	public int getRefillPeriod() {
		return refillPeriod;
	}

	public ThrottleGatewayFilter setRefillPeriod(int refillPeriod) {
		this.refillPeriod = refillPeriod;
		return this;
	}

	public TimeUnit getRefillUnit() {
		return refillUnit;
	}

	public ThrottleGatewayFilter setRefillUnit(TimeUnit refillUnit) {
		this.refillUnit = refillUnit;
		return this;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		TokenBucket tokenBucket = TokenBuckets.builder()
				.withCapacity(capacity)
				.withFixedIntervalRefillStrategy(refillTokens, refillPeriod, refillUnit)
				.build();

        //TODO: get a token bucket for a key
        log.debug("TokenBucket capacity: ");
        boolean consumed = tokenBucket.tryConsume();
        if (consumed) {
            return chain.filter(exchange);
        }
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
	}
	*/
}
