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
package com.microservice.log.api;

import com.microservice.core.UserAnnotation;
import com.microservice.log.service.OperateLogService;
import com.microservice.log.vo.NameValueVo;
import com.microservice.log.vo.OperateLogParameter;
import com.microservice.log.vo.PageDataRequest;
import com.microservice.log.vo.UserParameter;
import common.entity.OperateLog;
import common.entity.OperateResult;
import common.search.entity.PageResult;
import common.vo.AdminVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jimmy on 2020/2/21.
 */
@Api(value = "操作日志接口", tags = {"操作日志接口"})
@RestController
@RequestMapping("/dfove/v1/operatelog")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @ApiOperation(hidden = true, value = "添加操作日志")
    @PostMapping("add")
    public OperateResult<Boolean> addOperateLog(@RequestBody OperateLog log) {
        if (log == null) {
            return new OperateResult<Boolean>().failed("参数不能为空");
        }
        Boolean result = operateLogService.addOperateLog(log);
        return OperateResult.b().success(result);
    }

    @ApiOperation(value = "查询日志操作类型")
    @PostMapping(value = "query/type")
    public OperateResult<List<NameValueVo<String>>> getOperateLogOperateType() {
        List<NameValueVo<String>> data = operateLogService.getOperateLogOperateType();
        return new OperateResult().success(data);
    }

    @ApiOperation(value = "查询日志操作用户")
    @UserAnnotation
    @PostMapping(value = "query/user")
    public PageResult<List<AdminVo>> getOperateLogUsers(@RequestBody PageDataRequest<UserParameter> request) {
        if (request == null || !request.checkPage()) {
            return new PageResult<List<AdminVo>>().failed("参数有误");
        }
        PageResult<List<AdminVo>> adminVos = operateLogService.getOperateLogUsers(request);
        return adminVos;
    }

    @ApiOperation(value = "根据条件获取操作日志")
    @UserAnnotation
    @PostMapping(value = "query/list")
    public PageResult<List<OperateLog>> getOperateLogs(@RequestBody PageDataRequest<OperateLogParameter> request) {
        if (request == null || !request.checkPage()) {
            return new PageResult<List<OperateLog>>().failed("参数有误");
        }
        PageResult<List<OperateLog>> logs = operateLogService.getOperateLogs(request);
        return logs;
    }

}
