//wrong_info author Andy
var code = urlparameter("code"); //WeChat auth code
$(document).ready(function(){
	
	browse_record(code);//记录访问统计
});

//弹出框
function popRemind(id, childId){
	$("#" + id).css({display:"block",height:document.body.clientHeight});

	var popLeft = (document.body.clientWidth - $("#"+childId).width()) / 2;
	$("#"+childId).css("left", popLeft +"px");
	
	var popTop = document.documentElement.clientHeight/2 + document.body.scrollTop - 120;
	$("#"+childId).css("top", popTop +"px");
}

// 关闭弹出框
function closePopFunc(obj){
	$(obj).parents(".wrapper").css("display","none");
	window.history.go(-1);
}

function addFeedback(){
	var content = $.trim($("#content").val());
	var param = {"content":content, "code":code};
	if (content != null && content != ""){
		$(".bz_wz").hide();
		ajaxUtil(param, mainpath+"/feedback/addFeedback.shtml", function(response){
			if(response == 1){
				popRemind('wrapperId', 'conId');
				$("#content").val("");
			}else{
				popupMsg("提交失败，请您稍后再试。");
			}
		}, function(response){
			popupMsg("提交失败，请稍后再试。");
		});
	}else{
		$(".bz_wz").html("请填写您的建议").show();
	}
}


function cancel(){
	$("#content").val("");
	window.history.go(-1);
}
