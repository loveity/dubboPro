package cn.itsource.aigou.facade.server;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonServiceServer {
	public static void main(String[] args) throws IOException {
		String configLocation = "classpath*:spring-service-common.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		System.out.println("common服务正在监听，按任意键退出");
		String[] names = context.getBeanDefinitionNames();
		System.out.print("Beans:");
		for (String string : names)
			System.out.println(string);
		System.out.println();
		
		//启动服务的消费者（接受者）
		
		System.in.read();
	}
}
