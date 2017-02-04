//search_shop author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var city = null;//城市

var rtitle = $("#searchreward").val(); //搜索优惠的名称

$(document).ready(function(){
	
	if (/ipad|iphone|mac/i.test(navigator.userAgent)){
		$(".menu_fixed").css("position", "static");
		$(".search_mar").css("padding", "0px");
	}
	
	initConf();
	
	if(notEmpty(rtitle)){
		$(".empty_btn").show();
		$(".search_con").hide();
		loadReward();//加载优惠
	}else{
		loadSearchReward();//加载优惠搜索排名
	}
	
	$("#searchreward").keyup(function(event){
		$(".empty_btn").show();
		$(".search_ts").hide();
		if(event.keyCode == 13){
			rtitle = $(this).val();
			if(notEmpty(rtitle)){
				$(".search_con").hide();
				loadReward();//加载优惠
			}
		}
	});

	$(".empty_btn").click(function(){
		$(this).hide();
		$("#searchreward").val("");
		$(".deal_container, .search_ts").hide();
		$(".search_con").show();
	});
	
	browse_record(code);//记录访问统计
});

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			city = uc.city;
		}
	});
}

function search(){
	$(".empty_btn").show();
	$(".search_ts").hide();
	rtitle = $("#searchreward").val();
	if(notEmpty(rtitle)){
		$(".search_con").hide();
		loadReward();//加载优惠
	}
}

//参数
function param(){
	return {"rtitle":rtitle, "hot":1, "city":city, "start":currentStart, "size":size};
}

function loadReward(){
	currentStart = 0;
	printUtil(".hsh_list", param(), mainpath+"/reward/listRewardHot.shtml", printReward);
	if (currentStart == size){
		$(".moreBtn").show();
	}
	
	if(currentStart == 0){
		$(".search_ts").show();
	}
	
	$(".deal_container").show();
}

function appendReward(){
	var beforeStart = currentStart;
	appendUtil(".hsh_list", param(), mainpath+"/reward/listRewardHot.shtml", printReward);
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
		html += "	<a href=\"javascript:rewardDetail("+list[i].id+");\">";
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


function rewardDetail(rid){
	ajaxUtil({"code":code, "rid":rid}, mainpath+"/searchReward/recordSearch.shtml");

	window.location.href = wecharauto2url("hsh_detail.shtml?id="+rid);
}


function loadSearchReward(){
	printUtil(".hot_search", {"city":city, "top":10}, mainpath+"/searchReward/searchTop.shtml", printRewardSearch);
	$(".search_con").show();
}


function printRewardSearch(response){
	var list = response.list;
	var html = "";
	for (i in list){
		html += "<li><a href=\"javascript:searchReward("+list[i].rid+");\" >"+list[i].rtitle+"</a></li>";
	}

	return html;
}

//搜索优惠
function searchReward(rid){
	var rids  = new Array();
	rids.push(rid);
	$(".search_con").hide();
	
	printUtil(".hsh_list", {"rids":rids, "start":currentStart, "size":size}, mainpath+"/reward/listRewardHot.shtml", printReward);
	if (currentStart == size){
		$(".moreBtn").show();
	}
	
	if(currentStart == 0){
		$(".search_ts").show();
	}
	
	$(".deal_container").show();
}
