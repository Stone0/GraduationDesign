package com.stone0.gobook.dao;

import java.util.List;

import com.stone0.gobook.BookMess;

public interface IBookMessDao {

	//新书入库
	public boolean add(BookMess bookmess);
	//删除书籍信息
	public boolean delete(String isbn);	
	//更新书籍信息
	public boolean update(BookMess bookmess);
	//根据ISBN查询书籍信息
	public List<BookMess> findByIsbn(String isbn);
	//根据书名模糊查询书籍信息
	public List<BookMess> findByName(String name);
	//根据作者模糊查询书籍信息
	public List<BookMess> findByAuthor(String author);

}
