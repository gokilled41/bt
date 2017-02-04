//shop detail author Andy
var code = urlparameter("code"); //WeChat auth code
var id = urlparameter("id"); //shop id
var bkid = urlparameter("bkid"); //bank id
var dist = urlparameter("dist");//dist
var alias = urlparameter("alias");//选择分类
var stime = null; //当前时间点
var today_week = null; //今天星期
var city = null; //所属城市


var attend_sids = new Array();//关注商户ids

$(document).ready(function(){
	stime = new Date().format("yyyy-MM-dd") + " 00:00:00"; 
	var day = new Date().getDay();
	today_week = (day == 0) ? 7 : day; 
	
	//畅言sid设置
	$("#SOHUCS").attr("sid", id);
	
	initConf();
	
	getAttention();
	
	var swiper = new Swiper(".swiper-container", {
        pagination: ".swiper-pagination",
        slidesPerView: 4,
        spaceBetween: 0
    });

	$(".swiper-wrapper>div.swiper-slide").click(function(){
    	$(this).addClass("current").siblings().removeClass("current");
    	var index = $(this).index();
    	$("#bankContentId>div").eq(index).removeClass("dn").siblings().addClass("dn");
    });
	
	if(bkid != null){
		$(".swiper-wrapper>div.swiper-slide[lang='"+bkid+"']").trigger("click");
	}
	
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
	wx.ready(function(){
		share_WeChat();
	});
	
	//判断用户是否关注
	judge_if_subscribe();
});

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			city = uc.city;
			loadShop(); //加载店铺
		}
	});
}

function loadShop(){
	ajaxUtil({"id": id, "stime":stime, "city":city}, mainpath+"/store/detailStore.shtml", printShopDetail);
}

//获取关注
function getAttention(){
	ajaxUtil({"code":code}, mainpath+"/attention/getAttention.shtml", function(response){
		var attention = response;
		if(attention != null){
			attend_sids = attention.sids.split(",");
			if(notEmpty(attend_sids)){
				//如果相同
				for(i in attend_sids){
					if(id == attend_sids[i]){
						$(".attention_btn").addClass("on").html("已关注");
						break;
					}
				}
			}
		}
	});
}

function updateAttention(flag){
	//flag > 0 时关注
	if(flag > 0){
		attend_sids.push(id);
	}else{
		for(var i=0; i<attend_sids.length; i++){
			if(attend_sids[i] == id){
				attend_sids.splice(i, 1);
				break;
			}
		}
	}
	
	ajaxUtil({"code":code, "sids":attend_sids.join()}, mainpath+"/attention/updateAttention.shtml");
}


