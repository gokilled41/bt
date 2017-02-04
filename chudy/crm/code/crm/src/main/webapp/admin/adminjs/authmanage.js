var approvalPageNo = 1;

$(document).ready(function() {

  // tabs
  $("#add-img-tab a:first").tab("show");
  $("#add-img-tab a").click(function(e) {
    e.preventDefault();
    $(this).tab("show");
  });

  // prepare list
  prepareSearchDropdownList();
  listApproval();
  enterClickSearch("approval");
  changeClickSearch("approval", "businessType");
  
  // buttons
  $("#list-approval-btn").click(listApproval);
    // 1. detail
    $("#view-approval-detail-auth-btn").click(viewApprovalDetailAuth);
    $("#view-approval-detail-cancel-btn").click(viewApprovalDetailCancel);
    // 2. auth
    $("#view-approval-auth-review-btn").click(viewApprovalAuthReview);
    $("#view-approval-auth-return-btn").click(viewApprovalAuthReturn);
    $("#view-approval-auth-cancel-btn").click(viewApprovalAuthCancel);
    // 3. review auth
    $("#view-approval-reviewauth-submit-btn").click(viewApprovalReviewAuthSubmit);
    $("#view-approval-reviewauth-return-btn").click(viewApprovalReviewAuthReturn);
    $("#view-approval-reviewauth-cancel-btn").click(viewApprovalReviewAuthCancel);
});

// list
function listApproval() {
  var name = $("#search-name").val();
  var businessType = $("#search-businessType").val();
  var status = "文件and已结案-通过";
  var searchPara = "name="+name+"&businessType="+businessType+"&status="+status;
  printApprovalList("#approval-table", approvalPath+"/listApprovalForAuthManage.shtml", searchPara, 1, pageSize);
}
function printApprovalList(panel, url, data, pageNo, pageSize) {
  approvalPageNo = pageNo;
  var start = (pageNo-1)*pageSize;
  var dataStr = data;
  if ("" == data) {
    dataStr = "start="+start+"&size="+pageSize;
  } else {
    dataStr += "&start="+start+"&size="+pageSize;
  }
  printUtil(panel, dataStr, url, function(response) {
    var theHtml = "";
    theHtml += "<thead>";
    theHtml += "<tr>";
    theHtml += "<th width='10%'>申请号</th>";
    theHtml += "<th width='10%'>申请人工号</th>";
    theHtml += "<th width='10%'>申请人姓名</th>";
    theHtml += "<th width='10%'>申请人部门</th>";
    theHtml += "<th width='10%'>业务类型</th>";
    theHtml += "<th width='10%'>文件类型</th>";
    theHtml += "<th width='10%'>名称</th>";
    theHtml += "<th width='5%'>版本号</th>";
    theHtml += "<th width='15%'>更新日期</th>";
    theHtml += "<th width='10%'>操作</th>";
    theHtml += "</tr>";
    theHtml += "</thead>";
    var list = response.list;
    theHtml += "<tbody>";
    for (var index in list) {
      var r = list[index];
      theHtml += "<tr>";
      theHtml += "<td>"+r.applyId+"</td>";
      theHtml += "<td>"+r.employeeNo+"</td>";
      theHtml += "<td>"+r.nickName+"</td>";
      theHtml += "<td>"+r.department+"</td>";
      theHtml += "<td>"+r.businessType+"</td>";
      theHtml += "<td>"+r.fileType+"</td>";
      theHtml += "<td>"+r.name+"</td>";
      theHtml += "<td>"+r.version+"</td>";
      theHtml += "<td>"+formatTimestamp(r.timestamp)+"</td>";
      theHtml += "<td>";
      theHtml += "  <a class=\"btn btn-primary btn-sm\" href=\"javascript:viewApprovalDetail('" + r.id + "', '" + htmlspecialchars(r.name) + "')\"> 设定权限 </a>";
      theHtml += "</td>";
      theHtml += "</tr>";
    }
    theHtml += "</tbody>";
    //print page
    var count = response.size;
    setPage(panel, document.getElementById("paging_btn"), count, url, data, pageNo, pageSize, "printApprovalList");
    return theHtml;
  });
}

