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
package com.microservice.fastdfs.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.fastdfs.mapper.DataCleaningMapper;

import common.tools.DateUtil;
import oracle.net.aso.e;

@Service
public class CasepersonExpressRB extends AbstractDataCleaningStorage {

	@Override
	public void onDataBehavior(DataCleaningType dataCleaningType) {
		List<Map<String, Object>> behaviorList=new ArrayList<>();
		if (dataCleaningType!=null && dataCleaningType.getList() !=null&& dataCleaningType.getList().size()>0) {			
			dataCleaningType.getList().stream().forEach(e->{
			Map<String, Object>behavior=new HashMap<>();
			behavior.put("casepersonId", e.get("uuid"));
			behavior.put("casepersonName", e.get("name"));
			behavior.put("caseAddress", e.get("senderAddress"));
			behavior.put("startTime", DateUtil.toDate(e.get("acceptTime")+""));
			behavior.put("endTime", e.get(""));
			behavior.put("caseType", "CasepersonExpress");
			behavior.put("caseDescription", e.get(""));
			behavior.put("caseDetailsid", e.get("orderid"));
			behaviorList.add(behavior);	
			});
			try {
				
				dataCleaningMapper.addBehavior(behaviorList);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	@Override
	public void onDataRelation(DataCleaningType dataCleaningType) {
		
		List<Map<String, Object>> relationList=new ArrayList<>();
		if (dataCleaningType!=null && dataCleaningType.getList() !=null&& dataCleaningType.getList().size()>0) {			
			dataCleaningType.getList().stream().forEach(e->{
			Map<String, Object>relation=new HashMap<>();
			relation.put("casepersonId", e.get("uuid"));
			relation.put("casepersonName", e.get("name"));
			relation.put("senderMobile", e.get("senderMobile"));
			relation.put("receiverId", e.get(""));
			relation.put("receiverName", e.get("receiverName"));
			relation.put("receiverMobile", e.get("receiverMobile"));
			relation.put("relationName", e.get("寄递行为"));
			relation.put("detailedId", e.get("orderid"));
			relationList.add(relation);	
			});
			try {				
				dataCleaningMapper.addRelation(relationList);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
//		dataCleaningMapper.addRelation(dataCleaningType.getList());

	}
}
