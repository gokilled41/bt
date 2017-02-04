//card list author Andy
var currentStart = 0;
var size = 10;
var bid = urlparameter("bid");
var cardtype = decodeURI(urlparameter("cardtype"));

$(document).ready(function(){
	
	loadBank();
	
	//初始化选择
	if(cardtype != null && cardtype != ""){
		$("#navConId>li:eq(1)").text(cardtype);
		$("#grandlist>li").each(function(){
			var txt = $(this).children().text();
			if(txt == cardtype){
				$(this).addClass("on").siblings().removeClass("on");
			}
		});
	}
	if(bid != null && bid != ""){
		var txt = $("#banklist>li[lang='"+bid+"']").children().text();
		$("#navConId>li:eq(0)").text(txt);
		$("#banklist>li[lang='"+bid+"']").addClass("on").siblings().removeClass("on");
	}
	
	loadCard();
	
	// 么卡
	$("#navConId>li").click(function(){
		var index = $(this).index();
		if(index == 2){
			return false;
		}
		var moduleHei = $(".dropdown_wrapper>div.dropdown_module").eq(index).find("ul").height();
		if($(this).attr("class") == "active"){
			$(this).removeClass("active");
			$(".shade").addClass("dn");
			$(".dropdown_wrapper>div.dropdown_module").css("height","0px");
		}else {
			$(this).addClass("active").siblings().removeClass("active");
			$(".shade").removeClass("dn");
			$(".dropdown_wrapper>div.dropdown_module").eq(index).css("height",moduleHei).siblings().css("height","0px");
		}
	});

	$(".shade").click(function(){
        $("#navConId>li").removeClass("active");
        $(".shade").addClass("dn");
        $(".dropdown_wrapper>div.dropdown_module").css("height","0px");
    });
	
	//银行
	$("#banklist>li").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
		bid = $(this).attr("lang");
		var bank = $(this).children().text();
		$("#navConId>li:eq(0)").text(bank);
		loadCard();
		$(".shade").trigger("click");
		changeURL();
	});
	
	//分类
	$("#grandlist>li").click(function(){
		$(this).addClass("on").siblings().removeClass("on");
		cardtype = $(this).children().text();
		$("#navConId>li:eq(1)").text(cardtype);
		if(cardtype == "全部功能"){
			cardtype = null;
		}
		loadCard();
		$(".shade").trigger("click");
		changeURL();
	});
	
	browse_record(code);//记录访问统计
});

function changeURL(){
	var url = window.location.href ;
	window.history.pushState({}, 0, url.substr(0,url.indexOf("?"))+ initpm());		
}

//初始化设置参数
function initpm(){
	return "?bid="+bid+"&cardtype="+cardtype;
}

function loadBank(){
	printUtil("#banklist", {}, mainpath+"/bank/listBank.shtml", printBank);
}

function printBank(response){
	var list = response.list;
	var html = "";
	html += "<li class=\"on\"><a href=\"javascript:void(0)\">全部银行</a></li>";
	for (i in list){
		html += "<li lang=\""+list[i].id+"\"><a href=\"javascript:void(0)\" >"+list[i].name+"</a></li>";
	}
	return html;
}


function loadCard(){
	currentStart = 0;
	printUtil(".card_list_tab", {"bid":bid, "cardtype":cardtype, "start":currentStart, "size":size}, mainpath+"/card/listCards.shtml", printCard);
	if (currentStart == size){
		$(".moreBtn").show();
	}
}

function appendCard(){
	var beforeStart = currentStart;
	appendUtil(".card_list_tab", {"bid":bid, "cardtype":cardtype, "start":currentStart, "size":size}, mainpath+"/card/listCards.shtml", printCard);
	if ((beforeStart + size) > currentStart){
		$(".moreBtn").hide();
	}
}

function printCard(response){
	var html = "";
	var list = response.list;
	
	for (i in list){
		currentStart++;
		
		if(i % 2 == 0){
			html += "</tr>";
			html += "<tr>";
		}
		html += "	<td>";
		html += "		<a href=\"card_detail.shtml?id="+list[i].id+"\">";
		html += "			<p class=\"pt\">";
		html += "				<span class=\"name\">"+list[i].name+"</span><img src=\""+list[i].img+"\" alt=\""+list[i].name+"\">";
		html += "			</p>";
		html += "			<p class=\"wz\">"+list[i].mainreward+"</p>";
		html += "		</a>";
		html += "	</td>";
		
	}
	
	if(list == null || list.length < 1){
		$(".moreBtn").hide();
	}else{
		$(".moreBtn").show();
	}
	
	html += "</tr>";
	return html;
}


