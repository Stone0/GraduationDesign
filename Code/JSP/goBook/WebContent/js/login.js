// 点击登录按钮后判断用户名和密码是否输入 输入后再发送请求
function submitForm() {
	var id = $("#mid").val();
	var psw = $("#psw").val();

	//console.log(id + "- -" + psw);
	
	//判断用户名和密码是否为空
	if("" == id) {
		$(".tips").html("请输入编号!").show(300).delay(3000).hide(300);
		return;
	}
	
	if("" == psw) {
		$(".tips").html("请输入密码!").show(300).delay(3000).hide(300);
		return;
	}
	
	$.ajax({
		url:"../LoginController",
		type:"post",
		data: {'mid': $("#mid").val(), 'psw': $("#psw").val()},
		//data:"mid="+encodeURI($("#userName").val()),
		success:function(resp,status,xhr){
			if(resp == "2") {
				window.location.href='../jsp/root/showAllLib.jsp';
			} else if(resp == "1") {
				window.location.href='../jsp/manager/showAllSub.jsp';
			} else {
				$(".tips").html("编号或密码错误").show(300).delay(3000).hide(300);
			}
		},
		error:function(){
			alertNormal("异常error");
		}
	});
	//$("#submitForm").submit();
}

function btnOver(btn) {
	btn.style.backgroundColor="#474747";
	btn.style.color="#fff";
	btn.style.textShadow="#fff 0 0 7px";
}

function btnOut(btn) {
	btn.style.backgroundColor="#474747";
	btn.style.color="#fff";
	btn.style.textShadow="none";
}