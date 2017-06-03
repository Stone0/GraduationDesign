package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.Category;

public interface ICategoryDao {

	//添加分类信息
	public boolean add(Category category);
	//更新分类信息
	public boolean update(int cid);
	//查询所有分类信息
	public List<Category> findAll();
	
}
