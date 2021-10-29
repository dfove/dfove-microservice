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
package com.microservice.core;

import java.lang.annotation.*;
import common.enums.ScopeType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UserAnnotation {
	/**
	 * 名称
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 访问资源对应的Id，一般是userId，adminId
	 * 
	 * @return
	 */
	String resourceId() default "";

	/**
	 * 访问资源对应的scope，一般是user，admin
	 * 
	 * @return
	 */
	ScopeType resourceScope() default ScopeType.none;

	/**
	 * 只限本人，如设置该条件，其他条件忽略，优先级最高
	 * 
	 * @return
	 */
	boolean ownerOnly() default false;

	/**
	 * 本人或指定的Scopes，其他条件忽略，优先级次于ownerOnly
	 * 
	 * @return
	 */
	ScopeType[] ownerOrScopes() default {};

	/**
	 * 指定的Scopes，其他条件忽略，优先级次于ownerOrScopes
	 * 
	 * @return
	 */
	ScopeType[] scopes() default {};

	/**
	 * 指定访问该资源的最低级别
	 * @return
	 */
	int MinGrade() default -1;

}