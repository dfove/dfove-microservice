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
package common.enums;

public enum UserStatus {

	//0：禁用，1：正常，8：锁定，88：删除
	
	/**
	 * 禁用 0
	 */
	Forbidden(0, "禁用"),

	/**
	 * 正常 1
	 */
	Normal(1, "正常"),

	/**
	 * 锁定 8
	 */
	Locked(8, "锁定"),

	/**
	 * 删除 88
	 */
	Deleted(88, "删除");

	private int value;
	private String name;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private UserStatus(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static UserStatus find(Integer status) {

		if (status == null) {
            return null;
        }

		for (UserStatus s : UserStatus.values()) {
			if (s.getValue() == status.intValue()) {
                return s;
            }
		}

		return null;
	}
}
