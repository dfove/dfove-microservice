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

import com.microservice.log.config.ElasticsearchConfig;
import com.microservice.log.vo.EsSearchParameter;
import com.microservice.log.vo.EsSearchResult;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jimmy on 2020/2/26.
 */
@Service
public class ElasticsearchClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ElasticsearchConfig elasticsearchConfig;
    //根据条件查询日志
    public EsSearchResult search(EsSearchParameter esSearchParameter) {
        EsSearchResult esSearchResult = new EsSearchResult();

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esSearchParameter.getIndices());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        if (esSearchParameter.getQueryBuilder() != null) {
            searchSourceBuilder.query(esSearchParameter.getQueryBuilder());
        }
        searchSourceBuilder.sort(esSearchParameter.getSortBuilder());
        searchSourceBuilder.fetchSource(esSearchParameter.getIncludeFields(), esSearchParameter.getExcludeFields());
        Integer pageNumber = esSearchParameter.getPageNumber();
        Integer pageSize = esSearchParameter.getPageSize();
        int from = (pageNumber - 1) * pageSize;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        searchSourceBuilder.timeout(new TimeValue(esSearchParameter.getTimeout(), TimeUnit.SECONDS));
        RestHighLevelClient restHighLevelClient = null;
        try {
            restHighLevelClient = getRestHighLevelClient();
            long startTime = System.currentTimeMillis();
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            logger.info("time:{} ms, searchResponse:{}", (System.currentTimeMillis() - startTime), searchResponse);
            if (searchResponse != null && searchResponse.status() == RestStatus.OK) {
                List<String> data = new ArrayList<>();
                List<String> ids = null;
                if (esSearchParameter.isNeedId()) {
                    ids = new ArrayList<>();
                }
                SearchHits hits = searchResponse.getHits();
//                esSearchResult.setTotalHits(hits.getTotalHits().value); // 7.5.1版本可以获取总数
                for (SearchHit hit : hits) {
//                    logger.info("hit:{}", hit);
                    data.add(hit.getSourceAsString());
                    if (esSearchParameter.isNeedId()) {
                        ids.add(hit.getId());
                    }
                }
                esSearchResult.setData(data);
                if (esSearchParameter.isNeedId()) {
                    esSearchResult.setId(ids);
                }

                // 兼容 6.6.2 版本获取总数
                CountRequest countRequest = new CountRequest();
                countRequest.indices(esSearchParameter.getIndices());
                SearchSourceBuilder countSearchSourceBuilder = new SearchSourceBuilder();
                countSearchSourceBuilder.query(esSearchParameter.getQueryBuilder());
                countRequest.source(countSearchSourceBuilder);
                CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
                if (countResponse != null && countResponse.status() == RestStatus.OK) {
                    logger.info("countResponse:{}", countResponse);
                    esSearchResult.setTotalHits(countResponse.getCount());
                }
                esSearchResult.setSuccess(true);
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
        return esSearchResult;
    }

    //查询日志操作用户
    public RestHighLevelClient getRestHighLevelClient() {
        List<String> nodes = elasticsearchConfig.getNodes();
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticsearchConfig.getUserName(), elasticsearchConfig.getPassword()));
        HttpHost[] httpHosts = new HttpHost[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            httpHosts[i] = HttpHost.create(nodes.get(i));
        }
        RestClientBuilder builder = RestClient.builder(httpHosts);
//        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//            @Override
//            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
//                return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            }
//        });
        return new RestHighLevelClient(builder);
    }

}
