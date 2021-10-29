package com.microservice.fastdfs;

import com.microservice.core.LogComponent;
import com.microservice.fastdfs.Microservices;
import common.annotation.RecordLog;
import common.constant.Log;
import common.entity.OperateResult;
import common.enums.AccessType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

/**
 * Created by Jimmy on 2020/2/21.
 */
@Aspect
@Component
public class LogAop {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Microservices microservices;
    @Qualifier("RestTemplateEureka")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LogComponent logComponent;

    @Pointcut("within(com.microservice.fastdfs.api.*) && @annotation(common.annotation.RecordLog)")
    public void pointCut() {
    }

    @AfterReturning(pointcut = "pointCut()", returning = "ret")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) {
        logger.info("joinPoint:{} ret:{}", joinPoint, ret);
        if (ret instanceof OperateResult) {
            OperateResult operateResult = (OperateResult) ret;
            if (!operateResult.ok()) {
                return;
            }
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if (method.isAnnotationPresent(RecordLog.class)) {
                RecordLog recordLog = method.getAnnotation(RecordLog.class);
                String serviceUrl = microservices.getValue("log", AccessType.eureka.toString());
                String url = String.format("%s/" + Log.PATH_OPERATE_ADD, serviceUrl);
                boolean sendOperateLog = logComponent.sendOperateLog(restTemplate, url, recordLog);
            }
        }
    }

}
