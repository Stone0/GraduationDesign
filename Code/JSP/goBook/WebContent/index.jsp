<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GoBook-Index</title>
</head>
<body>
	<div class="index">
		<!-- index.css -->
		<style type="text/css">
			* {
				margin: 0;
				padding: 0;a
			}
			body{
				background-color: #6D696A;
				background-image: url(images/loginBG.jpg);
				background-repeat: no-repeat;
				background-size: cover;
				background-position: center center;
				background-attachment: fixed;
				font-family: "微软雅黑";
				overflow: hidden;
			}
			.index{
				background-color: RGBA(218,226,223,0.2);
				position: absolute;
				width: 1400px;
				height: 700px;
				/* width: 100%;
				height: 100%; */
			}
		</style>
		<div class="p">
			<p>WELCOME TO GOBOOK SYSTEM</p>
			<!-- p.css -->
			<style type="text/css">
				.p{
					background-color: RGBA(247,255,247,0.7);
					position: absolute;
					width: 840px;
					top: 25%;
					left: 20%;
					height: 80px;
					text-align: center;
					line-height: 80px;
					font-size: 32px;
					border-radius: 5px;
					border: 3px solid #999;
					white-space: nowrap;
				}
			</style>
		</div>
		<div class="btn">
			<input onclick="location.href='jsp/login.jsp'" type="submit" class="submit" value="前 往 登 录" onmouseover="btnOver(this)" onmouseout="btnOut(this)"/>
			<!-- button.css -->
			<style type="text/css">
				.btn{
					background-color: #6D696A;
					position: absolute;
					width: 18%;
					top: 60%;
					left: 41%;
					height: 50px;
				}
				.btn .submit{
					background-color: #243347;
					position: absolute;
					width: 100%;
					height: 50px;
					font-size: 32px;
					font-family: "微软雅黑";
					border: 1px solid;
					color: #D1D1D1;
				}
			</style>
			<!-- button.js -->
			<script type="text/javascript">
			function btnOver(btn) {
				btn.style.backgroundColor="#243347";
				btn.style.color="#D1D1D1";
				btn.style.textShadow="#D1D1D1 0 0 10px";
			}

			function btnOut(btn) {
				btn.style.backgroundColor="#243347";
				btn.style.color="#D1D1D1";
				btn.style.textShadow="none";
			}
			</script>
		</div>
	</div>
</body>
</html>