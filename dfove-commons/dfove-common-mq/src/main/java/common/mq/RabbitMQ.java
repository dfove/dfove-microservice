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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.MessageProperties;

import common.tools.ByteUtil;
import common.tools.JsonUtil;

public class RabbitMQ implements IQueue {

	// 连接配置
	private List<Config> configs = new ArrayList<Config>();

	// 连接
	private Connection publishConnection = null;
	private Connection readConnection = null;

	/**
	 * MQ实例
	 * 
	 */
	public RabbitMQ() {
		this(new Config());
	}

	/**
	 * MQ实例
	 * 
	 * @param ip       ip地址
	 * @param userName 用户名
	 * @param password 密码
	 */
	public RabbitMQ(String hostName, String userName, String password) {
		this(new Config(hostName, userName, password));
	}

	/**
	 * MQ实例
	 * 
	 * @param config 配置
	 */
	public RabbitMQ(Config config) {
		this.configs.add(config);
	}

	/**
	 * MQ实例
	 * 
	 * @param configs 配置列表
	 */
	public RabbitMQ(List<Config> configs) {
		this.configs = configs;
	}

	/**
	 * 创建连接工厂
	 * 
	 * @param configs 连接配置列表
	 * @return ConnectionFactory 连接工厂
	 */
	private ConnectionFactory createFactory(List<Config> configs) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(configs.get(0).getHost());
		factory.setUsername(configs.get(0).getUserName());
		factory.setPassword(configs.get(0).getPassword());

