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
package common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * Created by Jimmy on 2020/2/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RecordLog {

    /**
     * 操作模块
     *
     * @return
     */
    String module();

    /**
     * 操作类型
     *
     * @return
     */
    String type();

    /**
     * 操作内容
     *
     * @return
     */
    String content();

}
