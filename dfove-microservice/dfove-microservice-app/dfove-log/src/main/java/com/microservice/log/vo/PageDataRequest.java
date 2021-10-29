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
package com.microservice.log.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Jimmy on 2020/2/21.
 */
@ApiModel(value = "PageDataRequest", description = "分页数据查询参数")
public class PageDataRequest<T> {

    @ApiModelProperty(value = "查询页码，从1开始，默认为1")
    private Integer currentPage = 1;
    @ApiModelProperty(value = "页大小，默认为20")
    private Integer pageSize = 20;
    @ApiModelProperty(value = "查询参数")
    private T data;

    public boolean checkPage() {
        if (currentPage < 1) {
            return false;
        }
        if (pageSize < 1) {
            return false;
        }
        return true;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PageDataRequest{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", data=" + data +
                '}';
    }
}
