package com.stone0.gobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.stone0.gobook.BookMess;
import com.stone0.gobook.dao.IBookMessDao;
import com.stone0.gobook.util.DBUtils;

public class BookMessDaoImpl implements IBookMessDao {
	
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public BookMessDaoImpl(){
		System.out.println("BookMessDao数据库访问层实例化");
	}
	
	//新书入库
	@Override
	public boolean add(BookMess bookmess) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into bookmess(isbn, name, author, publishment, price, addtime, place, cid, total, exist) values(?,?,?,?,?,?,?,?,?,?)";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, bookmess.getIsbn());
			stmt.setString(2, bookmess.getName());
			stmt.setString(3, bookmess.getAuthor());
			stmt.setString(4, bookmess.getPublishment());
			stmt.setString(5, bookmess.getPrice());
			stmt.setTimestamp(6, Timestamp.valueOf(bookmess.getAddtime()));
			stmt.setString(7, bookmess.getPlace());
			stmt.setInt(8, bookmess.getCid());
			stmt.setInt(9, bookmess.getTotal());
			stmt.setInt(10, bookmess.getTotal());
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("添加书籍信息完成!" + result);
			} else {
				System.out.println("添加书籍信息出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//删除书籍信息
	@Override
	public boolean delete(String isbn) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "delete from bookmess where ISBN=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, isbn);
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("删除书籍信息完成!" + result);
			} else {
				System.out.println("删除书籍信息出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//更新书籍信息
	@Override
	public boolean update(BookMess bookmess) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update bookmess set place=?, total=?, loan=?, exist=? where ISBN=?";
			stmt = con.prepareStatement(sql);
			
			stmt.setString(1, bookmess.getPlace());
			stmt.setInt(2, bookmess.getTotal());
			stmt.setInt(3, bookmess.getLoan());
			stmt.setInt(4, bookmess.getExist());
			stmt.setString(5, bookmess.getIsbn());

			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("更新书籍信息完成!" + result);
			} else {
				System.out.println("更新书籍信息出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//根据ISBN查询书籍信息
	@Override
	public List<BookMess> findByIsbn(String isbn) {
		
		List<BookMess> list = new ArrayList<BookMess>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select b.*, c.* from bookmess b, category c where b.ISBN like '%" + isbn + "%' and b.cid=c.cid";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BookMess bookmess = new BookMess();
				
				bookmess.setIsbn(rs.getString("ISBN"));
				bookmess.setCid(rs.getInt("cid"));
				bookmess.setCname(rs.getString("cname"));
				bookmess.setName(rs.getString("name"));
				bookmess.setAuthor(rs.getString("author"));
				bookmess.setPublishment(rs.getString("publishment"));
				bookmess.setPrice(rs.getString("price"));
				bookmess.setAddtime(date.format(rs.getTimestamp("addtime")));
				bookmess.setPlace(rs.getString("place"));
				bookmess.setTotal(rs.getInt("total"));
				bookmess.setLoan(rs.getInt("loan"));
				bookmess.setExist(rs.getInt("exist"));
				
				list.add(bookmess);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据书名模糊查询书籍信息
	@Override
	public List<BookMess> findByName(String name) {
		
		List<BookMess> list = new ArrayList<BookMess>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select b.*, c.* from bookmess b, category c where b.name like '%" + name + "%' and b.cid=c.cid";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BookMess bookmess = new BookMess();
				
				bookmess.setIsbn(rs.getString("ISBN"));
				bookmess.setCid(rs.getInt("cid"));
				bookmess.setCname(rs.getString("cname"));
				bookmess.setName(rs.getString("name"));
				bookmess.setAuthor(rs.getString("author"));
				bookmess.setPublishment(rs.getString("publishment"));
				bookmess.setPrice(rs.getString("price"));
				bookmess.setAddtime(date.format(rs.getTimestamp("addtime")));
				bookmess.setPlace(rs.getString("place"));
				bookmess.setTotal(rs.getInt("total"));
				bookmess.setLoan(rs.getInt("loan"));
				bookmess.setExist(rs.getInt("exist"));
				
				list.add(bookmess);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}
	
	//根据作者模糊查询书籍信息
	@Override
	public List<BookMess> findByAuthor(String author) {
		
		List<BookMess> list = new ArrayList<BookMess>();
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select b.*, c.* from bookmess b, category c where b.author like '%" + author + "%'  and b.cid=c.cid";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				BookMess bookmess = new BookMess();
				
				bookmess.setIsbn(rs.getString("ISBN"));
				bookmess.setCid(rs.getInt("cid"));
				bookmess.setCname(rs.getString("cname"));
				bookmess.setName(rs.getString("name"));
				bookmess.setAuthor(rs.getString("author"));
				bookmess.setPublishment(rs.getString("publishment"));
				bookmess.setPrice(rs.getString("price"));
				bookmess.setAddtime(date.format(rs.getTimestamp("addtime")));
				bookmess.setPlace(rs.getString("place"));
				bookmess.setTotal(rs.getInt("total"));
				bookmess.setLoan(rs.getInt("loan"));
				bookmess.setExist(rs.getInt("exist"));
				
				list.add(bookmess);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}

}
