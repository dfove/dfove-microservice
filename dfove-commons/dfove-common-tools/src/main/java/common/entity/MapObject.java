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
import java.util.Map;

/**
 * 嵌套map Map<String, Map<String, String>>的操作 map: key1: key1-1:value1-1
 * key1-2:value1-2 key2: key2-1:value2-1 key2-2:value2-2
 */
public class MapObject<T> {

	private Map<String, T> map = new HashMap<String, T>();

	public Map<String, T> getMap() {
		return this.map;
	}

	public void setMap(Map<String, T> map) {
		this.map = map;
	}

	public T getValue(String key) {
		try {
			return this.getMap().get(key);
		} catch (Exception e) {
			return null;
		}
	}
}
