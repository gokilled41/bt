var path = "crm";
var mainpath = "/crm";
var appid = "wx76e3f8dce097a019";
var urlhost = "http://www.crme.cn/crm/";
var urlpre = urlhost+"page/";
$(document).ready(function() {
	$(".refresher").click(function() {
		refresh();
	});
	$("#goback").click(function() {
		goback();
	});
	
});
function popupMsg(msg) {
	alert(msg);
}
function printUtilViaGet(panel, requestdata, ajaxurl, printfunction) {
	$.ajax({
		type : 'GET',
		url : ajaxurl,
		data : requestdata,
		cache : false,
		dataType : "json",
		async : false,
		success : function(response) {
			if (response.code) {
				if (panel != null && panel.length > 0) {
					$(panel).html("");
					if (printfunction != null)
						$(panel).html(printfunction(response.response));
				}
				return true;
			} else {
				// alert(response.reason);
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
			// alert("call complete");
		}
	});
	return false;
}

function ajaxUtilViaGet(requestdata, ajaxurl, succFunction, failFunction) {
	$.ajax({
		url : ajaxurl,
		type : "GET",
		dataType : "json",
		cache : false,
		data : requestdata,
		async : false,
		success : function(response) {
			if (response.code) {
				if (succFunction != null)
					succFunction(response.response);
			} else {
				if (failFunction != null)
					failFunction(response.response);
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
		}
	});
	return false;
}
function printUtil(panel, requestdata, ajaxurl, printfunction, ajaxasync) {
	if (isEmpty(ajaxasync)) {
		ajaxasync = false;
	}
	$.ajax({
		type : 'POST',
		url : ajaxurl,
		data : requestdata,
		cache : false,
		dataType : "json",
		async : ajaxasync,
		success : function(response) {
			if (response.code) {
				if (panel != null && panel.length > 0) {
					$(panel).html("");
					if (printfunction != null)
						$(panel).html(printfunction(response.response));
				}
				return true;
			} else {
				// alert(response.reason);
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
			// alert("call complete");
		}
	});
	return false;
}
function appendUtil(panel, requestdata, ajaxurl, printfunction, ajaxasync) {
	if (isEmpty(ajaxasync)) {
		ajaxasync = false;
	}
	$.ajax({
		type : 'POST',
		url : ajaxurl,
		data : requestdata,
		cache : false,
		dataType : "json",
		async : ajaxasync,
		success : function(response) {
			if (response.code) {
				if (panel != null && panel.length > 0) {
					if (printfunction != null)
						$(panel).append(printfunction(response.response));
				}
				return true;
			} else {
				// alert(response.reason);
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
			// alert("call complete");
		}
	});
	return false;
}

function ajaxUtilAsync(requestdata, ajaxurl, succFunction, failFunction) {
	$.ajax({
		url : ajaxurl,
		type : "POST",
		dataType : "json",
		cache : false,
		data : requestdata,
		async : true,
		success : function(response) {
			if (typeof response.code == "number") {
				if (response.code > 0) {
					if (succFunction != null)
						succFunction(response.response);
				} else {
					if (failFunction != null)
						failFunction(response.response);
				}
			} else {
				if (response.result) {
					if (succFunction != null)
						succFunction(response.response);
				} else {
					if (failFunction != null)
						failFunction(response.response);
				}
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
		}
	});
	return false;
}

function ajaxUtil(requestdata, ajaxurl, succFunction, failFunction) {
	$.ajax({
		url : ajaxurl,
		type : "POST",
		dataType : "json",
		cache : false,
		data : requestdata,
		async : false,
		success : function(response) {
			if (typeof response.code == "number") {
				if (response.code > 0) {
					if (succFunction != null)
						succFunction(response.response);
				} else {
					if (failFunction != null)
						failFunction(response.response);
				}
			} else {
				if (response.result) {
					if (succFunction != null)
						succFunction(response.response);
				} else {
					if (failFunction != null)
						failFunction(response.response);
				}
			}
		},
		error : function(x, e) {
			// alert("error", x);
		},
		complete : function(x) {
		}
	});
	return false;
}
function loadSelection(panel, requestdata, ajaxurl, itemName) {
	ajaxUtil(requestdata, ajaxurl, function(response) {
		var list = response.list;
		for ( var i = 0; i < list.length; i++) {
			$(panel).append(
					"<option value='" + list[i][itemName] + "'>"
							+ list[i][itemName] + "</option>");
		}
	}, null);
}
function ajaxSubmitRefresh(formId) {
	var hideForm = $(formId);
	var options = {
		dataType : "json",
		beforeSubmit : function() {
		},
		success : function(result) {
			if (result.result) {
				showMsg("提交成功");
			} else {
				alert("提交失败�?");
			}
		},
		error : function(result) {
			alert("提交失败�?");
		}
	};
	hideForm.ajaxSubmit(options);
}
function ajaxSubmitWithJump(formId, nextPage) {
	var hideForm = $(formId);
	var options = {
		dataType : "json",
		beforeSubmit : function() {
		},
		success : function(result) {
			if (result.result) {
				alert("提交成功");
				window.location.href = nextPage;
			} else {
				alert("提交失败�?");
			}
		},
		error : function(result) {
			alert("提交失败�?");
		}
	};
	hideForm.ajaxSubmit(options);
}
function refresh() {
	window.location.href = window.location.href;
}
function goback() {
	history.go(-1);
}
function urlparameter(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {};
	for ( var i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length);
	}
	var returnValue = paraObj[paras.toLowerCase()];
	if (typeof (returnValue) == "undefined") {
		return "";
	} else {
		return returnValue;
	}
}
String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substring(this.length - str.length) == str)
		return true;
	else
		return false;
	return true;
};

String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0
			|| str.length > this.length)
		return false;
	if (this.substr(0, str.length) == str)
		return true;
	else
		return false;
	return true;
};

