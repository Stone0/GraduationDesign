package com.stone0.gobook.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stone0.gobook.LibCardMess;
import com.stone0.gobook.SubscribeRecord;
import com.stone0.gobook.dao.ILibCardMessDao;
import com.stone0.gobook.dao.IManagerDao;
import com.stone0.gobook.dao.ISubscribeRecordDao;
import com.stone0.gobook.dao.impl.LibCardMessDaoImpl;
import com.stone0.gobook.dao.impl.ManagerDaoImpl;
import com.stone0.gobook.dao.impl.SubscribeRecordDaoImpl;

public class LoginController extends HttpServlet{
	
	private static final long serialVersionUID = -5765372941823875807L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//req.getRequestDispatcher("../jsp/login.jsp").forward(req, resp); 带结果请求转发 不用路径
		//resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp"); 重定向 需要路径
		
		String uri = req.getRequestURI();
		System.out.println(uri + " - - LoginController - - ");
		
		/**
		 * 获取数据
		 */
		String mid = req.getParameter("mid");
		String psw = req.getParameter("psw");
		
		IManagerDao managerDao = new ManagerDaoImpl();
		boolean flag = managerDao.login(mid, psw);
		
		PrintWriter writer = resp.getWriter();
		
		HttpSession session = req.getSession();
		if(flag) {
			if(mid.equals("root")) {
				ILibCardMessDao libCardDao = new LibCardMessDaoImpl();
				List <LibCardMess> list = libCardDao.findAllNotProcess();
				session.setAttribute("managerMid", mid);
				System.out.println("session的mid = " + mid);
				session.setAttribute("libCardNoProcess", list);
				writer.write("2");
			} else {
				ISubscribeRecordDao subRecordDao = new SubscribeRecordDaoImpl();
				List <SubscribeRecord> list = subRecordDao.findAllNotComplete();
				session.setAttribute("managerMid", mid);
				System.out.println("session的mid = " + mid);
				session.setAttribute("subNoComplete", list);
				writer.write("1");
			}
		}else {
			writer.write("0");
		}
		writer.flush();
		writer.close();
	}
	
}
