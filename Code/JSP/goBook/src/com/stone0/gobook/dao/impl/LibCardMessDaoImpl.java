package com.stone0.gobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stone0.gobook.LibCardMess;
import com.stone0.gobook.dao.ILibCardMessDao;
import com.stone0.gobook.util.DBUtils;

public class LibCardMessDaoImpl implements ILibCardMessDao {
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	public LibCardMessDaoImpl(){
		System.out.println("LibCardMessDao数据库访问层实例化");
	}
	
	//现场办理借书证
	@Override
	public boolean add(LibCardMess libcardmess) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into libcardmess(realname, ugender, ubirth, uphone, wechat, qq, uaddress, uidcard, applydate, processtime, process, inuse) values(?,?,?,?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(sql);
			

			stmt.setString(1, libcardmess.getRealname());
			stmt.setString(2, libcardmess.getUgender());
			stmt.setDate(3, libcardmess.getUbirth());
			stmt.setString(4, libcardmess.getUphone());
			stmt.setString(5, libcardmess.getWechat());
			stmt.setString(6, libcardmess.getQq());
			stmt.setString(7, libcardmess.getUaddress());
			stmt.setString(8, libcardmess.getUidcard());
			stmt.setTimestamp(9, Timestamp.valueOf(libcardmess.getApplydate()));
			stmt.setTimestamp(10, Timestamp.valueOf(libcardmess.getApplydate()));
			stmt.setInt(11, libcardmess.getProcess());
			stmt.setInt(12, libcardmess.getInuse());
			
			int result = stmt.executeUpdate();
			
			if(result != 0){
				flag = true;
				System.out.println("办理借书证成功!" + result);
			} else {
				System.out.println("办理借书证出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//根据身份证号注销借书证信息
	@Override
	public boolean cancel(String uidcard) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update libcardmess set inuse=0 where uidcard=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, uidcard);
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("注销借书证成功!" + result);
			} else {
				System.out.println("注销借书证出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//完成验证后更新借书证信息
	@Override
	public boolean updateProcess(LibCardMess libcardmess) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update libcardmess set process=1, inuse=1, processtime=? where uidcard=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setTimestamp(1, Timestamp.valueOf(libcardmess.getProcesstime()));
			stmt.setString(2, libcardmess.getUidcard());

			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("验证完成!" + result);
			} else {
				System.out.println("验证出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//根据身份证号更新借书证信息
	@Override
	public boolean update(LibCardMess libcardmess) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update libcardmess set uphone=?, wechat=?, qq=?, uaddress=? where uidcard=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, libcardmess.getUphone());
			stmt.setString(2, libcardmess.getWechat());
			stmt.setString(3, libcardmess.getQq());
			stmt.setString(4, libcardmess.getUaddress());
			stmt.setString(5, libcardmess.getUidcard());

			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("更新" + libcardmess.getUidcard() + "的借书证信息完成!" + result);
			} else {
				System.out.println("更新借书证信息出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}
	
	//查询所有借书证信息
	@Override
	public List<LibCardMess> findAll() {
		
		List<LibCardMess> list = new ArrayList<LibCardMess>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from libcardmess";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				LibCardMess libcardmess = new LibCardMess();
				
				libcardmess.setNickname(rs.getString("nickname"));
				libcardmess.setRealname(rs.getString("realname"));
				libcardmess.setUgender(rs.getString("ugender"));
				libcardmess.setUbirth(rs.getDate("ubirth"));
				libcardmess.setUphone(rs.getString("uphone"));
				libcardmess.setWechat(rs.getString("wechat"));
				libcardmess.setQq(rs.getString("qq"));
				libcardmess.setUaddress(rs.getString("uaddress"));
				libcardmess.setUidcard(rs.getString("uidcard"));
				libcardmess.setUphoto(rs.getString("uphoto"));
				libcardmess.setProcesstime(date.format(rs.getTimestamp("processtime")));

				list.add(libcardmess);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}

	//查询所有当天未验证的借书证
	@Override
	public List<LibCardMess> findAllNotProcess() {
		
		List<LibCardMess> list = new ArrayList<LibCardMess>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from libcardmess where process='0'";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				LibCardMess libcardmess = new LibCardMess();
				
				libcardmess.setUphoto(rs.getString("uphoto"));
				libcardmess.setNickname(rs.getString("nickname"));
				libcardmess.setRealname(rs.getString("realname"));
				libcardmess.setUgender(rs.getString("ugender"));
				libcardmess.setUbirth(rs.getDate("ubirth"));
				libcardmess.setUphone(rs.getString("uphone"));
				libcardmess.setWechat(rs.getString("wechat"));
				libcardmess.setQq(rs.getString("qq"));
				libcardmess.setUaddress(rs.getString("uaddress"));
				libcardmess.setUidcard(rs.getString("uidcard"));
				libcardmess.setApplydate(date.format(rs.getTimestamp("applydate")));
				libcardmess.setProcess(rs.getInt("process"));

				list.add(libcardmess);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}

	//根据身份证号后6位模糊查询借书证所有信息
	@Override
	public List<LibCardMess> findAllByUidcard(String uidcard) {
		
		List<LibCardMess> list = new ArrayList<LibCardMess>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from libcardmess where uidcard like '%" + uidcard + "' and inuse='1' and processtime!='0000-00-00 00:00:00'";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				LibCardMess libcardmess = new LibCardMess();
				
				libcardmess.setRealname(rs.getString("realname"));
				libcardmess.setUgender(rs.getString("ugender"));
				libcardmess.setUbirth(rs.getDate("ubirth"));
				libcardmess.setUphone(rs.getString("uphone"));
				libcardmess.setWechat(rs.getString("wechat"));
				libcardmess.setQq(rs.getString("qq"));
				libcardmess.setUaddress(rs.getString("uaddress"));
				libcardmess.setUidcard(rs.getString("uidcard"));
				libcardmess.setUphoto(rs.getString("uphoto"));
				libcardmess.setApplydate(date.format(rs.getTimestamp("applydate")));
				libcardmess.setProcesstime(date.format(rs.getTimestamp("processtime")));
				libcardmess.setProcess(rs.getInt("process"));
				libcardmess.setInuse(rs.getInt("inuse"));
				
				list.add(libcardmess);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据身份证号后6位查询身份证号
	@Override
	public LibCardMess findUidcard(String uidcard) {

		LibCardMess libcardmess = null;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select uidcard from libcardmess where uidcard like '%" + uidcard + "'";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				libcardmess = new LibCardMess();
				
				libcardmess.setUidcard(rs.getString("uidcard"));
				
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return libcardmess;
	}

}
