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
package com.microservice.log.constant;

import common.constant.Log;

/**
 * Created by Jimmy on 2020/2/24.
 */
public class Elasticsearch {

    public static final String INDEX_LOG_OPERATE = "log-" + Log.TYPE_OPERATE;
    public static final String INDEX_LOG_SYSTEM = "log-" + Log.TYPE_SYSTEM;

    public static final String FIELD_TYPE = "type";
    public static final String FIELD_LEVEL = "level";
    public static final String FIELD_ADMIN_ID = "adminId";
    public static final String FIELD_ADMIN_NAME = "adminName";
    public static final String FIELD_ADMIN_REAL_NAME = "adminRealName";
    public static final String FIELD_ADMIN_ORGANIZATION_ID = "adminOrganizationId";
    public static final String FIELD_USER_SCOPE_TYPE = "userScopeType";
    public static final String FIELD_MODULE = "module";
    public static final String FIELD_OPERATE_TYPE = "operateType";
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_TIME = "time";
    public static final String FIELD_TERMINAL_TYPE = "terminalType";
    public static final String FIELD_TERMINAL_IP = "terminalIp";

    public static final String FIELD_SERVICE_NAME = "serviceName";
    public static final String FIELD_SERVICE_IP = "serviceIp";
    public static final String FIELD_SERVICE_PORT = "servicePort";

    public static final String FIELD_DATE_TIME = "dateTime";

    public static final String DOT_KEYWORD = ".keyword";
    public static final String FIELD_USER_NAME_KEYWORD = FIELD_ADMIN_NAME + DOT_KEYWORD;
    public static final String FIELD_USER_REAL_NAME_KEYWORD = FIELD_ADMIN_REAL_NAME + DOT_KEYWORD;

}
