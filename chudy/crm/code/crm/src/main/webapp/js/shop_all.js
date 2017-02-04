//card list author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var id = urlparameter("id"); //reward id
var bid = urlparameter("bid"); //brand id
var reflng = null;//经度
var reflat =  null;//纬度
var city = null;//所属城市

$(document).ready(function(){
	
	initConf();
	
	browse_record(code);//记录访问统计
});

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			reflat = uc.lat;
			reflng = uc.lng;
			city = uc.city;
			loadStore();//加载店铺
		}
	});
}

//参数
function param(){
	return {"rid":id, "bid":bid, "reflng":reflng, "reflat":reflat, "city":city, "start":currentStart, "size":size};
}

function loadStore(){
	$(".loading").hide();
	currentStart = 0;
	printUtil(".shop_all_box>ul", param(), mainpath+"/store/listStoreByRid.shtml", printStore);
	if (currentStart == size){
		$(".moreBtn").show();
	}
}

function appendStore(){
	var beforeStart = currentStart;
	appendUtil(".shop_all_box>ul", param(), mainpath+"/store/listStoreByRid.shtml", printStore);
	if ((beforeStart + size) > currentStart){
		$(".moreBtn").hide();
	}
}

function printStore(response){
	var html = "";
	var list = response.list;
	
	for (i in list){
		currentStart++;
		
		html += "<li>";
		html += "	<a href=\""+wecharauto2url("shop_detail.shtml?id="+list[i].id+"&bkid=&dist="+list[i].dist)+"\">";
		html += "		<p class=\"name\">"+list[i].name+"</p>";
		html += "		<p class=\"wz\"><span class=\"right\">"+distanceShow(list[i].dist)+"</span>"+list[i].calias+"</p>";
		html += "	</a>";
		html += "</li>";
	}
	
	return html;
}

