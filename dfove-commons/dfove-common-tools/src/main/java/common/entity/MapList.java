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
package common.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * map List Map<String, List<String>>的操作 map: key1: - value1-1 - value1-2 key2:
 * - value2-1 - value2-2
 */
public class MapList<T> {

	private Map<String, List<T>> map = new HashMap<>();

	public Map<String, List<T>> getMap() {
		return this.map;
	}

	public void setList(Map<String, List<T>> map) {
		this.map = map;
	}

	public List<T> getList(String key) {
		try {
			return this.getMap().get(key);
		} catch (Exception e) {
			return null;
		}
	}
}
