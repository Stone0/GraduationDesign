<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>GoBook-Login</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css">
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/login.js"></script>
	</head>
	<body>
		<div class ="login">
			<p class = "l_title">GoBook管理系统</p>
			<div class = "l_span">
				<span class = "tips"></span>
			</div>
			<form id="submitForm" action = "LoginController" method = "post">
			<div class = "l_input">
				<!-- onmouseover="style.border='1px solid #54DDE9'" onmouseout="style.border='1px solid #084C61'" -->
				<div class = "mid" >
					<input name ="mid" id="mid" type = "text" placeholder = "工作编号" /><br /><br />
				</div>
				<div class = "psw" >
					<input name ="psw" id="psw" type = "password" placeholder = "密码" /><br /><br />
				</div>
				<div class = "l_submit">
					<input onclick="submitForm()" class="submit" type = "button" value = "登 录"  onmouseover="btnOver(this)" onmouseout="btnOut(this)"/><br /><br />
				</div>
			</div>
			</form>
		</div>
	</body>
</html>