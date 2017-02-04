//reward detail author Andy
var code = urlparameter("code"); //WeChat auth code
var rid = urlparameter("id"); //reward id
var stime = null; //当前时间点
var today_week = null; //今天星期
var city = null; //所属城市

var attend_rids = new Array();//关注优惠ids

$(document).ready(function(){
	stime = new Date().format("yyyy-MM-dd") + " 00:00:00"; 
	var day = new Date().getDay();
	today_week = (day == 0) ? 7 : day; 
	
	
	//畅言sid设置
	$("#SOHUCS").attr("sid", rid);
	
	initConf();
	
	getAttention();
	
	//隐藏超过的店铺列表
	$(".pp_ul").each(function(i){
		var liLen = $(".pp_ul").eq(i).find("li").length;
		if(liLen <= 2){
			$(".pp_ul").eq(i).next().hide();
		}else{
			$(".pp_ul").eq(i).find("li:gt(1)").hide();
		}

		$(".pp_ul").eq(i).next().find(".moreNum").html(liLen-2);
	});
	
	$(".more_con_btn").click(function(){
		$(this).parent().find(".pp_ul>li").show();
		$(this).hide();
	});
	
	//关注
	$(".attention_btn").click(function(){
    	if($(this).hasClass("on")){
    		$(this).removeClass("on").html("关注");
    		updateAttention(0);
    	}else{
    		$(this).addClass("on").html("已关注");
    		updateAttention(1);
    	}
    });
	
	browse_record(code);//记录访问统计
	
	//微信分享
	initWX();
	
	//判断用户是否关注
	judge_if_subscribe();
	
});

//获取关注
function getAttention(){
	ajaxUtil({"code":code}, mainpath+"/attention/getAttention.shtml", function(response){
		var attention = response;
		if(attention != null){
			attend_rids = attention.rids.split(",");
			if(notEmpty(attend_rids)){
				//如果相同
				for(i in attend_rids){
					if(rid == attend_rids[i]){
						$(".attention_btn").addClass("on").html("已关注");
						break;
					}
				}
			}
		}
	});
}

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			city = uc.city;
			loadReward();//加载优惠
		}
	});
}

function updateAttention(flag){
	//flag > 0 时关注
	if(flag > 0){
		attend_rids.push(rid);
	}else{
		for(var i=0; i<attend_rids.length; i++){
			if(attend_rids[i] == rid){
				attend_rids.splice(i, 1);
				break;
			}
		}
	}
	
	ajaxUtil({"code":code, "rids":attend_rids.join()}, mainpath+"/attention/updateAttention.shtml");
}

function loadReward(){
	ajaxUtil({"id": rid, "city": city}, mainpath+"/reward/queryReward.shtml", printRewardDetail);
}

