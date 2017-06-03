<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-rootSearchBook</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllLib.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/rootSearchBook.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/rootSearchBook.js"></script>
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
		<div class="rootSearchBook">
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
									<th>添加时间</th>
									<th>位置</th>
									<th>总量</th>
									<th>借出</th>
									<th>现有</th>
									<th>操作</th>
								</tr>
							</table>
						</div>
						<div id="updateForm" class="updateForm_bg">
							<div class="updateForm_content">
								<form action="<%=request.getContextPath()%>/root/updateBook" method="post">
									<div class="content_text">
										<div class="content_textSpan">
											<p><span>I&nbsp;&nbsp;S&nbsp;&nbsp;B&nbsp;&nbsp;N：</span><input name ="isbn" id="isbn" type = "text" disabled="disabled" /><br /></p>
											<input name ="updateIsbn" id="updateIsbn" type = "text" style="display: none" />
											<p><span>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
												<select id="updateCategory" disabled="disabled">
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
											<p><span>书&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：</span><input id="updateName" type = "text" disabled="disabled" /><br /></p>
											<p><span>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：</span><input id="updateAuthor" type = "text" disabled="disabled" /><br /></p>
											<p><span>&nbsp;出&nbsp;版&nbsp;社：</span><input id="updatePublishment" type = "text" disabled="disabled" /><br /></p>
											<p><span>价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span><input id="updatePrice" type = "text" disabled="disabled" /><br /></p>
											<p><span>添加时间：</span><input id="updateAddtime" type = "text" disabled="disabled" /><br /></p>
											<p><span>位&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;置：</span><input class="place" name ="updatePlace" id="updatePlace" type = "text" /><br /></p>
											<p><span>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span><input class="total" name ="updateTotal" id="updateTotal" type = "text" /><br /></p>
											<p><span>借&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出：</span><input class="loan" name ="updateLoan" id="updateLoan" type = "text" /><br /></p>
											<p><span>现&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;有：</span><input class="exist" name ="updateExist" id="updateExist" type = "text" /><br /></p>
										</div>
									</div>
									<div class="btn_submit">
										<input type="button" value="确认修改" onclick="submit()" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
									<div class="btn_undo">
										<input type="button" value="取      消" onclick="document.getElementById('updateForm').style.display='none'" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
								</form>
							</div>
						</div>
						<div class="searchBook_content">
							<div class="items">
								<form action="" method="post">
									<table>
										<c:forEach items="${bookMess}" var="book">
											<tr>
												<td id="isbn" title="${book.isbn}">${book.isbn}</td>
												<td style="display: none">${book.cid}</td>
												<td align="center">${book.cname}</td>
												<td id="name" title="${book.name}">${book.name}</td>
												<td id="author" title="${book.author}">${book.author}</td>
												<td id="publishment" title="${book.publishment}">${book.publishment}</td>
												<td id="price" align="center">${book.price}</td>
												<td id="addtime" title="${book.addtime}">${book.addtime}</td>
												<td id="place" title="${book.place}">${book.place}</td>
												<td id="total" align="center">${book.total}</td>
												<td id="loan" align="center">${book.loan}</td>
												<td id="exist" align="center">${book.exist}</td>
												<td><input type="button" value="修  改" onclick="updateBookForm('${book.isbn}', '${book.cid}', '${book.name}', '${book.author}', '${book.publishment}', '${book.price}', '${book.addtime}', '${book.place}', '${book.total}', '${book.loan}', '${book.exist}')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
											</tr>
										</c:forEach>
									</table>
								</form>
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