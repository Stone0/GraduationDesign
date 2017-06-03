package test;

import java.util.List;

import org.junit.Test;

import com.stone0.gobook.BookMess;
import com.stone0.gobook.SubscribeRecord;
import com.stone0.gobook.dao.IBookMessDao;
import com.stone0.gobook.dao.ISubscribeRecordDao;
import com.stone0.gobook.dao.impl.BookMessDaoImpl;
import com.stone0.gobook.dao.impl.SubscribeRecordDaoImpl;

public class TestBookMess {
	
	IBookMessDao bookmessDao = new BookMessDaoImpl();
	ISubscribeRecordDao subrecDao = new SubscribeRecordDaoImpl();
	
	//@Test
	public void testAllBook(){
		String isbn = "";
		List<BookMess> list = bookmessDao.findByIsbn(isbn);
		for(BookMess bookmess : list){
			System.out.println(
					"ISBN : " + bookmess.getIsbn() +
					" 书名 : " + bookmess.getName() +
					" 作者 : " + bookmess.getAuthor() +
					" 出版社 : " + bookmess.getPublishment() +
					" 价格 : " + bookmess.getPrice() +
					" 添加时间 : " + bookmess.getAddtime() +
					" 位置 : " + bookmess.getPlace() +
					" 借出 : " + bookmess.getLoan() +
					" 现有 : " + bookmess.getExist() 
					);
		}
	}
	
	//@Test
	public void testDeleteBook(){
		String isbn = "123456";
		bookmessDao.delete(isbn);
	}
	
	//@Test
	public void testAddBook(){
		BookMess bookmess = new BookMess();
		
		bookmess.setIsbn("22333");
		bookmess.setCid(2);
		bookmess.setName("2233name");
		bookmess.setAuthor("The Stone0");
		bookmess.setPublishment("China");
		bookmess.setPrice("23.9");
		bookmess.setPlace("there");
		bookmess.setTotal(999);
		bookmess.setExist(999);
		
		bookmessDao.add(bookmess);
	}
	
	//@Test
	public void testShowSubNoProcess(){
		List<SubscribeRecord> list = subrecDao.findAllNotComplete();
		for(SubscribeRecord subscribeRecord : list){
			System.out.println(
					"ISBN : " + subscribeRecord.getIsbn() +
					" 书名 : " + subscribeRecord.getName() +
					" 作者 : " + subscribeRecord.getAuthor() +
					" 出版社 : " + subscribeRecord.getPublishment() +
					" 位置 : " + subscribeRecord.getPlace() +
					" 现有 : " + subscribeRecord.getExist() +
					"身份证号 : " + subscribeRecord.getUidcard() +
					"预约时间 : " + subscribeRecord.getSubtime() +
					"照片 : " + subscribeRecord.getUphoto() +
					"真实姓名 : " + subscribeRecord.getRealname()
					);
		}
		
	}
	
	@Test
	public void testUpdateSubRec(){
		SubscribeRecord subscriberecord = new SubscribeRecord();
		
		subscriberecord.setMid("test");
		subscriberecord.setLoan(1);
		subscriberecord.setExist(998);
		subscriberecord.setUidcard("123456");
		
		subrecDao.update(subscriberecord);
			
	}
	
	
}
