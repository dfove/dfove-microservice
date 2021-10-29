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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class MapUtil {

	/**
	 * 对象转换为map键值对
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		return objectToMap(obj, true);
	}

	/**
	 * 对象转换为map键值对，指定是否忽略null的值
	 * 
	 * @param obj
	 * @param excludeNull 是否剔除空值
	 * @return
	 */
	public static <T> Map<String, Object> objectToMap(T obj, Boolean excludeNull) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Field> fields = getFields(obj);

			for (Field field : fields) {

				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}

				field.setAccessible(true);
				Object v = field.get(obj);
				if (!excludeNull || (excludeNull && v != null)) {
					map.put(field.getName(), v);
				}
			}
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * map键值对转换为对象
	 * 
	 * @param map
	 * @param beanType
	 * @return
	 */
	public static <T> T mapToObject(Map<String, Object> map, Class<T> beanType) {
		return mapToObject(map, beanType, true);
	}

	/**
	 * map键值对转换为对象
	 * 
	 * @param map
	 * @param beanType
	 * @param excludeNull 是否剔除空值
	 * @return
	 */
	public static <T> T mapToObject(Map<String, Object> map, Class<T> beanType, Boolean excludeNull) {
		return mapToObject(map, beanType, true, false);
	}

	/**
	 * map键值对转换为对象，指定是否忽略null的值
	 * 
	 * @param map
	 * @param beanType
	 * @param excludeNull 是否剔除空值
	 * @param checkType   是否验证值的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T mapToObject(Map<String, Object> map, Class<T> beanType, Boolean excludeNull,
			Boolean checkType) {
		try {
			Object obj = beanType.newInstance();
			List<Field> fields = getFields(obj);

			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}

				field.setAccessible(true);
				Object v = map.get(field.getName());
				String fclass = field.getGenericType().getTypeName();

				String vclass = "null";
				if (map.containsKey(field.getName())) {
                    vclass = v.getClass().getTypeName();
                }

				if (!excludeNull || (excludeNull && v != null)) {
					if (!checkType || fclass.equals(vclass)) {
						field.set(obj, v);
					} else if ("java.lang.String".equals(vclass) && StringUtils.isNotEmpty((String) v)) {
						switch (fclass) {
						case "java.lang.Integer":
							field.set(obj, Integer.parseInt((String) v));
							break;
						case "java.lang.Long":
							field.set(obj, Long.parseLong((String) v));
							break;
						case "java.math.BigDecimal":
							field.set(obj, new BigDecimal((String) v));
							break;
						case "java.util.Date":
							field.set(obj, DateUtil.toDate((String) v));
							break;
						default:
							break;
						}
					}
				}
			}
			return (T) obj;
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> List<Field> getFields(T obj) {
		Class<?> tempClass = obj.getClass();
		List<Field> fields = new ArrayList<Field>();
		while (tempClass != null) {
			fields.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			tempClass = tempClass.getSuperclass();
		}
		return fields;
	}

	/**
	 * 组装map
	 * 
	 * @param objects
	 * @return
	 */
	public static Map<Object, Object> createMap(Object... objects) {
		if (objects == null || objects.length < 2) {
            return null;
        }

		Map<Object, Object> map = new HashMap<>();
		for (int i = 0; i < objects.length; i += 2) {
			map.put(objects[i], objects[i + 1]);
		}

		return map;
	}
}
