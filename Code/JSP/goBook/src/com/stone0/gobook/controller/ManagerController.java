package com.stone0.gobook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stone0.gobook.BookMess;
import com.stone0.gobook.BorrowRecord;
import com.stone0.gobook.LibCardMess;
import com.stone0.gobook.SubscribeRecord;
import com.stone0.gobook.dao.IBookMessDao;
import com.stone0.gobook.dao.IBorrowRecordDao;
import com.stone0.gobook.dao.ILibCardMessDao;
import com.stone0.gobook.dao.ISubscribeRecordDao;
import com.stone0.gobook.dao.impl.BookMessDaoImpl;
import com.stone0.gobook.dao.impl.BorrowRecordDaoImpl;
import com.stone0.gobook.dao.impl.LibCardMessDaoImpl;
import com.stone0.gobook.dao.impl.SubscribeRecordDaoImpl;

public class ManagerController extends HttpServlet{

	private static final long serialVersionUID = 1908476866106237454L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//获取项目的绝对地址
		String uri =req.getRequestURI();
		System.out.println(uri + " - - ManagerController - -");
		
		if(uri.endsWith("showAllSub")){//前往预约主页，显示所有未完成的预约
			showAllSub(req,resp);
		}else if(uri.endsWith("sessionManager")){//管理员登录成功，标记管理员名称
			sessionManager(req,resp);
		}else if(uri.endsWith("managerExit")){//退出登陆，跳转到登录界面
			managerExit(req,resp);
		}else if(uri.endsWith("subComplete")){//完成预约
			subComplete(req,resp);
		}else if(uri.endsWith("searchBook")){//查询书籍模块
			searchBook(req,resp);
		}else if(uri.endsWith("borrowNow")){//现场借阅
			borrowNow(req,resp);
		}else if(uri.endsWith("borrowSub")){//预约借阅
			borrowSub(req,resp);
		}else if(uri.endsWith("showAllSubComplete")){//查询所有预约借阅
			showAllSubComplete(req,resp);
		}else if(uri.endsWith("showSubBySixId")){//根据身份证号后6位查询预约借阅
			showSubBySixId(req,resp);
		}else if(uri.endsWith("showAllBorrow")){//查询所有已借阅的借阅记录
			showAllBorrow(req,resp);
		}else if(uri.endsWith("showBorrowBySixId")){//根据身份证号后6位查询已借阅记录
			showBorrowBySixId(req,resp);
		}else if(uri.endsWith("returnBook")){//还书
			returnBook(req,resp);
		}else if(uri.endsWith("renewBook")){//续借
			renewBook(req,resp);
		}else if(uri.endsWith("addLibCard")){//办理借书证
			try {
				addLibCard(req,resp);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(uri.endsWith("searchLibCard")){//查询借书证
			searchLibCard(req,resp);
		}
	}

	private void showAllSub(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String mid = (String)session.getAttribute("managerMid");
		session.setAttribute("managerMid", mid);
		
		ISubscribeRecordDao subRecordDao = new SubscribeRecordDaoImpl();
		List <SubscribeRecord> list = subRecordDao.findAllNotComplete();
		
		req.setAttribute("subNoComplete", list);
		
		for(SubscribeRecord subscribeRecord : list){
			System.out.println(
					"sid : " + subscribeRecord.getSid() +
					"ISBN : " + subscribeRecord.getIsbn() +
					" 书名 : " + subscribeRecord.getName() +
					" 作者 : " + subscribeRecord.getAuthor() +
					" 出版社 : " + subscribeRecord.getPublishment() +
					" 位置 : " + subscribeRecord.getPlace() +
					" 借出 : " + subscribeRecord.getLoan() +
					" 现有 : " + subscribeRecord.getExist() +
					"身份证号 : " + subscribeRecord.getUidcard() +
					"预约时间 : " + subscribeRecord.getSubtime() +
					"照片 : " + subscribeRecord.getUphoto() +
					"真实姓名 : " + subscribeRecord.getRealname()
					);
		}
		
		req.getRequestDispatcher("/jsp/manager/showAllSub.jsp").forward(req, resp);
		
	}

	private void sessionManager(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//获取参数
		HttpSession session = req.getSession();
		String mid = (String)session.getAttribute("managerMid");
		System.out.println("获取到session的mid为" + mid);
		
		PrintWriter writer = resp.getWriter();
		if(mid!=null){
			session.setAttribute("managerMid", mid);
			writer.write("1");
		}else{
			writer.write("0");
		}
		writer.flush();
		writer.close();
	}

	private void managerExit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//清除session
		req.getSession().invalidate();
		
		resp.sendRedirect(req.getContextPath()+"/jsp/login.jsp");
		
	}
	
