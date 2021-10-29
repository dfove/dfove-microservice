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
package com.microservice.fastdfs.api;

import static org.mockito.Matchers.anyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ls.LSInput;

import com.microservice.fastdfs.AreaConfig;
import com.microservice.fastdfs.kafka.KafkaUtil;
import com.microservice.fastdfs.service.IDataCleaningService;

@RestController
public class KafkaDemoController {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	@Autowired
	private IDataCleaningService dataCleaningService;

	@RequestMapping("/kafkad1")
	public String kafkaDemo(String msg) {
		try {
			KafkaUtil.dosend("发送值"+msg);
			// kafkaTemplate.send("test", 0,null,"1222中文");
			return "11";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/express")
	public String express() {
		List<String> list = new ArrayList<>();
		list.add("73111964316183");
		list.add("75141574389646");
		list.add("75141590968572");
		list.add("7514159242783611");
		StringBuilder sb=new StringBuilder();
		List<String> list2=dataCleaningService.keyList("TB_CASEPERSON_EXPRESS", "ORDERID", list);
		for (int i = 0; i < list2.size(); i++) {
			sb.append(list2.get(i)+",\n");
		}
		return sb.toString();
	}
	@RequestMapping("/a")
	public String demo2() {
		List<Map<String, Object>> list=new ArrayList<>();
		long t1=System.currentTimeMillis();
		for (int i = 0; i < 1500; i++) {			
			Map<String, Object> map =new HashMap<>();
			map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
			map.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
			list.add(map);
		}
		long t2=System.currentTimeMillis();
//		Map<String, Object> map2 =new HashMap<>();
//		map2.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
//		map2.put("uuid", UUID.randomUUID().toString().replaceAll("-", ""));
//		list.add(map2);
		int i=dataCleaningService.addRetailExtend(list);
		long t3=System.currentTimeMillis();
		
		System.out.println((t3-t2)+"--"+"---"+(t2-t1));
		return i+"";
	}
	@Autowired
	private AreaConfig areaConfig;
	@RequestMapping("/a1")
	public String demo3() {
//		for (Iterator iterator = areaConfig.getMap().) {
//			type type = (type) iterator.next();
//			
//		}
		return areaConfig.getMap().size()+"";
	}

}
