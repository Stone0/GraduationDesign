<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-还书/续借</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllSub.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/returnOrRenew.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/returnOrRenew.js"></script>
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
		<div class="returnOrRenew">
			<div class="head">
				<p onclick="window.location.href='<%=request.getContextPath()%>/manager/showAllSub'">GoBook管理系统</p>
				<span class="identity"></span>
				<a class="exit" href="<%=request.getContextPath()%>/manager/managerExit" onmouseover="exitBtnOver(this)" onmouseout="exitBtnOut(this)">exit</a>
				<div class="search">
					<form action="<%=request.getContextPath()%>/manager/showBorrowBySixId" method="post">
						<div class="search_text">
							<input name="searchText" type="text" placeholder="借阅者身份证后6位"/>
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
					<div class="showAllBorrow">
						<div class="showAllBorrow_title">
							<table>
								<tr>
									<th>真实姓名</th>
									<th>身份证号</th>
									<th>ISBN</th>
									<th>书名</th>
									<th>借阅时间</th> 
									<th>预归还时间</th>
									<th align="right">操</th>
									<th align="left">作</th>
								</tr>
							</table>
						</div>
						<div class="showAllBorrow_content">
							<div class="items">
								<table>
									<c:forEach items="${showAllBorrow}" var="sab">
										<tr>
											<td style="display: none">${sab.bid}</td>
											<td>${sab.realname}</td>
											<td title="${sab.uidcard}">${sab.uidcard}</td>
											<td title="${sab.isbn}">${sab.isbn}</td>
											<td title="${sab.name}">${sab.name}</td>
											<td title="${sab.borrowdate}">${sab.borrowdate}</td>
											<td title="${sab.returndate}">${sab.returndate}</td>
											<td><input type="submit" value="归还" onclick="alertReturn('确定归还?', '${sab.bid}')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
											<td><input type="submit" value="续借" onclick="alertRenew('确定续借?', '${sab.bid}', '${sab.uidcard}', '${sab.isbn}')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
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