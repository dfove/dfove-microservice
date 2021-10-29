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
package com.microservice.core;

import common.annotation.RecordLog;
import common.constant.Log;
import common.constant.RedisKey;
import common.entity.OperateLog;
import common.entity.OperateResult;
import common.entity.SystemLog;
import common.entity.UserScope;
import common.redis.IRedis;
import common.vo.AdminVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jimmy on 2020/2/22.
 */
public class LogComponent {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private Microservices microservices;
//    @Qualifier("RestTemplateEureka")
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private IRedis redis;

    @Value("${spring.application.name:service}")
    private String serviceName;
    @Value("${spring.cloud.client.ip-address:0.0.0.0}")
    private String serviceIp;
    @Value("${server.port:0}")
    private int servicePort;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 20, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    /**
     * 发送操作日志
     *
     * @param restTemplate
     * @param url
     * @param recordLog
     * @return
     */
    public boolean sendOperateLog(RestTemplate restTemplate, String url, RecordLog recordLog) {
        return sendOperateLog(restTemplate, url, recordLog.module(), recordLog.type(), recordLog.content());
    }

    /**
     * 发送操作日志
     *
     * @param operateType    操作类型
     * @param operateContent 操作内容
     * @return
     */
    public boolean sendOperateLog(RestTemplate restTemplate, String url, String module, String operateType, String operateContent) {
        OperateLog log = getOperateLog(App.userScope(), module, operateType, operateContent);
        if (log == null) {
            return false;
        }
        return sendOperateLog(restTemplate, url, log);
    }

    public OperateLog getOperateLog(UserScope userScope, String module, String operateType, String operateContent){
        if (userScope == null) {
            return null;
        }
        OperateLog log = new OperateLog();
        log.setType(Log.TYPE_OPERATE);
        if (Log.OPERATE_DELETE.equals(operateType)) {
            log.setLevel(Log.LEVEL_WARN);
        } else {
            log.setLevel(Log.LEVEL_INFO);
        }
        log.setAdminId(userScope.getUserId());
        log.setAdminName(userScope.getUserName());
        log.setAdminRealName(userScope.getRealName());
        log.setUserScopeType(userScope.getScopeType());
        log.setTerminalType(userScope.getSource());
        AdminVo adminVo = redis.getHashField(RedisKey.getUserKey(userScope.getUserId()), RedisKey.ADMIN, AdminVo.class);
        if (adminVo != null) {
            log.setAdminOrganizationId(adminVo.getOrganizationId());
        }
        log.setModule(module);
        log.setOperateType(operateType);
        log.setContent(operateContent);
        log.setTerminalIp(App.ip());
        log.setTime(new Date());
        return log;
    }

    public boolean sendOperateLog(RestTemplate restTemplate, String url, OperateLog log) {
        if (log == null) {
            return false;
        }
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ParameterizedTypeReference<OperateResult<Boolean>> returnType = new ParameterizedTypeReference<OperateResult<Boolean>>() {
                };
                OperateResult<OperateResult<Boolean>> operateResult = RestTemplateUtil.post(restTemplate, url, log, null, returnType, true);
                logger.info("url:{} operate log:{}, operateResult:{}", url, log, operateResult);
            }
        });
        return true;
    }


    public boolean sendSystemLog(RestTemplate restTemplate, String url, String content) {
        return sendSystemLog(restTemplate, url, Log.LEVEL_INFO, content);
    }

    public boolean sendSystemLog(RestTemplate restTemplate, String url, String level, String content) {
        SystemLog systemLog = new SystemLog();
        systemLog.setType(Log.TYPE_SYSTEM);
        systemLog.setLevel(level);
        systemLog.setServiceName(serviceName);
        systemLog.setServiceIp(serviceIp);
        systemLog.setServicePort(servicePort);
        systemLog.setContent(content);
        systemLog.setTime(new Date());
        return sendSystemLog(restTemplate, url, systemLog);
    }

    public boolean sendSystemLog(RestTemplate restTemplate, String url, SystemLog systemLog) {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ParameterizedTypeReference<OperateResult<Boolean>> returnType = new ParameterizedTypeReference<OperateResult<Boolean>>() {
                };
                OperateResult<OperateResult<Boolean>> operateResult = RestTemplateUtil.post(restTemplate, url, systemLog, null, returnType, true);
                logger.info("url:{} system log:{}, operateResult:{}", url, systemLog, operateResult);
            }
        });
        return true;
    }

}
