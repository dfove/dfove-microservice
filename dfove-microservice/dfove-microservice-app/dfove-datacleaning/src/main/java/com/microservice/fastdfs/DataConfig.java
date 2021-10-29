package com.microservice.fastdfs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import common.entity.MapObject;

@Component(value = "dataConfig")
@ConfigurationProperties(prefix = "data-config")
public class DataConfig extends MapObject<DataTemplate> {

}
