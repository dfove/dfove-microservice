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
package com.microservice.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import common.entity.Constants;
import common.entity.ErrorCode;
import common.entity.OperateResult;
import common.entity.UserScope;
import common.tools.DateUtil;
import common.tools.JsonUtil;
import common.tools.MapUtil;

public class RestTemplateUtil {

	public static HttpHeaders headerForm(HttpHeaders headers) {
		if (headers == null) {
            headers = new HttpHeaders();
        }

		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
		return headers;
	}

	public static HttpHeaders headerMedia(HttpHeaders headers) {
		if (headers == null) {
            headers = new HttpHeaders();
        }

		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));

		return headers;
	}

	public static HttpHeaders headerJson(HttpHeaders headers) {
		if (headers == null) {
            headers = new HttpHeaders();
        }

		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		return headers;
	}

	public static <T> MultiValueMap<String, Object> objectToMultiValueMap(T obj) {
		return objectToMultiValueMap(obj, true);
	}

	public static <T> String objectToPathValue(T obj) {
		return objectToPathValue(obj, true);
	}

	@SuppressWarnings("unchecked")
	public static <T> MultiValueMap<String, Object> objectToMultiValueMap(T obj, Boolean excludeNull) {
		if (obj == null) {
            return null;
        }

		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			// 参数是HashMap<>
			if ("class java.util.HashMap".equals(obj.getClass().toString())) {
				for (Map.Entry<String, Object> m : ((HashMap<String, Object>) obj).entrySet()) {
					Object v = m.getValue();
					if (excludeNull && v != null) {
						if ("class java.util.Date".equals(v.getClass().toString())) {
                            map.add(m.getKey(), DateUtil.toLongTimeString((Date) v));
                        } else {
                            map.add(m.getKey(), v);
                        }
					}
				}
				return map;
			}

			List<Field> fields = MapUtil.getFields(obj);
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				Object v = field.get(obj);
				if (excludeNull && v != null) {
					if ("class java.util.Date".equals(field.getType().toString())) {
                        map.add(field.getName(), DateUtil.toLongTimeString((Date) v));
                    } else {
                        map.add(field.getName(), v);
                    }
				}
			}
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> String objectToPathValue(T obj, Boolean excludeNull) {
		if (obj == null) {
            return null;
        }

		try {
			StringBuilder map = new StringBuilder();

			// 参数是HashMap<>
			if ("class java.util.HashMap".equals(obj.getClass().toString())) {
				for (Map.Entry<String, Object> m : ((HashMap<String, Object>) obj).entrySet()) {
					Object v = m.getValue();
					if (excludeNull && v != null) {
						map.append("&");
						map.append(m.getKey());
						map.append("=");
						if ("class java.util.Date".equals(v.getClass().toString())) {
                            map.append(DateUtil.toLongTimeString((Date) v));
                        } else {
                            map.append(v.toString());
                        }
					}
				}
				return map.toString();
			}

			List<Field> fields = MapUtil.getFields(obj);
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				field.setAccessible(true);
				Object v = field.get(obj);
				if (excludeNull && v != null) {
					map.append("&");
					map.append(field.getName());
					map.append("=");

					if ("class java.util.Date".equals(field.getType().toString())) {
                        map.append(DateUtil.toLongTimeString((Date) v));
                    } else {
                        map.append(v.toString());
                    }
				}
			}
			return map.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> OperateResult<T> post(RestTemplate restTemplate, String url) {
		return post(restTemplate, url, null);
	}

	public static <T> OperateResult<T> post(RestTemplate restTemplate, String url, Object param) {
		return post(restTemplate, url, param, null);
	}

	public static <T> OperateResult<T> post(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header) {
		return post(restTemplate, url, param, header, null);
	}

	public static <T> OperateResult<T> post(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType) {
		return post(restTemplate, url, param, header, returnType, false);
	}

	public static <T> OperateResult<T> post(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType, Boolean toJson) {
		return request(restTemplate, url, param, header, returnType, toJson, HttpMethod.POST);
	}

	public static <T> OperateResult<T> delete(RestTemplate restTemplate, String url) {
		return delete(restTemplate, url, null);
	}

	public static <T> OperateResult<T> delete(RestTemplate restTemplate, String url, Map<String, String> param) {
		return delete(restTemplate, url, param, null);
	}

	public static <T> OperateResult<T> delete(RestTemplate restTemplate, String url, Map<String, String> param,
			Map<String, String> header) {
		return delete(restTemplate, url, param, header, null);
	}

	public static <T> OperateResult<T> delete(RestTemplate restTemplate, String url, Map<String, String> param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType) {
		if (param != null && param.size() > 0) {
			if (url.indexOf("?") == -1) {
                url += "?";
            }
			for (Entry<String, String> entry : param.entrySet()) {
				url += "&" + entry.getKey() + "=" + entry.getValue();
			}
			url = url.replace("?&", "?");
		}
		return request(restTemplate, url, null, header, returnType, false, HttpMethod.DELETE);
	}

	public static <T> OperateResult<T> put(RestTemplate restTemplate, String url, Object param) {
		return put(restTemplate, url, param, null);
	}

	public static <T> OperateResult<T> put(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header) {
		return put(restTemplate, url, param, header, null);
	}

	public static <T> OperateResult<T> put(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType) {
		return put(restTemplate, url, param, header, returnType, false);
	}

	public static <T> OperateResult<T> put(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType, Boolean toJson) {
		return request(restTemplate, url, param, header, returnType, toJson, HttpMethod.PUT);
	}

	public static <T> OperateResult<T> get(RestTemplate restTemplate, String url) {
		return get(restTemplate, url, null);
	}

	public static <T> OperateResult<T> get(RestTemplate restTemplate, String url, Map<String, String> param) {
		return get(restTemplate, url, param, null);
	}

	public static <T> OperateResult<T> get(RestTemplate restTemplate, String url, Map<String, String> param,
			Map<String, String> header) {
		return get(restTemplate, url, param, header, null);
	}

	public static <T> OperateResult<T> get(RestTemplate restTemplate, String url, Map<String, String> param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType) {
		if (param != null && param.size() > 0) {
			if (url.indexOf("?") == -1) {
                url += "?";
            }
			for (Entry<String, String> entry : param.entrySet()) {
				url += "&" + entry.getKey() + "=" + entry.getValue();
			}
			url = url.replace("?&", "?");
		}
		return request(restTemplate, url, null, header, returnType, false, HttpMethod.GET);
	}

	private static <T> OperateResult<T> request(RestTemplate restTemplate, String url, Object param,
			Map<String, String> header, ParameterizedTypeReference<T> returnType, Boolean toJson, HttpMethod method) {

		HttpHeaders headers = null;
		if (header != null && header.size() > 0) {
			headers = new HttpHeaders();
			for (Entry<String, String> entry : header.entrySet()) {
				headers.add(entry.getKey(), entry.getValue());
			}
		}

		HttpEntity<?> httpEntity = null;
		if (param != null) {
			if (toJson == null) {
				httpEntity = new HttpEntity<String>((String) param, headerJson(headers));
			} else if (toJson) {
				String json = JsonUtil.objectToJson(param);
				httpEntity = new HttpEntity<String>(json, headerJson(headers));
			} else {
				if (method.equals(HttpMethod.POST)) {
					MultiValueMap<String, Object> params = RestTemplateUtil.objectToMultiValueMap(param);
					httpEntity = new HttpEntity<MultiValueMap<String, Object>>(params, headers);
				} else {
					String params = RestTemplateUtil.objectToPathValue(param);
					if (url.indexOf("?") > -1) {
                        url += params;
                    } else {
                        url += params.replaceFirst("&", "?");
                    }
					httpEntity = new HttpEntity<String>(null, headers);
				}
			}
		} else if (headers != null) {
			httpEntity = new HttpEntity<Object>(null, headers);
		}

		try {
			if (returnType == null) {
				restTemplate.exchange(url, method, httpEntity, String.class);
				return new OperateResult<T>().success(null);
			} else {
				// ResponseEntity<Object> response2 = restTemplate.exchange(url, method,
				// httpEntity, Object.class);
				ResponseEntity<T> response = restTemplate.exchange(url, method, httpEntity, returnType);
				return new OperateResult<T>().success(response.getBody());
			}
		} catch (HttpClientErrorException e) {
			System.out.println(e);
			e.printStackTrace();
			return new OperateResult<T>().failed(errorJson(e), ErrorCode.SYSTEM_ERROR);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return new OperateResult<T>().failed(e.getMessage(), ErrorCode.SYSTEM_ERROR);
		}
	}

	private static String errorJson(HttpClientErrorException exception) {
		List<String> errors = exception.getResponseHeaders().get("error");
		if (errors != null && errors.size() > 0) {
			try {
				String json = java.net.URLDecoder.decode(errors.get(0), "UTF-8");
				return json;
			} catch (Exception e1) {

			}
		}
		return null;
	}

	// 通用设置头部token的几种方法=====================================================

	/**
	 * 添加 头部 Authorization
	 * 
	 * @param token
	 * @return
	 */
	public static Map<String, String> authorization(String token) {
		return createHeader(Constants.AUTHORIZATION, Constants.BEARER + token);
	}

	/**
	 * 添加头部登录用户信息
	 * 
	 * @param scope
	 * @return
	 */
	public static Map<String, String> userScope(UserScope scope) {
		return userScope(JsonUtil.objectToJson(scope));
	}

	/**
	 * 添加头部登录用户信息
	 * 
	 * @param scope
	 * @return
	 */
	public static Map<String, String> userScope(String scope) {
		return userScope(scope, null);
	}

	/**
	 * 添加头部登录用户信息
	 * 
	 * @param scope
	 * @param map
	 * @return
	 */
	public static Map<String, String> userScope(UserScope scope, Map<String, String> map) {
		return userScope(JsonUtil.objectToJson(scope), map);
	}

	/**
	 * 添加头部登录用户信息
	 * 
	 * @param scope
	 * @param map
	 * @return
	 */
	public static Map<String, String> userScope(String scope, Map<String, String> map) {
		return createHeader(Constants.USER_SCOPE, scope, map);
	}

	/**
	 * 添加头部交易对象信息
	 * 
	 * @param scope
	 * @return
	 */
	public static Map<String, String> targetScope(UserScope scope) {
		return targetScope(JsonUtil.objectToJson(scope));
	}

	/**
	 * 添加头部交易对象信息
	 * 
	 * @param scope
	 * @return
	 */
	public static Map<String, String> targetScope(String scope) {
		return targetScope(scope, null);
	}

	/**
	 * 添加头部交易对象信息
	 * 
	 * @param scope
	 * @param map
	 * @return
	 */
	public static Map<String, String> targetScope(UserScope scope, Map<String, String> map) {
		return targetScope(JsonUtil.objectToJson(scope), map);
	}

	/**
	 * 添加头部交易对象信息
	 * 
	 * @param scope
	 * @param map
	 * @return
	 */
	public static Map<String, String> targetScope(String scope, Map<String, String> map) {
		return createHeader(Constants.TARGET_SCOPE, scope, map);
	}

	private static Map<String, String> createHeader(String key, String value) {
		return createHeader(key, value, null);
	}

	private static Map<String, String> createHeader(String key, String value, Map<String, String> map) {
		if (map == null) {
            map = new HashMap<>();
        }
		try {
			map.put(key, java.net.URLEncoder.encode(value, "UTF-8"));
		} catch (Exception e) {
		}
		return map;
	}

}
