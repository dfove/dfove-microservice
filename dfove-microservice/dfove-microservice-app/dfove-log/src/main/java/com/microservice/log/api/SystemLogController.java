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

import com.microservice.log.service.SystemLogService;
import common.entity.OperateResult;
import common.entity.SystemLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jimmy on 2020/2/24.
 */
@Api(value = "系统日志接口", tags = {"系统日志接口"})
@RestController
@RequestMapping("/dfove/v1/systemlog")
public class SystemLogController {

    @Autowired
    private SystemLogService systemLogService;

    @ApiOperation(hidden = true, value = "")
    @PostMapping("add")
    public OperateResult<Boolean> createSystemLog(@RequestBody SystemLog systemLog) {
        if (systemLog == null) {
            return new OperateResult<Boolean>().failed("参数不能为空");
        }
        Boolean result = systemLogService.addSystemLog(systemLog);
        return OperateResult.b().success(result);
    }

}
