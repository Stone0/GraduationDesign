package com.stone0.gobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.stone0.gobook.Category;
import com.stone0.gobook.dao.ICategoryDao;
import com.stone0.gobook.util.DBUtils;

public class CategoryDaoImpl implements ICategoryDao {

	public CategoryDaoImpl(){
		System.out.println("CategoryDao数据库访问层实例化");
	}
	
	//添加分类信息
	@Override
	public boolean add(Category category) {

		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "insert into category(cname) values(?)";
			stmt = con.prepareStatement(sql);

			stmt.setString(1, category.getCname());
			
			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("添加分类完成!" + result);
			} else {
				System.out.println("添加分类出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//更新分类信息
	@Override
	public boolean update(int cid) {
		
		boolean flag = false;
		
		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "update category set cname=? where cid=?";
			stmt = con.prepareStatement(sql);
			
			Category category = new Category();
			
			stmt.setString(1, category.getCname());
			stmt.setInt(2, cid);

			int result = stmt.executeUpdate();
			
			if(result == 1){
				flag = true;
				System.out.println("更新分类完成!" + result);
			} else {
				System.out.println("更新分类出错!" + result);
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return flag;
	}

	//查询所有分类信息
	@Override
	public List<Category> findAll() {
		
		List<Category> list = new ArrayList<Category>();

		Connection con = DBUtils.openConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "select * from category";
			stmt = con.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				Category category = new Category();
				
				category.setCid(rs.getInt("cid"));
				category.setCname(rs.getString("cname"));

				list.add(category);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			DBUtils.close(rs, stmt, con);
		}
		return list;
	}

}
