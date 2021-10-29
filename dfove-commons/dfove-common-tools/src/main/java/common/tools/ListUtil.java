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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListUtil<T> {

	@SuppressWarnings("unchecked")
	public List<T> list(T... objects) {
		return Arrays.asList(objects);
	}

	/**
	 * List<Integer>
	 * 
	 * @param objects
	 * @return
	 */
	public static List<Integer> i(Integer... objects) {
		return Arrays.asList(objects);
	}

	/**
	 * List<Long>
	 * 
	 * @param objects
	 * @return
	 */
	public static List<Long> l(Long... objects) {
		return Arrays.asList(objects);
	}

	/**
	 * List<BigDecimal>
	 * 
	 * @param objects
	 * @return
	 */
	public static List<BigDecimal> b(BigDecimal... objects) {
		return Arrays.asList(objects);
	}

	/**
	 * List<String>
	 * 
	 * @param objects
	 * @return
	 */
	public static List<String> s(String... objects) {
		return Arrays.asList(objects);
	}

	/**
	 * List<Date>
	 * 
	 * @param objects
	 * @return
	 */
	public static List<Date> d(Date... objects) {
		return Arrays.asList(objects);
	}

}