// view
function viewApprovalDetail(id, name) {
  doSetViewApprovalDetail(id, name);
  refreshViewAttachmentList();
  $("#view-approval-detail-modal").modal("show");
}
function viewApprovalDetailAuth() {
  doSetViewApprovalDetailAuth();
  $("#view-approval-detail-modal").modal("hide");
  $("#view-approval-auth-modal").modal("show");
}
function viewApprovalDetailCancel() {
}
function viewApprovalAuthReview() {
  prepareReviewViewAuth();
  $("#view-approval-auth-modal").modal("hide");
  $("#view-approval-reviewauth-modal").modal("show");
}
function viewApprovalAuthReturn() {
  $("#view-approval-detail-modal").modal("show");
  $("#view-approval-auth-modal").modal("hide");
}
function viewApprovalAuthCancel() {
  viewApprovalDetailCancel();
}
function viewApprovalReviewAuthSubmit() {
  if (validateViewApprovalReviewAuth()) {
    ajaxSubmitRefresh("#update-auth-form");
    $("#view-approval-reviewauth-modal").modal("hide");
  }
}
function viewApprovalReviewAuthReturn() {
  $("#view-approval-auth-modal").modal("show");
  $("#view-approval-reviewauth-modal").modal("hide");
}
function viewApprovalReviewAuthCancel() {
  viewApprovalDetailCancel();
}
function doSetViewApprovalDetail(id, name) {
  ajaxUtil({"id":id}, approvalPath+"/getApprovalForView.shtml", function(r) {
    $("#view-applyId").text(r.applyId);
    $("#view-employeeNo").text(r.employeeNo);
    $("#view-nickName").text(r.nickName);
    $("#view-department").text(r.department);
    $("#view-businessType").text(r.businessType);
    $("#view-fileType").text(r.fileType);
    $("#view-name").text(r.name);
    $("#view-version").text(r.version);
    $("#view-formId").text(r.formId);
    $("#view-description").html(formatHtml(r.description));
    $("#view-userId").text(r.userId);
  });
}
function refreshViewAttachmentList() {
  var applyId = $("#view-applyId").text();
  var category = "submitter";
  var dataStr = "applyId="+applyId + "&category=" + category;
  printUtil("#view-attachment-list", dataStr, filePath+"/listFiles.shtml", function(response) {
    var theHtml = "";
    var list = response.list;
    var floatside;
    for (var index in list) {
      var r = list[index];
      theHtml += "<div class=\"att\">";
      theHtml += "  <img src=\""+getUploadFileIcon(r.type)+"\" />";
      theHtml += getAttachmentFileName(r);
      theHtml += getAttachmentButtons(r);
      theHtml += "  <br>";
      theHtml += "  <span class=\"s2\">"+r.size+"</span>";
      theHtml += "</div>";
    }
    return theHtml;
  });
  
  // update dl button css
  $(".dl").hover(function() {
    $(this).css("background-color", "darkgreen");
    $(this).children().css("color", "white");
    $(this).css("cursor", "pointer");
  }, function() {
    $(this).css("background-color", "#F0F8F0");
    $(this).children().css("color", "green");
  });
}
function doSetViewApprovalDetailAuth() {
  var applyId = $("#view-applyId").text();
  var dataStr = "applyId="+applyId;
  printUtil("#view-auth-table", dataStr, authPath+"/getUsers.shtml", function(ugs) {
    var theHtml = "";
    
    theHtml += "<tbody>";
    theHtml += "<tr class=\"rtt1 rttitle\">";
    theHtml += "    <td colspan=\"2\">阅读权限</td>";
    theHtml += "</tr>";
    
    var ugl1 = ugs.userGroups1;
    for (var i in ugl1) {
      var ug = ugl1[i];
      var ugrl = ug.usersPieces;
      theHtml += "<tr class=\"rtl1 rtline\">";
      theHtml += "    <td class=\"rtleft\">";
      theHtml += "      <label class=\"checkbox-inline\">";
      theHtml += "        <input class=\"rtcbleft\" type=\"checkbox\">"+ug.businessType+"";
      theHtml += "      </label>";
      theHtml += "    </td>";
      theHtml += "    <td class=\"rtright\""+doubleClickNotSelectText()+">";
      theHtml += "    <table class=\"addrtctable\"><tbody>";
      for (var j in ugrl) {
        var ugrsl = ugrl[j];
        
        theHtml += "<tr>";
        for (var k in ugrsl) {
          var ugr = ugrsl[k];
          theHtml += "<td>";
          theHtml += "    <label class=\"checkbox-inline addrtclabel\">";
          theHtml += "      <input type=\"checkbox\" value=\"阅读权限;"+ug.businessType+";"+ugr.userName+";"+ugr.nickName+"\" class=\"addrtc\""+(ugr.checked?" checked=\"true\"":"")+">" + ugr.nickName;
          theHtml += "    </label>";
          theHtml += "</td>";
        }
        theHtml += "</tr>";

      }
      theHtml += "    </tbody></table>";
      theHtml += "    </td>";
      theHtml += "</tr>";
    }

    theHtml += "<tr class=\"rtt2 rttitle\">";
    theHtml += "    <td colspan=\"2\">下载权限</td>";
    theHtml += "</tr>";

    var ugl2 = ugs.userGroups2;
    for (var i in ugl2) {
      var ug = ugl2[i];
      var ugrl = ug.usersPieces;
      theHtml += "<tr class=\"rtl2 rtline\">";
      theHtml += "    <td class=\"rtleft\">";
      theHtml += "      <label class=\"checkbox-inline\">";
      theHtml += "        <input class=\"rtcbleft\" type=\"checkbox\">"+ug.businessType+"";
      theHtml += "      </label>";
      theHtml += "    </td>";
      theHtml += "    <td class=\"rtright\""+doubleClickNotSelectText()+">";
      theHtml += "    <table class=\"addrtctable\"><tbody>";
      for (var j in ugrl) {
        var ugrsl = ugrl[j];
        
        theHtml += "<tr>";
        for (var k in ugrsl) {
          var ugr = ugrsl[k];
          theHtml += "<td>";
          theHtml += "    <label class=\"checkbox-inline addrtclabel\">";
          theHtml += "      <input type=\"checkbox\" value=\"下载权限;"+ug.businessType+";"+ugr.userName+";"+ugr.nickName+"\" class=\"addrtc\""+(ugr.checked?" checked=\"true\"":"")+">" + ugr.nickName;
          theHtml += "    </label>";
          theHtml += "</td>";
        }
        theHtml += "</tr>";

      }
      theHtml += "    </tbody></table>";
      theHtml += "    </td>";
      theHtml += "</tr>";
    }
    
    theHtml += "</tbody>";
    
    return theHtml;
  });
  
  // multiple tabs
  $(".rtt1").addClass("rtactive");
  $(".rtl1").show();
  $(".rtl2").hide();
  $(".rtl3").hide();
  $(".rtt1").click(function() {
    $(".rttitle").removeClass("rtactive");
    $(this).addClass("rtactive");
    $(".rtl1").show();
    $(".rtl2").hide();
    $(".rtl3").hide();
  });
  $(".rtt2").click(function() {
    $(".rttitle").removeClass("rtactive");
    $(this).addClass("rtactive");
    $(".rtl1").hide();
    $(".rtl2").show();
    $(".rtl3").hide();
  });
  $(".rtt3").click(function() {
    $(".rttitle").removeClass("rtactive");
    $(this).addClass("rtactive");
    $(".rtl1").hide();
    $(".rtl2").hide();
    $(".rtl3").show();
  });
  
  // click left, show or hide right
  $(".rtcbleft").change(function() {
    var cbl = $(this);
    var checked = cbl.prop('checked');
    var rtl = cbl.parent().parent();
    var rtr = rtl.next();
    if (checked) {
      rtr.children().show();
    } else {
      rtr.children().hide();
      rtr.find("input").prop("checked", false);
    }
  });
  
  // check all business types
  $(".rtcbleft").prop('checked', true);
  
  doubleClickCheck(".rtright");
}
function prepareReviewViewAuth() {
  var users = getReviewApprovalViewAuthValue();
  var dataStr = "users=" + users;
  printUtil("#view-reviewauth-table", dataStr, authPath+"/parseUsers.shtml", function(ugs) {
    var theHtml = "";
    
    theHtml += "<tbody>";
    
    var ugl1 = ugs.userGroups1;
    for (var i in ugl1) {
      var ug = ugl1[i];
      theHtml += "<tr>";
      if (i == 0) {
        theHtml += "<td class=\"rtt1\" rowspan=\""+ugl1.length+"\">阅读权限</td>";
      }
      theHtml += "<td class=\"rtt2\">"+ug.businessType+"</td>";
      theHtml += "<td class=\"rtt3\">"+ug.usersNickNames+"</td>";
      theHtml += "</tr>";
    }

    var ugl2 = ugs.userGroups2;
    for (var i in ugl2) {
      var ug = ugl2[i];
      theHtml += "<tr>";
      if (i == 0) {
        theHtml += "<td class=\"rtt1\" rowspan=\""+ugl2.length+"\">下载权限</td>";
      }
      theHtml += "<td class=\"rtt2\">"+ug.businessType+"</td>";
      theHtml += "<td class=\"rtt3\">"+ug.usersNickNames+"</td>";
      theHtml += "</tr>";
    }
    
    theHtml += "</tbody>";
    
    return theHtml;
  });
  
  $("#update-applyId").val($("#view-applyId").text());
  $("#update-users").val(users);
}
function getReviewApprovalViewAuthValue() {
  var result = "";
  $(".addrtc").each(function(index) {
    var checked = $(this).prop('checked');
    if (checked) {
      result += $(this).val();
      result += ",";
    }
  });
  return result;
}
function validateViewApprovalReviewAuth() {
  return true;
}
function prepareSearchDropdownList() {
  loadSelectionSimple("#search-businessType", {}, configPath+"/getBusinessTypesWithAll.shtml");
}

