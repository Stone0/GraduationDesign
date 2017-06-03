package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.BorrowRecord;

public interface IBorrowRecordDao {

	//添加借阅记录信息
	public boolean add(BorrowRecord borrowrecord);
	//还书更新借阅记录信息
	public boolean updateReturn(BorrowRecord borrowrecord);
	//查询所有借阅记录
	public List<BorrowRecord> findAll();
	//根据身份证号查询已借书籍
	public List<BorrowRecord> findByUidcard(String uidcard);
	//预约完成添加暂时借阅记录
	public boolean subAdd(BorrowRecord borrowrecord);
	//借阅后更新库存信息
	public boolean updateByBorrow(BorrowRecord borrowrecord);
	//预约借阅确认更新借阅时间
	public boolean updateSubComplete(BorrowRecord borrowrecord);
	//查询所有预约完成未确认借阅的记录
	public List<BorrowRecord> findAllSubComplete();
	//查询预约完成的借阅记录
	public BorrowRecord findNoComplete(BorrowRecord borrowrecord);
	//根据身份证号查询已借书籍
	public List<BorrowRecord> findByBid(int bid);
	//根据身份证号后6位模糊查询预约记录
	public List<BorrowRecord> findBySixId(String sixId);
	//查询所有已借阅
	public List<BorrowRecord> findAllBorrow();
	//根据身份证号后6位模糊查询已借阅记录
	public List<BorrowRecord> findBorrowBySixId(String sixId);
	//根据bid查询归还借阅信息
	public List<BorrowRecord> findByReturn(int bid);
	
}
