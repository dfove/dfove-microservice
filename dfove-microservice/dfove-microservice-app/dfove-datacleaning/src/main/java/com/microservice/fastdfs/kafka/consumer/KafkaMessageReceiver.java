package com.microservice.fastdfs.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.microservice.fastdfs.kafka.DataCleaningType;

import common.tools.JsonUtil;

@Component
public class KafkaMessageReceiver {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaMessageReceiver.class);
	@Autowired
	private DataCleaningRunRB dataCleaningRunRb;

    //指定监听的topic，当前消费者组id
    @KafkaListener(topics = {"test"}, groupId = "receiver")
    public void registryReceiver(ConsumerRecord<Integer, String> integerStringConsumerRecords) {
//    	System.out.println(integerStringConsumerRecords.value()+"---"+DateUtil.dateTime());
    	if (integerStringConsumerRecords.value()!=null&& !integerStringConsumerRecords.value().isEmpty()) {
    		DataCleaningType dataCleaningType=JsonUtil.jsonToObject(integerStringConsumerRecords.value(), DataCleaningType.class);
    		dataCleaningRunRb.onDataRb(dataCleaningType.getType(),dataCleaningType);
		}
    	
    	
        log.info(integerStringConsumerRecords.value());
    }
}
