package cn.itsource.aigou.mq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

/**
 * activemq 异步接收消息处理类
 * @author nixianhua
 *
 */
public class ActivemqQueueConsumerAsyn {
	private String quequeName = "";
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private MessageListener listner;

	public ActivemqQueueConsumerAsyn(Connection connection,String quequeName,MessageListener listener) {
		this.connection = connection;
		this.quequeName = quequeName;
		this.listner = listener;
	}

	private void initialize() throws JMSException {
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue(quequeName);
		consumer = session.createConsumer(destination);
		connection.start();
	}

	public void recive() {
		try {
			initialize();
			consumer.setMessageListener(listner);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void submit() throws JMSException {
		session.commit();
	}

	// 关闭连接
	public void close() throws JMSException {
		if (consumer != null)
			consumer.close();
		if (session != null)
			session.close();
		if (connection != null)
			connection.close();
	}

}