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
package com.microservice.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Jimmy on 2020/2/24.
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "log")
public class LogConfig {

    private Map<String,String> operateTypeMap;

    public Map<String, String> getOperateTypeMap() {
        return operateTypeMap;
    }

    public void setOperateTypeMap(Map<String, String> operateTypeMap) {
        this.operateTypeMap = operateTypeMap;
    }

    @Override
    public String toString() {
        return "LogOperateType{" +
                "operateTypeMap=" + operateTypeMap +
                '}';
    }
}
