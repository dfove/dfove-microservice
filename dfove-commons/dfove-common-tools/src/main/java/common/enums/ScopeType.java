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
 * 用户权限类型
 */
public enum ScopeType {
	/**
	 * 完全匿名
	 */
	none(0),

	/**
	 * 带匿名token
	 */
	anonymous(1),

	/**
	 * 互联网用户
	 */
	user(2),

	/**
	 * 管理员，公司内部业务员
	 */
	admin(3),

	/**
	 * 系统权限
	 */
	system(4);

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	private ScopeType(int value) {
		this.value = value;
	}

	/**
	 * 根据名称查找类型
	 * 
	 * @param userType
	 * @return
	 */
	public static ScopeType find(String userType) {
		if (userType == null || userType.isEmpty()) {
            return null;
        }

		for (ScopeType s : ScopeType.values()) {
			if (s.name().equals(userType)) {
                return s;
            }
		}

		return null;
	}

	/**
	 * 根据vi查找类型
	 * 
	 * @param scopeType
	 * @return
	 */
	public static ScopeType find(int scopeType) {
		for (ScopeType s : ScopeType.values()) {
			if (s.getValue() == scopeType) {
                return s;
            }
		}

		return null;
	}
}
