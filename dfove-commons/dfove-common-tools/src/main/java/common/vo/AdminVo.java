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
package common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Jimmy on 2020/2/24.
 */
@ApiModel(value = "AdminVo", description = "用户")
public class AdminVo {

    @ApiModelProperty(value = "用户id")
    private String adminId;
    @ApiModelProperty(value = "用户名")
    private String adminName;
    @ApiModelProperty(value = "真实名")
    private String realName;
    @ApiModelProperty(value = "角色类型：1-超级管理员，10-管理员，100-用户", hidden = true)
    private List<Integer> roleSuperAdmins;
    @ApiModelProperty(value = "组织机构ID",required=true)
    private String organizationId;
    @ApiModelProperty(value = "组织机构，从上级到下级")
    private OrganizationVo organization;
	@ApiModelProperty(value = "用户头像链接")
	private String adminLogo;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<Integer> getRoleSuperAdmins() {
        return roleSuperAdmins;
    }

    public void setRoleSuperAdmins(List<Integer> roleSuperAdmins) {
        this.roleSuperAdmins = roleSuperAdmins;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public OrganizationVo getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationVo organization) {
        this.organization = organization;
    }

    public String getAdminLogo() {
        return adminLogo;
    }

    public void setAdminLogo(String adminLogo) {
        this.adminLogo = adminLogo;
    }

    @Override
    public String toString() {
        return "AdminVo{" +
                "adminId=" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", realName='" + realName + '\'' +
                ", roleSuperAdmins=" + roleSuperAdmins +
                ", organizationId=" + organizationId + '\'' +
                ", organization=" + organization +
                '}';
    }
}
