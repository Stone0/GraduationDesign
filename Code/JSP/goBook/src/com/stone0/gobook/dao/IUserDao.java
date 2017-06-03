package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.User;

public interface IUserDao {

	//删除用户
	public void delete(User user);
	//查询所有用户
	public List<User> findAll();
	
}
