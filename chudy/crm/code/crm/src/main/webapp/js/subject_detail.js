//shop detail author Andy
var code = urlparameter("code"); //WeChat auth code
var id = urlparameter("id"); //subject id
var currentStart = 0;
var size = 10;

$(document).ready(function(){
	
	loadSubject();//加载专题
	loadReward();//加载专题优惠
	
	browse_record(code);//记录访问统计
	
	//微信分享
	initWX();
	wx.ready(function(){
		share_WeChat();
	});
	
	//判断用户是否关注
	judge_if_subscribe();
});

function loadSubject(){
	ajaxUtil({"id": id, "code":code}, mainpath+"/subject/getSubject.shtml", printSubjectDetail);
}

function printSubjectDetail(response){
	var info = response;
	var html = "";
	
	html += "<div class=\"zt_con\">";
	html += "	<img src=\""+info.img+"\" alt=\""+info.title+"\">";
	html += "	<span class=\"ic_logo\"></span>";
	var cname = "";
	if(notEmpty(info.userSubject)){
		cname = "on";
	}
	html += "	<p class=\"icon\"><span onclick=\"zanfun("+info.id+", this)\" class=\"ic_2 ic_zan "+cname+"\">"+info.scount+"</span></p>";
	html += "</div>";
	html += "<div class=\"zt_text\">";
	html += "	<p class=\"wz\">"+info.description+"</p>";
	html += "	<p class=\"bank\">";
	var citys = notEmpty(info.citys) ? info.citys :"全国"; 
	html += "		<span class=\"right\">"+citys+"</span>";
	var banks = info.banks;
	for(i in banks){
		var blogo = "/images/default1.jpg";
		if(banks[i].logo != null){
			blogo = banks[i].logo.split(",")[1];
		}
		html += "		<span class=\"ic\"><img src=\""+(mainpath+blogo)+"\" alt=\""+banks[i].name+"\"></span>";
		
	}
	html += "	</p>";
	html += "</div>";
	
	$(".zt_detail").append(html);
	
	if(notEmpty(info.special)){
		$(".zt_detail").after("<div class=\"yh_title\">"+info.special+"</div>");
	}
	
	
}

//参数
function param(){
	return {"sid":id, "start":currentStart, "size":size};
}

function loadReward(){
	currentStart = 0;
	printUtil(".hsh_list", param(), mainpath+"/reward/listRewardSubject.shtml", printReward);
	if (currentStart == size){
		$(".moreBtn").show();
	}
}

function appendReward(){
	var beforeStart = currentStart;
	appendUtil(".hsh_list", param(), mainpath+"/reward/listRewardSubject.shtml", printReward);
	if ((beforeStart + size) > currentStart){
		$(".moreBtn").hide();
	}
}

function printReward(response){
	var html = "";
	var list = response.list;
	for (i in list){
		currentStart++;
		
		html += "<li>";
		html += "	<a href=\""+wecharauto2url("hsh_detail.shtml?id="+list[i].id+"&city=")+"\">";
		var blogo = null;
		if(list[i].blogo != null){
			blogo = list[i].blogo.split(",")[1];
		}
		var hotimg = isEmpty(list[i].hotimg) ? mainpath+"/images/default.jpg" : list[i].hotimg;
		html += "		<p class=\"pt\"><img src=\""+hotimg+"\" alt=\""+list[i].title+"\"></p>";
		html += "		<div class=\"wz_con\">";
		html += "			<span class=\"ic_bank\"><img src=\""+(mainpath+blogo)+"\" height=\"17\" alt=\""+list[i].bname+"\"></span>";
		html += "			<p class=\"name\">"+list[i].title+"</p>";
		html += "		</div>";
		html += "	</a>";
		html += "</li>";
		
	}
	
	return html;
}

function zanfun(sid, zan){
	var num = $(zan).html();
	if($(zan).hasClass("on")){
		$(zan).removeClass("on").html(parseInt(num)-1);
		ajaxUtil({"code":code, "sid":sid}, mainpath+"/subject/delUserSubject.shtml");
	}else{
		$(zan).addClass("on").html(parseInt(num)+1);
		ajaxUtil({"code":code, "sid":sid}, mainpath+"/subject/addUserSubject.shtml");
	}
}