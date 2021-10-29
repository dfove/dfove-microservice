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

/**
 * 访问终端
 * 
 * @author YangTianhua
 *
 */
public enum TerminalSource {

	/**
	 * PC 11
	 */
	PC(11, "PC"),

	/**
	 * 安卓 21
	 */
	Android(21, "安卓"),

	/**
	 * IOS 31
	 */
	IOS(31, "IOS"),

	/**
	 * 微信或H5 41
	 */
	WechatH5(41, "微信或H5"),

	/**
	 * 其他未知 51
	 */
	Others(51, "其他");

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

	private TerminalSource(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static boolean check(int value) {
		for (TerminalSource terminalSource : TerminalSource.values()) {
			if (terminalSource.value == value) {
				return true;
			}
		}
		return false;
	}

	public static String bankChannel(Integer value) {
		switch (value) {
		case 11:
			return "000002";
		case 21:
		case 31:
			return "000001";
		case 41:
			return "000003";
		default:
			return "000004";
		}
	}
}
