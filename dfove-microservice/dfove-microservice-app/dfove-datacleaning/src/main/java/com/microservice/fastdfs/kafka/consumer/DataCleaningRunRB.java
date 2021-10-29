package com.microservice.fastdfs.kafka.consumer;

import org.springframework.stereotype.Service;

import com.microservice.fastdfs.BeanListener;
import com.microservice.fastdfs.kafka.DataCleaningRB;
import com.microservice.fastdfs.kafka.DataCleaningType;

@Service
public class DataCleaningRunRB {

	// private DataCleaningStorage
	// dataCleaningStorage=(DataCleaningStorage)BeanListener.getBean("dataCleaningStorage");

	public DataCleaningRB getDataCleaningRb(String dataRbName) {
		if (dataRbName == null || dataRbName.isEmpty()) {
			throw new RuntimeException("dataCleaningStorage 为空！");
		}
		return (DataCleaningRB) BeanListener.getBean(dataRbName +"RB");
	}

	public void onDataRb(String dataRbName, DataCleaningType dataCleaningType) {
		DataCleaningRB dcs = getDataCleaningRb(dataRbName);
		dcs.onDataRb(dataCleaningType);
	}

}
