package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.LibCardMess;

public interface ILibCardMessDao {

	//现场办理借书证
	public boolean add(LibCardMess libcardmess);
	//根据身份证号注销借书证信息
	public boolean cancel(String uidcard);
	//完成验证后更新借书证信息
	public boolean updateProcess(LibCardMess libcardmess);
	//根据身份证号更新借书证信息
	public boolean update(LibCardMess libcardmess);
	//查询所有借书证信息
	public List<LibCardMess> findAll();
	//查询所有当天未验证的借书证
	public List<LibCardMess> findAllNotProcess();
	//根据身份证号查询借书证所有信息
	public List<LibCardMess> findAllByUidcard(String uidcard);
	//根据身份证号后6位查询身份证号
	public LibCardMess findUidcard(String uidcard);
	
}
