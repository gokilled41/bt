//attention_reward author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 10;
var rids = null; //关注优惠ids

$(document).ready(function(){
	
	ajaxUtil({"code":code}, mainpath+"/attention/getAttention.shtml", function(response){
		var attention = response;
		if(attention != null){
			
			if(notEmpty(attention.rids)){
				rids = attention.rids.split(",");
				$(".m_con a:eq(1) .num").text(rids.length);
			}
			if(notEmpty(attention.sids)){
				$(".m_con a:eq(0) .num").text(attention.sids.split(",").length);
			}
			
			if(rids != null && rids.length > 0){
				loadReward();//初始化加载优惠
			}
		}
	});
	
	browse_record(code);//记录访问统计
});

//参数
function param(){
	return {"rids":rids, "start":currentStart, "size":size};
}

function loadReward(){
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

function attention(url){
	window.location.href = wecharauto2url(url);
}