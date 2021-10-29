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
package com.microservice.fastdfs.demo;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import common.tools.FastdfsUtil;

@Configuration
public class FastdfsConfig implements ApplicationRunner {
	
	@Value("${fdfs.tracker-list[0]}")
	private String trackerServers;
	private String connectTimeoutInSeconds ="30";
	private String networkTimeoutInSeconds ="60";
	private String charset="UTF-8";
	private String httpAntiStealToken ="no";
	private String httpSecretKey ="zgr888";
	private String httpTrackerHttpPort ="9990";
	
	private void initByProperties() {
		Properties prop = new Properties();
		prop.setProperty("fastdfs.connect_timeout_in_seconds",this.connectTimeoutInSeconds);
		prop.setProperty("fastdfs.network_timeout_in_seconds", this.networkTimeoutInSeconds);
		prop.setProperty("fastdfs.charset", this.charset);
		prop.setProperty("fastdfs.http_anti_steal_token", this.httpAntiStealToken);
		prop.setProperty("fastdfs.http_secret_key", this.httpSecretKey);
		prop.setProperty("fastdfs.http_tracker_http_port",this.httpTrackerHttpPort);
		prop.setProperty("fastdfs.tracker_servers",this.trackerServers);
		
		FastdfsUtil.initByProperties(prop);
	}
	
 	@Override
    public void run(ApplicationArguments args) throws Exception {
 		initByProperties();
    }
}
