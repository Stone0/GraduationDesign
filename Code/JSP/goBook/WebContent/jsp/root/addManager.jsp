<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-添加管理员</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllLib.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/addManager.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript">
    			function login(){
    				$.ajax({
    					url:"<%=request.getContextPath() %>/manager/sessionManager",
    					type:"get",
    					data: null,
    					success:function(resp,status,xhr){
    						if(resp=="1"){
    							$(".identity").html("${managerMid}");
    						}else{
    							alertLogin("请先登录!");
    						}
    					},
    					error:function(){
    						alertLogin("请重启项目服务器!");
    					}
    				});		
    			}// end login
		    	window.onload=login;
    	</script>
	</head>
	<body>
		<div class="addManager">
			<div class="head">
				<p onclick="window.location.href='<%=request.getContextPath()%>/root/showAllLib'">GoBook管理系统</p>
				<span class="identity"></span>
				<a class="exit" href="<%=request.getContextPath()%>/manager/managerExit" onmouseover="exitBtnOver(this)" onmouseout="exitBtnOut(this)">exit</a>
				<div class="search">
					<form action="<%=request.getContextPath()%>/root/searchManager" method="post">
						<div class="search_text">
							<input name="searchText" type="text" placeholder="管理员编号"/>
						</div>
						<div class="search_btn">
							<input type="button" onclick="submit()" onmouseover="searchBtnOver(this)" onmouseout="searchBtnOut(this)"/>
						</div>
					</form>
				</div>
			</div>
			<div class="body">
				<p>BODY</p>
				<div class="body_left">
					<div class="body_left_main">
						<div class="menu_title">
							<span>菜单</span>
						</div>
						<div class="leftBoo">
							<button class="booBtn">书籍管理</button>
							<div class="booContent">
								<a href="<%=request.getContextPath()%>/jsp/root/addBook.jsp">添加书籍</a>
							</div>
						</div>
						<div class="leftMan">
							<button class="manBtn">员工管理</button>
							<div class="manContent">
								<a href="<%=request.getContextPath()%>/jsp/root/addManager.jsp">添加管理员</a>
							</div>
						</div>
						<div class="leftLib">
							<button class="libBtn">借书证管理</button>
							<div class="libContent">
								<a href="<%=request.getContextPath()%>/root/searchLibCard?searchText=">查询借书证</a>
							</div>
						</div>
					</div>
				</div>
				<div class="body_main">
					<div class="newManager">
						<div class="newManager_title">
							<span>管理员入职</span>
						</div>
						<div class="newManagerForm">
							<form action="<%=request.getContextPath()%>/root/addManager" method="post">
								<div class="content_text">
									<div class="content_textSpan">
										<p><span>编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：</span><input name ="mid" type = "text" /><br /></p>
										<p><span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input name ="mname" type = "text" /><br /></p>
										<p><span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
											<select name="mgender">
											  <option value="男">男</option>
											  <option value="女">女</option>
											</select>
										</p>
										<p><span>出生日期：</span><input name ="mbirth" type = "text" value="2016-01-01" /><br /></p>
										<p><span>联系方式：</span><input name ="mphone" type = "text" /><br /></p>
										<p><span>身份证号：</span><input name ="midcard" type = "text" /><br /></p>
										<p><span>家庭住址：</span><input name ="maddress" type = "text" /><br /></p>
									</div>
								</div>
								<div class="btn">
									<div class="btn_submit">
									<input type="button" value="确认添加" onclick="submit()" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
									<div class="btn_reset">
									<input type="reset" value="重       置" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="footer">
				<p>- Nothing -</p>
			</div>
		</div>
	</body>
</html>