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
package com.microservice.fastdfs.service;

import org.springframework.web.multipart.MultipartFile;

import com.microservice.fastdfs.BeanListener;

public class DataCleaningRun implements Runnable {
	private MultipartFile file;
	private String table;
	private String id;
	private String adminId;
	private String originalFileName;
	
	private  DataCleaning dataCleaning=(DataCleaning)BeanListener.getBean("dataCleaning");
	
	public DataCleaningRun(MultipartFile file, String table, String id,String adminId) {
		super();
		this.file = file;
		this.table = table;
		this.id = id;
		this.adminId = adminId;
	}

	public DataCleaningRun(MultipartFile file, String table, String id, String adminId, String originalFileName) {
		super();
		this.file = file;
		this.table = table;
		this.id = id;
		this.adminId = adminId;
		this.originalFileName = originalFileName;
	}

	@Override
	public void run() {
		try {
			dataCleaning.doDataCleaning(file, table, id,adminId,originalFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
