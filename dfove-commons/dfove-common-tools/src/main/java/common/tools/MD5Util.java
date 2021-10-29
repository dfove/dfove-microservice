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

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5加密类
 * 
 * @author YangTianhua
 *
 */
public class MD5Util {

	/**
	 * 字符串转换为md5格式
	 * 
	 * @param plainText
	 * @return
	 */
	public static String stringToMD5(String plainText) {
		return stringToMD5(plainText, "");
	}

	/**
	 * 字符串转换为md5格式，指定盐值
	 * 
	 * @param plainText
	 * @param suffix
	 * @return
	 */
	public static String stringToMD5(String plainText, String suffix) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest((plainText + suffix).getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有这个md5算法！");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	/**
	 * 验证明文和md5是否匹配
	 * 
	 * @param md5
	 * @param plainText
	 * @return
	 */
	public static Boolean valid(String md5, String plainText) {
		return valid(md5, plainText, "");
	}

	/**
	 * 验证明文和md5是否匹配
	 * 
	 * @param md5
	 * @param plainText
	 * @param prefix
	 * @return
	 */
	public static Boolean valid(String md5, String plainText, String suffix) {
		String newMd5 = stringToMD5(plainText, suffix);
		return newMd5.equals(md5);
	}
}