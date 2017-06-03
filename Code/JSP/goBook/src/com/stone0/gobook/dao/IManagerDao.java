package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.Manager;

public interface IManagerDao {
	
	//添加管理员信息
	public boolean add(Manager manager);
	//删除管理员信息
	public boolean cancel(String mid);	
	//更新管理员信息
	public boolean update(Manager manager);
	//查询所有管理员信息
	public List<Manager> findAll();
	//根据编号查询管理员信息
	public List<Manager> findByMid(String mid);
	//验证登录信息
	public boolean login(String mid, String psw);
	//修改密码
	public boolean modifyPsw(String mid, String mpsw);
	
}