		return factory;
	}

	/**
	 * 创建连接
	 * 
	 * @param factory     连接工厂
	 * @param operateType 操作类型
	 */
	private void createConnection(ConnectionFactory factory, OperateType operateType) {
		try {
			if (operateType == OperateType.Publish) {
				publishConnection = factory.newConnection();
			} else {
				readConnection = factory.newConnection();
			}
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化服务器配置
	 * 
	 * @param operateType 操作类型
	 */
	private void initial(OperateType operateType) {
		ConnectionFactory factory = createFactory(this.configs);

		if (factory == null) {
			return;
		}

		try {
			this.createConnection(factory, operateType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据发布或读取的性质选择连接
	 * 
	 * @param operateType 频道类型：0发布，1读取
	 * @return 连接
	 */
	private Connection getConnection(OperateType operateType) {
		Connection connection = (operateType == OperateType.Publish ? publishConnection : readConnection);
		if (connection == null || !connection.isOpen()) {
			initial(operateType);
			connection = (operateType == OperateType.Publish ? publishConnection : readConnection);
		}
		return connection;
	}

	/**
	 * 声明频道
	 * 
	 * @param name        频道名称，可以和队列名称一致
	 * @param operateType 频道类型：0写，1读
	 * @return 频道
	 */
	private Channel createChannel(String queueName, OperateType operateType) {
		// 选择连接
		Connection conn = getConnection(operateType);

		// 声明新的频道
		Channel channel = null;
		try {
			channel = conn.createChannel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return channel;
	}

	/**
	 * 声明交换器
	 * 
	 * @param channel      已有频道
	 * @param exchangeName 交换器名称
	 * @param exchangeType 交换器类型： 1、Direct Exchange，需要将一个队列绑定到交换机上，并且路由键完全匹配；
	 *                     2、Fanout
	 *                     Exchange，只需要简单的将队列绑定到交换机上，不要求路由键匹配，Fanout交换机转发消息是最快的；
	 *                     3、Topic
	 *                     Exchange，需要队列绑定到一个模式上，符号“#”匹配一个或多个词，符号“*”匹配不多不少一个词，
	 *                     因此“audit.#”能够匹配到“audit.irs.corporate”，但是“audit.*”只会匹配到“audit.irs”
	 * @param durable      是否持久化
	 * @param autoDelete   是否自动删除
	 * @param arguments    其他参数
	 */
	private void createExchange(Channel channel, String exchangeName, ExchangeType exchangeType, Boolean durable,
			Boolean autoDelete, Map<String, Object> arguments) {
		if (exchangeName == null || exchangeName.isEmpty()) {
            return;
        }
		try {
			channel.exchangeDeclare(exchangeName, exchangeType.toString().toLowerCase(), durable, autoDelete,
					arguments);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 声明队列
	 * 
	 * @param channel    频道
	 * @param queueName  队列名称
	 * @param durable    是否持久化
	 * @param exclusive  是否排他队列，如果一个队列被声明为排他队列，该队列仅对首次声明它的连接可见，
	 *                   并在连接断开时自动删除。这里需要注意三点：其一，排他队列是基于连接可见的，同一连接的不同信道是可
	 *                   以同时访问同一个连接声明的排他队列的。其二，“首次”，如果一个连接已经声明了一个排他队列，其他连
	 *                   接是不允许建立同名的排他队列的，这个与普通队列不同。其三，即使该队列是持久化的，一旦连接关闭或者
	 *                   客户端退出，该排他队列都会被自动删除的。这种队列适用于只限于一个客户端发送读取消息的应用场景。
	 * @param autoDelete 是否自动删除
	 * @param arguments  其他参数
	 */
	private void createQueue(Channel channel, String queueName, Boolean durable, Boolean exclusive, Boolean autoDelete,
			Map<String, Object> arguments) {
		if (queueName == null || queueName.isEmpty()) {
            return;
        }
		try {
			channel.queueDeclare(queueName, durable, exclusive, autoDelete, arguments);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定队列和交换器
	 * 
	 * @param channel      频道
	 * @param exchangeName 交换器名称
	 * @param queueName    队列名称
	 * @param routingKey   路由键
	 */
	private void bindQueue(Channel channel, String exchangeName, String queueName, String routingKey) {
		if ((exchangeName == null || exchangeName.isEmpty()) || (routingKey == null || routingKey.isEmpty())) {
            return;
        }

		try {
			channel.queueBind(queueName, exchangeName, routingKey);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Channel createChannel(String queueName, Boolean beforeOperate, OperateType operateType,
								  QueueSetting setting) {

		String exchangeName = setting.getExchangeName();
		String routingKey = setting.getRoutingKey();

		if (exchangeName == null || exchangeName.isEmpty()) {
			if (routingKey != null && !routingKey.isEmpty()) {
				return null;
			}
			exchangeName = "";
			routingKey = queueName;
		}
		if (routingKey == null || routingKey.isEmpty()) {
			routingKey = queueName;
		}
		setting.setExchangeName(exchangeName);
		setting.setRoutingKey(routingKey);

		// 声明新的频道
		Channel channel = this.createChannel(queueName, operateType);
		if (channel == null) {
			return null;
		}

		if (beforeOperate) {
			this.beforeOperate(channel, queueName, setting, 0);
		}

		return channel;
	}

	/**
	 * 创建交换器、队列及绑定
	 * 
	 * @param channel   频道
	 * @param queueName 原始队列名称
	 * @param setting   队列配置
	 * @param index     队列层次序号
	 */
	private void beforeOperate(Channel channel, String queueName, QueueSetting setting, int index) {

		if (setting == null) {
            return;
        }

		String exname = (index == 0) ? setting.getExchangeName() : setting.deadExchange(index);
		String qname = (index == 0) ? queueName : setting.deadQueue(queueName, index);

		Map<String, Object> arguments = setting.deadArguments(queueName, index, true);

		// 创建交换器
		this.createExchange(channel, exname, setting.getExchangeType(), setting.getDurable(), setting.getAutoDelete(),
				null);

		// 创建队列
		this.createQueue(channel, qname, setting.getDurable(), setting.getExclusive(), setting.getAutoDelete(),
				arguments);

		// 绑定交换器和队列
		String routingKey = index == 0 ? setting.getRoutingKey() : qname;
		this.bindQueue(channel, exname, qname, routingKey);

		this.beforeOperate(channel, queueName, setting.getDeadLetter(), index + 1);
	}

	private void disposeConnection(Connection conn) {
		if (conn != null && conn.isOpen()) {
			try {
				conn.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}

	// ==========================对外公开方法=================

	/**
	 * 发送消息
	 * 
	 * @param T         消息类型
	 * @param queueName 队列名称
	 * @param message   要发送的消息实体
	 */
	@Override
	public <T> Boolean publish(String queueName, T message) {
		try {
			return this.publish(queueName, message, true);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 发送消息
	 * 
	 * @param T         消息类型
	 * @param queueName 队列名称
	 * @param message   要发送的消息实体
	 * @param toJson    是否转换为Json？如果生产和消费是同一个类，可以不转Json，否则就要先转Json，如生产是com.A，消费是com.B
	 */
	@Override
	public <T> Boolean publish(String queueName, T message, Boolean toJson) {
		return this.publish(queueName, message, toJson, null);
	}

	/**
	 * 发送消息
	 * 
	 * @param T         消息类型
	 * @param queueName 队列名称
	 * @param message   要发送的消息实体
	 * @param setting   配置项
	 */
	@Override
	public <T> Boolean publish(String queueName, T message, QueueSetting setting) {
		return this.publish(queueName, message, true, null);
	}

	/**
	 * 发送消息
	 * 
	 * @param T         消息类型
	 * @param queueName 队列名称
	 * @param message   要发送的消息实体
	 * @param toJson    是否转换为Json？如果生产和消费是同一个类，可以不转Json，否则就要先转Json，如生产是com.A，消费是com.B
	 * @param setting   配置项
	 */
	@Override
	public <T> Boolean publish(String queueName, T message, Boolean toJson, QueueSetting setting) {
		try {
			
	
		if (setting == null) {
			setting = new QueueSetting();
		}

		// 创建交换器、队列、绑定
		Channel channel = this.createChannel(queueName, true, OperateType.Publish, setting);
		if (channel == null) {
			return false;
		}

		String exchangeName = setting.getExchangeName();
		String routingKey = setting.getRoutingKey();

		byte[] body = null;
		if (toJson) {
			body = JsonUtil.objectToByte(message);
		} else {
			body = ByteUtil.objectToByte(message);
		}

		// 发布消息
		try {
			AMQP.BasicProperties properties = MessageProperties.MINIMAL_BASIC;
			int deliverMode = setting.getDurable() ? 2 : 1;
			int expiration = setting.getExpiration();
			AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
			if (expiration > 0) {
				properties = builder.expiration(String.valueOf(expiration)).deliveryMode(deliverMode).build();
			} else {
				properties = builder.deliveryMode(deliverMode).build();
			}
			channel.basicPublish(exchangeName, routingKey, properties, body);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 接收信息
	 * 
	 * @param queueName     队列名
	 * @param beforeOperate 是否需要准备工作：创建交换器、队列和绑定
	 * @param receiver      消息接收器
	 * @param setting       设置项
	 */
	@Override
	public <T> Boolean beginReceive(String queueName, Boolean beforeOperate, QueueReceiver<T> receiver,
			QueueSetting setting) {
		if (setting == null) {
			setting = new QueueSetting();
		}

		// 创建交换器、队列、绑定
		Channel channel = this.createChannel(queueName, beforeOperate, OperateType.Read, setting);
		if (channel == null) {
			return false;
		}

		try {
			channel.basicQos(0, setting.getNoAckCount(), false);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		final Boolean multipleAck = setting.getMultipleAck();
		final Boolean noAck = setting.getNoAck();
		final int delay = setting.getReceiveDelay();

		Consumer consumer = new DefaultConsumer(channel) {
			@SuppressWarnings("unchecked")
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {

				T message = null;
				if (receiver.getTojson()) {
					message = (T) new String(body, "UTF-8");
				} else {
					message = ByteUtil.byteToObject(body);
				}

				AckType ackType = receiver.receive(message);

				if (!noAck) {
					switch (ackType) {
					case SuccessAndDelete:
						channel.basicAck(envelope.getDeliveryTag(), multipleAck);
						break;
					case FailButDelete:
						channel.basicReject(envelope.getDeliveryTag(), false);
						break;
					case FailButRequeue:
						channel.basicReject(envelope.getDeliveryTag(), true);
						break;
					case DoNothing:
						break;
					default:
						break;
					}
				}
				if (delay > 0) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		try {
			channel.basicConsume(queueName, setting.getNoAck(), consumer);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 结束
	 */
	@Override
	public void dispose() {
		disposeConnection(publishConnection);
		disposeConnection(readConnection);
	}

	// =======================对外公开方法结束=============================
}
