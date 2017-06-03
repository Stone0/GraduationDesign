<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-办理借书证</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllSub.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/addLibCard.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/addLibCard.js"></script>
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
		<div class="addLibCard">
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
					<div class="libCard">
						<div class="libCard_title">
							<span>办理借书证</span>
						</div>
						<div class="libCardForm">
							<form action="<%=request.getContextPath()%>/manager/addLibCard" method="post">
								<div class="content_text">
									<div class="content_textSpan">
										<p><span>真实姓名：</span><input name ="realname" type = "text" /><br /></p>
										<p><span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
											<select name="ugender">
											  <option value="男">男</option>
											  <option value="女">女</option>
											</select>
										</p>
										<p><span>出生日期：</span><input name ="ubirth" type = "text" value="2016-01-01" /><br /></p>
										<p><span>联系方式：</span><input name ="uphone" type = "text" /><br /></p>
										<p><span>微信：</span><input name ="wechat" type = "text" /><br /></p>
										<p><span>QQ：</span><input name ="qq" type = "text" /><br /></p>
										<p><span>家庭住址：</span><input name ="uaddress" type = "text" /><br /></p>
										<p><span>身份证号：</span><input name ="uidcard" type = "text" /><br /></p>
									</div>
								</div>
								<div class="btn">
									<div class="btn_submit">
									<input type="button" value="确认办理" onclick="submit()" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
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