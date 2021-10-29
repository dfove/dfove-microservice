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
package common.tools;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import common.entity.OperateResult;
import common.enums.HttpClientMethod;

/**
 * httpClient访问类
 */
public class HttpClientUtil {

	/**
	 * httpclient使用步骤 1、创建一个HttpClient对象;
	 * 2、创建一个Http请求对象并设置请求的URL，比如GET请求就创建一个HttpGet对象，POST请求就创建一个HttpPost对象;
	 * 3、如果需要可以设置请求对象的请求头参数，也可以往请求对象中添加请求参数; 4、调用HttpClient对象的execute方法执行请求;
	 * 5、获取请求响应对象和响应Entity; 6、从响应对象中获取响应状态，从响应Entity中获取响应内容; 7、关闭响应对象;
	 * 8、关闭HttpClient.
	 */

	private final static int TIMEOUT = 20000;

	private static RequestConfig defaultConfig = null;

	private static RequestConfig createConfig(int timeout) {
		return RequestConfig.custom()
				// 从连接池中获取连接的超时时间
				// 要用连接时尝试从连接池中获取，若是在等待了一定的时间后还没有获取到可用连接（比如连接池中没有空闲连接了）则会抛出获取连接超时异常。
				.setConnectionRequestTimeout(timeout)
				// 与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
				// 连接目标url的连接超时时间，即客服端发送请求到与目标url建立起连接的最大时间。超时时间3000ms过后，系统报出异常
				.setConnectTimeout(timeout)
				// socket读数据超时时间：从服务器获取响应数据的超时时间
				// 连接上一个url后，获取response的返回等待时间
				// ，即在与目标url建立连接后，等待放回response的最大时间，在规定时间内没有返回响应的话就抛出SocketTimeout。
				.setSocketTimeout(timeout).build();
	}

	private static RequestConfig getConfig(int timeout) {
		if (timeout == TIMEOUT) {
			if (defaultConfig == null) {
				defaultConfig = createConfig(TIMEOUT);
			}
			return defaultConfig;
		}
		return createConfig(timeout);
	}

	/**
	 * 发送http请求，默认 get
	 * 
	 * @param url 请求路径
	 * @return 响应文本
	 */
	public static OperateResult<String> send(String url) {
		return send(url, HttpClientMethod.HttpGet);
	}

	/**
	 * 发送http请求 无参数
	 * 
	 * @param url    请求路径
	 * @param method 请求方式
	 * @return 响应文本
	 */
	public static OperateResult<String> send(String url, HttpClientMethod method) {
		return send(url, method, null, null);
	}

	/**
	 * 发送http请求 无header
	 * 
	 * @param url    请求路径
	 * @param method 请求方式
	 * @param params 请求参数
	 * @return 响应文本
	 */
	public static OperateResult<String> send(String url, HttpClientMethod method, Object params) {
		return send(url, method, params, null);
	}

	/**
	 * 发送http请求 无header
	 * 
	 * @param url    请求路径
	 * @param method 请求方式
	 * @param params 请求参数
	 * @return 响应文本
	 */
	public static OperateResult<String> send(String url, HttpClientMethod method, Object params,
			Map<String, String> header) {
		return send(url, method, params, header, TIMEOUT);
	}

	/**
	 * 发送http请求
	 * 
	 * @param url    请求路径
	 * @param method 请求方式
	 * @param params post请求参数
	 * @param header 请求头
	 * @return 响应文本
	 */
	public static OperateResult<String> send(String url, HttpClientMethod method, Object params,
			Map<String, String> header, int timeout) {

		HttpRequestBase request = method.createRequest(url);
		request.setConfig(getConfig(timeout));

		if (header != null) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;

		Boolean success = false;
		String responseContent = null;

		// 往对象中添加相关参数
		try {
			if (params != null) {
				((HttpEntityEnclosingRequest) request).setEntity(new StringEntity(JsonUtil.objectToJson(params),
						ContentType.create("application/json", "UTF-8")));
			}

			httpResponse = httpClient.execute(request);

			HttpEntity httpEntity = httpResponse.getEntity();

			if (httpEntity != null) {
				responseContent = EntityUtils.toString(httpEntity, "UTF-8");
			}
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			responseContent = e.getMessage();
		} finally {
			try {

				if (httpResponse != null) {
					httpResponse.close();
				}

				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return success ? OperateResult.s().success(responseContent) : OperateResult.s().failed(responseContent);
	}
}
