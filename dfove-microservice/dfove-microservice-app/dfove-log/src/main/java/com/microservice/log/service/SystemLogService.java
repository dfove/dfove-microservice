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
package com.microservice.log.service;

import com.microservice.log.constant.Elasticsearch;
import common.entity.SystemLog;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jimmy on 2020/2/24.
 */
@Service
public class SystemLogService {

    public Boolean addSystemLog(SystemLog systemLog) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put(Elasticsearch.FIELD_TYPE, systemLog.getType());
        logMap.put(Elasticsearch.FIELD_SERVICE_NAME, systemLog.getServiceName());
        logMap.put(Elasticsearch.FIELD_SERVICE_IP, systemLog.getServiceIp());
        logMap.put(Elasticsearch.FIELD_SERVICE_PORT, systemLog.getServicePort());
        logMap.put(Elasticsearch.FIELD_CONTENT, systemLog.getContent());
        logMap.put(Elasticsearch.FIELD_TIME, systemLog.getTime());
        LogstashLoggerService.writeLog(systemLog.getLevel(), logMap);
        return true;
    }

}
