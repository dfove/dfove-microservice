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

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {

	public static final char UNDERLINE = '_';

	/**
	 * 字符串1,2,3转换为List<Integer>()
	 * @param s
	 * @return
	 */
	public static List<Integer> stringToListInteger(String s) {
		List<Integer> result = Arrays.asList(s.split(",")).stream().map(v -> Integer.parseInt(v.trim()))
				.collect(Collectors.toList());
		return result;
	}

	/**
	 * 字符串1,2,3转换为List<Long>()
	 * @param s
	 * @return
	 */
	public static List<Long> stringToListLong(String s) {
		List<Long> result = Arrays.asList(s.split(",")).stream().map(v -> Long.parseLong(v.trim()))
				.collect(Collectors.toList());
		return result;
	}

	/**
	 * 字符串1,2,3转换为List<String>()
	 * @param s
	 * @return
	 */
	public static List<String> stringToListString(String s) {
		List<String> result = Arrays.asList(s.split(","));
		return result;
	}

	/**
	 * 驼峰格式转换为下划线
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		sb.append(Character.toLowerCase(param.charAt(0)));
		for (int i = 1; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线转换为驼峰格式
	 * @param param
	 * @return
	 */
	public static String underlineToCamel(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (c == UNDERLINE) {
				if (++i < len) {
					sb.append(Character.toUpperCase(param.charAt(i)));
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 判断是否匹配
	 * @param regex 正则表达式
	 * @param str 待验证字符串
	 * @return 是否匹配
	 */
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static boolean isEmpty(String s) {
		if (s==null || s.isEmpty()) {
			return true;
		}
		return false;
	}

}
