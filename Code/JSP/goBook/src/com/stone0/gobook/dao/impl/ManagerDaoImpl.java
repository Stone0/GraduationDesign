package com.stone0.gobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stone0.gobook.Manager;
import com.stone0.gobook.dao.IManagerDao;
import com.stone0.gobook.util.DBUtils;

public class ManagerDaoImpl implements IManagerDao {
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ManagerDaoImpl(){
		System.out.println("ManagerDao数据库访问层实例化");
	}
	
	//添加管理员信息
	@Override
	public boolean add(Manager manager) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into manager(mid, mname, mpsw, mgender, mbirth, mphone, midcard, maddress, hiredate, inuse) values(?,?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, manager.getMid());
			stmt.setString(2, manager.getMname());
			stmt.setString(3, manager.getMpsw());
			stmt.setString(4, manager.getMgender());
			stmt.setDate(5, manager.getMbirth());
			stmt.setString(6, manager.getMphone());
			stmt.setString(7, manager.getMidcard());
			stmt.setString(8, manager.getMaddress());
			stmt.setTimestamp(9, Timestamp.valueOf(manager.getHiredate()));
			stmt.setInt(10, manager.getInuse());
			
			int result = stmt.executeUpdate();
			
			if(result != 0){
				flag = true;
				System.out.println("添加管理员成功!" + result);
			} else {
				System.out.println("添加管理员出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//注销管理员信息
	@Override
	public boolean cancel(String mid) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update manager set inuse=0 where mid=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, mid);
			
			int result = stmt.executeUpdate();
			
			if(result != 0){
				flag = true;
				System.out.println("注销管理员成功!" + result);
			} else {
				System.out.println("注销管理员出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//更新管理员信息
	@Override
	public boolean update(Manager manager) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update manager set mphone=?, maddress=? where mid=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, manager.getMphone());
			stmt.setString(2, manager.getMaddress());
			stmt.setString(3, manager.getMid());

			int result = stmt.executeUpdate();
			
			String mid = manager.getMid();
			
			if(result != 0){
				flag = true;
				System.out.println("成功更新编号为" + mid + "的管理员信息!" + result);
			} else {
				System.out.println("更新管理员信息出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//查询所有管理员信息
	@Override
	public List<Manager> findAll() {
		
		List<Manager> list = new ArrayList<Manager>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from manager";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				Manager manager = new Manager();
				
				manager.setMid(rs.getString("mid"));
				manager.setMname(rs.getString("mname"));
				manager.setMgender(rs.getString("mgender"));
				manager.setMbirth(rs.getDate("mbirth"));
				manager.setMphone(rs.getString("mphone"));
				manager.setMidcard(rs.getString("midcard"));
				manager.setMaddress(rs.getString("maddress"));
				manager.setHiredate(date.format(rs.getTimestamp("hiredate")));

				list.add(manager);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}

	//根据编号查询管理员信息
	@Override
	public List<Manager> findByMid(String mid) {
		
		List<Manager> list = new ArrayList<Manager>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from manager where mid like '%" + mid + "' and inuse='1'";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				Manager manager = new Manager();
				
				manager.setMid(rs.getString("mid"));
				manager.setMname(rs.getString("mname"));
				manager.setMgender(rs.getString("mgender"));
				manager.setMbirth(rs.getDate("mbirth"));
				manager.setMphone(rs.getString("mphone"));
				manager.setMidcard(rs.getString("midcard"));
				manager.setMaddress(rs.getString("maddress"));
				manager.setHiredate(date.format(rs.getTimestamp("hiredate")));
				manager.setInuse(rs.getInt("inuse"));
				
				list.add(manager);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//验证登录信息
	@Override
	public boolean login(String mid, String psw) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from manager where mid=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, mid);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				if (rs.getString("mpsw").equals(psw)) {
					flag = true;
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//修改密码
	@Override
	public boolean modifyPsw(String mid, String mpsw) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update manager set mpsw=? where mid=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, mpsw);
			stmt.setString(2, mid);
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("更改密码完成!" + result);
			} else {
				System.out.println("更改密码出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

}
