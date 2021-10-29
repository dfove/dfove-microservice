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

import java.lang.reflect.Method;

public class ObjectUtil {

	public static Object getFieldValueByName(Object o, String fieldName) {
		try {
			String getter = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static Object getFieldValueByFullName(Object o, String fullName) {
		try {
			Method method = o.getClass().getMethod(fullName,new Class[] {});//new Class[] {}
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
}