	private void subComplete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//获取管理员id及预约id
		HttpSession session = req.getSession();
		String mid = (String)session.getAttribute("managerMid");
		int sid = Integer.parseInt(req.getParameter("sid"));
		String isbn = req.getParameter("isbn");
		String uidcard = req.getParameter("uidcard");
		
		System.out.println(" - - 选定sid为 - - " + sid + " - - 选定的mid为 - - " + mid + " - - 选定的isbn为 - - " + isbn + " - - 选定的uidcard为 - - " + uidcard);
		
		ISubscribeRecordDao subRecordDao = new SubscribeRecordDaoImpl();
		SubscribeRecord subscribeRecord = new SubscribeRecord();
		IBorrowRecordDao borRecordDao = new BorrowRecordDaoImpl();
		BorrowRecord borrowRecord = new BorrowRecord();
		
		//设置预约参数
		subscribeRecord.setMid(mid);
		subscribeRecord.setSid(sid);
		
		//设置借阅参数
		borrowRecord.setIsbn(isbn);
		borrowRecord.setUidcard(uidcard);
		borrowRecord.setMid(mid);
		
		BorrowRecord borrowrecord = new BorrowRecord();
		borrowrecord.setIsbn(isbn);
		borrowrecord.setUidcard(uidcard);
		borrowrecord.setMid(mid);
		
