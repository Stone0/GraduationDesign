package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.SubscribeRecord;

public interface ISubscribeRecordDao {

	//完成预约后更新预约信息及书籍库存信息
	public boolean update(SubscribeRecord subscriberecord);
	//查询所有当天未完成的预约
	public List<SubscribeRecord> findAllNotComplete();
	//根据sid查询完成的预约
	public SubscribeRecord findSubComplete(int sid);
	//查询所有预约记录
	public List<SubscribeRecord> findAll();
	
}
