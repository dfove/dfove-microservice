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
package com.microservice.log.service;

import com.microservice.core.App;
import com.microservice.core.RestTemplateUtil;
import com.microservice.log.config.LogConfig;
import com.microservice.log.config.Microservices;
import com.microservice.log.constant.Elasticsearch;
import com.microservice.log.vo.*;
import common.constant.RoleType;
import common.entity.OperateLog;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.enums.AccessType;
import common.redis.IRedis;
import common.search.entity.PageResult;
import common.tools.JsonUtil;
import common.tools.StringUtil;
import common.vo.AdminVo;
import common.vo.OrganizationVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.CardinalityAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
import org.elasticsearch.search.aggregations.metrics.max.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketsort.BucketSortPipelineAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Jimmy on 2020/2/21.
 */
@Service
public class OperateLogService {

    private final static String KEY_SUPER_ADMIN_ID = "super.admin.id";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogConfig logConfig;
    @Autowired
    private Microservices microservices;
    @Qualifier("RestTemplateEureka")
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ElasticsearchClient elasticsearchClient;
    @Autowired
    private IRedis redis;

    //添加操作日志
    public Boolean addOperateLog(OperateLog log) {
        Map<String, Object> logMap = new HashMap<>();
        logMap.put(Elasticsearch.FIELD_TYPE, log.getType());
        logMap.put(Elasticsearch.FIELD_ADMIN_ID, log.getAdminId());
        logMap.put(Elasticsearch.FIELD_ADMIN_NAME, log.getAdminName());
        logMap.put(Elasticsearch.FIELD_ADMIN_REAL_NAME, log.getAdminRealName());
        logMap.put(Elasticsearch.FIELD_ADMIN_ORGANIZATION_ID, log.getAdminOrganizationId());
        logMap.put(Elasticsearch.FIELD_USER_SCOPE_TYPE, log.getUserScopeType());
        logMap.put(Elasticsearch.FIELD_MODULE, log.getModule());
        logMap.put(Elasticsearch.FIELD_OPERATE_TYPE, log.getOperateType());
        logMap.put(Elasticsearch.FIELD_CONTENT, log.getContent());
        logMap.put(Elasticsearch.FIELD_TERMINAL_TYPE, log.getTerminalType());
        logMap.put(Elasticsearch.FIELD_TERMINAL_IP, log.getTerminalIp());
        logMap.put(Elasticsearch.FIELD_TIME, log.getTime());
        LogstashLoggerService.writeLog(log.getLevel(), logMap);
        return true;
    }

    //查询日志操作类型
    public List<NameValueVo<String>> getOperateLogOperateType() {
        List<NameValueVo<String>> nameValueVos = new ArrayList<>();
        for (Map.Entry<String, String> entry : logConfig.getOperateTypeMap().entrySet()) {
            NameValueVo<String> nameValueVo = new NameValueVo<>();
            nameValueVo.setName(entry.getKey());
            nameValueVo.setValue(entry.getValue());
            nameValueVos.add(nameValueVo);
        }
        return nameValueVos;
    }

