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

public interface IQueue {

	/**
	 * 发布消息队列
	 * 
	 * @param queueName 队列名
	 * @param message   队列信息
	 * @return 发布是否成功
	 */
	<T> Boolean publish(String queueName, T message);

	/**
	 * 发布消息队列
	 * 
	 * @param queueName 队列名
	 * @param message   队列信息
	 * @param toJson    是否转换为Json？如果生产和消费是同一个类，可以不转Json，否则就要先转Json，如生产是com.A，消费是com.B
	 * @return 发布是否成功
	 */
	<T> Boolean publish(String queueName, T message, Boolean toJson);

	/**
	 * 发布消息队列
	 * 
	 * @param queueName 队列名
	 * @param message   队列信息
	 * @param setting   设置项
	 * @return 发布是否成功
	 */
	<T> Boolean publish(String queueName, T message, QueueSetting setting);

	/**
	 * 发布消息队列
	 * 
	 * @param queueName 队列名
	 * @param message   队列信息
	 * @param toJson    是否转换为Json？如果生产和消费是同一个类，可以不转Json，否则就要先转Json，如生产是com.A，消费是com.B
	 * @param setting   设置项
	 * @return 发布是否成功
	 */
	<T> Boolean publish(String queueName, T message, Boolean toJson, QueueSetting setting);

	/**
	 * 接收信息
	 * 
	 * @param queueName     队列名
	 * @param beforeOperate 是否需要准备工作：创建交换器、队列和绑定
	 * @param receiver      消息接收器
	 * @param setting       设置项
	 */
	<T> Boolean beginReceive(String queueName, Boolean beforeOperate, QueueReceiver<T> receiver, QueueSetting setting);

	/**
	 * 结束
	 */
	void dispose();
}