function getFileUrl(sourceId) {
	var url = "";
	if (navigator.userAgent.indexOf("MSIE") >= 1) { // IE
		url = document.getElementById(sourceId).value;
	} else if (navigator.userAgent.indexOf("Firefox") > 0) { // Firefox
		url = window.URL
				.createObjectURL(document.getElementById(sourceId).files
						.item(0));
	} else if (navigator.userAgent.indexOf("Chrome") > 0) { // Chrome
		url = window.URL
				.createObjectURL(document.getElementById(sourceId).files
						.item(0));
	}
	return url;
}

function preImg(sourceId, targetId) {
	var url = getFileUrl(sourceId);
	var imgPre = document.getElementById(targetId);
	imgPre.src = url;
}

function initWX() {
	$.ajax({
		url : mainpath + '/wechatjs.shtml',
		type : 'POST',
		dataType : 'json',
		async : false,
		data : {
			url : location.href.split('#')[0]
		},
		success : function(result) {
			console.log(result);
			var data = result['response']['map'];
			if (result['code'] == 1) {
				wx.config({
					debug : false,
					appId : data['appId'],
					timestamp : data['timestamp'],
					nonceStr : data['nonceStr'],
					signature : data['signature'],
					jsApiList : [ 'onMenuShareTimeline',
							'onMenuShareAppMessage', 'onMenuShareQQ',
							'onMenuShareWeibo', 'getLocation', 'openLocation' ]
				});
			} else {
				alert("fail to get code");
				window.alert('fail');
			}
			;
		}
	});
}
var EARTH_RADIUS = 6378137.0; // 单位M
var PI = Math.PI;

function getRad(d) {
	return d * PI / 180.0;
}
function getGreatCircleDistance(lat1, lng1, lat2, lng2) {
	var radLat1 = getRad(lat1);
	var radLat2 = getRad(lat2);

	var a = radLat1 - radLat2;
	var b = getRad(lng1) - getRad(lng2);

	var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
			+ Math.cos(radLat1) * Math.cos(radLat2)
			* Math.pow(Math.sin(b / 2), 2)));
	s = s * EARTH_RADIUS;
	s = Math.round(s * 10000) / 10000.0;
	s = Math.round(s);
	return s;
}
// 对Date的扩展，�? Date 转化为指定格式的String
// �?(M)、日(d)、小�?(h)、分(m)、秒(s)、季�?(q) 可以�? 1-2 个占位符�?
// �?(y)可以�? 1-4 个占位符，毫�?(S)只能�? 1 个占位符(�? 1-3 位的数字)
// 例子�?
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // �?
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // �?
		"s+" : this.getSeconds(), // �?
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

