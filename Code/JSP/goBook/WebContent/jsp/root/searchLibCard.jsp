<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-查询借书证信息</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/cssMain.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/showAllLib.css">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/rootSearchLibCard.css">
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
		<div class="rootSearchLibCard">
			<div class="head">
				<p onclick="window.location.href='<%=request.getContextPath()%>/root/showAllLib'">GoBook管理系统</p>
				<span class="identity"></span>
				<a class="exit" href="<%=request.getContextPath()%>/manager/managerExit" onmouseover="exitBtnOver(this)" onmouseout="exitBtnOut(this)">exit</a>
				<div class="search">
					<form action="<%=request.getContextPath()%>/root/searchLibCard" method="post">
						<div class="search_text">
							<input name="searchText" type="text" placeholder="身份证后6位"/>
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
					<div class="libCardMess">
						<div class="libCardMess_title">
							<table>
								<tr>
									<th>真实姓名</th>
									<th>性别</th>
									<th>联系方式</th>
									<th>微信</th>
									<th>QQ</th>
									<th>家庭住址</th>
									<th>身份证号</th>
									<th>操作</th>
								</tr>
							</table>
						</div>
						<div id="detailsForm" class="detailsForm_bg">
							<div class="detailsForm_content">
								<form action="<%=request.getContextPath()%>/root/updateLibCard" method="post">
									<div class="content_text">
										<div class="content_textSpan">
											<p><span>真实姓名：</span><input name ="realname" id="realname" type = "text" disabled="disabled" /><br /></p>
											<p><span>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</span>
												<select id="ugender" disabled="disabled">
												  <option value="男">男</option>
												  <option value="女">女</option>
												</select>
											</p>
											<p><span>出生日期：</span><input name ="ubirth" id="ubirth" type = "text" disabled="disabled"/><br /></p>
											<p><span>联系方式：</span><input class="uphone" name ="updateUphone" id="updateUphone" type = "text" /><br /></p>
											<p><span>微信：</span><input class="wechat" name ="updateWechat" id="updateWechat" type = "text" /><br /></p>
											<p><span>QQ：</span><input class="qq" name ="updateQq" id="updateQq" type = "text" /><br /></p>
											<p><span>家庭住址：</span><input class="uaddress" name ="updateUaddress" id="updateUaddress" type = "text" /><br /></p>
											<p><span>身份证号：</span><input name ="idcard" id="idcard" type = "text" disabled="disabled" /><br /></p>
											<input name ="uidcard" id="uidcard" type = "text" style="display: none" />
											<p><span>申请时间：</span><input name ="applydate" id="applydate" type = "text" disabled="disabled" /><br /></p>
											<p><span>验证时间：</span><input name ="processtime" id="processtime" type = "text" disabled="disabled" /><br /></p>
										</div>
									</div>
									<div class="btn_submit">
										<input type="button" value="确认修改" onclick="submit()" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
									<div class="btn_cancel">
										<input type="button" value="注      销" onclick="alertCancel('确定注销?')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
									<div class="btn_undo">
										<input type="button" value="取      消" onclick="document.getElementById('detailsForm').style.display='none'" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)" />
									</div>
								</form>
							</div>
						</div>
						<div class="libCardMess_content">
							<div class="items">
								<form action="" method="post">
									<table>
										<c:forEach items="${showAllLibCard}" var="salc">
											<tr>
												<td id="realname" align="center">${salc.realname}</td>
												<td id="ugender" align="center">${salc.ugender}</td>
												<td id="ubirth" style="display: none">${salc.ubirth}</td>
												<td id="uphone" title="${salc.uphone}">${salc.uphone}</td>
												<td id="wechat" title="${salc.wechat}">${salc.wechat}</td>
												<td id="qq" title="${salc.qq}">${salc.qq}</td>
												<td id="uaddress" title="${salc.uaddress}">${salc.uaddress}</td>
												<td id="uidcard" title="${salc.uidcard}">${salc.uidcard}</td>
												<td id="applydate" style="display: none">${salc.applydate}</td>
												<td id="processtime" style="display: none">${salc.processtime}</td>
												<td id="process" style="display: none">${salc.process}</td>
												<td id="inuse" style="display: none">${salc.inuse}</td>
												<td><input type="button" value="详细信息" onclick="detailsForm('${salc.realname}', '${salc.ugender}', '${salc.ubirth}', '${salc.uphone}', '${salc.wechat}', '${salc.qq}', '${salc.uaddress}', '${salc.uidcard}', '${salc.applydate}', '${salc.processtime}', '${salc.process}', '${salc.inuse}')" onmouseover="sureBtnOver(this)" onmouseout="sureBtnOut(this)"/></td>
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