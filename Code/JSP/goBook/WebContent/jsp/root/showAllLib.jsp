<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-root主页</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllLib.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/myAlert.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/btnMain.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/showAllLib.js"></script>
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
		<div class="showAllLib">
			<div class="head">
				<p>GoBook管理系统</p>
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
				<div class="refresh">
					<input type="button" onclick="window.location.href='<%=request.getContextPath()%>/root/showAllLib'" onmouseover="refreshBtnOver(this)" onmouseout="refreshBtnOut(this)"/>
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
					<div class="libNoProcess">
						<div class="libNoProcess_title">
							<span>借书证申请表</span>
						</div>
						<div class="libNoProcess_content">
							<div class="contents">
								<table>
									<c:forEach items="${libNoProcess}" var="lnp">
										<tr>
											<td id="img" rowspan="4">
												<div class="item_img">
													<img src="../images/libCardImgs/${lnp.uphoto}.jpg">
												</div>
											</td>
											<td id="yhr" rowspan="4"><hr hidden="hidden" /></td>
											<th>昵称:</th>
											<td title="${lnp.nickname}">${lnp.nickname}</td>
											<th>真实姓名:</th>
											<td title="${lnp.realname}">${lnp.realname}</td>
											<th>性别:</th>
											<td>${lnp.ugender}</td>
											<th>出生日期:</th>
											<td title="${lnp.ubirth}">${lnp.ubirth}</td>
										</tr>
										<tr>
											<th colspan="2">联系方式:</th>
											<td colspan="2" >${lnp.uphone}</td>
											<th>微信:</th>
											<td>${lnp.wechat}</td>
											<th>QQ:</th>
											<td>${lnp.qq}</td>
										</tr>
										<tr>
											<th colspan="2">家庭住址:</th>
											<td colspan="2" title="${lnp.uaddress}">${lnp.uaddress}</td>
											<th colspan="2">身份证号码:</th>
											<td colspan="2" title="${lnp.uidcard}">${lnp.uidcard}</td>
										</tr>
										<tr>
											<th colspan="2">申请时间:</th>
											<td colspan="2" title="${lnp.applydate}">${lnp.applydate}</td>
											<td colspan="4"><input type="submit" value="通过验证" onclick="alertLibProcess('确定通过验证?', '${lnp.uidcard}')"<%-- onclick="window.location.href='<%=request.getContextPath()%>/root/libProcess'" --%> onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
										</tr>
										<tr>
											<td id="xhr" colspan="10"><hr hidden="hidden" /></td>
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