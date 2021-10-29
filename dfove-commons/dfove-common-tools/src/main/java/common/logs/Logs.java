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
package common.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义系统日志， 不一定使用org.slf4j.Logger
 */
public class Logs {

	private final static String LOG_STRING = "==========";
	
	private static Logger _log = null;

	/**
	 * 自定义日志
	 * 
	 * @param clazz 当前类
	 */
	public Logs(Class<?> clazz) {
		_log = LoggerFactory.getLogger(clazz);
	}

	/**
	 * debug 信息
	 * 
	 * @param message 消息体
	 */
	public void debug(String... message) {
		_log.debug(customizeMessage(message));
	}

	/**
	 * info 信息
	 * 
	 * @param message 消息体
	 */
	public void info(String... message) {
		_log.info(customizeMessage(message));
	}

	/**
	 * 警告 信息
	 * 
	 * @param message 消息体
	 */
	public void warn(String... message) {
		_log.warn(customizeMessage(message));
	}

	/**
	 * 错误 信息
	 * 
	 * @param message 消息体
	 */
	public void error(String... message) {
		_log.error(customizeMessage(message));
	}

	private String customizeMessage(String... message) {
		String result = LOG_STRING;
		for (String m : message) {
			result += m;
		}
		return result + LOG_STRING;
	}
}
