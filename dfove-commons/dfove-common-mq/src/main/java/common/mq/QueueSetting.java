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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * MQ配置参数
 */
public class QueueSetting {
	private ExchangeType exchangeType = ExchangeType.Direct;
	private Boolean durable = true;
	private int prefetchSize = 5;

	private String exchangeName = "";
	private String routingKey = "";

	private Boolean autoDelete = false;
	private Boolean exclusive = false;
	private Boolean noAck = false;
	private int noAckCount = 5;
	private Boolean multipleAck = false;

	private int expiration = 0;
	private int receiveDelay = 0;

	private Map<String, Object> arguments = null;
	private QueueSetting deadLetter = null;

	/**
	 * 配置构造函数
	 */
	public QueueSetting() {

	}

	/**
	 * 设置交换器名称
	 */
	public QueueSetting(String exchangeName, int expiration) {
		this.exchangeName = exchangeName;
		this.expiration = expiration;
	}

	/**
	 * 设置交换器名称
	 */
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	/**
	 * 获取交换器名称
	 */
	public String getExchangeName() {
		return this.exchangeName;
	}

	/**
	 * 路由键，当交换器名称为空时，routingKey必须为空
	 **/
	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	/**
	 * 路由键，当交换器名称为空时，routingKey必须为空
	 **/
	public String getRoutingKey() {
		return this.routingKey;
	}

	/**
	 * 交换器类型
	 **/
	public void setExchangeType(ExchangeType exchangeType) {
		this.exchangeType = exchangeType;
	}

	/**
	 * 交换器类型
	 **/
	public ExchangeType getExchangeType() {
		return this.exchangeType;
	}

	/**
	 * 是否持久化
	 **/
	public void setDurable(Boolean durable) {
		this.durable = durable;
	}

	/**
	 * 是否持久化
	 **/
	public Boolean getDurable() {
		return this.durable;
	}

	/**
	 * 是否自动删除
	 **/
	public void setAutoDelete(Boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	/**
	 * 是否自动删除
	 **/
	public Boolean getAutoDelete() {
		return this.autoDelete;
	}

	/**
	 * 是否排他队列
	 **/
	public void setExclusive(Boolean exclusive) {
		this.exclusive = exclusive;
	}

	/**
	 * 是否排他队列
	 **/
	public Boolean getExclusive() {
		return this.exclusive;
	}

	/**
	 * 不需应答，消息读取后即删除
	 **/
	public void setNoAck(Boolean noAck) {
		this.noAck = noAck;
	}

	/**
	 * 不需应答，消息读取后即删除
	 **/
	public Boolean getNoAck() {
		return this.noAck;
	}

	/**
	 * 每次取的数据数
	 **/
	public void setPrefetchSize(int prefetchSize) {
		this.prefetchSize = prefetchSize;
	}

	/**
	 * 每次取的数据数
	 **/
	public int getPrefetchSize() {
		return this.prefetchSize;
	}

	/**
	 * 当设置为需要应答时，无应答前最多可以获得的信息数
	 **/
	public void setNoAckCount(int noAckCount) {
		this.noAckCount = noAckCount;
	}

	/**
	 * 当设置为需要应答时，无应答前最多可以获得的信息数
	 **/
	public int getNoAckCount() {
		return this.noAckCount;
	}

	/**
	 * 是否批量一次性应答所有小于当前消息序号的所有消息
	 **/
	public void setMultipleAck(Boolean multipleAck) {
		this.multipleAck = multipleAck;
	}

	/**
	 * 是否批量一次性应答所有小于当前消息序号的所有消息
	 **/
	public Boolean getMultipleAck() {
		return this.multipleAck;
	}

	/**
	 * 有效期，毫秒，默认0不设置
	 **/
	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	/**
	 * 有效期，毫秒，默认0不设置
	 **/
	public int getExpiration() {
		return this.expiration;
	}

	/**
	 * 其他参数
	 **/
	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	/**
	 * 其他参数
	 **/
	public Map<String, Object> getArguments() {
		return this.arguments;
	}

	/**
	 * 每条消息接收延迟毫秒数
	 **/
	public void setReceiveDelay(int receiveDelay) {
		this.receiveDelay = receiveDelay;
	}

	/**
	 * 每条消息接收延迟毫秒数
	 **/
	public int getReceiveDelay() {
		return this.receiveDelay;
	}

	/**
	 * 死信配置
	 **/
	public void setDeadLetter(QueueSetting deadLetter) {
		this.deadLetter = deadLetter;
	}

	/**
	 * 死信配置
	 **/
	public QueueSetting getDeadLetter() {
		return this.deadLetter;
	}

	public String deadExchange(int index) {
		if(index<1 ||(exchangeName != null && !exchangeName.isEmpty()) ) {
            return this.exchangeName;
        }
		
		return "mq.dead";
	}

	public String deadQueue(String queueName, int index) {
		if(index<1) {
            return queueName;
        }
		
		String qname = String.format("%s.dead.0%d", queueName, index);
		return qname;
	}

	public Map<String, Object> deadArguments(String queueName, int index, Boolean createNew) {
		if (this.deadLetter == null) {
            return this.arguments;
        }

		Map<String, Object> newArguments = null;

		if (!createNew) {
			newArguments = this.arguments;
			if (newArguments == null) {
                newArguments = new HashMap<String, Object>();
            }

			if (this.arguments.get("x-dead-letter-exchange") == null) {
                newArguments.put("x-dead-letter-exchange", this.deadLetter.deadExchange(index+1));
            }
			if (this.arguments.get("x-dead-letter-routing-key") == null) {
                newArguments.put("x-dead-letter-routing-key", this.deadLetter.deadQueue(queueName, index + 1));
            }
			if (this.arguments.get("x-message-ttl") == null) {
                newArguments.put("x-message-ttl", this.expiration);
            }
		} else {
			newArguments = new HashMap<String, Object>();

			if (this.arguments != null) {
				Iterator<String> iter = this.arguments.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next();
					Object val = this.arguments.get(key);
					newArguments.put(key, val);
				}
			}

			newArguments.put("x-dead-letter-exchange", this.deadLetter.deadExchange(index+1));
			newArguments.put("x-dead-letter-routing-key", this.deadLetter.deadQueue(queueName, index + 1));
			newArguments.put("x-message-ttl", this.expiration);
		}
		return newArguments;
	}

}
