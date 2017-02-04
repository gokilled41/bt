//city_list author Andy
var  code = urlparameter("code"); //WeChat auth code
var  url = urlparameter("url");
var  reflng = null;
var  reflat = null;
var  city = "";

$(document).ready(function(){
	
	initConf();
	
	browse_record(code);//记录访问统计
});

function initConf(){
	ajaxUtil({"code":code}, mainpath+"/userConf/getUserConf.shtml", function(response){
		var uc = response;
		if(uc != null){
			reflat = uc.lat;
			reflng = uc.lng;
			show_city();
		}
	});
}

function show_city(){
	$.ajax({
		type: "GET",
		url: "http://api.map.baidu.com/geocoder/v2/?ak=7196a26b2b27b3c9efbb47d2cc9b71f9&callback=renderReverse&location="+reflat+","+reflng+"&output=json&coordtype=gcj02ll",
		dataType : "jsonp",
		success : function(locals){
			if(locals.status == 0){
				city = locals.result.addressComponent.city;
				var len = city.length-1;
				if(city.lastIndexOf("市") == len){
					city = city.substring(0, len);
				}
				if(notEmpty(city)){
					$("#localcity").text(city);
				}else{
					$("#localcity").parent().hide();
					$(".city_none").show();
				}
				
			}
		}
	});
}


$("li>a").click(function(){
	city = $(this).text();
	updateConf_city();
	window.location.href = wecharauto2url(decodeURIComponent(url) );
	
});

function updateConf_city(){
	ajaxUtilAsync({"code":code, "city":city}, mainpath+"/userConf/flushUserConf.shtml");
}
