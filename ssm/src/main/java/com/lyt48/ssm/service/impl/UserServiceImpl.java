package com.lyt48.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyt48.ssm.bean.User;
import com.lyt48.ssm.dao.UserMapper;
import com.lyt48.ssm.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> list() {
		return userMapper.list();
	}
	
}
