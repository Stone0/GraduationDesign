package com.stone0.gobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stone0.gobook.SubscribeRecord;
import com.stone0.gobook.dao.ISubscribeRecordDao;
import com.stone0.gobook.util.DBUtils;

public class SubscribeRecordDaoImpl implements ISubscribeRecordDao {

	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public SubscribeRecordDaoImpl(){
		System.out.println("SubscribeRecordDao数据库访问层实例化");
	}
	
	//完成预约后更新预约信息及书籍库存信息
	@Override
	public boolean update(SubscribeRecord subscriberecord) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update subscriberecord s, bookmess b set s.complete=1, s.mid=?, b.loan=b.loan+1, b.exist=b.exist-1 where s.complete='0' and s.sid=? and s.isbn=b.isbn";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, subscriberecord.getMid());
			stmt.setInt(2, subscriberecord.getSid());

			int result = stmt.executeUpdate();
			
			if(result != 0){
				flag = true;
				System.out.println("完成预约!" + result);
			} else {
				System.out.println("预约更新出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//查询所有当天未完成的预约
	@Override
	public List<SubscribeRecord> findAllNotComplete() {
		
		List<SubscribeRecord> list = new ArrayList<SubscribeRecord>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
//			String sql = "select isbn, name, author, publishment, place, exist, uidcard, subtime, uphoto, realname" +
//					"from bookmess, subscriberecord, libcardmess where complete='0'";
//			String sql = "select isbn, uidcard, subtime from subscriberecord where complete='0' and isbn='22333' ,select isbn, name, author, publishment, place, exist from bookmess where isbn='22333'";
//			 where subscriberecord.isbn = bookmess.isbn and subscriberecord.uidcard = libcardmess.uidcard
//			String sql = "select * from bookmess where isbn in (select isbn from subscriberecord where complete='0')";
			String sql = "select s.sid, s.isbn, s.uidcard, s.subtime, b.name, b.author, b.publishment, b.place, b.exist, b.loan, l.uidcard, l.uphoto, l.realname" +
					" from subscriberecord s, bookmess b, libcardmess l" +
					" where s.complete='0' and s.isbn = b.isbn and s.uidcard = l.uidcard";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				SubscribeRecord subscriberecord = new SubscribeRecord();
				
				subscriberecord.setSid(rs.getInt("sid"));
				subscriberecord.setIsbn(rs.getString("ISBN"));
				subscriberecord.setName(rs.getString("name"));
				subscriberecord.setAuthor(rs.getString("author"));
				subscriberecord.setPublishment(rs.getString("publishment"));
				subscriberecord.setPlace(rs.getString("place"));
				subscriberecord.setExist(rs.getInt("exist"));
				subscriberecord.setLoan(rs.getInt("loan"));
				subscriberecord.setUidcard(rs.getString("uidcard"));
				subscriberecord.setSubtime(date.format(rs.getTimestamp("subtime")));
				subscriberecord.setUphoto(rs.getString("uphoto"));
				subscriberecord.setRealname(rs.getString("realname"));

				list.add(subscriberecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据sid查询完成的预约
	@Override
	public SubscribeRecord findSubComplete(int sid) {
		
		SubscribeRecord subscriberecord = null;

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select s.sid, s.isbn, s.uidcard, s.complete, s.mid, b.name, b.exist, b.loan, l.uidcard, l.realname" +
					" from subscriberecord s, bookmess b, libcardmess l" +
					" where s.sid=? and s.isbn = b.isbn and s.uidcard = l.uidcard";
			stmt = con.prepareStatement(sql);
			
			stmt.setInt(1, sid);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				subscriberecord = new SubscribeRecord();
				
				subscriberecord.setSid(rs.getInt("sid"));
				subscriberecord.setRealname(rs.getString("realname"));
				subscriberecord.setUidcard(rs.getString("uidcard"));
				subscriberecord.setIsbn(rs.getString("ISBN"));
				subscriberecord.setName(rs.getString("name"));
				subscriberecord.setExist(rs.getInt("exist"));
				subscriberecord.setLoan(rs.getInt("loan"));
				subscriberecord.setComplete(rs.getInt("complete"));
				subscriberecord.setMid(rs.getString("mid"));

			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return subscriberecord;
	}

	//查询所有预约记录
	public List<SubscribeRecord> findAll() {
		
		List<SubscribeRecord> list = new ArrayList<SubscribeRecord>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from subscriberecord";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				SubscribeRecord subscriberecord = new SubscribeRecord();
				
				subscriberecord.setIsbn(rs.getString("ISBN"));
				subscriberecord.setUidcard(rs.getString("uidcard"));
				subscriberecord.setSubtime(date.format(rs.getTimestamp("subtime")));
				subscriberecord.setComplete(rs.getInt("complete"));
				subscriberecord.setMid(rs.getString("mid"));

				list.add(subscriberecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
}
