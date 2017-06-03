package com.stone0.gobook.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stone0.gobook.BookMess;
import com.stone0.gobook.LibCardMess;
import com.stone0.gobook.Manager;
import com.stone0.gobook.dao.IBookMessDao;
import com.stone0.gobook.dao.ILibCardMessDao;
import com.stone0.gobook.dao.IManagerDao;
import com.stone0.gobook.dao.impl.BookMessDaoImpl;
import com.stone0.gobook.dao.impl.LibCardMessDaoImpl;
import com.stone0.gobook.dao.impl.ManagerDaoImpl;

public class RootController extends HttpServlet {
	
	private static final long serialVersionUID = -1365548659440957664L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		//获取项目的绝对地址
		String uri =req.getRequestURI();
		System.out.println(uri + " - - RootController - -");
		
		if(uri.endsWith("showAllLib")){//前往借书证主页，显示所有申请的借书证
			showAllLib(req,resp);
		}else if(uri.endsWith("libProcess")){//验证借书证完成
			libProcess(req,resp);
		}else if(uri.endsWith("searchBook")){//查询书籍
			searchBook(req,resp);
		}else if(uri.endsWith("addBook")){//添加书籍
			addBook(req,resp);
		}else if(uri.endsWith("updateBook")){//修改书籍信息
			updateBook(req,resp);
		}else if(uri.endsWith("searchManager")){//查询员工信息
			searchManager(req,resp);
		}else if(uri.endsWith("addManager")){//添加管理员
			try {
				addManager(req,resp);
			} catch (ParseException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}else if(uri.endsWith("cancelManager")){//注销管理员
			cancelManager(req,resp);
		}else if(uri.endsWith("updateManager")){//修改员工信息
			updateManager(req,resp);
		}else if(uri.endsWith("searchLibCard")){//查询借书证
			searchLibCard(req,resp);
		}else if(uri.endsWith("cancelLibCard")){//注销借书证
			cancelLibCard(req,resp);
		}else if(uri.endsWith("updateLibCard")){//修改借书证信息
			updateLibCard(req,resp);
		}
		
	}

	private void showAllLib(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String mid = (String)session.getAttribute("managerMid");
		session.setAttribute("managerMid", mid);
		
		ILibCardMessDao libCardDao = new LibCardMessDaoImpl();
		List <LibCardMess> list = libCardDao.findAllNotProcess();
		
		for(LibCardMess libcardmess : list){
			System.out.println(
					" 昵称 : " + libcardmess.getNickname() +
					" 真实姓名 : " + libcardmess.getRealname() +
					" 性别 : " + libcardmess.getUgender() +
					" 出生日期 : " + libcardmess.getUbirth() +
					" 联系方式 : " + libcardmess.getUphone() +
					" 微信 : " + libcardmess.getWechat() +
					" QQ : " + libcardmess.getQq() +
					" 家庭住址 : " + libcardmess.getUaddress() +
					" 身份证号 : " + libcardmess.getUidcard() +
					" 申请时间 : " + libcardmess.getApplydate() +
					" 验证时间 : " + libcardmess.getProcesstime() +
					" 验证结果 : " + libcardmess.getProcess()
					);
		}
		
		req.setAttribute("libNoProcess", list);
		req.getRequestDispatcher("/jsp/root/showAllLib.jsp").forward(req, resp);
		
	}