		//完成预约
		boolean flag = subRecordDao.update(subscribeRecord);
		if (flag) {
			subscribeRecord = subRecordDao.findSubComplete(sid);
			System.out.println(
					" 完成预约反馈 : " +
					" Sid : " + subscribeRecord.getSid() +
					" 真实姓名 : " + subscribeRecord.getRealname() +
					" 身份证号 : " + subscribeRecord.getUidcard() +
					" ISBN : " + subscribeRecord.getIsbn() +
					" 书名 : " + subscribeRecord.getName() +
					" 现有 : " + subscribeRecord.getExist() +
					" 借出 : " + subscribeRecord.getLoan() +
					" 完成情况 : " + subscribeRecord.getComplete() +
					" Mid : " + subscribeRecord.getMid()
					);
			
			//添加暂时借阅记录
			boolean borFlag = borRecordDao.subAdd(borrowRecord);
			if(borFlag) {
				BorrowRecord borRecord = new BorrowRecord();
				borRecord = borRecordDao.findNoComplete(borrowrecord);
				System.out.println(
						" 完成预约添加的借阅记录 : " +
						" Bid : " + borRecord.getBid() +
						" ISBN : " + borRecord.getIsbn() +
						" Uidcard : " + borRecord.getUidcard() +
						" 借阅时间 : " + borRecord.getBorrowdate() +
						" Mid : " + borRecord.getMid()
						);
				
				//resp.sendRedirect(req.getContextPath() + "/jsp/showAllSub.jsp");
				List <SubscribeRecord> list = subRecordDao.findAllNotComplete();
				
				req.setAttribute("subNoComplete", list);
				req.getRequestDispatcher("/jsp/manager/showAllSub.jsp").forward(req, resp);
				
			} else {
				System.out.println("预约添加的借阅记录出错!");
			}
			
		} else {
			System.out.println("完成预约出现错误!");
		}
		//req.getRequestDispatcher("/jsp/showAllSub.jsp").forward(req, resp);
	}

	private void searchBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String search = req.getParameter("searchText").trim();
		System.out.println("获取搜索的文本为: " + search);
		
		IBookMessDao bookMessDao = new BookMessDaoImpl();
		
		//根据ISBN查询
		List <BookMess> bookIsbn = new ArrayList<BookMess>();
		bookIsbn = bookMessDao.findByIsbn(search);
		
		for(BookMess bookMess : bookIsbn){
			System.out.println(
					"FindByISBN - - " +
					" ISBN : " + bookMess.getIsbn() +
					" Cid : " + bookMess.getCid() +
					" 类别 : " + bookMess.getCname() +
					" 书名 : " + bookMess.getName() +
					" 作者 : " + bookMess.getAuthor() +
					" 出版社 : " + bookMess.getPublishment() +
					" 价格 : " + bookMess.getPrice() +
					" 位置 : " + bookMess.getPlace() +
					" 总量 : " + bookMess.getTotal() +
					" 借出 : " + bookMess.getLoan() +
					" 现有 : " + bookMess.getExist()
					);
		}
		
		//根据书名查询
		List <BookMess> bookName = new ArrayList<BookMess>();
		bookName = bookMessDao.findByName(search);
		
		for(BookMess bookMess : bookName){
			System.out.println(
					"FindByName - - " +
					" ISBN : " + bookMess.getIsbn() +
					" Cid : " + bookMess.getCid() +
					" 类别 : " + bookMess.getCname() +
					" 书名 : " + bookMess.getName() +
					" 作者 : " + bookMess.getAuthor() +
					" 出版社 : " + bookMess.getPublishment() +
					" 价格 : " + bookMess.getPrice() +
					" 位置 : " + bookMess.getPlace() +
					" 总量 : " + bookMess.getTotal() +
					" 借出 : " + bookMess.getLoan() +
					" 现有 : " + bookMess.getExist()
					);
		}
		
		//根据作者查询
		List <BookMess> bookAuthor = new ArrayList<BookMess>();
		bookAuthor = bookMessDao.findByAuthor(search);
		
		for(BookMess bookMess : bookAuthor){
			System.out.println(
					"FindByAuthor - - " +
					" ISBN : " + bookMess.getIsbn() +
					" Cid : " + bookMess.getCid() +
					" 类别 : " + bookMess.getCname() +
					" 书名 : " + bookMess.getName() +
					" 作者 : " + bookMess.getAuthor() +
					" 出版社 : " + bookMess.getPublishment() +
					" 价格 : " + bookMess.getPrice() +
					" 位置 : " + bookMess.getPlace() +
					" 总量 : " + bookMess.getTotal() +
					" 借出 : " + bookMess.getLoan() +
					" 现有 : " + bookMess.getExist()
					);
		}
		
		if(bookIsbn.size() != 0 && bookName.size() != 0 && bookAuthor.size() != 0) {
			System.out.println(" 111 ");
			//isbn与name合并去掉相同结果
			List<BookMess> containIN = new ArrayList<BookMess>();//新建用于存放isbn与nameISBN相同的结果集
			for(int i=0; i<bookIsbn.size(); i++) {//遍历一遍isbn结果集
				for(int n=0; n<bookName.size(); n++) {//遍历一遍name结果集
					if(bookIsbn.get(i).getIsbn().equals(bookName.get(n).getIsbn())) {//如果其中存在isbn.isbn=name.isbn
						containIN.add(bookName.get(n));//把后者的相同结果放到containIN结果集里
					}
				}
			}
			
			bookName.removeAll(containIN);//从后者中移除相同的结果
			
			List<BookMess> allByIN = new ArrayList<BookMess>();//新建用于存放不存在重复结果的结果集
			allByIN.addAll(bookIsbn);//添加原来的isbn结果集
			allByIN.addAll(bookName);//添加移除相同结果后的name结果集
			
			/*for(BookMess bookMess : allByIN){
				System.out.println(
						" isbn和name去重后 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			//isbn与name合并后的allByIN再与author合并去重
			List<BookMess> containINA = new ArrayList<BookMess>();//新建用于存放allByIN与authorISBN相同的结果集
			for(int i=0; i<allByIN.size(); i++) {//遍历一遍allByIN结果集
				for(int a=0; a<bookAuthor.size(); a++) {//遍历一遍author结果集
					if(allByIN.get(i).getIsbn().equals(bookAuthor.get(a).getIsbn())) {//如果其中存在allByIN.isbn=author.isbn
						containINA.add(bookAuthor.get(a));//把后者的相同结果放到containINA结果集里
					}
				}
			}

			bookAuthor.removeAll(containINA);//从后者中移除相同的结果
			
			List<BookMess> allByINA = new ArrayList<BookMess>();//新建用于存放不存在重复结果的结果集
			allByINA.addAll(allByIN);//添加原来的allByIN结果集
			allByINA.addAll(bookAuthor);//添加移除相同结果后的author结果集
			
			/*for(BookMess bookMess : allByINA){
				System.out.println(
						" 111 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", allByINA);
			//req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() == 0 && bookName.size() != 0 && bookAuthor.size() != 0) {
			System.out.println(" 011 ");
			
			//name与author合并去重
			List<BookMess> containNA = new ArrayList<BookMess>();//新建用于存放name与authorISBN相同的结果集
			for(int n=0; n<bookName.size(); n++) {//遍历一遍name结果集
				for(int a=0; a<bookAuthor.size(); a++) {//遍历一遍author结果集
					if(bookName.get(n).getIsbn().equals(bookAuthor.get(a).getIsbn())) {//如果其中存在name.isbn=author.isbn
						containNA.add(bookAuthor.get(a));//把后者的相同结果放到containNA结果集里
					}
				}
			}
			
			bookAuthor.removeAll(containNA);//从后者中移除相同的结果
			
			List<BookMess> allByNA = new ArrayList<BookMess>();//新建用于存放不存在重复结果的结果集
			allByNA.addAll(bookName);//添加原来的name结果集
			allByNA.addAll(bookAuthor);//添加移除相同结果后的author结果集
			
			/*for(BookMess bookMess : allByNA){
				System.out.println(
						" 011 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", allByNA);
//			req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() == 0 && bookName.size() == 0 && bookAuthor.size() != 0) {
			System.out.println(" 001 ");

			//author就是结果
			/*for(BookMess bookMess : bookAuthor){
				System.out.println(
						" 001 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", bookAuthor);
//			req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() == 0 && bookName.size() != 0 && bookAuthor.size() == 0) {
			System.out.println(" 010 ");
			
			//name就是结果
			/*for(BookMess bookMess : bookName){
				System.out.println(
						" 010 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", bookName);
//			req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() != 0 && bookName.size() == 0 && bookAuthor.size() != 0) {
			System.out.println(" 101 ");
			
			//isbn与author合并去重
			List<BookMess> containIA = new ArrayList<BookMess>();//新建用于存放isbn与authorISBN相同的结果集
			for(int i=0; i<bookIsbn.size(); i++) {//遍历一遍isbn结果集
				for(int a=0; a<bookAuthor.size(); a++) {//遍历一遍author结果集
					if(bookIsbn.get(i).getIsbn().equals(bookAuthor.get(a).getIsbn())) {//如果其中存在isbn.isbn=author.isbn
						containIA.add(bookAuthor.get(a));//把后者的相同结果放到containIA结果集里
					}
				}
			}
			
			bookAuthor.removeAll(containIA);//从后者中移除相同的结果
			
			List<BookMess> allByIA = new ArrayList<BookMess>();//新建用于存放不存在重复结果的结果集
			allByIA.addAll(bookIsbn);//添加原来的isbn结果集
			allByIA.addAll(bookAuthor);//添加移除相同结果后的author结果集
			
			/*for(BookMess bookMess : allByIA){
				System.out.println(
						" 101 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", allByIA);
//			req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() != 0 && bookName.size() == 0 && bookAuthor.size() == 0) {
			System.out.println(" 100 ");
			
			//isbn就是结果
			/*for(BookMess bookMess : bookIsbn){
				System.out.println(
						" 100 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", bookIsbn);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() != 0 && bookName.size() != 0 && bookAuthor.size() == 0) {
			System.out.println(" 110 ");
			
			//isbn与name合并去掉相同结果
			List<BookMess> containIN = new ArrayList<BookMess>();//新建用于存放isbn与nameISBN相同的结果集
			for(int i=0; i<bookIsbn.size(); i++) {//遍历一遍isbn结果集
				for(int n=0; n<bookName.size(); n++) {//遍历一遍name结果集
					if(bookIsbn.get(i).getIsbn().equals(bookName.get(n).getIsbn())) {//如果其中存在isbn.isbn=name.isbn
						containIN.add(bookName.get(n));//把后者的相同结果放到containIN结果集里
					}
				}
			}
			
			bookName.removeAll(containIN);//从后者中移除相同的结果
			
			List<BookMess> allByIN = new ArrayList<BookMess>();//新建用于存放不存在重复结果的结果集
			allByIN.addAll(bookIsbn);//添加原来的isbn结果集
			allByIN.addAll(bookName);//添加移除相同结果后的name结果集
			
			/*for(BookMess bookMess : allByIN){
				System.out.println(
						"110 - - " +
						" ISBN : " + bookMess.getIsbn() +
						" Cid : " + bookMess.getCid() +
						" 类别 : " + bookMess.getCname() +
						" 书名 : " + bookMess.getName() +
						" 作者 : " + bookMess.getAuthor() +
						" 出版社 : " + bookMess.getPublishment() +
						" 价格 : " + bookMess.getPrice() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}*/
			
			req.setAttribute("bookMess", allByIN);
//			req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
			
		} else {
			System.out.println(" 000 ");
			
//			req.getRequestDispatcher("/jsp/searchBook.jsp").forward(req, resp);
			req.getRequestDispatcher("/jsp/manager/searchBook.jsp").forward(req, resp);
		}
		
	}

	private void borrowNow(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//获取管理员id
		HttpSession session = req.getSession();
		String mid = (String)session.getAttribute("managerMid");
		
		//获取借阅的信息
		String isbn = req.getParameter("borrowIsbn");
		String sixId = req.getParameter("borrowUidcard");
		
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		LibCardMess libcardmess = new LibCardMess();
		
		libcardmess = libCardMessDao.findUidcard(sixId);
		String uidcard = libcardmess.getUidcard();
		
		System.out.println("借阅isbn为 - - " + isbn + " - - sixId为 - - " + sixId + " - - 借阅者为 - - " + uidcard + " - - 管理员为 - - " + mid);
		
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		BorrowRecord borrowRecord = new BorrowRecord();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//设置借阅参数
		borrowRecord.setIsbn(isbn);
		borrowRecord.setUidcard(uidcard);
		borrowRecord.setMid(mid);
		borrowRecord.setBorrowdate(date.format(System.currentTimeMillis()));
		
		//设置归还时间 在借阅时间上加30天
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 30);
		String newdate = date.format(c.getTime());
		borrowRecord.setReturndate(newdate);
		
		//现场借阅添加借阅记录
		boolean flag = borrowRecordDao.add(borrowRecord);
		if(flag) {
			//完成借阅后更新库存信息
			boolean borFlag = borrowRecordDao.updateByBorrow(borrowRecord);
			if(borFlag) {
				List <BorrowRecord> list = borrowRecordDao.findByUidcard(uidcard);
				for(BorrowRecord borrowrecord : list){
					System.out.println(
							" 更新库存后借阅信息 : " +
							" ISBN : " + borrowrecord.getIsbn() +
							" Uidcard : " + borrowrecord.getUidcard() +
							" 借阅时间 : " + borrowrecord.getBorrowdate() +
							" 归还时间 : " + borrowrecord.getReturndate() +
							" 借出 : " + borrowrecord.getLoan() +
							" 现有 : " + borrowrecord.getExist() +
							" Mid : " + borrowrecord.getMid()
							);
				}
				
				req.getRequestDispatcher("/manager/searchBook?searchText=" + "").forward(req, resp);
				
			} else {
				System.out.println("更新库存信息出错!");
			}
			
		} else {
			System.out.println("借阅添加记录出错!");
		}
		
	}

	private void showAllSubComplete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//查询所有预约完成未确认借阅的记录
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		List <BorrowRecord> list = borrowRecordDao.findAllSubComplete();
		
		req.setAttribute("subComplete", list);
		
		for(BorrowRecord borrowrecord : list){
			System.out.println(
					" bid : " + borrowrecord.getBid() +
					" 身份证号 : " + borrowrecord.getUidcard() +
					" 真实姓名 : " + borrowrecord.getRealname() +
					" ISBN : " + borrowrecord.getIsbn() +
					" 书名 : " + borrowrecord.getName() +
					" 借阅时间 : " + borrowrecord.getBorrowdate() +
					" 归还时间 : " + borrowrecord.getReturndate()
					);
		}
		
		req.getRequestDispatcher("/jsp/manager/subBorrow.jsp").forward(req, resp);
		
	}

	private void showSubBySixId(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String sixId = req.getParameter("searchText").trim();
		System.out.println("身份证后6位为: " + sixId);
		
		//根据身份证号后6位查询预约借阅
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		List <BorrowRecord> list = borrowRecordDao.findBySixId(sixId);
		
		req.setAttribute("subComplete", list);
		
		for(BorrowRecord borrowrecord : list){
			System.out.println(
					" bid : " + borrowrecord.getBid() +
					" 身份证号 : " + borrowrecord.getUidcard() +
					" 真实姓名 : " + borrowrecord.getRealname() +
					" ISBN : " + borrowrecord.getIsbn() +
					" 书名 : " + borrowrecord.getName()
					);
		}
		
		req.getRequestDispatcher("/jsp/manager/subBorrow.jsp").forward(req, resp);
		
	}
	
	private void showBorrowBySixId(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String sixId = req.getParameter("searchText").trim();
		System.out.println("身份证后6位为: " + sixId);
		
		//根据身份证号后6位查询预约借阅
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		List <BorrowRecord> list = borrowRecordDao.findBorrowBySixId(sixId);
		
		req.setAttribute("showAllBorrow", list);
		
		for(BorrowRecord borrowrecord : list){
			System.out.println(
					" bid : " + borrowrecord.getBid() +
					" 身份证号 : " + borrowrecord.getUidcard() +
					" 真实姓名 : " + borrowrecord.getRealname() +
					" ISBN : " + borrowrecord.getIsbn() +
					" 书名 : " + borrowrecord.getName() +
					" 借阅时间 : " + borrowrecord.getBorrowdate() +
					" 归还时间 : " + borrowrecord.getReturndate()
					);
		}
		
		req.getRequestDispatcher("/jsp/manager/returnOrRenew.jsp").forward(req, resp);
		
	}
	
	private void showAllBorrow(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//查询所有已借阅
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		List <BorrowRecord> list = borrowRecordDao.findAllBorrow();
		
		req.setAttribute("showAllBorrow", list);
		
		for(BorrowRecord borrowrecord : list){
			System.out.println(
					" bid : " + borrowrecord.getBid() +
					" 身份证号 : " + borrowrecord.getUidcard() +
					" 真实姓名 : " + borrowrecord.getRealname() +
					" ISBN : " + borrowrecord.getIsbn() +
					" 书名 : " + borrowrecord.getName() +
					" 借阅时间 : " + borrowrecord.getBorrowdate() +
					" 归还时间 : " + borrowrecord.getReturndate()
					);
		}
		
		req.getRequestDispatcher("/jsp/manager/returnOrRenew.jsp").forward(req, resp);
		
	}
	
	private void borrowSub(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//获取借阅bid
		int bid = Integer.parseInt(req.getParameter("bid"));
		System.out.println("借阅bid为 - - " + bid);
		
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		BorrowRecord borrowRecord = new BorrowRecord();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//设置借阅参数
		borrowRecord.setBid(bid);
		borrowRecord.setBorrowdate(date.format(System.currentTimeMillis()));
		
		//设置归还时间 在借阅时间上加30天
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 30);
		String newdate = date.format(c.getTime());
		borrowRecord.setReturndate(newdate);
		
		//预约借阅添加借阅记录
		boolean flag = borrowRecordDao.updateSubComplete(borrowRecord);
		if(flag) {
			//查询预约借阅后借阅信息
			List <BorrowRecord> list = borrowRecordDao.findByBid(bid);
			for(BorrowRecord borrowrecord : list){
				System.out.println(
						" 预约借阅确认后借阅信息 : " +
						" Bid : " + borrowrecord.getBid() +
						" ISBN : " + borrowrecord.getIsbn() +
						" Uidcard : " + borrowrecord.getUidcard() +
						" 借阅时间 : " + borrowrecord.getBorrowdate() +
						" 归还时间 : " + borrowrecord.getReturndate() +
						" 借出 : " + borrowrecord.getLoan() +
						" 现有 : " + borrowrecord.getExist()
						);
			}
			
			req.getRequestDispatcher("/manager/showAllSubComplete").forward(req, resp);
				
			} else {
				System.out.println("更新库存信息出错!");
			}
			
	}
	
	private void returnBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//获取借阅bid
		int bid = Integer.parseInt(req.getParameter("bid"));
		System.out.println("借阅bid为 - - " + bid);
		
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		BorrowRecord borrowRecord = new BorrowRecord();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//设置借阅参数
		borrowRecord.setBid(bid);
		borrowRecord.setRealreturndate(date.format(System.currentTimeMillis()));
		
		//System.out.println(" bid : " + bid + " realreturndate : " + date.format(new Date()));
		//还书添加实际归还时间及更新库存
		boolean flag = borrowRecordDao.updateReturn(borrowRecord);
		if(flag) {
			//归还书籍后借阅信息
			List <BorrowRecord> list = borrowRecordDao.findByReturn(bid);
			for(BorrowRecord borrowrecord : list){
				System.out.println(
						" 还书后借阅信息 : " +
						" Bid : " + borrowrecord.getBid() +
						" ISBN : " + borrowrecord.getIsbn() +
						" Uidcard : " + borrowrecord.getUidcard() +
						" 借阅时间 : " + borrowrecord.getBorrowdate() +
						" 预归还时间 : " + borrowrecord.getReturndate() +
						" 实际归还时间 : " + borrowrecord.getRealreturndate() +
						" 借出 : " + borrowrecord.getLoan() +
						" 现有 : " + borrowrecord.getExist()
						);
			}
			
			req.getRequestDispatcher("/manager/showAllBorrow").forward(req, resp);
				
			} else {
				System.out.println("还书更新借阅记录信息出错!");
			}
		
	}

	private void renewBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//获取管理员id
		HttpSession session = req.getSession();
		String mid = (String)session.getAttribute("managerMid");
		
		//获取借阅的信息
		int bid = Integer.parseInt(req.getParameter("borrowBid"));
		String isbn = req.getParameter("borrowIsbn");
		String uidcard = req.getParameter("borrowUidcard");
		
		IBorrowRecordDao borrowRecordDao = new BorrowRecordDaoImpl();
		BorrowRecord borrowRecord = new BorrowRecord();

		//还书
		//设置借阅参数
		borrowRecord.setBid(bid);
		borrowRecord.setRealreturndate(date.format(System.currentTimeMillis()));
		
		//还书添加实际归还时间及更新库存
		boolean returnFlag = borrowRecordDao.updateReturn(borrowRecord);
		if(returnFlag) {
			//还书后借阅记录
			List <BorrowRecord> returnBook = borrowRecordDao.findByReturn(bid);
			for(BorrowRecord returnbook : returnBook){
				System.out.println(
						" 还书后借阅信息 : " +
						" Bid : " + returnbook.getBid() +
						" ISBN : " + returnbook.getIsbn() +
						" Uidcard : " + returnbook.getUidcard() +
						" 借阅时间 : " + returnbook.getBorrowdate() +
						" 预归还时间 : " + returnbook.getReturndate() +
						" 实际归还时间 : " + returnbook.getRealreturndate() +
						" 借出 : " + returnbook.getLoan() +
						" 现有 : " + returnbook.getExist()
						);
			}
			
			//按照原来的isbn和uidcard再借阅
			//设置借阅参数
			borrowRecord.setIsbn(isbn);
			borrowRecord.setUidcard(uidcard);
			borrowRecord.setMid(mid);
			borrowRecord.setBorrowdate(date.format(System.currentTimeMillis()));
			
			//设置归还时间 在借阅时间上加30天
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 30);
			String newdate = date.format(c.getTime());
			borrowRecord.setReturndate(newdate);
			
			//续借添加借阅记录
			boolean renewflag = borrowRecordDao.add(borrowRecord);
			if(renewflag) {
				//完成借阅后更新库存信息
				boolean borFlag = borrowRecordDao.updateByBorrow(borrowRecord);
				if(borFlag) {
					List <BorrowRecord> borrowBook = borrowRecordDao.findByUidcard(uidcard);
					for(BorrowRecord borrowbook : borrowBook){
						System.out.println(
								" 更新库存后借阅信息 : " +
								" ISBN : " + borrowbook.getIsbn() +
								" Uidcard : " + borrowbook.getUidcard() +
								" 借阅时间 : " + borrowbook.getBorrowdate() +
								" 归还时间 : " + borrowbook.getReturndate() +
								" 实际归还时间 : " + borrowbook.getRealreturndate() +
								" 借出 : " + borrowbook.getLoan() +
								" 现有 : " + borrowbook.getExist() +
								" Mid : " + borrowbook.getMid()
								);
					}
					
					req.getRequestDispatcher("/manager/showAllBorrow").forward(req, resp);
					
				} else {
					System.out.println("续借更新库存信息出错!");
				}
				
			} else {
				System.out.println("续借借阅添加记录出错!");
			}
			
		} else {
			System.out.println("还书更新借阅记录出错!");
		}
		
	}

	private void addLibCard(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ParseException {

		//获取借书证信息
		String realname = req.getParameter("realname");
		String ugender = req.getParameter("ugender");
		String birth = req.getParameter("ubirth");
		String uphone = req.getParameter("uphone");
		String wechat = req.getParameter("wechat");
		String qq = req.getParameter("qq");
		String uaddress = req.getParameter("uaddress");
		String uidcard = req.getParameter("uidcard");
		
		System.out.println(" 借书证信息： " + 
				" realname： " + realname +
				" ugender： " + ugender +
				" ubirth： " + birth +
				" uphone： " + uphone +
				" wechat： " + wechat +
				" qq： " + qq +
				" uaddress： " + uaddress +
				" uidcard： " + uidcard 
				);
		
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		LibCardMess libCardMess = new LibCardMess();
		
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat birthdate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		d = birthdate.parse(birth);
		Date ubirth = new Date(d.getTime());
		
		//System.out.println(" newbirth : " + ubirth);
		//设置借书证参数
		libCardMess.setRealname(realname);
		libCardMess.setUgender(ugender);
		libCardMess.setUbirth(ubirth);
		libCardMess.setUphone(uphone);
		libCardMess.setWechat(wechat);
		libCardMess.setQq(qq);
		libCardMess.setUaddress(uaddress);
		libCardMess.setUidcard(uidcard);
		libCardMess.setApplydate(datetime.format(System.currentTimeMillis()));
		libCardMess.setProcess(1);
		libCardMess.setInuse(1);
		
		//现场办理借书证
		boolean flag = libCardMessDao.add(libCardMess);
		if(flag) {
			//添加完成后查询借书证信息
			List <LibCardMess> libCard = libCardMessDao.findAllByUidcard(uidcard);
			for(LibCardMess libcardmess : libCard){
				System.out.println(
						" 办理借书证成功 : " +
						" 真实姓名 : " + libcardmess.getRealname() +
						" 性别 : " + libcardmess.getUgender() +
						" 出生日期 : " + libcardmess.getUbirth() +
						" 联系方式 : " + libcardmess.getUphone() +
						" 微信 : " + libcardmess.getWechat() +
						" QQ : " + libcardmess.getQq() +
						" 家庭住址 : " + libcardmess.getUaddress() +
						" 身份证号码 : " + libcardmess.getUidcard() +
						" 照片 : " + libcardmess.getUphoto() +
						" 申请日期 : " + libcardmess.getApplydate() +
						" 处理日期 : " + libcardmess.getProcesstime() +
						" 处理情况 : " + libcardmess.getProcess() +
						" 使用情况 : " + libcardmess.getInuse()
						);
			}
				
				req.getRequestDispatcher("/manager/searchLibCard?searchText=" + uidcard).forward(req, resp);
			
		} else {
			System.out.println("添加借书证信息出错!");
		}
		
	}
	
	private void searchLibCard(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String uidcard = req.getParameter("searchText").trim();
		System.out.println("身份证为: " + uidcard);
		
		//根据身份证号后6位查询借书证信息
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		List <LibCardMess> libCard = libCardMessDao.findAllByUidcard(uidcard);
		
		req.setAttribute("libCardMess", libCard);
		
		for(LibCardMess libcardmess : libCard){
			System.out.println(
					" 真实姓名 : " + libcardmess.getRealname() +
					" 性别 : " + libcardmess.getUgender() +
					" 出生日期 : " + libcardmess.getUbirth() +
					" 联系方式 : " + libcardmess.getUphone() +
					" 身份证号码 : " + libcardmess.getUidcard() +
					" 申请日期 : " + libcardmess.getApplydate()
					);
		}
			
		req.getRequestDispatcher("/jsp/manager/searchLibCard.jsp").forward(req, resp);
		
	}
	
}
