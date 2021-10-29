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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ByteUtil {

	/**
	 * 对象转换为字节
	 * 
	 * @author yth
	 * @param obj 对象
	 * @return byte[] 字节
	 */
	public static byte[] objectToByte(Object obj) {

		byte[] body = null;

		if (obj == null) {
			System.out.println("对像为空");
			return body;
		}

		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;

		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.close();
			bos.close();
			body = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return body;
	}

	@SuppressWarnings("unchecked")
	public static <T> T byteToObject(byte[] body) {
		T obj = null;

		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;

		try {
			if (body != null) {
				bis = new ByteArrayInputStream(body);
				ois = new ObjectInputStream(bis);
				obj = (T) ois.readObject();
				ois.close();
				bis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return obj;
	}
}
