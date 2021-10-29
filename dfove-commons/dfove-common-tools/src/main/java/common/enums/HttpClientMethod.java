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
package common.enums;

import org.apache.http.client.methods.*;
 
/**
 * httpclient 访问方式
 */
public enum HttpClientMethod {
	
    // HttpGet请求
    HttpGet {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpGet(url); }
    },
    
    // HttpPost 请求
    HttpPost {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpPost(url); }
    },
    
    // HttpPut 请求
    HttpPut {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpPut(url); }
    },
    
    // HttpDelete 请求
    HttpDelete {
        @Override
        public HttpRequestBase createRequest(String url) { return new HttpDelete(url); }
    };
 
    public HttpRequestBase createRequest(String url) { return null; }
}