    //查询日志操作用户
    public PageResult<List<AdminVo>> getOperateLogUsers(PageDataRequest<UserParameter> request) {
        UserScope userScope = App.userScope();
        if (userScope == null) {
            logger.error("getOperateLogUsers() userScope == null");
            return new PageResult<>(new ArrayList<>(), 0, request.getPageSize(), request.getCurrentPage());
        }
        logger.info("getOperateLogUsers() request:{}", request);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolean isSuperAdmin = false; // 是否超级管理员
        boolean isAdmin = false; // 是否管理员
        boolean isBureauAdmin = false; // 是否县局管理员
        AdminVo curAdminVo = getAdminVo(userScope);
        if (curAdminVo != null) {
            if (RoleType.isSuperAdmin(curAdminVo.getRoleSuperAdmins())) {
                isSuperAdmin = true;
            }
            if (RoleType.isAdmin(curAdminVo.getRoleSuperAdmins())) {
                isAdmin = true;
            }
            if (RoleType.isBureauAdmin(curAdminVo.getRoleSuperAdmins())) {
                isBureauAdmin = true;
            }
        }
        logger.info("getOperateLogUsers() isSuperAdmin:{}", isSuperAdmin);
        if (!isSuperAdmin) {
            String superAdminId = getSuperAdminId();
            if (superAdminId != null) {
                boolQueryBuilder.mustNot(QueryBuilders.termQuery(Elasticsearch.FIELD_ADMIN_ID, superAdminId));
            }
            if (!isAdmin) {
                if (isBureauAdmin) { // 查看该机构和子机构的用户
                    List<String> organizationIds = getOrganizationVoBelows(userScope.getUserId()).stream()
                            .map(item -> item.getOrganizationId()).collect(Collectors.toList());
                    if (!organizationIds.isEmpty()) {
                        boolQueryBuilder.filter(QueryBuilders.termsQuery(Elasticsearch.FIELD_ADMIN_ORGANIZATION_ID, organizationIds));
                    } else {
                        boolQueryBuilder.filter(QueryBuilders.termQuery(Elasticsearch.FIELD_ADMIN_ORGANIZATION_ID, curAdminVo.getOrganizationId()));
                    }
                } else {
                    boolQueryBuilder.filter(QueryBuilders.termQuery(Elasticsearch.FIELD_ADMIN_ID, userScope.getUserId()));
                }
            }
        }
        if (request.getData() != null && !StringUtil.isEmpty(request.getData().getRealName())) {
            boolQueryBuilder.filter(QueryBuilders.wildcardQuery(Elasticsearch.FIELD_USER_NAME_KEYWORD, getWildcardQuery(request.getData().getRealName())));
        }
        RestHighLevelClient restHighLevelClient = elasticsearchClient.getRestHighLevelClient();
        TermsAggregationBuilder userIdAgg = AggregationBuilders.terms("userIdAgg").field(Elasticsearch.FIELD_ADMIN_ID).size(99999);
        TermsAggregationBuilder userAccountAgg = AggregationBuilders.terms("userAccountAgg").field(Elasticsearch.FIELD_USER_NAME_KEYWORD).size(1);
        TermsAggregationBuilder userNameAgg = AggregationBuilders.terms("userNameAgg").field(Elasticsearch.FIELD_USER_REAL_NAME_KEYWORD).size(1);
        MaxAggregationBuilder maxTimeAgg = AggregationBuilders.max("maxTimeAgg").field(Elasticsearch.FIELD_TIME);
        List<FieldSortBuilder> fieldSorts = new ArrayList<>();
        fieldSorts.add(new FieldSortBuilder("maxTimeAgg").order(SortOrder.DESC));
        int from = (request.getCurrentPage() - 1) * request.getPageSize();
        userIdAgg.subAggregation(userAccountAgg).subAggregation(userNameAgg).subAggregation(maxTimeAgg)
                .subAggregation(new BucketSortPipelineAggregationBuilder("userSort", fieldSorts).from(from).size(request.getPageSize()));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(userIdAgg);
        searchSourceBuilder.timeout(new TimeValue(30, TimeUnit.SECONDS));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(Elasticsearch.INDEX_LOG_OPERATE);
        searchRequest.source(searchSourceBuilder);
        List<AdminVo> adminVos = new ArrayList<>();
        int totalCount = 0;
        try {
            long startTime = System.currentTimeMillis();
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            logger.info("getOperateLogUsers() time:{} ms, searchResponse:{}", (System.currentTimeMillis() - startTime), searchResponse);
            if (searchResponse != null && searchResponse.status() == RestStatus.OK) {
                Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
                ParsedLongTerms userIdTerms = (ParsedLongTerms) aggMap.get("userIdAgg");
                List serviceLists = userIdTerms.getBuckets();
                for (Object serviceList : serviceLists) {
                    AdminVo adminVo = new AdminVo();
                    ParsedLongTerms.ParsedBucket serviceListObj = (ParsedLongTerms.ParsedBucket) serviceList;
                    adminVo.setAdminId(serviceListObj.getKey().toString());

                    ParsedStringTerms userAccountTerm = serviceListObj.getAggregations().get("userAccountAgg");
                    if (userAccountTerm != null && !userAccountTerm.getBuckets().isEmpty()) {
                        ParsedStringTerms.ParsedBucket serAccountParse = (ParsedStringTerms.ParsedBucket) userAccountTerm.getBuckets().get(0);
                        adminVo.setAdminName(serAccountParse.getKeyAsString());

                        ParsedStringTerms userNameTerm = serviceListObj.getAggregations().get("userNameAgg");
                        if (userNameTerm != null && !userNameTerm.getBuckets().isEmpty()) {
                            ParsedStringTerms.ParsedBucket userNameParse = (ParsedStringTerms.ParsedBucket) userNameTerm.getBuckets().get(0);
                            adminVo.setRealName(userNameParse.getKeyAsString());
                            adminVos.add(adminVo);
                        }
                    }
                }
                // 查总数
                SearchSourceBuilder countSearchBuilder = new SearchSourceBuilder();
                CardinalityAggregationBuilder countBuilder = AggregationBuilders.cardinality("userIdAgg").field(Elasticsearch.FIELD_ADMIN_ID);
                countSearchBuilder.query(boolQueryBuilder);
                countSearchBuilder.aggregation(countBuilder);
                countSearchBuilder.timeout(new TimeValue(30, TimeUnit.SECONDS));
                SearchRequest countSearchRequest = new SearchRequest();
                countSearchRequest.indices(Elasticsearch.INDEX_LOG_OPERATE);
                countSearchRequest.source(countSearchBuilder);
                SearchResponse countSearchResponse = restHighLevelClient.search(countSearchRequest, RequestOptions.DEFAULT);
                logger.info("getOperateLogUsers() countSearchResponse:{}", countSearchResponse);
                if (countSearchResponse != null && countSearchResponse.status() == RestStatus.OK) {
                    Map<String, Aggregation> countResult = countSearchResponse.getAggregations().asMap();
                    ParsedCardinality parsedCardinality = (ParsedCardinality) countResult.get("userIdAgg");
                    totalCount = (int) parsedCardinality.getValue();
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (restHighLevelClient != null) {
                try {
                    restHighLevelClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new PageResult<>(adminVos, totalCount, request.getPageSize(), request.getCurrentPage());
    }

    private String getWildcardQuery(String query) {
        return "*" + query + "*";
    }


    //根据条件获取操作日志
    public PageResult<List<OperateLog>> getOperateLogs(PageDataRequest<OperateLogParameter> request) {
        UserScope userScope = App.userScope();
        if (userScope == null) {
            logger.error("getOperateLogs() userScope == null");
            return new PageResult<>(new ArrayList<>(), 0, request.getPageSize(), request.getCurrentPage());
        }
        logger.info("getOperateLogs() request:{}", request);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        OperateLogParameter param = request.getData();

        boolean isSuperAdmin = false; // 是否超级管理员
        boolean isAdmin = false; // 是否管理员
        boolean isBureauAdmin = false; // 是否县局管理员
        AdminVo adminVo = getAdminVo(userScope);
        if (adminVo != null) {
            if (RoleType.isSuperAdmin(adminVo.getRoleSuperAdmins())) {
                isSuperAdmin = true;
            }
            if (RoleType.isAdmin(adminVo.getRoleSuperAdmins())) {
                isAdmin = true;
            }
            if (RoleType.isBureauAdmin(adminVo.getRoleSuperAdmins())) {
                isBureauAdmin = true;
            }
        }

        if (!isSuperAdmin) {
            String superAdminId = getSuperAdminId();
            if (superAdminId != null) {
                boolQueryBuilder.mustNot(QueryBuilders.termQuery(Elasticsearch.FIELD_ADMIN_ID, superAdminId));
            }
            if (!isAdmin) {
                if (isBureauAdmin) { // 查看该机构和子机构的用户日志
                    List<String> organizationIds = getOrganizationVoBelows(userScope.getUserId()).stream()
                            .map(item -> item.getOrganizationId()).collect(Collectors.toList());
                    if (!organizationIds.isEmpty()) {
                        boolQueryBuilder.filter(QueryBuilders.termsQuery(Elasticsearch.FIELD_ADMIN_ORGANIZATION_ID, organizationIds));
                    } else {
                        boolQueryBuilder.filter(QueryBuilders.termQuery(Elasticsearch.FIELD_ADMIN_ORGANIZATION_ID, adminVo.getOrganizationId()));
                    }
                } else {
                    boolQueryBuilder.filter(QueryBuilders.termQuery(Elasticsearch.FIELD_ADMIN_ID, userScope.getUserId()));
                }
            }
        }

        if (param != null) {
            if (param.getStartTime() != null) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery(Elasticsearch.FIELD_TIME).gte(param.getStartTime().getTime()));
            }
            if (param.getEndTime() != null) {
                boolQueryBuilder.filter(QueryBuilders.rangeQuery(Elasticsearch.FIELD_TIME).lte(param.getEndTime().getTime()));
            }
            if (!StringUtil.isEmpty(param.getType())) {
                boolQueryBuilder.filter(QueryBuilders.termQuery(Elasticsearch.FIELD_OPERATE_TYPE, param.getType()));
            }
            if (isSuperAdmin || isAdmin || isBureauAdmin) {
                if (param.getUserIds() != null && !param.getUserIds().isEmpty()) {
                    boolQueryBuilder.filter(QueryBuilders.termsQuery(Elasticsearch.FIELD_ADMIN_ID, param.getUserIds()));
                }
            }
        }
        logger.info("getOperateLogs() isSuperAdmin:{} isAdmin:{} isBureauAdmin:{} boolQueryBuilder:{}", isSuperAdmin, isAdmin, isBureauAdmin, boolQueryBuilder);

        EsSearchParameter searchParameter = new EsSearchParameter();
        searchParameter.setIndices(new String[]{Elasticsearch.INDEX_LOG_OPERATE});
        String[] includes = new String[]{
                Elasticsearch.FIELD_ADMIN_ID, Elasticsearch.FIELD_ADMIN_NAME, Elasticsearch.FIELD_ADMIN_REAL_NAME, Elasticsearch.FIELD_USER_SCOPE_TYPE, Elasticsearch.FIELD_ADMIN_ORGANIZATION_ID,
                Elasticsearch.FIELD_MODULE, Elasticsearch.FIELD_OPERATE_TYPE, Elasticsearch.FIELD_CONTENT,
                Elasticsearch.FIELD_TERMINAL_TYPE, Elasticsearch.FIELD_TERMINAL_IP,
                Elasticsearch.FIELD_TIME
        };
        searchParameter.setIncludeFields(includes);
        searchParameter.setQueryBuilder(boolQueryBuilder);
        searchParameter.setSortBuilder(new FieldSortBuilder(Elasticsearch.FIELD_TIME).order(SortOrder.DESC));
        searchParameter.setPageNumber(request.getCurrentPage());
        searchParameter.setPageSize(request.getPageSize());
        EsSearchResult searchResult = elasticsearchClient.search(searchParameter);
        logger.info("getOperateLogs() searchResult:{}", searchResult);
        List<OperateLog> logs = new ArrayList<>();
        int totalCount = 0;
        if (searchResult.isSuccess()) {
            logs = searchResult.getData().stream()
                    .map(item -> JsonUtil.jsonToObject(item, OperateLog.class))
                    .collect(Collectors.toList());
            totalCount = (int) searchResult.getTotalHits();
        }
        return new PageResult<>(logs, totalCount, request.getPageSize(), request.getCurrentPage());
    }




    private List<OrganizationVo> getOrganizationVoBelows(String adminId) {
        if (adminId == null) {
            return new ArrayList<>();
        }
        String serviceUrl = microservices.getValue("admin", AccessType.eureka.toString());
        String url = String.format("%s/dfove/v1/organization/below", serviceUrl);
        ParameterizedTypeReference<OperateResult<List<OrganizationVo>>> returnType = new ParameterizedTypeReference<OperateResult<List<OrganizationVo>>>() {
        };
        Map<String, String> param = new HashMap<>();
        param.put("adminId", String.valueOf(adminId));
        OperateResult<OperateResult<List<OrganizationVo>>> result = RestTemplateUtil.get(restTemplate, url, param, null, returnType);
        logger.info("getOrganizationVo() url:{} result.ok():{}", url, result.ok());
        if (result.ok() && result.getData() != null && result.getData().ok()) {
            return result.getData().getData();
        }
        return new ArrayList<>();
    }

    private AdminVo getAdminVo(UserScope userScope) {
        if (userScope == null) {
            return null;
        }
        String serviceUrl = microservices.getValue("admin", AccessType.eureka.toString());
        String url = String.format("%s/dfove/v1/admin/" + userScope.getUserId(), serviceUrl);
        ParameterizedTypeReference<OperateResult<AdminVo>> returnType = new ParameterizedTypeReference<OperateResult<AdminVo>>() {
        };
        Map<String, String> header = new HashMap<>();
        try {
            header.put("UserScope", java.net.URLEncoder.encode(userScope.toJson(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OperateResult<OperateResult<AdminVo>> result = RestTemplateUtil.get(restTemplate, url, null, header, returnType);
        logger.info("getAdminVo() url:{} result.ok():{}", url, result.ok());
        if (result.ok() && result.getData() != null && result.getData().ok()) {
            return result.getData().getData();
        }
        return null;
    }

    private String getSuperAdminId() {
        String idString = redis.get(KEY_SUPER_ADMIN_ID);
        logger.info("getSuperAdminId() idString:{}", idString);
        if (!StringUtil.isEmpty(idString)) {
            String id = null;
            try {
                id = idString;
            } catch (NumberFormatException e) {
                logger.error("", e);
            } catch (Exception e) {
                logger.error("", e);
            }
            if (id != null) {
                return id;
            }
        }
        AdminVo superAdminVo = getSuperAdminVo();
        if (superAdminVo != null) {
            String adminId = superAdminVo.getAdminId();
            Boolean set = redis.set(KEY_SUPER_ADMIN_ID, adminId, 1800);
            return adminId;
        }
        return null;
    }

    private AdminVo getSuperAdminVo() {
        String serviceUrl = microservices.getValue("admin", AccessType.eureka.toString());
        String url = String.format("%s/dfove/v1/admin/super", serviceUrl);
        ParameterizedTypeReference<OperateResult<AdminVo>> returnType = new ParameterizedTypeReference<OperateResult<AdminVo>>() {
        };
        OperateResult<OperateResult<AdminVo>> result = RestTemplateUtil.get(restTemplate, url, null, null, returnType);
        logger.info("getSuperAdminVo() url:{} result.ok():{}", url, result.ok());
        if (result.ok() && result.getData() != null && result.getData().ok()) {
            return result.getData().getData();
        }
        return null;
    }

}
