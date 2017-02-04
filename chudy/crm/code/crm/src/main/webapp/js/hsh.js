//card list author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var bids = null; //银行id
var alias = decodeURI(urlparameter("alias")); //分类名称
var weekday = urlparameter("weekday");//星期数
var stime = null; //当前时间点
var bidspre = bids; //bids的前一个数据
var city = null;//城市
var from = urlparameter("from");//外链来源

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
	
	//初始化选择 类别
	if(notEmpty(alias)){
		$("#navConId>li:eq(0)").text(alias);
		$("#classify_param>li").each(function(){
			var txt = $(this).children().text();
			if(txt == alias){
				$(this).addClass("on").siblings().removeClass("on");
			}
		});
	}
	
	initConf();
	
	//惠生活
	$("#navConId>li").click(function(){
		var index = $(this).index();
		var moduleHei = $(".dropdown_wrapper>div.module_con").eq(index).find(".con_hei").height();
		if($(this).attr("class") == "active"){
			$(this).removeClass("active");
			$(".shade").addClass("dn");
			$(".dropdown_wrapper>div.module_con").css("height","0px");
			$(".onload_address").addClass("onload_bg");
		}else {
			$(this).addClass("active").siblings().removeClass("active");
			$(".shade").removeClass("dn");
			$(".dropdown_wrapper>div.module_con").eq(index).css("height",moduleHei).siblings().css("height","0px");
			$(".onload_address").removeClass("onload_bg");
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
		loadReward();
		changeURL();
		
	});

	$(".shade").click(function(){
        $("#navConId>li").removeClass("active");
        $(".shade").addClass("dn");
        $(".dropdown_wrapper>div.module_con").css("height","0px");
        $(".onload_address").addClass("onload_bg");
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
		$(".onload_address").addClass("onload_bg");
		
		loadReward();//加载优惠
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
		loadReward();//加载优惠
		$(".shade").trigger("click");
		changeURL();
	});
	
	browse_record(code);//记录访问统计
	
	//微信分享
	initWX();
	wx.ready(function(){
		share_WeChat();
	});
	
	//判断用户是否关注
	//judge_if_subscribe();
});


function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			if(notEmpty(uc.bids)){
				bids = uc.bids.split(",");
				bidspre = bids;
			}
			city = uc.city;
			initBids();//初始化银行卡
		}
		if(isEmpty(city)){
			city = "北京";
		}
		
		if(notEmpty(from)){//如果是外链来源
			city = "北京";
			bids = urlparameter("bids").split(",");
			bidspre = bids;
			initBids();//初始化银行卡
			alias = (alias == "全部分类") ? "" : txt;
		}
		
		loadReward();//加载优惠
	});
}

function updateConf_bids(){
	ajaxUtilAsync({"code":code, "bids":bids.join()}, mainpath+"/userConf/flushUserConf.shtml");
}


function initBids(){
	if(bids != null && bids != "" && bids.length > 0){
		for(i in bids){
			$("#xyk_ul>li[lang='"+bids[i]+"']").addClass("on");
		}
		$(".xyk_num").html(bids.length).show();
		$(".all_xyk_btn").removeClass("on");
	}
}

function changeURL(){
	var url = window.location.href ;
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

//初始化设置参数
function initpm(){
	return "?alias="+alias+"&weekday="+weekday;
}

//参数
function param(){
	return {"bids":bids, "alias":alias, "stime":stime, "weekday":weekday, "hot":1, "city":city, "start":currentStart, "size":size};
}

function loadReward(){
	$(".loading").hide();
	currentStart = 0;
	printUtil(".hsh_list", param(), mainpath+"/reward/listRewardHot.shtml", printReward);
	if (currentStart == size){
		$(".moreBtn").show();
	}
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
		html += "	<a href=\""+wecharauto2url("hsh_detail.shtml?id="+list[i].id)+"\">";
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

