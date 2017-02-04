//attention_bank author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 30;

var bids = ""; //银行id
var bidspre = bids; //bids的前一个数据

$(document).ready(function(){
	
	initConf();
	
	$(".attention_bank_list li").click(function(){
		if($(this).hasClass("on")){
			$(this).removeClass("on");
		}else{
			$(this).addClass("on");
		}
	});
	
	$(".btn_1").click(function(){
		bids = new Array();
		$("ul>li.on").each(function(i, li){
			bids.push(parseInt($(li).attr("lang")));
		});
		if(bids != bidspre){
			updateConf_bids();//更新配置
		}
		window.location.href = wecharauto2url("home.shtml");
	});
	
	$(".btn_2").click(function(){
		window.location.href = wecharauto2url("home.shtml");
	});
	
	browse_record(code);//记录访问统计
});

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			if(notEmpty(uc.bids)){
				bids = uc.bids.split(",");
				bidspre = bids;
			}
		}
		
		//初始化加载  银行
		loadBank();
	});
}

//更新配置信息
function updateConf_bids(){
	ajaxUtilAsync({"code":code, "bids":bids.join()}, mainpath+"/userConf/flushUserConf.shtml");
}

function loadBank(){
	currentStart = 0;
	printUtil(".attention_bank_list ul", {}, mainpath+"/bank/listBank.shtml", printBank);
}

function appendBank(){
	appendUtil(".attention_bank_list ul", {}, mainpath+"/bank/listBank.shtml", printBank);
}

function printBank(response){
	var html = "";
	var list = response.list;
	for (i in list){
		currentStart++;
		
		var attendClass = "";
		for(j in bids){
			if(bids[j] == list[i].id){
				attendClass ="on";
			}
		}
		html += "<li lang=\""+list[i].id+"\" class=\""+attendClass+"\">";
		html += "<div class=\"cz_con\">";
		html += "	<span class=\"ic\"></span>";
		html += "	<div class=\"t_bg\"></div>";
		html += "</div>";
		html += "<p class=\"pt\"><img src=\""+list[i].img+"\" alt=\"\"></p>";
		html += "<p class=\"name\">"+list[i].name+"</p>";
		html += "</li>";	
	}
	return html;
}