	private void libProcess(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String uidcard = req.getParameter("uidcard").trim();
		System.out.println("借书证验证的人身份证号是：" + uidcard);
		
		//验证通过更新借书证信息
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		LibCardMess libcardmess = new LibCardMess();
		libcardmess.setProcesstime(date.format(System.currentTimeMillis()));
		libcardmess.setUidcard(uidcard);
		
		boolean flag = libCardMessDao.updateProcess(libcardmess);
		if(flag) {
			List <LibCardMess> list  = libCardMessDao.findAllByUidcard(uidcard);
			for(LibCardMess libCardMess : list){
				System.out.println(
						" 验证后的借书证信息 : " +
						" 真实姓名 : " + libCardMess.getRealname() +
						" 性别 : " + libCardMess.getUgender() +
						" 出生日期 : " + libCardMess.getUbirth() +
						" 联系方式 : " + libCardMess.getUphone() +
						" 微信 : " + libCardMess.getWechat() +
						" QQ : " + libCardMess.getQq() +
						" 家庭住址 : " + libCardMess.getUaddress() +
						" 身份证号 : " + libCardMess.getUidcard() +
						" 申请时间 : " + libCardMess.getApplydate() +
						" 验证时间 : " + libCardMess.getProcesstime() +
						" 验证结果 : " + libCardMess.getProcess() +
						" 使用情况 : " + libCardMess.getInuse()
						);
			}
			
			req.getRequestDispatcher("/root/showAllLib").forward(req, resp);
			
		} else {
			System.out.println("验证失败!");
		}
		
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
					" 添加时间 : " + bookMess.getAddtime() +
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
					" 添加时间 : " + bookMess.getAddtime() +
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
					" 添加时间 : " + bookMess.getAddtime() +
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
			
			req.setAttribute("bookMess", allByINA);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
			
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
			
			req.setAttribute("bookMess", allByNA);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);

		} else if (bookIsbn.size() == 0 && bookName.size() == 0 && bookAuthor.size() != 0) {
			System.out.println(" 001 ");

			//author就是结果
			
			req.setAttribute("bookMess", bookAuthor);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() == 0 && bookName.size() != 0 && bookAuthor.size() == 0) {
			System.out.println(" 010 ");
			
			//name就是结果
			
			req.setAttribute("bookMess", bookName);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
			
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
			
			req.setAttribute("bookMess", allByIA);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
			
		} else if (bookIsbn.size() != 0 && bookName.size() == 0 && bookAuthor.size() == 0) {
			System.out.println(" 100 ");
			
			req.setAttribute("bookMess", bookIsbn);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
			
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
			
			req.setAttribute("bookMess", allByIN);
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
			
		} else {
			System.out.println(" 000 ");
			req.getRequestDispatcher("/jsp/root/searchBook.jsp").forward(req, resp);
		}
		
	}

	private void addBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//获取书籍的信息
		String isbn = req.getParameter("isbn");
		int cid = Integer.parseInt(req.getParameter("cid"));
		String name = req.getParameter("name");
		String author = req.getParameter("author");
		String publishment = req.getParameter("publishment");
		String price = req.getParameter("price");
		String place = req.getParameter("place");
		int total = Integer.parseInt(req.getParameter("total"));
		
		System.out.println(" 书籍信息： " + 
				" ISBN： " + isbn +
				" cid： " + cid +
				" name： " + name +
				" author： " + author +
				" publishment： " + publishment +
				" price： " + price +
				" place： " + place +
				" total： " + total 
				);
		
		IBookMessDao bookMessDao = new BookMessDaoImpl();
		BookMess bookMess = new BookMess();
		
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//设置书籍参数
		bookMess.setIsbn(isbn);
		bookMess.setName(name);
		bookMess.setAuthor(author);
		bookMess.setPublishment(publishment);
		bookMess.setPrice(price);
		bookMess.setAddtime(date.format(System.currentTimeMillis()));
		bookMess.setPlace(place);
		bookMess.setCid(cid);
		bookMess.setTotal(total);
		bookMess.setExist(total);

		//新书入库添加书籍信息
		boolean flag = bookMessDao.add(bookMess);
		if(flag) {
			//添加完成后查询书籍信息
			List <BookMess> list = bookMessDao.findByIsbn(isbn);
			for(BookMess bookmess : list){
				System.out.println(
						" 添加书籍成功 : " +
						" ISBN : " + bookmess.getIsbn() +
						" 类别id : " + bookmess.getCid() +
						" 类别名称 : " + bookmess.getCname() +
						" 书名 : " + bookmess.getName() +
						" 作者 : " + bookmess.getAuthor() +
						" 出版社 : " + bookmess.getPublishment() +
						" 价格 : " + bookmess.getPrice() +
						" 入库时间 : " + bookmess.getAddtime() +
						" 书籍位置 : " + bookmess.getPlace() +
						" 总量 : " + bookmess.getTotal() +
						" 借出 : " + bookmess.getLoan() +
						" 现有 : " + bookmess.getExist()
						);
			}
				
				req.getRequestDispatcher("/root/searchBook?searchText=" + isbn).forward(req, resp);
			
		} else {
			System.out.println("添加书籍出错!");
		}
		
	}

	private void updateBook(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String updateIsbn = req.getParameter("updateIsbn").trim();
		String updatePlace = req.getParameter("updatePlace").trim();
		int updateTotal = Integer.parseInt(req.getParameter("updateTotal").trim());
		int updateLoan = Integer.parseInt(req.getParameter("updateLoan").trim());
		int updateExist = Integer.parseInt(req.getParameter("updateExist").trim());
		
		System.out.println("获取更改的数据为: " +
				" 修改的ISBN: " + updateIsbn +
				" 新位置: " + updatePlace +
				" 新总量: " + updateTotal +
				" 新借出: " + updateLoan +
				" 新现有: " + updateExist
				);
		
		IBookMessDao bookMessDao = new BookMessDaoImpl();
		
		BookMess bookmess = new BookMess();
		bookmess.setIsbn(updateIsbn);
		bookmess.setPlace(updatePlace);
		bookmess.setTotal(updateTotal);
		bookmess.setLoan(updateLoan);
		bookmess.setExist(updateExist);
		
		//修改书籍信息
		boolean flag = bookMessDao.update(bookmess);
		if(flag) {
			List <BookMess> newBookMess = bookMessDao.findByIsbn(updateIsbn);
			for(BookMess bookMess : newBookMess){
				System.out.println(
						" 修改后的书籍信息 : " +
						" ISBN : " + bookMess.getIsbn() +
						" 分类 : " + bookMess.getCname() +
						" 位置 : " + bookMess.getPlace() +
						" 总量 : " + bookMess.getTotal() +
						" 借出 : " + bookMess.getLoan() +
						" 现有 : " + bookMess.getExist()
						);
			}
			
			req.getRequestDispatcher("/root/searchBook?searchText=" + updateIsbn).forward(req, resp);
			
		} else {
			System.out.println("修改书籍信息出错!");
		}
		
	}

	private void searchManager(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String mid = req.getParameter("searchText").trim();
		
		//查询所有借书证
		IManagerDao managerDao = new ManagerDaoImpl();
		List <Manager> list = managerDao.findByMid(mid);
		
		req.setAttribute("searchManager", list);
		
		for(Manager managerMess : list){
			System.out.println(
					" mid : " + managerMess.getMid() +
					" 姓名 : " + managerMess.getMname() +
					" 性别 : " + managerMess.getMgender() +
					" 出生日期 : " + managerMess.getMbirth() +
					" 联系方式 : " + managerMess.getMphone() +
					" 身份证号码 : " + managerMess.getMidcard() +
					" 家庭住址 : " + managerMess.getMaddress() +
					" 入职时间 : " + managerMess.getHiredate() +
					" 使用情况 : " + managerMess.getInuse()
					);
		}
		
		req.getRequestDispatcher("/jsp/root/searchManager.jsp").forward(req, resp);
		
	}

	private void addManager(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ParseException {
		
		//获取管理员信息
		String mid = req.getParameter("mid");
		String mname = req.getParameter("mname");
		String mpsw = req.getParameter("mid");
		String mgender = req.getParameter("mgender");
		String birth = req.getParameter("mbirth");
		String mphone = req.getParameter("mphone");
		String midcard = req.getParameter("midcard");
		String maddress = req.getParameter("maddress");
		
		System.out.println(" 管理员信息： " + 
				" mid： " + mid +
				" mname： " + mname +
				" mpsw： " + mpsw +
				" mgender： " + mgender +
				" birth： " + birth +
				" mphone： " + mphone +
				" midcard： " + midcard +
				" maddress： " + maddress 
				);
		
		IManagerDao managerDao = new ManagerDaoImpl();
		Manager manager = new Manager();
		
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat birthdate = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = null;
		d = birthdate.parse(birth);
		Date mbirth = new Date(d.getTime());
		
		//设置管理员参数
		manager.setMid(mid);
		manager.setMname(mname);
		manager.setMpsw(mpsw);
		manager.setMgender(mgender);
		manager.setMbirth(mbirth);
		manager.setMphone(mphone);
		manager.setMidcard(midcard);
		manager.setMaddress(maddress);
		manager.setHiredate(datetime.format(System.currentTimeMillis()));
		manager.setInuse(1);
		
		//添加管理员信息
		boolean flag = managerDao.add(manager);
		if(flag) {
			//添加完成后查询管理员信息
			List <Manager> list = managerDao.findByMid(mid);
			for(Manager managerMess : list){
				System.out.println(
						" 新增管理员成功 : " +
						" mid : " + managerMess.getMid() +
						" 姓名 : " + managerMess.getMname() +
						" 性别 : " + managerMess.getMgender() +
						" 出生日期 : " + managerMess.getMbirth() +
						" 联系方式 : " + managerMess.getMphone() +
						" 身份证号码 : " + managerMess.getMidcard() +
						" 家庭住址 : " + managerMess.getMaddress() +
						" 入职时间 : " + managerMess.getHiredate() +
						" 使用情况 : " + managerMess.getInuse()
						);
			}
				
			req.getRequestDispatcher("/root/searchManager?searchText=" + mid).forward(req, resp);
			
		} else {
			System.out.println("添加管理员信息出错!");
		}
				
	}

	private void cancelManager(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String mid = req.getParameter("mid").trim();
		
		IManagerDao managerDao = new ManagerDaoImpl();
		
		boolean flag = managerDao.cancel(mid);
		if(flag) {
			List <Manager> list = managerDao.findByMid(mid);
			for(Manager managerMess : list){
				System.out.println(
						" 注销管理员成功 : " +
						" mid : " + managerMess.getMid() +
						" 姓名 : " + managerMess.getMname() +
						" 性别 : " + managerMess.getMgender() +
						" 出生日期 : " + managerMess.getMbirth() +
						" 联系方式 : " + managerMess.getMphone() +
						" 身份证号码 : " + managerMess.getMidcard() +
						" 家庭住址 : " + managerMess.getMaddress() +
						" 入职时间 : " + managerMess.getHiredate() +
						" 使用情况 : " + managerMess.getInuse()
						);
			}
				
			req.getRequestDispatcher("/root/searchManager?searchText=").forward(req, resp);
			
		} else {
			System.out.println("注销管理员出错!");
		}
		
	}

	private void updateManager(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String mid = req.getParameter("mid").trim();
		String updateMphone = req.getParameter("updateMphone").trim();
		String updateMaddress = req.getParameter("updateMaddress").trim();
		
		System.out.println("获取更改的数据为: " +
				" 修改的mid为 : " + mid +
				" 新联系方式 : " + updateMphone +
				" 新地址 : " + updateMaddress
				);
		
		IManagerDao managerDao = new ManagerDaoImpl();
		Manager manager = new Manager();
		
		manager.setMid(mid);
		manager.setMphone(updateMphone);
		manager.setMaddress(updateMaddress);
		
		//修改管理员信息
		boolean flag = managerDao.update(manager);
		if(flag) {
			List <Manager> list = managerDao.findByMid(mid);
			for(Manager managerMess : list){
				System.out.println(
						" 修改管理员成功 : " +
						" mid : " + managerMess.getMid() +
						" 姓名 : " + managerMess.getMname() +
						" 性别 : " + managerMess.getMgender() +
						" 出生日期 : " + managerMess.getMbirth() +
						" 联系方式 : " + managerMess.getMphone() +
						" 身份证号码 : " + managerMess.getMidcard() +
						" 家庭住址 : " + managerMess.getMaddress() +
						" 入职时间 : " + managerMess.getHiredate() +
						" 使用情况 : " + managerMess.getInuse()
						);
			}
			
			req.getRequestDispatcher("/root/searchManager?searchText=" + mid).forward(req, resp);
			
		} else {
			System.out.println("修改管理员信息出错!");
		}
		
	}

	private void searchLibCard(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String uidcard = req.getParameter("searchText");
		System.out.println(" searchText为 : " + uidcard);
		
		//查询所有借书证
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		List <LibCardMess> list = libCardMessDao.findAllByUidcard(uidcard);
		
		req.setAttribute("showAllLibCard", list);
		
		for(LibCardMess libcardmess : list){
			System.out.println(
					" 真实姓名 : " + libcardmess.getRealname() +
					" 性别 : " + libcardmess.getUgender() +
					" 出生日期 : " + libcardmess.getUbirth() +
					" 联系方式 : " + libcardmess.getUphone() +
					" 微信 : " + libcardmess.getWechat() +
					" QQ : " + libcardmess.getQq() +
					" 家庭住址 : " + libcardmess.getUaddress() +
					" 身份证号码 : " + libcardmess.getUidcard() +
					" 申请日期 : " + libcardmess.getApplydate() +
					" 验证日期 : " + libcardmess.getProcesstime() +
					" 验证情况 : " + libcardmess.getProcess() +
					" 使用情况 : " + libcardmess.getInuse()
					);
		}
		
		req.getRequestDispatcher("/jsp/root/searchLibCard.jsp").forward(req, resp);
		
	}

	private void cancelLibCard(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uidcard = req.getParameter("uidcard").trim();
		System.out.println(" uidcard 为 : " + uidcard);
		
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		
		boolean flag = libCardMessDao.cancel(uidcard);
		if(flag) {
			List <LibCardMess> list = libCardMessDao.findAllByUidcard(uidcard);
			for(LibCardMess libcardMess : list){
				System.out.println(
						" 注销后的借书证信息 : " +
						" 真实姓名 : " + libcardMess.getRealname() +
						" 性别 : " + libcardMess.getUgender() +
						" 出生日期 : " + libcardMess.getUbirth() +
						" 联系方式 : " + libcardMess.getUphone() +
						" 微信 : " + libcardMess.getWechat() +
						" QQ : " + libcardMess.getQq() +
						" 家庭住址 : " + libcardMess.getUaddress() +
						" 身份证号码 : " + libcardMess.getUidcard() +
						" 申请日期 : " + libcardMess.getApplydate() +
						" 验证日期 : " + libcardMess.getProcesstime() +
						" 验证情况 : " + libcardMess.getProcess() +
						" 使用情况 : " + libcardMess.getInuse()
						);
			}
			
			req.getRequestDispatcher("/root/searchLibCard?searchText=" + uidcard).forward(req, resp);
			
		} else {
			System.out.println("注销借书证信息出错!");
		}
		
	}

	private void updateLibCard(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uidcard = req.getParameter("uidcard").trim();
		String updateUphone = req.getParameter("updateUphone");
		String updateWechat = req.getParameter("updateWechat").trim();
		String updateQq = req.getParameter("updateQq").trim();
		String updateUaddress = req.getParameter("updateUaddress").trim();
		
		System.out.println("获取更改的数据为: " +
				" 修改的uidcard: " + uidcard +
				" 新联系方式: " + updateUphone +
				" 新微信: " + updateWechat +
				" 新QQ: " + updateQq +
				" 新地址: " + updateUaddress
				);
		
		ILibCardMessDao libCardMessDao = new LibCardMessDaoImpl();
		LibCardMess libcardmess = new LibCardMess();
		
		libcardmess.setUphone(updateUphone);
		libcardmess.setWechat(updateWechat);
		libcardmess.setQq(updateQq);
		libcardmess.setUaddress(updateUaddress);
		libcardmess.setUidcard(uidcard);
		
		//修改借书证信息
		boolean flag = libCardMessDao.update(libcardmess);
		if(flag) {
			List <LibCardMess> list = libCardMessDao.findAllByUidcard(uidcard);
			for(LibCardMess libcardMess : list){
				System.out.println(
						" 修改后的借书证信息 : " +
						" 真实姓名 : " + libcardMess.getRealname() +
						" 性别 : " + libcardMess.getUgender() +
						" 出生日期 : " + libcardMess.getUbirth() +
						" 联系方式 : " + libcardMess.getUphone() +
						" 微信 : " + libcardMess.getWechat() +
						" QQ : " + libcardMess.getQq() +
						" 家庭住址 : " + libcardMess.getUaddress() +
						" 身份证号码 : " + libcardMess.getUidcard() +
						" 申请日期 : " + libcardMess.getApplydate() +
						" 验证日期 : " + libcardMess.getProcesstime() +
						" 验证情况 : " + libcardMess.getProcess() +
						" 使用情况 : " + libcardMess.getInuse()
						);
			}
			
			req.getRequestDispatcher("/root/searchLibCard?searchText=" + uidcard).forward(req, resp);
			
		} else {
			System.out.println("修改借书证信息出错!");
		}
		
	}
	
	
}
