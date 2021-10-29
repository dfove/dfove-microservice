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
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.enums.CollectionType;

@SuppressWarnings("unused")
public final class JsonUtil {

	private final static ObjectMapper MAPPER = new ObjectMapper().setSerializationInclusion(Include.NON_NULL)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	/**
	 * 获取JavaType
	 * 
	 * @author yth
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses  元素类
	 * @return JavaType Java类型
	 */
	private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

	/**
	 * 对象转换为json字符串
	 * 
	 * @author yth
	 * @param T   泛型
	 * @param obj 对象
	 * @return String json字符串
	 */
	public static <T> String objectToJson(T obj) {
		if (obj == null) {
			System.out.println("对像为空");
			return "";
		}

		//if (obj.getClass().getSimpleName().equals("String"))
			//return (String) obj;

		String json = null;
		try {
			json = MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 对象转换为json字符串后再转换为二进制byte[]
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> byte[] objectToByte(T obj) {
		try {
			return objectToJson(obj).getBytes("UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Jspn字符串转换为对象
	 * 
	 * @author yth
	 * @param T        对象类型
	 * @param json     json字符串
	 * @param beanType 对象类名称
	 * @return T 对象
	 */
	public static <T> T jsonToObject(String json, Class<T> beanType) {
		json = json.replaceAll("zkhb", "[").replaceAll("zkhe", "]").replaceAll("%3C%3C", "[").replaceAll("%3E%3E", "]")
				.replaceAll("<<", "[").replaceAll(">>", "]");
		return jsonToObject(json, beanType, CollectionType.None);
	}

	/**
	 * Jspn字符串转换为对象
	 * 
	 * @author yth
	 * @param T        对象类型
	 * @param json     json字符串
	 * @param beanType 对象类名称
	 * @param type     类型，0 basic，1 List，2 HashMap
	 * @return T 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToObject(String json, Class<?> beanType, CollectionType type) {
		T obj = null;

		try {
			
			//if (beanType.getSimpleName().equals("String"))
				//return (T) json;
			
			if (type == null || type.equals(CollectionType.None)) {
				obj = (T) MAPPER.readValue(json, beanType);
			} else {
				JavaType javaType = null;
				if (type == CollectionType.ListType) {
					javaType = getCollectionType(ArrayList.class, beanType);
				} else if (type == CollectionType.HashMapType) {
					javaType = getCollectionType(HashMap.class, String.class, beanType);
				}
				obj = (T) MAPPER.readValue(json, javaType);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static String replaceByJson(String content, String json) {
		try {
			throw new Exception("not implement");
		} catch (Exception e) {
			return null;
		}
	}
}
