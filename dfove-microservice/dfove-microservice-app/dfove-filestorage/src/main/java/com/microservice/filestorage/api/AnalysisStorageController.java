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
package com.microservice.filestorage.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.filestorage.entity.AnalysisStorageModel;
import com.microservice.filestorage.service.IAnalysisStorageService;

import common.entity.OperateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "解析入库接口", tags = { "解析入库接口" })
@RestController
@RequestMapping(value = "/dfove/v1/analysisstorage")

public class AnalysisStorageController {
	
	@Autowired
	private IAnalysisStorageService analysisStorageService;

	@ApiOperation(value = "基础数据清洗入库")
	@RequestMapping(value = "/analysis/storage", method = RequestMethod.POST)
	public OperateResult<Integer> analysisStorage(@RequestBody AnalysisStorageModel analysisStorageModel) {
		try {

			return analysisStorageService.analysisService(analysisStorageModel);
		} catch (Exception e) {
			e.printStackTrace();
			return new OperateResult<Integer>().failed(e.getMessage());
		}
		
	}
}
