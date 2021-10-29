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
package common.search.entity;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldComment {

	/**
	 * 表格别名
	 * 
	 * @return
	 */
	String tableName() default "";

	/**
	 * 字符串模糊查找，默认true，如设置为false则精确查找（=）
	 * 
	 * @return
	 */
	boolean like() default true;

	/**
	 * 手动组织查询条件，默认false，如设置为true则会忽略该条件，需要手动在mybatis里面拼接
	 * 
	 * @return
	 */
	boolean manual() default false;
}