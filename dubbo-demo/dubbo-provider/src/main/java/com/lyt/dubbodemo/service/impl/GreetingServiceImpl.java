package com.lyt.dubbodemo.service.impl;

import java.util.Date;
import com.lyt.dubbodemo.service.GreetingService;

public class GreetingServiceImpl implements GreetingService {
	public String hello(String name) {
		System.out.println("Greeting Service is calling : " + new Date());
		System.out.println(name);
		return "Hello, " + name;
	}
}