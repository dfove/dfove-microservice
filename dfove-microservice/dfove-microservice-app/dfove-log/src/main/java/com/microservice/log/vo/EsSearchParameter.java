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

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.Arrays;

/**
 * Created by Jimmy on 2020/2/26.
 */
public class EsSearchParameter {

    private String[] indices;
    private QueryBuilder queryBuilder;
    private int pageNumber = 1;
    private int pageSize = 20;
    private SortBuilder sortBuilder;
    private String[] includeFields;
    private String[] excludeFields;
    private boolean needId;
    /**
     * 单位为秒
     */
    private long timeout = 30;

    public String[] getIndices() {
        return indices;
    }

    public void setIndices(String[] indices) {
        this.indices = indices;
    }

    public QueryBuilder getQueryBuilder() {
        return queryBuilder;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public SortBuilder getSortBuilder() {
        return sortBuilder;
    }

    public void setSortBuilder(SortBuilder sortBuilder) {
        this.sortBuilder = sortBuilder;
    }

    public String[] getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(String[] includeFields) {
        this.includeFields = includeFields;
    }

    public String[] getExcludeFields() {
        return excludeFields;
    }

    public void setExcludeFields(String[] excludeFields) {
        this.excludeFields = excludeFields;
    }

    public boolean isNeedId() {
        return needId;
    }

    public void setNeedId(boolean needId) {
        this.needId = needId;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "EsSearchParameter{" +
                "indices=" + Arrays.toString(indices) +
                ", queryBuilder=" + queryBuilder +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", sortBuilder=" + sortBuilder +
                ", includeFields=" + Arrays.toString(includeFields) +
                ", excludeFields=" + Arrays.toString(excludeFields) +
                ", needId=" + needId +
                ", timeout=" + timeout +
                '}';
    }
}
