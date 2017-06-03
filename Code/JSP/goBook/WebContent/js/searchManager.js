function detailsForm(mid, mname, mgender, mbirth, mphone, maddress, midcard, hiredate) {
	
	$("#managerId").val(mid);
	$("#mid").val(mid);
	$("#mname").val(mname);
	$("#mgender").val(mgender);
	$("#mbirth").val(mbirth);
	$("#updateMphone").val(mphone);
	$("#updateMaddress").val(maddress);
	$("#idcard").val(midcard);
	$("#hiredate").val(hiredate);

	document.getElementById("detailsForm").style.display="block";
}

window.alertCancel = function(str)
{
	var mid = $("#mid").val();
	
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
    alertFram.style.position = "fixed";
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
    		"<input type=\"button\" value=\"确 定\" onclick=\"cancelManager()\" style=\"position:absolute;top:126px;left:45px;width:80px;height:20px;background:#626262;color:white;border-radius:3px;border:1px solid #CCCCCC;font-size:14px;\" onmouseover=\"this.style.background='#343434'\" onmouseout=\"this.style.background=0\" />" +
    		"<input type=\"button\" value=\"取 消\" onclick=\"unDo()\" style=\"position:absolute;top:126px;right:45px;width:80px;height:20px;line-height:20px;background:#626262;color:white;border-radius:3px;border:1px solid #CCCCCC;font-size:14px;\" onmouseover=\"this.style.background='#343434'\" onmouseout=\"this.style.background=0\" />" +		
    			"</li>\n";
    strHtml += "</ul>\n";
    alertFram.innerHTML = strHtml;
    document.body.appendChild(alertFram);
    document.body.appendChild(shield);
    this.unDo = function(){
        alertFram.style.display = "none";
        shield.style.display = "none";
    }
    this.cancelManager = function(){
    	window.location.href = "../root/cancelManager?mid="+mid;
    }
    alertFram.focus();
    document.body.onselectstart = function(){return false;};
}