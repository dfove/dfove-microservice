package com.microservice.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import common.entity.MapMap;

@Component(value = "TopicSearch")
@ConfigurationProperties(prefix = "topic-search")
public class TopicSearch extends MapMap {

}
