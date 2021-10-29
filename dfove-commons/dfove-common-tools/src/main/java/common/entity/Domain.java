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
package common.entity;

import java.util.Map;

/**
 * 配置token访问域
 * 如果允许匿名访问，该类可以不用
 */
public class Domain {

	private String name;
	private String anonymous;
	private String password;
	private Integer expiration;

	public Domain() {

	}

	public Domain(Map<String, String> map) {
		setName(map.get("name"));
		setAnonymous(map.get("anonymous"));
		setPassword(map.get("password"));
		setExpiration(Integer.parseInt(map.get("expiration")));
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public String getAnonymous() {
		return this.anonymous;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setExpiration(Integer expiration) {
		this.expiration = expiration;
	}

	public Integer getExpiration() {
		return this.expiration;
	}
	
	public Boolean isValid(String anonymous, String password) {
		return anonymous != null && !anonymous.isEmpty() && password != null && !password.isEmpty()
				&& this.anonymous.equals(anonymous) && this.password.equals(password);
	}

}
