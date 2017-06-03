<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-查询借书证</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllSub.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/searchLibCard.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/searchLibCard.js"></script>
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
		<div class="searchLibCard">
			<div class="head">
				<p onclick="window.location.href='<%=request.getContextPath()%>/manager/showAllSub'">GoBook管理系统</p>
				<span class="identity"></span>
				<a class="exit" href="<%=request.getContextPath()%>/manager/managerExit" onmouseover="exitBtnOver(this)" onmouseout="exitBtnOut(this)">exit</a>
				<div class="search">
					<form action="<%=request.getContextPath()%>/manager/searchLibCard" method="post">
						<div class="search_text">
							<input name="searchText" type="text" placeholder="身份证号后6位"/>
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
					<div class="libCardMess">
						<div class="libCardMess_title">
							<table>
								<tr>
									<th>真实姓名</th>
									<th>性别</th>
									<th>联系方式</th>
									<th>身份证号</th>
									<th>申请时间</th>
									<th>操作</th>
								</tr>
							</table>
						</div>
						<div class="libCardMess_content">
							<div class="items">
								<table>
									<c:forEach items="${libCardMess}" var="lcm">
										<tr>
											<td align="center">${lcm.realname}</td>
											<td align="center">${lcm.ugender}</td>
											<td title="${lcm.uphone}">${lcm.uphone}</td>
											<td title="${lcm.uidcard}">${lcm.uidcard}</td>
											<td title="${lcm.applydate}">${lcm.applydate}</td>
											<td><input type="button" value="查询借阅" onclick="window.location.href='<%=request.getContextPath()%>/manager/showBorrowBySixId?searchText=${lcm.uidcard}'" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
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