function printShopDetail(response){
	var info = response;
	var html = "";
	html += "<div class=\"con_2 mar_1\">";
	var  blogo = isEmpty(info.blogo) ? mainpath+"/images/default1.jpg" : info.blogo;
	html += "	<div class=\"pt\"><img src=\""+blogo+"\" alt=\""+info.bname+"\"></div>";
	html += "	<div class=\"con\">";
	html += "		<p class=\"name\"><a href=\"javascript:void(0);\" class=\"attention_btn\">关注</a>"+info.name+"</p>";
	html += "		<p class=\"wz\"><span class=\"right\">"+distanceShow(dist)+"</span>"+info.calias+"</p>";
	html += "	</div>";
	html += "	<div class=\"clear\"></div>";
	html += "</div>";
	
	
	html += "<div class=\"con_tel mar_1\">";
	var phone = info.phone;
	var phoneArr = [];
	if(phone != null){
		phoneArr = phone.split(",");
	}
	html += "	<a href=\"tel:"+phoneArr[0]+"\" class=\"tel_btn\">"+phoneArr[0]+"</a>";
	html += "	<div class=\"address_wz\"><a href=\""+wecharauto2url("map.shtml?id="+info.id)+"\">"+info.address+"</a></div>";
	html += "</div>";
	
	var rewards = info.rewards;
	html += "<div class=\"bank_slide\">";
	html += "	<div class=\"swiper-container\">";
	html += "    	<div class=\"swiper-wrapper\">";
	
	for(i in rewards){
		var currClass = "";
		if(i == 0){
			currClass = " current";
		}
		
		html += "			<div lang=\""+rewards[i].bid+"\" class=\"swiper-slide "+currClass+"\"><a href=\"javascript:void(0);\">"+rewards[i].bname+"</a></div>";
	}
	
	html += "		</div>";
	html += "	</div>";
	html += "	<div id=\"bankContentId\">";
	
	for(i in rewards){
		var detailClass = "";
		if(i > 0){
			detailClass = " dn";
		}
		
		html += "		<div class=\"bank_detail  "+detailClass+"\">";
		html += "			<div class=\"djj_con\">";
		html += "				<a href=\"javascript:void(0);\">";
		var rimg = isEmpty(rewards[i].img) ? mainpath+"/images/default1.jpg" : rewards[i].img;
		html += "					<div class=\"pt\"><img src=\""+rimg+"\" alt=\""+rewards[i].title+"\"></div>";
		html += "					<div class=\"con\">";
		html += "						<p>"+rewards[i].title+"</p>";
		
		var weekday = rewards[i].weekday;
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
		
		html += "					</div>";
		html += "				</a>";
		html += "			</div>";
		
		html += "<ul class=\"bank_yh_ul\">";
		html += "	<li class=\"n_1\"><span>剩余"+getdays(rewards[i].endtime)+"天</span></li>";
		if(!todayspecial){
			html += "	<li class=\"n_2\"><span>今日有优惠</span></li>";
		}else{
			html += "	<li class=\"n_5\"><span>今日无优惠</span></li>";
		}
		if(rewards[i].cards.length < rewards[i].ccount){
			html += "<li class=\"n_4\"><span>限制卡种</span></li>";
		}else{
			html += "<li class=\"n_3\"><span>不限卡种</span></li>";
		}
		
		html += "	<div class=\"clear\"></div>";
		html += "</ul>";
		
		
		if(weeks.length > 0){
			var weekstr = "";
			for(j in weeks){
				if(j > 0){
					weekstr += "、";
				}
				weekstr += wday(weeks[j]);
			}
			
			html += "			<div class=\"bank_yh_time\">每周"+weekstr+" 有优惠哦~</div>";
		}
		
		html += "			<div class=\"bank_con\">";
		html += "				<div class=\"title\"><a href=\""+wecharauto2url("active_card_list.shtml?id="+rewards[i].id)+"\" class=\"more_btn\">参加活动的信用卡</a></div>";
		html += "				<div class=\"bank_pt\">";
		html += "					<ul>";
		
		var cards = rewards[i].cards;
		var len = (cards.length > 4) ? 3 : cards.length;
		for(var j=0; j<len; j++){
			html += "						<li><a href=\""+wecharauto2url("card_detail.shtml?id="+cards[j].id)+"\"><img src=\""+cards[j].img+"\" alt=\""+cards[j].name+"\"></a></li>";
		}
		html += "					</ul>";
		html += "				</div>";
		html += "			</div>";
		if(rewards[i].special != null){
			html += "			<div class=\"bank_con\">";
			html += "				<div class=\"title\"><a href=\"javascript:void(0);\" style=\"background:none;\" class=\"more_btn\">特别提示</a></div>";
			html += "				<div class=\"wz_con_1\">";
			if(rewards[i].special.indexOf("<p>") <0){
				html += "					<p>"+rewards[i].special+"</p>";
			}else{
				html += rewards[i].special;
			}
			html += "				</div>";
			html += "			</div>";
			
		}
		html += "			<div class=\"bank_con con_bd\">";
		html += "				<div class=\"title\"><a href=\""+wecharauto2url("text_detail.shtml?id="+rewards[i].id)+"\" class=\"more_btn\">优惠详情</a></div>";
		html += "				<div class=\"wz_con_2\">";
		var auximg = rewards[i].auximg;
		var auximgArr = [];
		if (auximg != "" && auximg != null){
			auximgArr = auximg.split(",");
		}
		if(auximgArr.length > 0){
			html += "					<p><a href=\""+auximgArr[0]+"\"><img src=\""+auximgArr[0]+"\" alt=\""+rewards[i].title+"\"></a></p>";
		}
		
		if(rewards[i].description != null){
			var desc_len = (rewards[i].description.length > 300) ? 297 : rewards[i].description.length;
			if(rewards[i].description.indexOf("<p>") <0){
				html += "						<p>"+rewards[i].description.substr(0, desc_len)+"...</p>";
			}else{
				if(desc_len == 297){
					var patt = new RegExp("<p>","g");
					var result;
					var pindex = new Array();
					while ((result = patt.exec(rewards[i].description)) != null)  {
						pindex.push(result.index);
					}
					for(var j=pindex.length-1; j >=0; j--){
						if(pindex[j] <= 300){
							desc_len = pindex[j];
							break;
						}
					}
					html += rewards[i].description.substr(0, desc_len)+"...";
				}else{
					html += rewards[i].description.substr(0, desc_len);
				}
				
				
			}
		}
		html += "				</div>";
		html += "			</div>";
		html += "			<a href=\""+wecharauto2url("shop_all.shtml?id="+rewards[i].id+"&city="+city)+"\" class=\"more_shop_btn\"><span class=\"name\">查看全部"+rewards[i].scount+"家适用店铺</span></a>";
		html += "		</div>";
		
	}
	
	html += " </div>";
	html += "</div>";
	
	
	html += "<a href=\""+wecharauto2url("wrong_info.shtml")+"\" class=\"yh_wrong_btn\"><span class=\"wz\">优惠信息报错</span></a>";
	$(".shop_detail").append(html);
	
	$("body").append("<script id=\"changyan_mobile_js\" charset=\"utf-8\" type=\"text/javascript\" src=\"http://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=cyrSlPrdY&conf=prod_aa2055d37ad116172a4d7ebc71fc4cff\"></script>");
}

