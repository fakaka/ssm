package com.lyt48.ssm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lyt48.ssm.bean.User;

@Repository
public interface UserMapper {
	public List<User> list();
}
