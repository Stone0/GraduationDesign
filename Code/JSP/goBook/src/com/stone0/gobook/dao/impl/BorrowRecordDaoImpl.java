package com.stone0.gobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stone0.gobook.BorrowRecord;
import com.stone0.gobook.dao.IBorrowRecordDao;
import com.stone0.gobook.util.DBUtils;

public class BorrowRecordDaoImpl implements IBorrowRecordDao {
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public BorrowRecordDaoImpl(){
		System.out.println("BorrowRecordDao数据库访问层实例化");
	}

	//添加借阅记录信息
	@Override
	public boolean add(BorrowRecord borrowrecord) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into borrowrecord(isbn, uidcard, borrowdate, returndate, mid) values(?,?,?,?,?)";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, borrowrecord.getIsbn());
			stmt.setString(2, borrowrecord.getUidcard());
			stmt.setTimestamp(3, Timestamp.valueOf(borrowrecord.getBorrowdate()));
			stmt.setTimestamp(4, Timestamp.valueOf(borrowrecord.getReturndate()));
			stmt.setString(5, borrowrecord.getMid());
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("添加借阅记录完成!" + result);
			} else {
				System.out.println("添加借阅记录出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}
	
	//预约完成添加暂时借阅记录
	@Override
	public boolean subAdd(BorrowRecord borrowrecord) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into borrowrecord(isbn, uidcard, mid) values(?,?,?)";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, borrowrecord.getIsbn());
			stmt.setString(2, borrowrecord.getUidcard());
			stmt.setString(3, borrowrecord.getMid());
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("添加借阅记录完成!" + result);
			} else {
				System.out.println("添加借阅记录出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//借阅后更新库存信息
	@Override
	public boolean updateByBorrow(BorrowRecord borrowrecord) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update bookmess set loan=loan+1, exist=exist-1 where isbn=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, borrowrecord.getIsbn());

			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("借阅后更新库存完成!" + result);
			} else {
				System.out.println("借阅后更新库存出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}
	
	//预约借阅确认更新借阅时间
	@Override
	public boolean updateSubComplete(BorrowRecord borrowrecord) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update borrowrecord set borrowdate=?, returndate=? where bid=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setTimestamp(1, Timestamp.valueOf(borrowrecord.getBorrowdate()));
			stmt.setTimestamp(2, Timestamp.valueOf(borrowrecord.getReturndate()));
			stmt.setInt(3, borrowrecord.getBid());

			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("确认预约借阅完成!" + result);
			} else {
				System.out.println("确认预约借阅出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}
	
	//还书更新借阅记录信息及库存信息
	@Override
	public boolean updateReturn(BorrowRecord borrowrecord) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update borrowrecord br, bookmess b set br.realreturndate=?, b.loan=b.loan-1, b.exist=b.exist+1 where br.bid=? and br.isbn=b.isbn";
			stmt = con.prepareStatement(sql);
			
			stmt.setTimestamp(1, Timestamp.valueOf(borrowrecord.getRealreturndate()));
			stmt.setInt(2, borrowrecord.getBid());

			int result = stmt.executeUpdate();
			
			if(result == 2){
				flag = true;
				System.out.println("还书更新借阅记录完成!" + result);
			} else {
				System.out.println("还书更新借阅记录出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}
	
	//查询所有借阅记录
	@Override
	public List<BorrowRecord> findAll() {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from borrowrecord";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setBorrowdate(date.format(rs.getTimestamp("borrowdate")));
				borrowrecord.setReturndate(date.format(rs.getTimestamp("returndate")));
				borrowrecord.setMid(rs.getString("mid"));

				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}

	//查询所有已借阅
	@Override
	public List<BorrowRecord> findAllBorrow() {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select bo.*, b.name, l.realname" +
					" from borrowrecord bo, bookmess b, libcardmess l" +
					" where bo.realreturndate='0000-00-00 00:00:00' and bo.borrowdate!='0000-00-00 00:00:00' and bo.isbn = b.isbn and bo.uidcard = l.uidcard";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setRealname(rs.getString("realname"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setName(rs.getString("name"));
				borrowrecord.setBorrowdate(date.format(rs.getTimestamp("borrowdate")));
				borrowrecord.setReturndate(date.format(rs.getTimestamp("returndate")));
				
				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据身份证号查询已借书籍
	@Override
	public List<BorrowRecord> findByUidcard(String uidcard) {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select b.isbn, b.loan, b.exist, br.uidcard, br.borrowdate, br.returndate, br.mid" +
					" from borrowrecord br, bookmess b" +
					" where br.isbn = b.isbn and br.uidcard=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, uidcard);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setBorrowdate(date.format(rs.getTimestamp("borrowdate")));
				borrowrecord.setReturndate(date.format(rs.getTimestamp("returndate")));
				borrowrecord.setLoan(rs.getInt("loan"));
				borrowrecord.setExist(rs.getInt("exist"));
				borrowrecord.setMid(rs.getString("mid"));
				
				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据bid查询完成预约借阅的信息
	@Override
	public List<BorrowRecord> findByBid(int bid) {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select b.isbn, b.loan, b.exist, br.*" +
					" from borrowrecord br, bookmess b" +
					" where br.isbn = b.isbn and br.bid=" + bid;
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setBorrowdate(date.format(rs.getTimestamp("borrowdate")));
				borrowrecord.setReturndate(date.format(rs.getTimestamp("returndate")));
				borrowrecord.setLoan(rs.getInt("loan"));
				borrowrecord.setExist(rs.getInt("exist"));
				
				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据bid查询归还借阅信息
	@Override
	public List<BorrowRecord> findByReturn(int bid) {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select b.isbn, b.loan, b.exist, br.*" +
					" from borrowrecord br, bookmess b" +
					" where br.isbn = b.isbn and br.bid=" + bid;
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setBorrowdate(date.format(rs.getTimestamp("borrowdate")));
				borrowrecord.setReturndate(date.format(rs.getTimestamp("returndate")));
				borrowrecord.setRealreturndate(date.format(rs.getTimestamp("realreturndate")));
				borrowrecord.setLoan(rs.getInt("loan"));
				borrowrecord.setExist(rs.getInt("exist"));
				
				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//查询预约完成的借阅记录
	@Override
	public BorrowRecord findNoComplete(BorrowRecord borrowrecord) {
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from borrowrecord where borrowdate='0000-00-00 00:00:00' and ISBN=? and uidcard=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, borrowrecord.getIsbn());
			stmt.setString(2, borrowrecord.getUidcard());
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setBorrowdate(date.format(rs.getTimestamp("borrowdate")));
				borrowrecord.setReturndate(date.format(rs.getTimestamp("returndate")));
				borrowrecord.setMid(rs.getString("mid"));
				
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return borrowrecord;
	}

	//根据身份证号后6位模糊查询预约记录
	@Override
	public List<BorrowRecord> findBySixId(String sixId) {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select bo.bid, bo.uidcard, bo.isbn, b.name, l.realname" +
					" from borrowrecord bo, bookmess b, libcardmess l" +
					" where bo.uidcard like '%" + sixId + "' and bo.borrowdate='0000-00-00 00:00:00' and bo.isbn = b.isbn and bo.uidcard = l.uidcard";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setRealname(rs.getString("realname"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setName(rs.getString("name"));
				
				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据身份证号后6位模糊查询已借阅记录
	@Override
	public List<BorrowRecord> findBorrowBySixId(String sixId) {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select bo.*, b.name, l.realname" +
					" from borrowrecord bo, bookmess b, libcardmess l" +
					" where bo.uidcard like '%" + sixId + "' and bo.realreturndate='0000-00-00 00:00:00' and bo.borrowdate!='0000-00-00 00:00:00' and bo.isbn = b.isbn and bo.uidcard = l.uidcard";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setRealname(rs.getString("realname"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setName(rs.getString("name"));
				borrowrecord.setBorrowdate(rs.getString("borrowdate"));
				borrowrecord.setReturndate(rs.getString("returndate"));
				
				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//查询所有预约完成未确认借阅的记录
	@Override
	public List<BorrowRecord> findAllSubComplete() {
		
		List<BorrowRecord> list = new ArrayList<BorrowRecord>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select bo.*, b.name, l.realname" +
					" from borrowrecord bo, bookmess b, libcardmess l" +
					" where bo.borrowdate='0000-00-00 00:00:00' and bo.isbn = b.isbn and bo.uidcard = l.uidcard";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BorrowRecord borrowrecord = new BorrowRecord();
				
				borrowrecord.setBid(rs.getInt("bid"));
				borrowrecord.setRealname(rs.getString("realname"));
				borrowrecord.setUidcard(rs.getString("uidcard"));
				borrowrecord.setIsbn(rs.getString("ISBN"));
				borrowrecord.setName(rs.getString("name"));
				borrowrecord.setBorrowdate(rs.getString("borrowdate"));
				borrowrecord.setReturndate(rs.getString("returndate"));

				list.add(borrowrecord);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
}
