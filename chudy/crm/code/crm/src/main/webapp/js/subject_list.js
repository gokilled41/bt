//subject list author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;

$(document).ready(function(){
	loadSubject();//加载专题
	
	browse_record(code);//记录访问统计
});

//参数
function param(){
	return {"code":code, "status":1, "start":currentStart, "size":size};
}

function loadSubject(){
	currentStart = 0;
	printUtil(".zt_list", param(), mainpath+"/subject/listSubject.shtml", printSubject);
	if (currentStart == size){
		$(".moreBtn").show();
	}
}

function appendSubject(){
	var beforeStart = currentStart;
	appendUtil(".zt_list", param(), mainpath+"/subject/listSubject.shtml", printSubject);
	if ((beforeStart + size) > currentStart){
		$(".moreBtn").hide();
	}
}

function printSubject(response){
	var html = "";
	var list = response.list;
	for (i in list){
		currentStart++;
		
		html += "<li>";
		html += "	<div class=\"zt_con\">";
		html += "		<a href=\""+wecharauto2url("subject_detail.shtml?id="+list[i].id)+"\">";
		html += "			<div class=\"pt\">";
		html += "				<img src=\""+list[i].img+"\" alt=\""+list[i].title+"\"/>";
		html += "			</div>";
		html += "		</a>";
		var cname = "";
		if(notEmpty(list[i].userSubject)){
			cname = "on";
		}
		html += "		<p class=\"icon\"><span onclick=\"zanfun("+list[i].id+", this)\" class=\"ic_2 ic_zan "+cname+"\">"+list[i].scount+"</span></p>";
		html += "	</div>";
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
