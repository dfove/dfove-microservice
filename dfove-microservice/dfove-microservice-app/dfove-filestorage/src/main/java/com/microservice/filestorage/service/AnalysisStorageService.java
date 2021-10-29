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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.inject.New;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.filestorage.entity.AnalysisStorage;
import com.microservice.filestorage.entity.AnalysisStorageModel;
import com.microservice.filestorage.entity.DataAnalysisModel;
import com.microservice.filestorage.mapper.AnalysisStorageMapper;

import common.entity.OperateResult;
import common.redis.IRedis;
import common.search.entity.BaseService;

@Service
public class AnalysisStorageService extends BaseService<AnalysisStorage, Long, AnalysisStorageModel> implements IAnalysisStorageService {

	@Resource
	private AnalysisStorageMapper analysisStorageMapper;

	@Autowired
	private IRedis redis;

	@Resource
	private IBusinessImportService businessImportService;

	private final static String DEUALT_KEY = "DataAnalysisTemp";

	@Override
	public String itemName() {
		return null;
	}

	@Override
	public AnalysisStorageMapper mapper() {
		return analysisStorageMapper;
	}

	@Override
	public OperateResult<Integer> analysisService(AnalysisStorageModel analysisStorageModel) {
		DataAnalysisModel dam = redis.get(DEUALT_KEY + analysisStorageModel.getId(), DataAnalysisModel.class);
		if (dam == null || dam.getData() == null || dam.getData().size() == 0) {
			return new OperateResult<Integer>().failed("数据不存在！！");
		}
		//时间转换
		List<Map<String, Object>> list = dam.getData();
		String[] datetypes = dam.getDatetypes();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			if (datetypes != null) {
				for (int j = 0; j < datetypes.length; j++) {
					Object value = map.get(datetypes[j]);
					if (!"".equals(value + "")) {
						try {			
							map.put(datetypes[j], new Date((long) map.get(datetypes[j])));
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("类型转换出错："+map.get(datetypes[j]));
							map.put(datetypes[j], new Date());
						}						
					}
				}
			}
		}
		int sum=0;//批量入库 异常逐个入库
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> list2=new ArrayList<>();
			list2.add(list.get(i));
			try {				
				OperateResult<Integer> result=businessImportService.batchImport(dam.getTable(), dam.getColumns(), list2, null);
				sum+=result.getData();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new OperateResult<Integer>().success(sum);
//		return businessImportService.batchImport(dam.getTable(), dam.getColumns(), dam.getData(), null);
	}

}