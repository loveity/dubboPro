package cn.itsource.aigou.service;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import org.springframework.jms.core.JmsTemplate;

import cn.itsource.aigou.mq.ActivemqQueueConsumerAsyn;

/**
 * 消息队列通用操作接口
 * @author nixianhua
 *
 */
public interface ActiveMqService {
	/**
	 * 创建队列
	 * @param queueName
	 * @param listener 消息接收监听者（提供onMessage(Message message)方法）
	 * 在需要的时机关闭连接：
	 * ActivemqQueueConsumerAsyn.close();
	 * @throws JMSException
	 */
	ActivemqQueueConsumerAsyn createQueueAndListener(String queueName,MessageListener listener) throws JMSException;
	
	/**
	 * 发送队列消息
	 * @param queueName
	 * @param message
	 * @throws JMSException
	 */
	void sendQueueMessage(String queueName,Object message) throws JMSException;
	
	/**
	 * 删除消息队列
	 * @param queueName
	 * @throws JMSException
	 */
	void deleteQueue(String queueName) throws JMSException;
	
	/**
	 * 获取MQ连接
	 * @return
	 */
	ConnectionFactory getTargetConnectionFactory();
	
	/**
	 * 获取JMS操作模板
	 * @return
	 */
	JmsTemplate getJmsTemplate();
}
