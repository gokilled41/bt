var path = "/crm/admin";
var mainpath = "/crm";
var host = "crm";
var pageSize = 5;

// paths
var approvalPath = "/crm/approval";
var filePath = "/crm/file";
var userPath = "/crm/user";
var reviewerPath = "/crm/reviewer";
var authPath = "/crm/auth";
var docViewPath = "/crm/docview";
var configPath = "/crm/config";

$(document).ready(function() {
  $(".refresher").click(function() {
    refresh();
  });
  
  // init left menu
  initLeftMenu();
  // init functions
  initFunctions();
});
function printUtil(panel, requestdata, ajaxurl, printfunction) {
  $.ajax({
    type: 'POST',
    url: ajaxurl,
    data: requestdata,
    cache:false,
    dataType:"json",
    async: false,
        success: function(response) {
          if (response.code) {
            if (panel != null && panel.length > 0) {
              $(panel).html("");
              if (printfunction != null)
                $(panel).html(printfunction(response.response));
            }
            return true;
          } else {
            //alert(response.reason);
          }
        },
        error: function(x, e) {
          alert("error", x);
        },
        complete: function(x) {
          //alert("call complete");
        }
  });
  return false;
}
function printUtilNoUrl(panel, printfunction) {
  $(panel).html(printfunction());
}
function ajaxUtil(requestdata, ajaxurl, succFunction, failFunction) {
  $.ajax({
        url: ajaxurl,
        type: "POST",
        dataType: "json",
        cache:false,
        data: requestdata,
        async: false,
        success: function(response) {
          if (response.code) {
            if (succFunction != null)
              succFunction(response.response);
          } else {
            if (failFunction != null)
              failFunction(response.response);
          }
        },
        error: function(x, e) {
          //alert("error", x);
        },
        complete: function(x) {
        }
  });
  return false;
}
function loadSelectionSimple(panel, requestdata, ajaxurl) {
  ajaxUtil(requestdata, ajaxurl, function(response){
    $(panel).html("");
    var list = response.list;
    for (var i = 0;i<list.length;i++){
      $(panel).append("<option value='"+list[i]+"'>"+list[i]+"</option>");
    }
  }, null);
}
function loadSelection(panel, requestdata, ajaxurl, itemName) {
  ajaxUtil(requestdata, ajaxurl, function(response) {
    var list = response.list;
    for (var i = 0;i<list.length;i++) {
      $(panel).append("<option value='"+list[i][itemName]+"'>"+list[i][itemName]+"</option>");
    }
  }, null);
}
function loadSelectionNV(panel, requestdata, ajaxurl, itemName, itemValue) {
  ajaxUtil(requestdata, ajaxurl, function(response) {
    $(panel).html("");
    var list = response.list;
    for (var i = 0;i<list.length;i++) {
      $(panel).append("<option value='"+list[i][itemValue]+"'>"+list[i][itemName]+"</option>");
    }
  }, null);
}
function getFileUrl(sourceId) {
  var url = "";
  if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
  url = document.getElementById(sourceId).value;
  } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
  url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
  } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
  url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
  }
  return url;
}
function preImg(sourceId, targetId) { //将本地图片 显示到浏览器上
  var url = getFileUrl(sourceId);
  var imgPre = document.getElementById(targetId);
  imgPre.src = url;
}
function preImgBut(sourceId, imgSrcId, delButId, delImgFlag) { //显示图片 并控制删除按钮 标志位
  var url = getFileUrl(sourceId);
    var imgPre = document.getElementById(imgSrcId);
    imgPre.src = url;
  
  if(url !== null && url != "") {
    $("#" + delButId).removeClass("hidden").addClass("show");
    $("#" + delImgFlag).val(0);
  }else{
    $("#" + delButId).removeClass("show").addClass("hidden");
  }
}
function deleteImg(delBut, filedId, imgSrcId,  delImgFlag) { //删除图片  删除图片默认标记为1
  $("#" + imgSrcId).attr("src", "");
  $("#" + delImgFlag).val(1);
  $(delBut).removeClass("show").addClass("hidden");
  $("#" + filedId).html($("#" + filedId).html());
}
function updateImg(resourceName, key) {
  var userImg = $("#userImg").val();
  if(userImg == "") {
    alert("请选择图片！");
    return;
  }
  var tmp = userImg.toLowerCase();
  var length = tmp.length - 4;
  if(tmp.lastIndexOf(".bmp") != length && tmp.lastIndexOf(".jpg") != length && tmp.lastIndexOf(".png") != length) {
    alert("只支持bmp、jpg和png格式的图片！");
    return;
  }
    var hideForm = $('#modifyUserInfoForm');
    var options = {
        dataType : "json",
        beforeSubmit : function() {
            //alert("正在上传");
        },
        success : function(result) {
          if (result.result) {
              //update img
              $("#userImgPic").attr("src", result.response);
              savePicResource(resourceName, "#userImgPic", key);
          } else {
            alert("图片修改失败！");
          }
        },
        error : function(result) {
          alert("图片修改失败！");
        }
    };
    hideForm.ajaxSubmit(options);
}
function updateImgById(id, resourceName, key) {
  var userImg = $("#userImg"+id).val();
  if(userImg == "") {
    alert("请选择图片！");
    return;
  }
  var tmp = userImg.toLowerCase();
  var length = tmp.length - 4;
  if(tmp.lastIndexOf(".bmp") != length && tmp.lastIndexOf(".jpg") != length && tmp.lastIndexOf(".png") != length) {
    alert("只支持bmp、jpg和png格式的图片！");
    return;
  }
    var hideForm = $('#modifyUserInfoForm'+id);
    var options = {
        dataType : "json",
        beforeSubmit : function() {
            //alert("正在上传");
        },
        success : function(result) {
          if (result.code) {
              //update img
              $("#userImgPic").attr("src", result.response);
              savePicResource(resourceName, "#userImgPic"+id, key);
          } else {
            alert("图片修改失败！");
          }
        },
        error : function(result) {
          alert("图片修改失败！");
        }
    };
    hideForm.ajaxSubmit(options);
}
function savePicResource(resourceName, panel, key) {
  var picURL = $(panel).attr("src");
  var json = {"resourceName":resourceName,"key":key, "value": picURL};
  ajaxUtil(json, mainpath+"/module/updateResource.shtml", showMsgWithoutRefresh("修改成功"), null);
}
function saveResource(resourceName, panel, key) {
  var value = $(panel).val();
  var json = {"resourceName":resourceName,"key":key, "value": value};
  ajaxUtil(json, mainpath+"/module/updateResource.shtml", showMsgWithoutRefresh("修改成功"), null);
}
function showMsg(msg) {
  $("#alertmessage").html(msg);
  $('#message-block').modal('show');
}
function showMsgWithoutRefresh(msg) {
  $("#alertmessage-norefresh").html(msg);
  $('#message-block-norefresh').modal('show');
}
function refresh() {
  window.location.href = window.location.href;
}
function ajaxSubmit(formId) {
    var hideForm = $(formId);
    var options = {
        dataType : "json",
        beforeSubmit : function() {
        },
        success : function(result) {
          if (result.code) {
            showMsgWithoutRefresh("提交成功");
          } else {
            alert("提交失败！");
          }
        },
        error : function(result) {
          alert("提交失败！");
        }
    };
    hideForm.ajaxSubmit(options);
}
function ajaxSubmitRefresh(formId) {
    var hideForm = $(formId);
    var options = {
        dataType : "json",
        beforeSubmit : function() {
        },
        success : function(result) {
          if (result.code) {
            showMsg("提交成功");
          } else {
            alert("提交失败！");
          }
        },
        error : function(result) {
          alert("提交失败！");
        }
    };
    hideForm.ajaxSubmit(options);
}
function ajaxSubmitWithFunc(formId, succFunc, failFunc) {
    var hideForm = $(formId);
    var options = {
        dataType : "json",
        beforeSubmit : function() {
        },
        success : function(result) {
          if (result.code) {
            if (succFunc) {
              succFunc(result.response);
            }
          } else {
            if (failFunc) {
              failFunc(result.response);
            }
          }
        },
        error : function(result) {
          alert("提交失败！");
        }
    };
    hideForm.ajaxSubmit(options);
}
function urlparameter(paras) {
  var url = location.href;
  var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");
  var paraObj = {}
  for (i=0; j=paraString[i]; i++) {
    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);
  }
  var returnValue = paraObj[paras.toLowerCase()];
  if(typeof(returnValue)=="undefined") {
    return "";
  }else{
    return returnValue;
  }
}
function formatTimestamp(d) {
  if (d != null)
    return (new Date(d)).format("yyyy-MM-dd hh:mm:ss");
  else
    return "null";
}
/** set page params
 * @param panel 用于显示列表的容器
 * @param container 用于显示分页按钮的容器
 * @param count 共有多少分页
 * @param pageNo 当前第几页
 * @param pageSize 每页显示多少条
 * @param funcName 点击分页后的函数
 */
