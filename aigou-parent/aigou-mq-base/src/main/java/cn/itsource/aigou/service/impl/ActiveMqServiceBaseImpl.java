package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.mq.ActivemqQueueConsumerAsyn;
import cn.itsource.aigou.service.ActiveMqService;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class ActiveMqServiceBaseImpl implements ActiveMqService {
	/**
	 * 默认创建队列并接收端监听实现
	 * @param queueName 队列名称
	 * 
	 * 在需要的时机关闭连接：
	 * ActivemqQueueConsumerAsyn.close();
	 */
	@Override
	public ActivemqQueueConsumerAsyn createQueueAndListener(String queueName, MessageListener listener) throws JMSException {
		ActivemqQueueConsumerAsyn consumer = new ActivemqQueueConsumerAsyn(
				getTargetConnectionFactory().createConnection(), queueName, listener);
		consumer.recive();
		return consumer;
	}

	/**
	 * 默认发送消息实现
	 * 
	 * @param message 消息
	 */
	@Override
	public void sendQueueMessage(String queueName, final Object message) throws JMSException {
		JmsTemplate jmsTemplate = getJmsTemplate();
		jmsTemplate.send(queueName, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				if (message instanceof String) {
					return session.createTextMessage(message.toString());
				} else if (message instanceof Map) {
					@SuppressWarnings("unchecked")
					Map<String, Object> messageDataMap = (Map<String, Object>) message;
					MapMessage mapMessage = session.createMapMessage();
					Set<Entry<String, Object>> entrySet = messageDataMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key = entry.getKey();
						Object value = entry.getValue();
						mapMessage.setObject(key, value);
					}
					return mapMessage;
				}
				return null;
			}
		});
	}

	/**
	 * 删除队列的默认实现
	 */
	@Override
	public void deleteQueue(String queueName) throws JMSException {
		ConnectionFactory connectionFactory = getTargetConnectionFactory();
		Connection connection = connectionFactory.createConnection();

		// 获取待删除的队列
		ActiveMQDestination deleteDestination = ActiveMQDestination.createDestination(queueName,
				ActiveMQDestination.QUEUE_TYPE);
		
		// 删除指定的队列
		if (connection instanceof ActiveMQConnection) {
			ActiveMQConnection activeMQConnection = (ActiveMQConnection) connection;
			activeMQConnection.destroyDestination(deleteDestination);
		}
		connection.close();
	}
}
