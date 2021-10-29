package com.microservice.filestorage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import common.entity.MapObject;

@Component(value = "dataSplit")
@ConfigurationProperties(prefix = "data-split")
public class DataSplit extends MapObject<FileTemplate> {

}
