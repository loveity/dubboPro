package cn.itsource.aigou.mq.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class CommonTextListner implements MessageListener{

	@Override
	public void onMessage(Message message) {
		if(message instanceof TextMessage){
			TextMessage textMessage = (TextMessage)message;
			try {
				System.out.println("textmessage = "+textMessage.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}else if(message instanceof MapMessage){
			MapMessage mapMessage = (MapMessage)message;
			System.out.println("mapMessage = "+mapMessage);
		}
	}
	
	
}
