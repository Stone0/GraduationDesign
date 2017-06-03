/*window.alert = function (str)
	{
		
		var msgw,msgh,bordercolor;
		msgw=400;//提示窗口的宽度
		msgh=100;//提示窗口的高度
		titleheight=25 //提示窗口标题高度
		bordercolor="#c51100";//提示窗口的边框颜色
		titlecolor="#c51100";//提示窗口的标题颜色

		var sWidth,sHeight;
		sWidth=screen.width;
		sHeight=screen.height;

		var bgObj=document.createElement("div");
		bgObj.setAttribute('id','bgDiv');
		bgObj.style.position="absolute";
		bgObj.style.top="0";
		bgObj.style.background="#cccccc";
		bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
		bgObj.style.opacity="0.6";
		bgObj.style.left="0";
		bgObj.style.width=sWidth + "px";
		bgObj.style.height=sHeight + "px";
		bgObj.style.zIndex = "10000";
		document.body.appendChild(bgObj);

		var msgObj=document.createElement("div")
		msgObj.setAttribute("id","msgDiv");
		msgObj.setAttribute("align","center");
		msgObj.style.background="white";
		msgObj.style.border="1px solid " + bordercolor;
		msgObj.style.position = "absolute";
		msgObj.style.left = "50%";
		msgObj.style.top = "50%";
		msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
		msgObj.style.marginLeft = "-225px" ;
		msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px";
		msgObj.style.width = msgw + "px";
		msgObj.style.height =msgh + "px";
		msgObj.style.textAlign = "center";
		msgObj.style.lineHeight ="25px";
		msgObj.style.zIndex = "10001";

		   var title=document.createElement("h4");
		   title.setAttribute("id","msgTitle");
		   title.setAttribute("align","right");
		   title.style.margin="0";
		   title.style.padding="3px";
		   title.style.background=bordercolor;
		   title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
		   title.style.opacity="0.75";
		   title.style.border="1px solid " + bordercolor;
		   title.style.height="18px";
		   title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif";
		   title.style.color="white";
		   title.style.cursor="pointer";
		   title.innerHTML="关闭";
		   title.onclick=function(){
		document.body.removeChild(bgObj);
		document.getElementById("msgDiv").removeChild(title);
		document.body.removeChild(msgObj);
		}
		   document.body.appendChild(msgObj);
		   document.getElementById("msgDiv").appendChild(title);
		   var txt=document.createElement("p");
		   txt.style.margin="1em 0"
		   txt.setAttribute("id","msgTxt");
		   txt.innerHTML=str;
		   document.getElementById("msgDiv").appendChild(txt);
	}*/

window.alertNormal = function(str)
{
    var shield = document.createElement("DIV");
    shield.id = "shield";
    shield.style.position = "fixed";
    shield.style.background = "RGBA(125,125,125,0.8)";
    shield.style.left = "0px";
    shield.style.top = "0px";
    shield.style.width = "100%";
    shield.style.height = "100%";
    shield.style.zIndex = "10";
    
    var alertFram = document.createElement("DIV");
    alertFram.id="alertFram";
    alertFram.style.position = "absolute";
    alertFram.style.left = "50%";
    alertFram.style.top = "50%";
    alertFram.style.width = "280px";
    alertFram.style.height = "210px";
    alertFram.style.marginLeft = "-140px";
    alertFram.style.marginTop = "-110px";
    alertFram.style.textAlign = "center";
    alertFram.style.lineHeight = "150px";
    alertFram.style.zIndex = "20";
    strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%\">\n";
    strHtml += " <li style=\"background:RGBA(85,85,85,0.9);text-align:center;height:120px;line-height:120px;font-size:16px;border-top-left-radius: 4px;border-top-right-radius: 4px;color:#DFE3EE\">"+str+"</li>\n";
    strHtml += " <li style=\"background:RGBA(85,85,85,0.9);text-align:center;height:30px;line-height:30px;border-bottom-left-radius: 4px;border-bottom-right-radius: 4px;border-top:1px solid #CCCCCC;\">" +
    		"<input type=\"button\" value=\"确 定\" onclick=\"doOk()\" style=\"width:80px;height:20px;line-height:20px;background:#626262;color:white;border-radius:3px;border:1px solid #CCCCCC;font-size:14px;\" onmouseover=\"this.style.background='#343434'\" onmouseout=\"this.style.background=0\" />" +
    				"</li>\n";
    strHtml += "</ul>\n";
    alertFram.innerHTML = strHtml;
    document.body.appendChild(alertFram);
    document.body.appendChild(shield);
    this.doOk = function(){
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
    alertFram.focus();
    document.body.onselectstart = function(){return false;};
}

window.alertLogin = function(str)
{
    var shield = document.createElement("DIV");
    shield.id = "shield";
    shield.style.position = "fixed";
    /*shield.style.left = "50%";
    shield.style.top = "50%";
    shield.style.width = "280px";
    shield.style.height = "150px";
    shield.style.marginLeft = "-140px";
    shield.style.marginTop = "-110px";
    shield.style.zIndex = "25";*/
    shield.style.background = "RGBA(125,125,125,0.8)";
    shield.style.left = "0px";
    shield.style.top = "0px";
    shield.style.width = "100%";
    shield.style.height = "100%";
    shield.style.zIndex = "10";
    
    var alertFram = document.createElement("DIV");
    alertFram.id="alertFram";
    //alertFram.style.background = "RGBA(119,119,119,0.9)";
    alertFram.style.position = "absolute";
    alertFram.style.left = "50%";
    alertFram.style.top = "50%";
    alertFram.style.width = "280px";
    alertFram.style.height = "210px";
    alertFram.style.marginLeft = "-140px";
    alertFram.style.marginTop = "-110px";
    alertFram.style.textAlign = "center";
    alertFram.style.lineHeight = "150px";
    alertFram.style.zIndex = "20";
    strHtml = "<ul style=\"list-style:none;margin:0px;padding:0px;width:100%\">\n";
    strHtml += " <li style=\"background:RGBA(85,85,85,0.9);text-align:center;height:120px;line-height:120px;font-size:16px;border-top-left-radius: 4px;border-top-right-radius: 4px;color:#DFE3EE\">"+str+"</li>\n";
    strHtml += " <li style=\"background:RGBA(85,85,85,0.9);text-align:center;height:30px;line-height:30px;border-bottom-left-radius: 4px;border-bottom-right-radius: 4px;border-top:1px solid #CCCCCC;\">" +
    		"<input type=\"button\" value=\"确 定\" onclick=\"doLogin()\" style=\"width:80px;height:20px;line-height:20px;background:#626262;color:white;border-radius:3px;border:1px solid #CCCCCC;font-size:14px;\" onmouseover=\"this.style.background='#343434'\" onmouseout=\"this.style.background=0\" />" +
    				"</li>\n";
    strHtml += "</ul>\n";
    alertFram.innerHTML = strHtml;
    document.body.appendChild(alertFram);
    document.body.appendChild(shield);
    this.doLogin = function(){
    	window.location.href = "/goBook/jsp/login.jsp";
    }
    alertFram.focus();
    document.body.onselectstart = function(){return false;};
}




