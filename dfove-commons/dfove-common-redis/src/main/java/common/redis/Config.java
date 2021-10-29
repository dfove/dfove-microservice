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
package common.redis;

public class Config {

	private String host = "localhost";
	private int port = 6379;
	private String password = "";

	public Config() {

	}

	public Config(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Config(String host, int port, String password) {
		this.host = host;
		this.port = port;
		this.password=password;
	}

	public void setHost(String host) {
		this.host = host;
	}
	public String getHost() {
		return this.host;
	}

	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return this.port;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
}
