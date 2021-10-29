package com.microservice.fastdfs.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.microservice.fastdfs.BeanListener;

public class KafkaUtil {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static KafkaTemplate<String, Object> kafkaTemplate = (KafkaTemplate) BeanListener.getBean("kafkaTemplate");

	private final static String QUERY_DEFAULT = "test";

	public static ListenableFuture<SendResult<String, Object>> dosend(Object data) {
		return kafkaTemplate.send(QUERY_DEFAULT, data);
	}

	public static ListenableFuture<SendResult<String, Object>> dosend(String topic, Object data) {
		return kafkaTemplate.send(topic, 0, null, data);
	}

	public static ListenableFuture<SendResult<String, Object>> dosend(String topic, Integer partition, String key, Object data) {
		return kafkaTemplate.send(topic, partition, key, data);
	}

}
