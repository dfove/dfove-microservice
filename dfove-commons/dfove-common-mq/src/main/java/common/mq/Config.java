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
package common.mq;

import java.util.Date;

public class Config {

	private String host = "localhost";
	private String userName = "yth";
	private String password = "zgr888";

	private int port = 0;

	private Date configTime = new Date();
	private Boolean valid = true;

	/**
	 * MQ配置
	 */
	public Config() {

	}

	/**
	 * MQ配置
	 */
	public Config(String host, String userName, String password) {
		this.host = host;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * 设置服务器地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 获取服务器地址
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * 设置端口
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取端口
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * 设置用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取用户名
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * 设置密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取密码
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * 设置配置时间
	 */
	public void setConfigTime(Date configTime) {
		this.configTime = configTime;
	}

	/**
	 * 获取配置时间
	 */
	public Date getConfigTime() {
		return this.configTime;
	}

	/**
	 * 设置是否可用
	 */
	public void seValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * 获取是否可用
	 */
	public Boolean geValid() {
		return this.valid;
	}
}
