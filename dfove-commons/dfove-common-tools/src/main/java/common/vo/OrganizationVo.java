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

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Jimmy on 2020/4/6.
 */
public class OrganizationVo {

    @ApiModelProperty(value = "组织机构id")
    private String organizationId;
    @ApiModelProperty(value = "组织机构名称")
    private String organizationName;
    @ApiModelProperty(value = "下级组织机构")
    private List<OrganizationVo> children;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public List<OrganizationVo> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "OrganizationVo{" +
                "organizationId=" + organizationId +
                ", organizationName='" + organizationName + '\'' +
                ", children=" + children +
                '}';
    }
}
