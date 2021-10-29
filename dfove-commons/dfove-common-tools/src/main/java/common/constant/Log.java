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
package common.constant;

/**
 * Created by Jimmy on 2020/2/22.
 */
public class Log {

    public static final String PATH_OPERATE_ADD = "api/v1/operatelog/add";
    public static final String PATH_SYSTEM_ADD = "api/v1/systemlog/add";

    /**
     * 业务日志
     */
    public static final String TYPE_OPERATE = "operate";
    /**
     * 系统日志
     */
    public static final String TYPE_SYSTEM = "system";


    public static final String LEVEL_TRACE = "trace";
    public static final String LEVEL_DEBUG = "debug";
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_WARN = "warn";
    public static final String LEVEL_ERROR = "error";


    public static final String OPERATE_LOGIN = "login";
    public static final String OPERATE_LOGOUT = "logout";
    public static final String OPERATE_QUERY = "query";
    public static final String OPERATE_CREATE = "create";
    public static final String OPERATE_UPDATE = "update";
    public static final String OPERATE_DELETE = "delete";
    /**
     * 清洗
     */
    public static final String OPERATE_CLEANING = "cleaning";
    public static final String OPERATE_START = "start";
    public static final String OPERATE_STOP = "stop";
    public static final String OPERATE_FINISH = "finish";
    public static final String OPERATE_UPLOAD = "upload";
    public static final String OPERATE_DOWNLOAD = "download";
    public static final String OPERATE_IMPORT = "import";
    public static final String OPERATE_EXPORT = "export";


    // admin
    public static final String MODULE_ADMIN = "管理员";
    public static final String MODULE_ADMIN_ROLE = "管理员角色";
    public static final String MODULE_ADMIN_LOGIN = "管理员登录";
    public static final String MODULE_MENU = "菜单";
    public static final String MODULE_ORGANIZATION = "机构管理";
    public static final String MODULE_PERMISSION = "权限";
    public static final String MODULE_ROLE = "角色";
    public static final String MODULE_ROLE_MENU = "角色菜单";
    public static final String MODULE_ROLE_PERMISSION = "角色权限";

    // datacleaning
    public static final String MODULE_FILE = "文件";

    // task
    public static final String MODULE_TASK = "检索任务";
    public static final String MODULE_TASK_DETAIL = "检索任务详情";
    public static final String MODULE_TASK_RESULT = "检索任务结果";

    // dictionary
    public static final String MODULE_DATA_DICTIONARY = "数据字典";
    public static final String MODULE_DATA_DICTIONARY_CONTENT = "数据字典内容";

    // datacenter
    public static final String MODULE_CASE = "案件";
    public static final String MODULE_CASE_GOODS = "涉案物品";
    public static final String MODULE_CASE_PERSON_BEHAVIOR = "涉案人员行为历程";
    public static final String MODULE_CONTACT = "通联信息";
    public static final String MODULE_CASE_PERSON = "涉案人员";
    public static final String MODULE_CASE_PERSON_EXPRESS = "涉烟人员寄递";
    public static final String MODULE_CASE_PERSON_EXTEND = "涉案人员补全信息";
    public static final String MODULE_FAKE = "制假售假";
    public static final String MODULE_FIGURE_LABEL = "人物标签";
    public static final String MODULE_CASE_PERSON_PROPAGANDA = "涉烟宣传";
    public static final String MODULE_CASE_PERSON_RELATION = "涉案人员社会关系";
    public static final String MODULE_CASE_PERSON_SMS_AD = "涉烟短信宣传";
    public static final String MODULE_CASE_PERSON_WEB_SEARCH = "涉烟搜索";
    public static final String MODULE_CLUE = "线索";
    public static final String MODULE_CLUE_INSPECT = "稽查人员";
    public static final String MODULE_CLUE_PROGRESS = "线索进展";
    public static final String MODULE_CLUE_SUSPECT = "线索嫌疑人";
    public static final String MODULE_COLLECTION_CIGARET = "涉烟卷烟采集";
    public static final String MODULE_COLLECTION_DATABASE = "案件资料库";
    public static final String MODULE_COLLECTION_ENTERPRISE_FILE = "物流寄递企业档案";
    public static final String MODULE_COLLECTION_EXPRESS = "寄递面单采集";
    public static final String MODULE_EXPRESS_AREA = "涉烟物流地";
    public static final String MODULE_EXPRESS = "快递";
    public static final String MODULE_EXPRESS_POINT = "涉烟物流点部";
    public static final String MODULE_EXPRESS_RIDER = "涉烟快递人员";
    public static final String MODULE_JARGON = "涉烟关键字暗语";
    public static final String MODULE_MESSAGE = "消息通知";
    public static final String MODULE_PREDICTION = "智能预警";
    public static final String MODULE_PREDICTION_RESULT = "智能预警结果";
    public static final String MODULE_RETAIL = "零售许可证";
    public static final String MODULE_RETAIL_EXTEND = "零售许可证补全信息";
    public static final String MODULE_RETAIL_RESELL = "跨区倒卖";
    public static final String MODULE_STATISTICS = "动态预测";
    public static final String MODULE_PHOTO = "图片";

    // 全息档案
    public static final String MODULE_ARCHIVES_TYPE = "全息档案-档案类型";
    public static final String MODULE_BEHAVIOR = "全息档案-行为历程";
    public static final String MODULE_ARCHIVES_HABIT = "全息档案-行为习惯";
    public static final String MODULE_ARCHIVES = "全息档案-档案信息";
    public static final String MODULE_ARCHIVES_EXTEND = "全息档案-相关档案信息";
    public static final String MODULE_RELATION = "全息档案-关系网络";
    public static final String MODULE_ARCHIVES_LABEL = "全息档案-人物标签";
    public static final String MODULE_ARCHIVES_LOG = "全息档案-档案日志";

    //寄递分析
    public static final String MODULE_EXPRESS_SEARCH = "寄递分析-一键搜";
    public static final String MODULE_EXPRESS_EXACT_QUERY = "寄递分析-精确查询";
    public static final String MODULE_EXPRESS_DATA_ANALYSE = "寄递分析-数据分析";
    public static final String MODULE_EXPRESS_MIDDLE_BASE = "寄递分析-中间库";
    public static final String MODULE_EXPRESS_BATCH_QUERY = "寄递分析-批量查询";
}
