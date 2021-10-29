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
package common.redis;

import java.util.Map;
import java.util.Set;

import common.enums.CollectionType;

public interface IRedis {

	/**
	 * 判断某个key的缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	Boolean exists(String key);

	/**
	 * 判断某个key的缓存是否存在
	 * 
	 * @param key
	 * @return
	 */
	Boolean exists(byte[] key);

	/**
	 * 设置缓存，默认过期时间是20分钟即1200秒
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	<T> Boolean set(String key, T value);

	/**
	 * 设置缓存，需指定过期时间
	 * 
	 * @param key
	 * @param value
	 * @param timeout
	 *            过期时间，单位秒
	 * @return
	 */
	<T> Boolean set(String key, T value, int timeout);

	/**
	 * 获取缓存，默认类型是String
	 * 
	 * @param key
	 * @return
	 */
	<T> T get(String key);

	/**
	 * 获取缓存，需指定返回数据类型
	 * 
	 * @param key
	 * @param beanType
	 *            数据类型，如String.Class
	 * @return
	 */
	<T> T get(String key, Class<?> beanType);

	/**
	 * 获取缓存，需指定返回数据类型
	 * 
	 * @param key
	 * @param beanType
	 *            数据类型，如String.Class
	 * @param type
	 *            集合类型，如ListType, HashMapType
	 * @return
	 */
	<T> T get(String key, Class<?> beanType, CollectionType type);

	/**
	 * 删除一个或多个缓存
	 * 
	 * 2019年6月6日 09:27:41 废弃此方法
	 * 
	 * @param keys
	 * @return
	 */
	@Deprecated
	Boolean delete(String... keys);

	/**
	 * 模糊删除
	 * 
	 * @param pattern
	 * @return
	 */
	Long dels(String pattern);

	/**
	 * 模糊查询
	 * 
	 * @param pattern
	 * @return
	 */
	Set<String> keys(String pattern);

	boolean setHash(String key, Map<String, String> map);

	Map<String, String> getHash(String key);

	<T> boolean setHashField(String key, String field, T value);

	<T> T getHashField(String key, String field, Class<T> clz);

	<T> T getHashField(String key, String field, Class<?> clz, CollectionType type);

	/**
	 * 删除某个hash field
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	Long hdel(String key, String... fields);

}