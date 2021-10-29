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
package com.microservice.filestorage.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.microservice.filestorage.entity.FastdfsEntity;
import com.microservice.filestorage.mapper.FastdfsMapper;

import common.search.entity.BaseService;

@Service
public class FastdfsService  extends BaseService<FastdfsEntity, String, FastdfsEntity> implements IFastdfsService {

	@Resource
	private FastdfsMapper fastdfsMapper;

	@Override
	public String itemName() {
		return null;
	}


	@Override
	public FastdfsMapper mapper() {
		return fastdfsMapper;
	}

}