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

import common.constant.Log;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Jimmy on 2020/2/21.
 */
public class LogstashLoggerService {

    private static final Logger logger = LoggerFactory.getLogger(LogstashLoggerService.class);

    public static boolean writeLog(String level, Map<String, Object> logMap) {
        switch (level) {
//            case Log.LEVEL_TRACE:
//                logger.trace("log:{}", StructuredArguments.entries(logMap));
//                return true;
            case Log.LEVEL_DEBUG:
                logger.debug("log:{}", StructuredArguments.entries(logMap));
                return true;
            case Log.LEVEL_INFO:
                logger.info("log:{}", StructuredArguments.entries(logMap));
                break;
            case Log.LEVEL_WARN:
                logger.warn("log:{}", StructuredArguments.entries(logMap));
                return true;
            case Log.LEVEL_ERROR:
                logger.error("log:{}", StructuredArguments.entries(logMap));
                return true;
            default:
                logger.error("level:{} logMap:{}", level, logMap);
        }
        return false;
    }

}
