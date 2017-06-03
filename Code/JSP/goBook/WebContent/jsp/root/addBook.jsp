<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-添加书籍</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllLib.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/addBook.css">
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
		<div class="addBook">
			<div class="head">
				<p onclick="window.location.href='<%=request.getContextPath()%>/root/showAllLib'">GoBook管理系统</p>
				<span class="identity"></span>
				<a class="exit" href="<%=request.getContextPath()%>/manager/managerExit" onmouseover="exitBtnOver(this)" onmouseout="exitBtnOut(this)">exit</a>
				<div class="search">
					<form action="<%=request.getContextPath()%>/root/searchBook" method="post">
						<div class="search_text">
							<input name="searchText" type="text" placeholder="ISBN/书名/作者"/>
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
					<div class="newBook">
						<div class="newBook_title">
							<span>新书入库</span>
						</div>
						<div class="newBookForm">
							<form action="<%=request.getContextPath()%>/root/addBook" method="post">
								<div class="content_text">
									<div class="content_textSpan">
										<p><span>I&nbsp;&nbsp;S&nbsp;&nbsp;B&nbsp;&nbsp;N：</span><input name ="isbn" type = "text" /><br /></p>
										<p><span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
											<select name="cid">
											  <option value="1">计算机</option>
											  <option value="2">外语</option>
											  <option value="3">文学</option>
											  <option value="4">艺术</option>
											  <option value="5">经管</option>
											  <option value="6">历史</option>
											  <option value="7">数学</option>
											  <option value="8">农业</option>
											  <option value="9">医学</option>
											  <option value="10">建筑</option>
											</select>
										</p>
										<p><span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input name ="name" type = "text" /><br /></p>
										<p><span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span><input name ="author" type = "text" /><br /></p>
										<p><span>&nbsp;出&nbsp;版&nbsp;社：</span><input name ="publishment" type = "text" /><br /></p>
										<p><span>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span><input name ="price" type = "text" /><br /></p>
										<p><span>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</span><input name ="place" type = "text" /><br /></p>
										<p><span>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span><input name ="total" type = "text" /><br /></p>
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