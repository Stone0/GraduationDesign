<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-主页</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllSub.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/showAllSub.js"></script>
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
		<div class="showAllSub">
			<div class="head">
				<p>GoBook管理系统</p>
				<%-- <a class="identity" id="id" >${managerMid}</a> --%>
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
				<div class="refresh">
					<!-- <input type="button" onclick="window.location.reload()" /> -->
					<input type="button" onclick="window.location.href='<%=request.getContextPath()%>/manager/showAllSub'" onmouseover="refreshBtnOver(this)" onmouseout="refreshBtnOut(this)"/>
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
					<div class="subNoComplete">
						<div class="subNoComplete_title">
							<table>
								<tr>
									<th>真实姓名</th>
									<th>ISBN</th>
									<th>书名</th>
									<th>作者</th>
									<th>出版社</th>
									<th>位置</th>
									<th>预约时间</th>
									<th>库存</th>
									<th>操作</th>
								</tr>
							</table>
						</div>
						<div class="subNoComplete_content">
							<div class="items">
								<table>
									<c:forEach items="${subNoComplete}" var="snc">
										<tr>
											<td style="display: none">${snc.sid}</td>
											<td>${snc.realname}</td>
											<td style="display: none">${snc.uidcard}</td>
											<td title="${snc.isbn}">${snc.isbn}</td>
											<td title="${snc.name}">${snc.name}</td>
											<td title="${snc.author}">${snc.author}</td>
											<td title="${snc.publishment}">${snc.publishment}</td>
											<td title="${snc.place}">${snc.place}</td>
											<td title="${snc.subtime}">${snc.subtime}</td>
											<td align="center">${snc.exist}</td>
											<td><input type="submit" value="完成预约" onclick="alertSubComplete('确定完成预约?', '${snc.sid}', '${snc.isbn}', '${snc.uidcard}')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
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