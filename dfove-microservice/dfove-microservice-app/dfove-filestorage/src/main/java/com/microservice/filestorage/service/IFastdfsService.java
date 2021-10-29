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
package com.microservice.filestorage.service;

import org.jboss.logging.Param;

import com.microservice.filestorage.entity.FastdfsEntity;

import common.entity.OperateResult;
import common.search.entity.IBaseService;

public interface IFastdfsService  extends IBaseService<FastdfsEntity, String, FastdfsEntity> {

//	public OperateResult<Boolean> update(@Param("monitorId") long monitorId, @Param("status") int status);

}