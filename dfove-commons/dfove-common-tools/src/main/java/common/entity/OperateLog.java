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
package common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Jimmy on 2020/2/21.
 */
@ApiModel(value = "OperateLog", description = "操作日志")
public class OperateLog {

    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "日志类型", hidden = true)
    private String type;
    @ApiModelProperty(value = "日志级别", hidden = true)
    private String level;

    @ApiModelProperty(value = "用户id")
    private String adminId;
    @ApiModelProperty(value = "用户名")
    private String adminName;
    @ApiModelProperty(value = "用户真实姓名")
    private String adminRealName;
    /**
     * 用户权限类型（11-PC，21-安卓，31-IOS，41-微信或H5）
     */
    @ApiModelProperty(value = "用户权限类型（11-PC，21-安卓，31-IOS，41-微信或H5）")
    private int userScopeType;
    @ApiModelProperty(value = "用户机构id")
    private String adminOrganizationId;
    /**
     * 操作模块
     */
    @ApiModelProperty(value = "操作模块")
    private String module;
    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作日志类型")
    private String operateType;
//    /**
//     * 操作类型
//     */
//    @ApiModelProperty(value = "日志类型描述")
//    private String operateTypeDesc;
    /**
     * 操作内容
     */
    @ApiModelProperty(value = "日志内容")
    private String content;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private Date time;
    /**
     * 终端类型（pc，android，ios）
     */
    @ApiModelProperty(value = "终端类型")
    private int terminalType;
    @ApiModelProperty(value = "终端ip")
    private String terminalIp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminRealName() {
        return adminRealName;
    }

    public void setAdminRealName(String adminRealName) {
        this.adminRealName = adminRealName;
    }

    public int getUserScopeType() {
        return userScopeType;
    }

    public void setUserScopeType(int userScopeType) {
        this.userScopeType = userScopeType;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getAdminOrganizationId() {
        return adminOrganizationId;
    }

    public void setAdminOrganizationId(String adminOrganizationId) {
        this.adminOrganizationId = adminOrganizationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(int terminalType) {
        this.terminalType = terminalType;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    @Override
    public String toString() {
        return "OperateLog{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", level='" + level + '\'' +
                ", adminId=" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminRealName='" + adminRealName + '\'' +
                ", userScopeType=" + userScopeType +
                ", adminOrganizationId=" + adminOrganizationId + '\'' +
                ", module='" + module + '\'' +
                ", operateType='" + operateType + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                ", terminalType=" + terminalType +
                ", terminalIp='" + terminalIp + '\'' +
                '}';
    }
}
