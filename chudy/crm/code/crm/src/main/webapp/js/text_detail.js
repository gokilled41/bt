//text detail author Andy
var code = urlparameter("code"); //WeChat auth code
var id = urlparameter("id");
$(document).ready(function(){
	loadReward();
	
	browse_record(code);//记录访问统计
});

function loadReward(){
	ajaxUtil({"id": id}, mainpath+"/reward/getReward.shtml", printRewardDetail);
}

function printRewardDetail(response){
	var info = response;
	var html = "";
	
	if(info.description != null){
		if(info.description.indexOf("<p>") <0){
			html += "<p>"+info.description+"</p>";
		}else{
			html += ""+info.description+"";
		}
	}
	
	var auximg = info.auximg;
	var auximgArr = [];
	if (auximg != "" && auximg != null){
		auximgArr = auximg.split(",");
	}
	if(auximgArr.length > 0){
		for(i in auximgArr){
			html += "<p><a href=\""+auximgArr[i]+"\"><img src=\""+auximgArr[i]+"\" alt=\""+info.title+"\"></a></p>";
		}
	}
	
	$(".text_con").append(html);
}
