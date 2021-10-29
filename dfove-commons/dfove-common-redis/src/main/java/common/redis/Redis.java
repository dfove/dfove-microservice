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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import common.enums.CollectionType;
import common.tools.JsonUtil;
import common.tools.StringUtil;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

public class Redis implements IRedis {


	// 如果不存在则设置
	private final static byte[] _notexists = "NX".getBytes();
	// 如果存在则设置
	private final static byte[] _exists = "XX".getBytes();
	// 过期时间单位秒
	private final static byte[] _second = "EX".getBytes();
	// 过期时间数值
	private final static int _timeout = 1200;

	private Config config = null;

	public Redis() {
		this(new Config());
	}

	public Redis(String host, int port) {
		this(new Config(host, port));
	}

	public Redis(String host, int port, String password) {
		this(new Config(host, port, password));
	}

	public Redis(Config config) {
		this.config = config;
	}

	private Jedis getJedis() {
		if (this.config == null) {
            return null;
        }

		String password = this.config.getPassword();
		Jedis jedis = new Jedis(config.getHost(), config.getPort());
		if (password != null && !password.isEmpty()) {
			jedis.auth(password);
		}

		jedis.connect();
		return jedis;
	}

	@Override
	public Boolean exists(String key) {
		return this.exists(key, null);
	}

	private Boolean exists(String key, Jedis jedis) {
		if (jedis == null) {
            jedis = this.getJedis();
        }
		boolean b = jedis.exists(key);
		close(jedis);
		return b;
	}

	@Override
	public Boolean exists(byte[] key) {
		return this.exists(key, null);
	}

	private Boolean exists(byte[] key, Jedis jedis) {
		if (jedis == null) {
            jedis = this.getJedis();
        }
		boolean b = jedis.exists(key);
		close(jedis);
		return b;
	}

	@Override
	public <T> Boolean set(String key, T value) {
		return this.set(key, value, _timeout);
	}

	@Override
	public <T> Boolean set(String key, T value, int timeout) {
		if (value == null) {
			return this.delete(key);
		}
		try {
			Jedis jedis = this.getJedis();
			byte[] keyByte = key.getBytes();
			byte[] body = JsonUtil.objectToByte(value);
			byte[] existsOrNot = jedis.exists(keyByte) ? _exists : _notexists;
			jedis.set(keyByte, body, existsOrNot, _second, timeout);
			close(jedis);
			return true;
		} catch (Exception e) {
			// System.out.println(e.getMessage());
			// e.printStackTrace();
			return false;
		}
	}

	@Override
	public <T> T get(String key) {
		return get(key, String.class, null);
	}

	@Override
	public <T> T get(String key, Class<?> beanType) {
		return get(key, beanType, null);
	}

	@Override
	public <T> T get(String key, Class<?> beanType, CollectionType type) {

		Jedis jedis = this.getJedis();

		if (!this.exists(key.getBytes(), jedis)) {
			close(jedis);
			return null;
		}

		byte[] body = jedis.get(key.getBytes());

		T obj = null;
		try {
			obj = JsonUtil.jsonToObject(new String(body, "UTF-8"), beanType, type);
		} catch (Exception e) {
		}
		close(jedis);
		return obj;
	}

	@Override
	public Boolean delete(String... keys) {

		Jedis jedis = this.getJedis();
		for (String key : keys) {
			if (this.exists(key)) {
				jedis.del(key);
			}
		}
		close(jedis);
		return true;
	}

	public void close(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Long dels(String pattern) {
		Jedis jedis = this.getJedis();
		Set<String> keys = jedis.keys(pattern);
		String[] keyss = new String[keys.size()];
		int i = 0;
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			keyss[i++] = string;
		}
		Long l = 0L;
		if (keyss.length > 0) {
			l = jedis.del(keyss);
		}
		close(jedis);
		return l;
	}

	@Override
	public Set<String> keys(String pattern) {
		Jedis jedis = this.getJedis();
		Set<String> keys = jedis.keys(pattern);
		close(jedis);
		return keys;

	};

	@Override
	public boolean setHash(String key, Map<String, String> map) {
		if (StringUtil.isEmpty(key)) {
			return false;
		}
		if (map == null) {
			return delete(key);
		}
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			jedis.hmset(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			close(jedis);
		}
	}

	@Override
	public Map<String, String> getHash(String key) {
		if (StringUtil.isEmpty(key)) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(jedis);
		}
	}

	@Override
	public <T> T getHashField(String key, String field, Class<T> clz) {
		return getHashField(key, field, clz, CollectionType.None);
	}

	@Override
	public <T> T getHashField(String key, String field, Class<?> clz, CollectionType type) {
		if (StringUtil.isEmpty(key) || StringUtil.isEmpty(field) || clz == null || type == null) {
			return null;
		}
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			String hget = jedis.hget(key, field);
			if (hget == null) {
				return null;
			}
			return JsonUtil.jsonToObject(hget, clz, type);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			close(jedis);
		}
	}

	@Override
	public <T> boolean setHashField(String key, String field, T value) {
		if (StringUtil.isEmpty(key) || StringUtil.isEmpty(field)) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			if (value != null) {
				jedis.hset(key, field, JsonUtil.objectToJson(value));
			} else {
				jedis.hdel(key, field);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			close(jedis);
		}
	}

	@Override
	public Long hdel(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			Long result = jedis.hdel(key, fields);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0L;
		} finally {
			close(jedis);
		}
	}

}
