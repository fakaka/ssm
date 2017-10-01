package com.lyt.dubbodemo.main;

import java.io.IOException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.lyt.dubbodemo.service.GreetingService;

public class ConsumerMain {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.start();
		GreetingService greetingService = (GreetingService) context.getBean("greetingService");
		String greetMessage = greetingService.hello("mj");
		System.out.println("Consumer ==> " + greetMessage);
		context.destroy();
	}
}