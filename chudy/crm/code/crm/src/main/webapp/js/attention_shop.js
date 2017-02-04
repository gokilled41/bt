//attention_shop author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var reflng = null;//经度
var reflat = null;//纬度
var sids = null; //关注店铺ids
$(document).ready(function(){
	
	initConf();
	
	browse_record(code);//记录访问统计
});

//初始化配置
function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			reflat = uc.lat;
			reflng = uc.lng;
			show_attention_shop();
		}
	});
}

function show_attention_shop(){
	ajaxUtil({"code":code}, mainpath+"/attention/getAttention.shtml", function(response){
		var attention = response;
		if(attention != null){
			
			if(notEmpty(attention.sids)){
				sids = attention.sids.split(",");
				$(".m_con a:eq(0) .num").text(sids.length);
			}
			if(notEmpty(attention.rids)){
				$(".m_con a:eq(1) .num").text(attention.rids.split(",").length);
			}
			
			if(sids != null && sids.length > 0){
				loadStore();//加载关注的店铺
			}
		}
	});
}

//参数
function param(){
	return {"reflng":reflng, "reflat":reflat, "sids":sids, "start":currentStart, "size":size};
}

function loadStore(){
	currentStart = 0;
	printUtil(".shop_box", param(), mainpath+"/store/listStoreDist.shtml", printStore);
	if (currentStart == size){
		$(".shop_box").append("<a href=\"javascript:appendStore();\" class=\"moreBtn\"><span class=\"name\">更多</span></a>");
	}
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
		html += "	<div class=\"pt\"><img src=\""+blogo+"\" alt=\""+list[i].bname+"\"></div>";
		html += "	<div class=\"con_1\">";
		html += "		<a href=\""+wecharauto2url("shop_detail.shtml?id="+list[i].id+"&bkid=&dist="+list[i].dist)+"\" class=\"s_name\">";
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
			html += "			<li><a href=\""+wecharauto2url("shop_detail.shtml?id="+list[i].id+"&bkid="+rewards[j].bid+"&dist="+list[i].dist)+"\"><span class=\"ic\"><img src=\""+mainpath+blogo+"\" height=\"18\" alt=\""+rewards[j].bname+"\"></span><p>"+rewards[j].title+"</p></a></li>";
			
		}
		html += "		</ul>";
		html += "	</div>";
		html += "</div>";
		
	}

	return html;
}

function attention(url){
	window.location.href = wecharauto2url(url);
}