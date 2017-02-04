//hotkeys common author Andy

$(document).ready(function(){
	// 快捷键
	$(".add_link").click(function(){
		if($(this).hasClass("close_link")){
			$(this).removeClass("close_link");
			$(".fixed_bg").addClass("dn");
			$(".sm_link").removeClass("on");
		}else{
			$(this).addClass("close_link");
			$(".fixed_bg").removeClass("dn");
			$(".sm_link").addClass("on");
		}
	});
	$(".fixed_bg").click(function(){
		$(".fixed_bg").addClass("dn");
		$(".add_link").removeClass("close_link");
		$(".sm_link").removeClass("on");
	});
});

//转到关注商铺
function attention_store(){
	window.location.href = wecharauto2url("attention_shop.shtml");
}

//转到关注商铺
function attention_reward(){
	window.location.href = wecharauto2url("attention_reward.shtml");
}

//到搜索店铺
function search_store(){
	window.location.href = wecharauto2url("search_shop.shtml");
}

//到搜索优惠
function search_reward(){
	window.location.href = wecharauto2url("search_reward.shtml");
}

//转到首页
function return_home(){
	window.location.href = wecharauto2url("home.shtml");
}