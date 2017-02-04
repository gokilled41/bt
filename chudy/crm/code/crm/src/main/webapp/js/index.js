//index list author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var bids = null; //银行id
var alias = decodeURI(urlparameter("alias")); //分类名称
var weekday = urlparameter("weekday");//星期数
var reflng = null;//经度
var reflat = null;//纬度
var stime = null; //当前时间点
var city = null;//城市

var bidspre = bids; //bids的前一个数据

$(document).ready(function(){
	stime = new Date().format("yyyy-MM-dd") + " 00:00:00"; 
	var day = new Date().getDay();
	
	loadCategory();
	loadBank();
	
	//初始化 今天或全部
	if(notEmpty(weekday)){
		$("#weekday_param>li").eq(1).addClass("on").siblings().removeClass("on");
		$("#navConId>li:eq(2)").text("今天可用");
	}
	
	//初始化选择 初始化类别
	if(notEmpty(alias)){
		$("#navConId>li:eq(0)").text(alias);
		$("#classify_param>li").each(function(){
			var txt = $(this).children().text();
			if(txt == alias){
				$(this).addClass("on").siblings().removeClass("on");
			}
		});
	}
	
	initConf();//初始化加载
	
	// 约身边
	$("#navConId>li").click(function(){
		var index = $(this).index();
		var moduleHei = $(".dropdown_wrapper>div.module_con").eq(index).find(".con_hei").height();
		if($(this).attr("class") == "active"){
			$(this).removeClass("active");
			$(".shade").addClass("dn");
			$(".dropdown_wrapper>div.module_con").css("height","0px");
		}else {
			$(this).addClass("active").siblings().removeClass("active");
			$(".shade").removeClass("dn");
			$(".dropdown_wrapper>div.module_con").eq(index).css("height",moduleHei).siblings().css("height","0px");
		}
	});
	
	//今天/全部
	$("#weekday_param>li").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
		var txt = $(this).children().text();
		$("#navConId>li:eq(2)").text(txt);
		if($(this).text() == "今天可用"){
			weekday = (day == 0) ? 7 : day; 
		}else{
			weekday = null;
		}
		$(".shade").trigger("click");
		loadStore();
		changeURL();
		
	});

	$(".shade").click(function(){
        $("#navConId>li").removeClass("active");
        $(".shade").addClass("dn");
        $(".dropdown_wrapper>div.module_con").css("height","0px");
    });
	

    $(".swiper-wrapper>div.swiper-slide").click(function(){
    	$(this).addClass("current").siblings().removeClass("current");
    	var index = $(this).index();
    	$("#bankContentId>div").eq(index).removeClass("dn").siblings().addClass("dn");
    });

	// 银行
	$("#xyk_ul>li").click(function(){
		if($(this).attr("class") == "on"){
			$(this).removeClass("on");
		}else {
			$(this).addClass("on");
		}

		var liOnLen = $("#xyk_ul>li.on").length;
		if(liOnLen == 0){
			$(".all_xyk_btn").addClass("on");
		}else{
			$(".all_xyk_btn").removeClass("on");
		}
	});

	$(".all_xyk_btn").click(function(){
		$(this).addClass("on");
		$("#xyk_ul>li").removeClass("on");
	});

	//完成button
	$(".finish_btn").click(function(){
		if($(".all_xyk_btn").attr("class") == "all_xyk_btn"){
			bids = new Array();
			$(".xyk_num").show();
			var liOnLen = $("#xyk_ul>li.on").length;
			$("#xyk_ul>li.on").each(function(i, li){
				bids.push(parseInt($(li).attr("lang")));
			});
			$(".xyk_num").html(liOnLen);
		}else{
			$(".xyk_num").hide();
			bids = new Array();
		}

		$(".shade").addClass("dn");
		$(".dropdown_wrapper>div.module_con").css("height","0px");
		$("#navConId>li").eq(1).removeClass("active");
		loadStore();//加载店铺
		changeURL();
		if(bids != bidspre){
			updateConf_bids();//更新配置
		}
	});
	
	//分类
	$("#classify_param>li").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
		var txt = $(this).children().text();
		$("#navConId>li:eq(0)").text(txt);
		alias = (txt == "全部分类") ? "" : txt;
		$(".shade").trigger("click");
		loadStore();//加载店铺
		changeURL();
	});
	
	browse_record(code);//记录访问统计
	
	//微信分享
	initWX();
	wx.ready(function(){
		share_WeChat();
	});
	
	//判断用户是否关注
	judge_if_subscribe();
});


function initBids(){
	if(notEmpty(bids) && bids.length > 0){
		for(i in bids){
			$("#xyk_ul>li[lang='"+bids[i]+"']").addClass("on");
		}
		$(".xyk_num").html(bids.length).show();
		$(".all_xyk_btn").removeClass("on");
	}
}


function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			if(notEmpty(uc.bids)){
				bids = uc.bids.split(",");
				bidspre = bids;
			}
			reflat = uc.lat;
			reflng = uc.lng;
			city = uc.city;
			initBids();//初始化银行卡
			loadStore();//加载店铺
		}
	});
}

function updateConf_bids(){
	ajaxUtilAsync({"code":code, "bids":bids.join()}, mainpath+"/userConf/flushUserConf.shtml");
}

function changeURL(){
	var url = window.location.href;
	window.history.pushState({}, 0, url.substr(0,url.indexOf("?"))+ initpm());		
}

function loadCategory(){
	printUtil("#classify_param", {}, mainpath+"/category/listAlias.shtml", printCategory);
}

function printCategory(response){
	var list = response.list;
	var html = "";
	html += "<li class=\"on\"><a href=\"javascript:void(0)\" >全部分类</a></li>";
	for (i in list){
		html += "<li><a href=\"javascript:void(0)\" >"+list[i]+"</a></li>";
	}
	return html;
}

function loadBank(){
	printUtil("#xyk_ul", {}, mainpath+"/bank/listBank.shtml", printBank);
}

function printBank(response){
	var list = response.list;
	var html = "";
	for (i in list){
		html += "<li lang=\""+list[i].id+"\" ><a href=\"javascript:void(0)\" >"+list[i].name+"</a></li>";
	}
	return html;
}

//初始化设置参数 主要保存类别和选择时间
function initpm(){
	return "?alias="+alias+"&weekday="+weekday;
}

//参数
function param(){
	return {"bids":bids, "alias":alias, "reflng":reflng, "reflat":reflat, "stime":stime, "weekday":weekday, "city":city, "start":currentStart, "size":size};
}

function loadStore(){
	$(".loading").hide();
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
	var bkid = "";
	if(bids != null && bids.length > 0){
		bkid = parseInt(bids[0]);
	}
	var html = "";
	for (i in list){
		currentStart++;
		
		html += "<div class=\"shop_content_1\">";
		var  blogo = isEmpty(list[i].blogo) ? mainpath+"/images/default1.jpg" : list[i].blogo;
		html += "	<div class=\"pt\"><img src=\""+blogo+"\" alt=\""+list[i].bname+"\"></div>";
		html += "	<div class=\"con_1\">";
		html += "		<a href=\""+wecharauto2url("shop_detail.shtml?id="+list[i].id+"&bkid="+bkid+"&dist="+list[i].dist)+"\" class=\"s_name\">";
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

