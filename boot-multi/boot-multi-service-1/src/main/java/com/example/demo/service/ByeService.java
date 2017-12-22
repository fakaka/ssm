package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.CommonUtil;

@Service
public class ByeService {

	public String sayBye() {
		return CommonUtil.bye();
	}

}