function setPage(panel, container, count, url, data, pageNo, pageSize, funcName) {
  var pageindex = pageNo;
  var pageLink = "javascript:"+funcName+"('"+panel+"','"+url+"','"+data+"', '#pageNo', '"+pageSize+"')";
  var a = [];
    //总页数少于10 全部显示,大于10 显示前3 后3 中间3 其余....
    if (pageindex == 1) {
      a[a.length] = "<a href=\"#\" class=\"prev unclick\">前一页</a>";
    } else {
      a[a.length] = "<a href=\""+pageLink.replace("#pageNo", pageNo-1)+"\" class=\"prev\">前一页</a>";
    }
    function setPageList(i) {
      if (pageindex == i) {
        a[a.length] = "<a href=\""+pageLink.replace("#pageNo", i)+"\" class=\"current\">" + i + "</a>";
      } else {
        a[a.length] = "<a href=\""+pageLink.replace("#pageNo", i)+"\">" + i + "</a>";
      }
    }
    //总页数小于10
    if (count <= 10) {
      for (var i = 1; i <= count; i++) {
        setPageList(i);
      }
    }
    //总页数大于10页
    else {
      if (pageindex <= 4) {
        for (var i = 1; i <= 5; i++) {
          setPageList(i);
        }
        a[a.length] = "...<a href=\""+pageLink.replace("#pageNo", count)+"\">" + count + "</a>";
      } else if (pageindex >= count - 3) {
        a[a.length] = "<a href=\""+pageLink.replace("#pageNo", 1)+"\">1</a>...";
        for (var i = count - 4; i <= count; i++) {
          setPageList(i);
        }
      }
      else { //当前页在中间部分
        a[a.length] = "<a href=\""+pageLink.replace("#pageNo", 1)+"\">1</a>...";
        for (var i = parseInt(pageindex) - 2; i <= parseInt(pageindex) + 2; i++) {
          setPageList(i);
        }
        a[a.length] = "...<a href=\""+pageLink.replace("#pageNo", count)+"\">" + count + "</a>";
      }
    }
    if (pageindex == count) {
      a[a.length] = "<a href=\"#\" class=\"next unclick\">后一页</a>";
    } else {
      a[a.length] = "<a href=\""+pageLink.replace("#pageNo", parseInt(pageNo)+1)+"\" class=\"next\">后一页</a>";
    }
    container.innerHTML = a.join("");
    //事件点击
}
function isEmpty(src) { //判断为空
  if(("undefined" == typeof src)  || (src == null) || ($.trim(src) == "") ) {
    return true;
  }
  return false;
}
function notEmpty(src) {//判断不为空
  return !isEmpty(src);
}
function htmlspecialchars(str) {
  return isEmpty(str)?"":str.replace(/'/g, '‘'); 
}
function toStr(str) {
  if (str == null) {
    return "";
  } else {
    return str;
  }
}
function formatHtml(str) {
  if (str != null) {
    str = str.replaceAll('\n', '<br>');
    str = str.replaceAll(' ', '&nbsp;');
    return str;
  } else {
    return "";
  }
}
function formatFileName(n, size) {
  if (n.length > size) {
    var dot = n.indexOf(".");
    var ext = n.substring(dot, n.length);
    var prefix = n.substring(0, size - 2 - ext.length);
    return prefix + ".." + ext;
  } else {
    return n;
  }
}
function getUploadFileName(o) {
  var pos=o.lastIndexOf("\\");
  return o.substring(pos+1);  
}
function getUploadFileExt(o) {
  var pos=o.lastIndexOf(".");
  return o.substring(pos+1);  
}
function getUploadFileIcon(type) {
  return path + "/img/icon/" + type + ".gif";  
}
function check(id, value) {
  $("[id='"+cutId(id)+"'][value='"+value+"']").prop('checked',true);
}
function checkOnly(id, value) {
  $("[id='"+cutId(id)+"']").each(function() {
    var v = $(this).val();
    if (v == value) {
      $(this).prop('checked',true);
    } else {
      $(this).prop('checked',false);
    }
  });
}
function checkOnlyByClick(id, value) {
  $("[id='"+cutId(id)+"']").each(function() {
    var v = $(this).val();
    if (v == value) {
      $(this).trigger("click");
    }
  });
}
function checkList(id, list) {
  $("[id='"+cutId(id)+"']").each(function() {
    var v = $(this).val();
    if (list.contains(v)) {
      $(this).prop('checked',true);
    } else {
      $(this).prop('checked',false);
    }
  });
}
function uncheck(id, value) {
  $("[id='"+cutId(id)+"'][value='"+value+"']").prop('checked',false);
}
function uncheckOnly(id, value) {
  $("[id='"+cutId(id)+"']").each(function() {
    var v = $(this).val();
    if (v == value) {
      $(this).prop('checked',false);
    } else {
      $(this).prop('checked',true);
    }
  });
}
function doubleClickCheck(id) {
  $(id).dblclick(function() {
    var allchecked = true;
    $(this).find('input').each(function(index) {
      var checked = $(this).prop('checked');
      if (checked == false) {
        allchecked = false;
      }
    });
    if (allchecked == true) {
      $(this).find('input').prop('checked', false);
    } else {
      $(this).find('input').prop('checked', true);
    }
  });
}
function getCheckboxValue(id) {
  var str="";     
  $("[id='"+cutId(id)+"']").each(function() {
    var checked = $(this).prop("checked");
    if (checked == true) {
      str+=$(this).val()+",";
    }
  });
  if (str.length > 1) {
    return str.substring(0, str.length - 1);
  } else {
    return str;
  }
}
function getCheckboxValueByClass(c) {
  var str="";     
  $(c).each(function() {
    var checked = $(this).prop("checked");
    if (checked == true) {
      str+=$(this).val()+",";
    }
  });
  if (str.length > 1) {
    return str.substring(0, str.length - 1);
  } else {
    return str;
  }
}
function readOnly(id) {
  $(id).prop('readonly',true);
}
function cutId(id) {
  if (id.startsWith("#")) {
    return id.substring(1, id.length);
  } else {
    return id;
  }
}
function formatLine(s) {
  return s.replaceAll(",", ",<br>");
}
function enterClick(box, btn) {
  $(box).keyup(function(event) {
    if (event.keyCode == 13) {
      clickBtn(btn);
    }
  });
}
function changeClick(box, btn) {
  $(box).change(function(event) {
    clickBtn(btn);
  });
}
function clickBtn(btn) {
  $(btn).trigger("click");
}
function enterClickSearch(name) {
  enterClick("#search-name", "#list-"+name+"-btn");
}
function changeClickSearch(name, searchid) {
  changeClick("#search-" + searchid, "#list-"+name+"-btn");
}
function changeClickSearchStatus(name) {
  changeClickSearch(name, "status");
}
function radioCheckbox(checkboxes, handler) {
  $(checkboxes).click(function() {
    $(this).parent().siblings().find("input").prop("checked", false);
    $(this).prop("checked", true);
    if (handler) {
      handler($(this).val());
    }
  });
}
function getHeaderUserId() {
  return trim($("#header-userId").text());
}
function getHeaderUserName() {
  return trim($("#header-userName").text());
}
function getHeaderBusinessType() {
  return trim($("#header-businessType").text());
}
function trim(s) {
  return $.trim(s);
}
function isPass() {
  var pass = getCheckboxValueByClass(".racb");
  return pass == "通过";
}
function getConclusionClass(c) {
  if (c != null) {
    if (c == "通过") {
      return "bd cg";
    } else {
      return "bd cr";
    }
  } else {
    return "bd";
  }
}
function getViewStatusClass(c) {
  if (c != null) {
    if (c == "正常") {
      return "bd cg";
    } else {
      return "bd cr";
    }
  } else {
    return "bd";
  }
}
function doubleClickNotSelectText() {
  return " unselectable=\"on\" style=\"-moz-user-select:none;\" onselectstart=\"return false;\"";
}
function getAttachmentButtons(r, role) {
  var view = true;
  var download = true;
  if (role != null) {
    view = role.contains("阅读权限");
    download = role.contains("下载权限");
  }
  var theHtml = "";
  if (download) {
    theHtml += "  <div class=\"dl\"><a href=\""+r.url+"\" target=\"_blank\">下载</a></div>";
  }
  if (view && r.swf != null) {
    theHtml += "  <div class=\"dl\"><a href=\""+docViewPath+"/viewDoc.shtml?id="+r.id+"\" target=\"_blank\">预览</a></div>";
  }
  return theHtml;
}
function getAttachmentFileName(r) {
  var theHtml = "";
  theHtml += "  <span class=\"s1\" title=\""+r.fileName+"\">"+formatFileName(r.fileName,22) +"</span>";
  return theHtml;
}

/************** left menu **************/
function initLeftMenu() {
  printUtil("#left-menu", "", userPath+"/getRole.shtml", function(response) {
    var role = response;
    
    var theHtml = "";
    theHtml += "<div class=\"nav-sm nav nav-stacked\"></div>";
    
    // approval
    if (role.showApproval == true) {
      theHtml += "<ul class=\"nav nav-pills nav-stacked main-menu\">";
      theHtml += "    <li class=\"nav-header\">";
      theHtml += "        <h4 style=\"margin-left:10px\"><b>审批管理</b></h4>";
      theHtml += "    </li>";
      theHtml += "</ul>";

      theHtml += "<ul class=\"nav nav-pills nav-stacked main-menu\">";
      var list = role.approvalItems;
      for (var index in list) {
        var r = list[index];
        theHtml += "<li>";
        theHtml += "    <a class=\"ajax-link\" href=\""+mainpath+"/page/admin/"+r.page+".shtml\"><i class=\"glyphicon glyphicon-"+r.icon+"\"></i><span> "+r.name+"</span></a>";
        theHtml += "</li>";
      }
      theHtml += "</ul>";
    }
    
    // file
    if (role.showFile == true) {
      theHtml += "<ul class=\"nav nav-pills nav-stacked main-menu\">";
      theHtml += "    <li class=\"nav-header\">";
      theHtml += "        <h4 style=\"margin-left:10px\"><b>文件管理</b></h4>";
      theHtml += "    </li>";
      theHtml += "</ul>";

      theHtml += "<ul class=\"nav nav-pills nav-stacked main-menu\">";
      var list = role.fileItems;
      for (var index in list) {
        var r = list[index];
        theHtml += "<li>";
        theHtml += "    <a class=\"ajax-link\" href=\""+mainpath+"/page/admin/"+r.page+".shtml\"><i class=\"glyphicon glyphicon-"+r.icon+"\"></i><span> "+r.name+"</span></a>";
        theHtml += "</li>";
      }
      theHtml += "</ul>";
    }
    
    // auth
    if (role.showAuth == true) {
      theHtml += "<ul class=\"nav nav-pills nav-stacked main-menu\">";
      theHtml += "    <li class=\"nav-header\">";
      theHtml += "        <h4 style=\"margin-left:10px\"><b>权限管理</b></h4>";
      theHtml += "    </li>";
      theHtml += "</ul>";

      theHtml += "<ul class=\"nav nav-pills nav-stacked main-menu\">";
      var list = role.authItems;
      for (var index in list) {
        var r = list[index];
        theHtml += "<li>";
        theHtml += "    <a class=\"ajax-link\" href=\""+mainpath+"/page/admin/"+r.page+".shtml\"><i class=\"glyphicon glyphicon-"+r.icon+"\"></i><span> "+r.name+"</span></a>";
        theHtml += "</li>";
      }
      theHtml += "</ul>";
    }
    
    return theHtml;
  });
}

/************** extenstions **************/

/* format date
// 对Date的扩展，将 Date 转化为指定格式的String   
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，   
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)   
// 例子：   
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
*/
Date.prototype.format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};
String.prototype.replaceAll = function(s1,s2) { 
  return this.replace(new RegExp(s1,"gm"),s2); 
};
String.prototype.contains = function(s) {
  return this.indexOf(s) != -1;
};

function initFunctions() {
  if (typeof String.prototype.startsWith != 'function') {
    String.prototype.startsWith = function (prefix){
      return this.slice(0, prefix.length) === prefix;
    };
  }
}
