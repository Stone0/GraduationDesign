<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-查询书籍</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllSub.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/managerSearchBook.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/managerSearchBook.js"></script>
		<script type="text/javascript">
    			function login(){
    				$.ajax({
    					url:"<%=request.getContextPath() %>/manager/sessionManager",
    					type:"get",
    					data: null,
    					success:function(resp,status,xhr){
    						if(resp=="1"){
    							$(".identity").html("${managerMid}");
    							//$(".identity").html("M201704057001");
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
		<div class="showAllBook">
			<div class="head">
				<p onclick="window.location.href='<%=request.getContextPath()%>/manager/showAllSub'">GoBook管理系统</p>
				<span class="identity"></span>
				<a class="exit" href="<%=request.getContextPath()%>/manager/managerExit" onmouseover="exitBtnOver(this)" onmouseout="exitBtnOut(this)">exit</a>
				<div class="search">
					<form action="<%=request.getContextPath()%>/manager/searchBook" method="post">
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
						<div class="leftBor">
							<button class="borBtn">借阅服务</button>
							<div class="borContent">
								<a href="<%=request.getContextPath()%>/manager/showAllSubComplete">预约借阅</a>
								<a href="<%=request.getContextPath()%>/manager/showAllBorrow">还书/续借</a>
							</div>
						</div>
						<div class="leftCar">
							<button class="carBtn">借书证服务</button>
							<div class="carContent">
								<a href="<%=request.getContextPath()%>/jsp/manager/addLibCard.jsp">办理借书证</a>
							</div>
						</div>
					</div>
				</div>
				<div class="body_main">
					<div class="searchBook">
						<div class="searchBook_title">
							<table>
								<tr>
									<th>ISBN</th>
									<th>类别</th>
									<th>书名</th>
									<th>作者</th>
									<th>出版社</th>
									<th>价格</th>
									<th>位置</th>
									<th>库存总量</th>
									<th>借出</th>
									<th>现有</th>
									<th>操作</th>
								</tr>
							</table>
						</div>
						<div id="nowBorrowForm" class="nowBorrowForm_bg">
							<div class="nowBorrowForm_content">
								<form action="<%=request.getContextPath()%>/manager/borrowNow" method="post">
									<div class="content_text">
										<div class="content_textSpan">
											<p><span>I&nbsp;&nbsp;S&nbsp;&nbsp;B&nbsp;&nbsp;N：</span><input id="isbn" type = "text" disabled="disabled"/><br /></p>
											<input name ="borrowIsbn" id="borrowIsbn" type = "text" style="display: none" />
											<p><span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input id="name" type = "text" disabled="disabled"/><br /></p>
											<input id="borrowName" type = "text" style="display: none" />
											<p><span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span><input id="author" type = "text" disabled="disabled"/><br /></p>
											<input id="borrowAuthor" type = "text" style="display: none" />
											<p><span>&nbsp;出&nbsp;版&nbsp;社：</span><input id="publishment" type = "text" disabled="disabled"/><br /></p>
											<input id="borrowPublishment" type = "text" style="display: none" />
											<p><span>现&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有：</span><input id="exist" type = "text" disabled="disabled"/><br /></p>
											<input id="borrowExist" type = "text" style="display: none" />
											<p id="uidcard"><span>身份证号码：</span><input name ="borrowUidcard" type = "text" placeholder="身份证号码后6位" /><br /></p>
										</div>
									</div>
									<div class="btn_submit">
										<input type="button" value="确认借阅" onclick="submit()" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
									<div class="btn_undo">
										<input type="button" value="取      消" onclick="document.getElementById('nowBorrowForm').style.display='none'" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
								</form>
							</div>
						</div>
						<div class="searchBook_content">
							<div class="items">
								<table>
									<c:forEach items="${bookMess}" var="book">
										<tr>
											<td title="${book.isbn}">${book.isbn}</td>
											<td style="display: none">${book.cid}</td>
											<td align="center">${book.cname}</td>
											<td title="${book.name}">${book.name}</td>
											<td title="${book.author}">${book.author}</td>
											<td title="${book.publishment}">${book.publishment}</td>
											<td align="center">${book.price}</td>
											<td title="${book.place}">${book.place}</td>
											<td align="center">${book.total}</td>
											<td align="center">${book.loan}</td>
											<td align="center">${book.exist}</td>
											<td><input type="submit" value="借  阅" onclick="nowBorrowForm('${book.isbn}', '${book.name}', '${book.author}', '${book.publishment}', '${book.exist}')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
										</tr>
										</c:forEach>
									</table>
							</div>
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