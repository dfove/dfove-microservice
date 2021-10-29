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
package common.mq;

/// <summary>
/// 消息响应类型
/// </summary>
public enum AckType
{
	/// <summary>
    /// 什么也不做，不回应
    /// </summary>
    DoNothing,
    
    /// <summary>
    /// 处理成功并删除
    /// </summary>
    SuccessAndDelete,

    /// <summary>
    /// 处理失败但删除
    /// </summary>
    FailButDelete,

    /// <summary>
    /// 处理失败但重新入队
    /// </summary>
    FailButRequeue,

    /// <summary>
    /// 处理失败、重新入队并分给新的消费者
    /// </summary>
    FailButRequeueAndNewConsumer,

    /// <summary>
    /// 处理失败、重新入队并分给原来的消费者
    /// </summary>
    FailButRequeueAndSameConsumer,
}
