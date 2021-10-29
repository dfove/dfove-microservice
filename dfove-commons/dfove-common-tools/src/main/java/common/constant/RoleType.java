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
package common.constant;

import java.util.List;

/**
 * Created by Jimmy on 2020/3/21.
 */
public class RoleType {

    /**
     * 系统角色
     */
    public static final int SYSTEM = 1;
    /**
     * 自定义角色
     */
    public static final int CUSTOM = 2;

    /**
     * 超级管理员
     */
    public static final int USER_SUPER_ADMIN = 1;
    /**
     * 管理员
     */
    public static final int USER_ADMIN = 10;
    /**
     * 县局管理员
     */
    public static final int USER_BUREAU_ADMIN = 50;
    /**
     * 成员
     */
    public static final int USER_MEMBER = 100;

    /**
     * 是否超级管理员
     *
     * @param roleUserType
     * @return
     */
    public static boolean isSuperAdmin(int roleUserType) {
        return roleUserType == USER_SUPER_ADMIN;
    }

    /**
     * 是否超级管理员
     *
     * @param roleUserTypes
     * @return
     */
    public static boolean isSuperAdmin(List<Integer> roleUserTypes) {
        if (roleUserTypes == null || roleUserTypes.isEmpty()) {
            return false;
        }
        return roleUserTypes.contains(USER_SUPER_ADMIN);
    }

    /**
     * 是否管理员
     *
     * @param roleUserType
     * @return
     */
    public static boolean isAdmin(int roleUserType) {
        return roleUserType == USER_ADMIN;
    }

    /**
     * 是否管理员
     *
     * @param roleUserTypes
     * @return
     */
    public static boolean isAdmin(List<Integer> roleUserTypes) {
        if (roleUserTypes == null || roleUserTypes.isEmpty()) {
            return false;
        }
        return roleUserTypes.contains(USER_ADMIN);
    }

    /**
     * 是否县局管理员
     *
     * @param roleUserType
     * @return
     */
    public static boolean isBureauAdmin(int roleUserType) {
        return roleUserType == USER_BUREAU_ADMIN;
    }

    /**
     * 是否县局管理员
     *
     * @param roleUserTypes
     * @return
     */
    public static boolean isBureauAdmin(List<Integer> roleUserTypes) {
        if (roleUserTypes == null || roleUserTypes.isEmpty()) {
            return false;
        }
        return roleUserTypes.contains(USER_BUREAU_ADMIN);
    }

}
