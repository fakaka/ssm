package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.CommonUtil;
import com.example.demo.service.ByeService;

@RestController
public class HelloController {
	
	@Autowired
	ByeService byeService;

	@RequestMapping("/")
	public String welcome() {
		System.out.println("welcome");
		return "welcome";
	}

	@RequestMapping("/hello")
	public String hello() {
		System.out.println("hello");
		return "hi";
	}

	@RequestMapping("/common")
	public String common() {
		return CommonUtil.common();
	}
	
	@RequestMapping("/bye")
	public String bye() {
		return byeService.sayBye();
	}

}