function printRewardDetail(response){
	var info = response;
	var html = "";
	html = "<div class=\"hsh_detail\">";
	html += "	<ul class=\"hsh_list\">";
	html += "		<li class=\"no_mar\">";
	var blogo = null;
	if(info.blogo != null){
		blogo = info.blogo.split(",")[1];
	}
	var hotimg = isEmpty(info.hotimg) ? mainpath+"/images/default.jpg" : info.hotimg;
	html += "			<p class=\"pt\"><img src=\""+hotimg+"\" alt=\""+info.title+"\"></p>";
	html += "			<div class=\"wz_con\">";
	html += "				<span class=\"ic_bank\"><img src=\""+(mainpath+blogo)+"\" height=\"17\" alt=\""+info.bname+"\"></span>";
	html += "				<p class=\"name\"><a href=\"javascript:void(0);\" class=\"attention_btn\">关注</a>"+info.title+"</p>";
	html += "			</div>";
	html += "		 </li>";
	html += "	</ul>";
	html += "	<div class=\"hsh_yh\">";
	html += "		<ul class=\"yh_ul\">";
	html += "			<li class=\"n_1\"><span>剩余"+getdays(info.endtime)+"天</span></li>";
	
	var weekday = info.weekday;
	var weeks = [];
	if(weekday != null && weekday > 0){
		weeks = weekday.toString().split("");
	}
	
	var todayspecial = false;
	if(weeks.length > 0){
		for(j in weeks){
			if(weeks[j] == today_week.toString()){
				todayspecial = false;
				break;
			}else{
				todayspecial = true;
			}
		}
	}
	
	if(!todayspecial){
		html += "	<li class=\"n_2\"><span>今日有优惠</span></li>";
	}else{
		html += "	<li class=\"n_5\"><span>今日无优惠</span></li>";
	}
	
	if(info.cards.length < info.ccount){
		html += "	<li class=\"n_4\"><span>限制卡种</span></li>";
	}else{
		html += "	<li class=\"n_3\"><span>不限卡种</span></li>";
	}
	html += "			<div class=\"clear\"></div>";
	html += "		</ul>";
	
	if(weeks.length > 0){
		var weekstr = "";
		for(j in weeks){
			if(j > 0){
				weekstr += "、";
			}
			weekstr += wday(weeks[j]);
		}
		
		html += "				<div class=\"bank_yh_time\">每周"+weekstr+" 有优惠哦~</div>";
	}
	
	html += "	</div>";
	
	var cards = info.cards;
	if(cards != null && cards.length > 0){
		html += "<div class=\"bank_detail\">";
		html += "	<div class=\"bank_con\" style=\"margin-bottom: 12px;border-bottom:none;\">";
		html += "		<div class=\"title\"><a href=\""+wecharauto2url("active_card_list.shtml?id="+info.id)+"\" class=\"more_btn\">参加活动的信用卡</a></div>";
		html += "		<div class=\"bank_pt\">";
		html += "			<ul>";
		
		var len = (cards.length > 4) ? 3 : cards.length;
		for(var j=0; j<len; j++){
			html += "						<li><a href=\""+wecharauto2url("card_detail.shtml?id="+cards[j].id)+"\"><img src=\""+cards[j].img+"\" alt=\""+cards[j].name+"\"></a></li>";
		}
		
		html += "			</ul>";
		html += "		</div>";
		html += "	</div>";
		html += "</div>";
	}
	
	
	html += "	<div class=\"bank_detail\">";
	
	if(info.special != null){
		html += "				<div class=\"bank_con\">";
		html += "					<div class=\"title\"><a href=\"javascript:void(0);\" style=\"background:none;\" class=\"more_btn\">特别提示</a></div>";
		html += "					<div class=\"wz_con_1\">";
		if(info.special.indexOf("<p>") <0){
			html += "						<p>"+info.special+"</p>";
		}else{
			html += info.special;
		}
		html += "					</div>";
		html += "				</div>";
	}
	
	
	
	html += "				<div class=\"bank_con con_bd\">";
	html += "					<div class=\"title\"><a href=\""+wecharauto2url("text_detail.shtml?id="+info.id)+"\" class=\"more_btn\">优惠详情</a></div>";
	html += "					<div class=\"wz_con_2\">";
	var auximg = info.auximg;
	var auximgArr = [];
	if (auximg != "" && auximg != null){
		auximgArr = auximg.split(",");
	}
	if(auximgArr.length > 0){
		html += "						<p><a href=\""+auximgArr[0]+"\"><img src=\""+auximgArr[0]+"\" alt=\""+info.title+"\"></a></p>";
	}
	
	if(info.description != null){
		var desc_len = (info.description.length > 300) ? 297 : info.description.length;
		if(info.description.indexOf("<p>") <0){
			html += "							<p>"+info.description.substr(0, desc_len)+"...</p>";
		}else{
			if(desc_len == 297){
				var patt = new RegExp("<p>","g");
				var result;
				var pindex = new Array();
				while ((result = patt.exec(info.description)) != null)  {
					pindex.push(result.index);
				}
				for(var i=pindex.length-1; i >=0; i--){
					if(pindex[i] <= 300){
						desc_len = pindex[i];
						break;
					}
				}
				html += info.description.substr(0, desc_len)+"...";
			}else{
				html += info.description.substr(0, desc_len);
			}
			 
		}
	}
	html += "					</div>";
	html += "				</div>";
	html += "			</div>";
	var brands = info.brands;
	if(brands != null && brands.length > 0){
		html += "	<div class=\"cypp_con\">";
		html += "		<div class=\"title\">参与品牌</div>";
		html += "		<ul class=\"pp_ul\">";
		
		for(j in brands){
			html += "			<li>";
			html += "				<a href=\""+wecharauto2url("shop_all.shtml?id="+info.id+"&bid="+brands[j].id)+"\">";
			html += "					<p class=\"name\">"+brands[j].name+"</p>";
			html += "					<p class=\"wz\">共"+brands[j].count+"家门店参与</p>";
			html += "				</a>";
			html += "			</li>";
			
		}
		
		html += "		</ul>";
		html += "		<a href=\"javascript:void(0);\" class=\"more_con_btn\"><span class=\"name\">查看其他<span class=\"moreNum\">3</span>个参与品牌</span></a>";
		html += "	</div>";
	}
	
	html += "</div>";
	html += "<a href=\""+wecharauto2url("wrong_info.shtml")+"\" class=\"yh_wrong_btn\"><span class=\"wz\">优惠信息报错</span></a>";
	
	$(".index_box").append(html);
	
	$("body").append("<script id=\"changyan_mobile_js\" charset=\"utf-8\" type=\"text/javascript\" src=\"http://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=cyrSlPrdY&conf=prod_aa2055d37ad116172a4d7ebc71fc4cff\"></script>");
	
	//微信分享
	wx.ready(function(){
		share_WeChat(info.title);
	});
}

