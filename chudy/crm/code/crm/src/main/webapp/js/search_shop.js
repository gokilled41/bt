//search_shop author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var reflng = null;//经度
var reflat = null;//纬度
var city = null;//城市

var sname = $("#searchstore").val(); //搜索店铺名称

$(document).ready(function(){
	
	if (/ipad|iphone|mac/i.test(navigator.userAgent)){
		$(".menu_fixed").css("position", "static");
		$(".search_mar").css("padding", "0px");
	}
	
	initConf();
	
	if(notEmpty(sname)){
		$(".empty_btn").show();
		$(".search_con").hide();
		loadStore();//加载店铺
	}else{
		loadSearchStore();//加载店铺搜索排名
	}
	
	$("#searchstore").keyup(function(event){
		$(".empty_btn").show();
		$(".search_ts").hide();
		if(event.keyCode == 13){
			sname = $(this).val();
			if(notEmpty(sname)){
				$(".search_con").hide();
				loadStore();//加载店铺
			}
		}
	});

	$(".empty_btn").click(function(){
		$(this).hide();
		$("#searchstore").val("");
		$(".deal_container, .search_ts").hide();
		$(".search_con").show();
	});
	
	browse_record(code);//记录访问统计
});

function search(){
	$(".empty_btn").show();
	$(".search_ts").hide();
	sname = $("#searchstore").val();
	if(notEmpty(sname)){
		$(".search_con").hide();
		loadStore();//加载店铺
	}
}

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			reflat = uc.lat;
			reflng = uc.lng;
			city = uc.city;
		}
	});
}

//参数
function param(){
	return {"sname":sname, "reflng":reflng, "reflat":reflat, "city":city, "start":currentStart, "size":size};
}

function loadStore(){
	currentStart = 0;
	printUtil(".shop_box", param(), mainpath+"/store/listStoreDist.shtml", printStore);
	if (currentStart == size){
		$(".shop_box").append("<a href=\"javascript:appendStore();\" class=\"moreBtn\"><span class=\"name\">更多</span></a>");
	}
	
	if(currentStart == 0){
		$(".search_ts").show();
	}
	
	$(".deal_container").show();
}

function appendStore(){
	var beforeStart = currentStart;
	appendUtil(".shop_box", param(), mainpath+"/store/listStoreDist.shtml", printStore);
	$(".moreBtn").remove();
	if ((beforeStart + size) <= currentStart){
		$(".shop_box").append("<a href=\"javascript:appendStore();\" class=\"moreBtn\"><span class=\"name\">更多</span></a>");
	}
}

function printStore(response){
	var list = response.list;
	var html = "";
	for (i in list){
		currentStart++;
		
		html += "<div class=\"shop_content_1\">";
		var  blogo = isEmpty(list[i].blogo) ? mainpath+"/images/default1.jpg" : list[i].blogo;
		html += "	<div class=\"pt\"><a href=\""+blogo+"\"><img src=\""+blogo+"\" alt=\""+list[i].bname+"\"></a></div>";
		html += "	<div class=\"con_1\">";
		html += "		<a href=\"javascript:storeDetail("+list[i].id+",'',"+list[i].dist+");\" class=\"s_name\">";
		html += "			<p class=\"name\">"+list[i].name+"</p>";
		html += "			<p class=\"wz\"><span class=\"right\">"+distanceShow(list[i].dist)+"</span>"+list[i].calias+"</p>";
		html += "		</a>";
		html += "		<ul class=\"bank_active\">";
		var rewards = list[i].rewards;
		
		for(j in rewards){
			var blogo = null;
			if(rewards[j].blogo != null){
				blogo = rewards[j].blogo.split(",")[1];
			}
			html += "			<li><a href=\"javascript:storeDetail("+list[i].id+","+rewards[j].bid+","+list[i].dist+");\"><span class=\"ic\"><img src=\""+mainpath+blogo+"\" height=\"18\" alt=\""+rewards[j].bname+"\"></span><p>"+rewards[j].title+"</p></a></li>";
			
		}
		html += "		</ul>";
		html += "	</div>";
		html += "</div>";
		
	}

	return html;
}

function storeDetail(sid, bkid, dist){
	ajaxUtil({"code":code, "sid":sid}, mainpath+"/searchStore/recordSearch.shtml");
	var url = "shop_detail.shtml?id="+sid+"&dist="+dist;
	if(notEmpty(bkid)){
		url += "&bkid="+bkid;
	}
	window.location.href = wecharauto2url(url);
}


function loadSearchStore(){
	printUtil(".hot_search", {"city":city, "top":10}, mainpath+"/searchStore/searchTop.shtml", printStoreSearch);
	$(".search_con").show();
}


function printStoreSearch(response){
	var list = response.list;
	var html = "";
	for (i in list){
		html += "<li><a href=\"javascript:searchStore("+list[i].sid+");\" >"+list[i].sname+"</a></li>";
	}

	return html;
}

//搜索店铺
function searchStore(sid){
	var sids = new Array();
	sids.push(sid);
	$(".search_con").hide();
	currentStart = 0;
	printUtil(".shop_box", {"sids":sids, "reflng":reflng, "reflat":reflat, "start":currentStart, "size":size}, mainpath+"/store/listStoreDist.shtml", printStore);
	if (currentStart == size){
		$(".shop_box").append("<a href=\"javascript:appendStore();\" class=\"moreBtn\"><span class=\"name\">更多</span></a>");
	}
	
	if(currentStart == 0){
		$(".search_ts").show();
	}
	
	$(".deal_container").show();
}