// 换算公里�?
function distanceShow(dist) {
	if(isEmpty(dist)){
		return dist;
	}
	var showDistance = "";
	if (dist > 1000) {
		var km = parseInt(dist / 1000);
		var m = parseInt(dist % 1000).toString().substr(0, 2);
		showDistance = km + "." + m + "km";
	} else {
		showDistance = dist + "m";
	}
	return showDistance;
}

// 判断为空
function isEmpty(src) {
	if (("undefined" == typeof src) || (src == null) || ($.trim(src) == "")) {
		return true;
	}
	return false;
}

// 判断不为�?
function notEmpty(src) {
	return !isEmpty(src);
}

// 微信页面授权 snsapi_base方式
function wecharauto2url(url) {
	return wecharauto2hosturl(urlpre + url);
	
}
//页面授权针对url
function wecharauto2hosturl(url) {
	return "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid
	+ "&redirect_uri=" + encodeURIComponent(url)
	+ "&response_type=code&scope=snsapi_base&state=crm#wechat_redirect";
}


// 获取星期�?
function wday(d) {
	if (d == "7") {
		return "�?";
	} else if (d == "6") {
		return "�?";
	} else if (d == "5") {
		return "�?";
	} else if (d == "4") {
		return "�?";
	} else if (d == "3") {
		return "�?";
	} else if (d == "2") {
		return "�?";
	} else if (d == "1") {
		return "�?";
	}
}

// 获取两个时间的日期差
function getdays(edate) {
	var sdate = new Date(stime.replace(/\-/g, "/"));
	var diff = edate - sdate.getTime();
	var day = 0;
	day = diff / (24 * 60 * 60 * 1000);
	return day;
}

// 访问统计
function browse_record(code) {
	var url = window.location.href;
	url = url.substring(0, url.indexOf("code") - 1);// 截取code以及之后的数�?
	ajaxUtilAsync({
		"code" : code,
		"url" : url
	}, mainpath + "/record/browse.shtml");
}

//判断用户是否关注微信�?
function judge_if_subscribe(){
	ajaxUtilAsync({"code":code}, mainpath+"/userConf/subscribe.shtml", function(response){
		var subflag = response;
		if(isEmpty(subflag) || subflag == "0"){
			//未关�?
			$(".wrapper_code").show();
		}
	});
}

// 关闭扫描关注弹出�?
function close_subscribe_tip(obj) {
	$(obj).parents(".wrapper_code").hide();
}

// 微信分享 此方法需放在wx.ready�?
function share_WeChat(title, imgUrl, desc, link) {
	if (isEmpty(title)) {
		title = "约惠�?";
	}

	if (isEmpty(desc)) {
		desc = "信用卡优�?   弹指间定�?";
	}

	if (isEmpty(link)) {
		var url = window.location.href;
		url = url.substring(0, url.indexOf("code") - 1);// 截取code以及之后的数�?
		link = wecharauto2hosturl(url);
	}

	if (isEmpty(imgUrl)) {
		imgUrl = urlhost + "/images/crm-logo-04large.jpg";
	}

	wx.onMenuShareTimeline({
		title : title, // 分享标题
		link : link, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
			// 用户确认分享后执行的回调函数
			alert("分享成功");
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
		}
	});

	// 分享给朋�?
	wx.onMenuShareAppMessage({
		title : title, // 分享标题
		desc : desc, // 分享描述
		link : link, // 分享链接
		imgUrl : imgUrl, // 分享图标
		type : 'link', // 分享类型,music、video或link，不填默认为link
		dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
		success : function() {
			// 用户确认分享后执行的回调函数
			alert("分享成功");
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
		}
	});

	// 分享到QQ
	wx.onMenuShareQQ({
		title : title, // 分享标题
		desc : desc, // 分享描述
		link : link, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
			// 用户确认分享后执行的回调函数
			alert("分享成功");
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
		}
	});

	// 分享到腾讯微�?
	wx.onMenuShareWeibo({
		title : title, // 分享标题
		desc : desc, // 分享描述
		link : link, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
			// 用户确认分享后执行的回调函数
			alert("分享成功");
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数

		}
	});
}
