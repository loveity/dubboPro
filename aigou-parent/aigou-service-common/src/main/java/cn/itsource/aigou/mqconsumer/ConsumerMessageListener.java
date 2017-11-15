package cn.itsource.aigou.mqconsumer;

public class ConsumerMessageListener {
	public void receiveMessage(String message) {
		System.out.println("接收到消息：" + message);
	}
}
