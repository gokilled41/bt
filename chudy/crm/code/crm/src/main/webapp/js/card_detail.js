//card detail author Andy
var code = urlparameter("code"); //WeChat auth code
$(document).ready(function(){
	loadCard();
	
	browse_record(code);//记录访问统计
});

function loadCard(){
	var id = urlparameter("id");
	ajaxUtil({"id": id}, mainpath+"/card/getCard.shtml", printCardDetail);
}

function printCardDetail(response){
	var info = response;
	var html = "";
	html += "<div class=\"con_1\">";
	html += "	<div class=\"pt\">";
	html += "		<img src=\""+info.img+"\" alt=\""+info.name+"\">";
	html += "	</div>";
	html += "	<div class=\"con\">";
	html += "		<p class=\"name\">"+info.name+"</p>";
	html += "		<p class=\"wz_1\">"+info.cardtype+"</p>";
	html += "		<p class=\"wz_2\">"+info.mainreward+"</p>";
	html += "	</div>";
	html += "	<div class=\"clear\"></div>";
	html += "</div>";
	html += "<div class=\"con_2\">";
	html += "	<div class=\"title\">信用卡详情</div>";
	html += "	<ul class=\"wz_con\">";
	html += 		"<li><span class=\"name\">信用卡等级：</span><p>"+info.cardgrand+"</p></li>";
	html += 		"<li><span class=\"name\">取现手续费：</span><p>"+info.cashrate+"</p></li>";
	html += 		"<li><span class=\"name\">分期费率：</span><p>"+info.stagerate+"</p></li>";
	html += 		"<li><span class=\"name\">年费政策：</span><p>"+info.yearfeepolicy+"</p></li>";
	html += "	</ul>";
	html += "</div>";
	if(notEmpty(info.privilege)){
		html += "<div class=\"con_2 mar\">";
		html += "	<div class=\"title\">特权</div>";
		html += "	<div class=\"wz_1\">"+info.privilege+"</div>";
		html += "</div>";
	}
	$(".card_detail").html(html);
	
	html = "";
	if(info.applyonline != 1){
		html += "	<div class=\"apply_cz\">";
		html += "		<a href=\""+info.applyurl+"\" class=\"apply_btn\">立即申请</a>";
		html += "	</div>";
	}
	$(".card_detail").after(html);
}
