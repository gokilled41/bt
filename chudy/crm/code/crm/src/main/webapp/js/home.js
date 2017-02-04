//home list author Andy
var code = urlparameter("code"); //WeChat auth code
var currentStart = 0;
var size = 5;
var bids = new Array(); //银行id
var reflng = null;//经度
var reflat = null;//纬度
var stime = null; //当前时间点
var weekday = null;//星期数
var positionfrom = null;//定位来源
var city = null;//城市
var range = 3000; //距离范围3km

$(document).ready(function(){
	stime = new Date().format("yyyy-MM-dd") + " 00:00:00"; 
	var day = new Date().getDay();
	if(isEmpty(weekday)){
		weekday = (day == 0) ? 7 : day; 
	}
	
	if(notEmpty(city)){
		$(".address>a").text(city);
	}
	
	initConf();//初始化加载
	initWX();
	geocoder();
	
	loadSubject(); //加载专题
	
	browse_record(code);//记录访问统计
	
	//判断用户是否关注
	judge_if_subscribe();
	
});

//定位显示城市
function showCity(){
	if(notEmpty(reflng) && notEmpty(reflat)){
		$.ajax({
			type: "GET",
			url: "http://api.map.baidu.com/geocoder/v2/?ak=7196a26b2b27b3c9efbb47d2cc9b71f9&callback=renderReverse&location="+reflat+","+reflng+"&output=json&coordtype=gcj02ll",
			dataType : "jsonp",
			async : false,
			success : function(locals){
				if(locals.status == 0)
					city = locals.result.addressComponent.city;
				if(notEmpty(city)){
					var len = city.length - 1;
					if(city.lastIndexOf("市") == len){
						city = city.substring(0, len);
						
						if(notEmpty(city)){
							$(".address>a").text(city);
						}
						loadStore();//加载店铺
						updateConf_city();
					}
				}
			}
		});
	}
}


function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			if(notEmpty(uc.bids)){
				bids = uc.bids.split(",");
			}
			reflat = uc.lat;
			reflng = uc.lng;
			city = uc.city;
			positionfrom = uc.positionfrom;
			if(notEmpty(city)){
				$(".address>a").text(city);
				loadStore();//加载店铺
			}else{
				if(positionfrom < 1){//当且仅当进入公众号获取地理位置时加载
					showCity();
				}
			}
		}
	});
}

function updateConf_lnglat(){
	ajaxUtilAsync({"code":code, "lng":reflng, "lat":reflat, "positionfrom":positionfrom}, mainpath+"/userConf/flushUserConf.shtml");
}

function updateConf_city(){
	ajaxUtilAsync({"code":code, "city":city}, mainpath+"/userConf/flushUserConf.shtml");
}

function geocoder() {
	wx.ready(function(){
		
		if(isEmpty(positionfrom) || positionfrom == 1){
			//如果是网页定位
			wx.getLocation({
				success: function (res) {
					reflng = res.longitude; // 经度，浮点数，范围为180 ~ -180。
					reflat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
					positionfrom = 1;
					if(isEmpty(city)){
						showCity();
					}else{
						loadStore();//加载店铺
					}
					updateConf_lnglat();
				}
			});
		}
		
		share_WeChat();
	});
}

//附件优惠店铺参数
function param(){
	return {"bids":bids, "reflng":reflng, "reflat":reflat, "stime":stime, "weekday":weekday, "city":city, "range":range};
}

function loadStore(){
	printUtil(".ljx_con", param(), mainpath+"/store/listStoreRange.shtml", printStore, true);
}

function printStore(response){
	var list = response.list;
	var html = "";
	var rewardsize = String(response.size);
	for (i in list){
		currentStart++;
		if(i < 4){
			var  blogo = isEmpty(list[i].blogo) ? mainpath+"/images/default1.jpg" : list[i].blogo;
			html += "<li><a href=\""+wecharauto2url("shop_detail.shtml?id="+list[i].id+"&bkid=&dist="+list[i].dist)+"\"><img src=\""+blogo+"\" alt=\""+list[i].bname+"\"></a></li>";
		}
	}
	
	var rdhtml = "";
	for(var i=0; i<rewardsize.length; i++){
		rdhtml += ("<span>" + rewardsize[i] + "</span>");
	}
	$(".yh_num").html(rdhtml);
	return html;
}

//专题参数
function paramsj(){
	return {"code":code, "status":1, "start":currentStart, "size":size};
}

function loadSubject(){
	currentStart = 0;
	printUtil(".home_content>ul", paramsj(), mainpath+"/subject/listSubject.shtml", printSubject, true);
	if (currentStart == size){
		$(".moreBtn").show();
	}
}

function appendSubject(){
	var beforeStart = currentStart;
	appendUtil(".home_content>ul", paramsj(), mainpath+"/subject/listSubject.shtml", printSubject, true);
	if ((beforeStart + size) > currentStart){
		$(".moreBtn").hide();
	}
}

function printSubject(response){
	var html = "";
	var list = response.list;
	for (i in list){
		currentStart++;
		
		html += "<li>";
		html += "	<div class=\"zt_con\">";
		html += "		<a href=\""+wecharauto2url("subject_detail.shtml?id="+list[i].id)+"\">";
		html += "			<div class=\"pt\">";
		html += "				<img src=\""+list[i].img+"\" alt=\""+list[i].title+"\"/>";
		html += "			</div>";
		html += "		</a>";
		var cname = "";
		if(notEmpty(list[i].userSubject)){
			cname = "on";
		}
		html += "		<p class=\"icon\"><span onclick=\"zanfun("+list[i].id+", this)\" class=\"ic_2 ic_zan "+cname+"\">"+list[i].scount+"</span></p>";
		html += "	</div>";
		html += "</li>";
		
	}
	
	return html;
}

//点赞
function zanfun(sid, zan){
	var num = $(zan).html();
	if($(zan).hasClass("on")){
		$(zan).removeClass("on").html(parseInt(num)-1);
		ajaxUtil({"code":code, "sid":sid}, mainpath+"/subject/delUserSubject.shtml");
	}else{
		$(zan).addClass("on").html(parseInt(num)+1);
		ajaxUtil({"code":code, "sid":sid}, mainpath+"/subject/addUserSubject.shtml");
	}
}


function selectAddress(){
	var url = encodeURIComponent("home.shtml");
	window.location.href = wecharauto2url("city_list.shtml?url="+url);
}

//转到热门活动
function index_reward(alias){
	window.location.href = wecharauto2url("hsh.shtml?alias="+alias);
}

//转到约身边
function index_shop(){
	window.location.href = wecharauto2url("index.shtml?weekday="+weekday);
}

//转到关注
function attention(){
	window.location.href = wecharauto2url("attention_shop.shtml");
}

//到搜索店铺
function search_store(){
	window.location.href = wecharauto2url("search_shop.shtml");
}

//绑定银行
function bind_bank(){
	window.location.href = wecharauto2url("attention_bank.shtml");
}

//卡列表
function return_card_list(){
	window.location.href = wecharauto2url("card_list.shtml");
}
