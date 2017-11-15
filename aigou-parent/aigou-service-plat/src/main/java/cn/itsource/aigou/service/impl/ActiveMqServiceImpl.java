package cn.itsource.aigou.service.impl;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActiveMqServiceImpl extends ActiveMqServiceBaseImpl{
	
	@Autowired
	@Qualifier("targetConnectionFactory")
	private ActiveMQConnectionFactory targetConnectionFactory;
	
	@Autowired
	@Qualifier("jmsTemplate")
	private JmsTemplate jmsTemplate;
	
	
	@Override
	public ConnectionFactory getTargetConnectionFactory() {
		return targetConnectionFactory;
	}
	
	@Override
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}
}
