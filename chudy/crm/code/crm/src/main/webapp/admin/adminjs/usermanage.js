var userPageNo = 1;

$(document).ready(function() {

  // tabs
  $("#add-img-tab a:first").tab("show");
  $("#add-img-tab a").click(function(e) {
    e.preventDefault();
    $(this).tab("show");
  });

  // prepare
  listUser();
  enterClickSearch("user");
  doSetDefaultUser();
  
  // buttons
  $("#list-user-btn").click(listUser);
  $("#add-user-btn").click(addUser);
  $("#add-user-go-review-btn").click(addUserGoReview);
  $("#add-user-return-edit-btn").click(addUserReturnEdit);
  $("#add-user-submit-btn").click(addUserSubmit);
  $("#add-user-cancel-btn").click(addUserCancel);
  $("#add-user-review-cancel-btn").click(addUserCancel);
  $("#update-user-go-review-btn").click(updateUserGoReview);
  $("#update-user-return-edit-btn").click(updateUserReturnEdit);
  $("#update-user-submit-btn").click(updateUserSubmit);
  $("#update-user-cancel-btn").click(updateUserCancel);
  $("#update-user-review-cancel-btn").click(updateUserCancel);
});

function listUser() {
  var name = $("#search-name").val();
  var searchPara = "name="+name;
  printUserList("#user-table", userPath+"/listUser.shtml", searchPara, userPageNo, pageSize);
}
function printUserList(panel, url, data, pageNo, pageSize) {
  userPageNo = pageNo;
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
    theHtml += "<th width='6%'>账号</th>";
    theHtml += "<th width='6%'>姓名</th>";
    theHtml += "<th width='8%'>工号</th>";
    theHtml += "<th width='7%'>所在部门</th>";
    theHtml += "<th width='8%'>邮箱</th>";
    theHtml += "<th width='7%'>业务类型</th>";
    theHtml += "<th width='6%'>岗位</th>";
    theHtml += "<th width='7%'>审批角色</th>";
    theHtml += "<th width='12%'>管理员角色</th>";
    theHtml += "<th width='8%'>账号状态</th>";
    theHtml += "<th width='10%'>更新时间</th>";
    theHtml += "<th width='15%'>操作</th>";
    theHtml += "</tr>";
    theHtml += "</thead>";
    var list = response.list;
    theHtml += "<tbody>";
    for (var index in list) {
      var r = list[index];
      theHtml += "<tr>";
      theHtml += "<td>"+r.userName+"</td>";
      theHtml += "<td>"+r.nickName+"</td>";
      theHtml += "<td>"+r.employeeNo+"</td>";
      theHtml += "<td>"+r.department+"</td>";
      theHtml += "<td>"+r.email+"</td>";
      theHtml += "<td>"+r.businessType+"</td>";
      theHtml += "<td>"+r.position+"</td>";
      theHtml += "<td>"+formatLine(r.approvalRole)+"</td>";
      theHtml += "<td>"+formatLine(r.adminRole)+"</td>";
      theHtml += "<td>"+r.status+"</td>";
      theHtml += "<td>"+formatTimestamp(r.timestamp)+"</td>";
      theHtml += "<td>";
      theHtml += "  <a class=\"btn btn-primary btn-sm\" href=\"javascript:updateUser('" + r.id + "', '" + htmlspecialchars(r.nickName) + "')\"> 编辑 </a>";
      if (r.status == "正常") {
        theHtml += "  <a class=\"btn btn-danger btn-sm\"  href=\"javascript:freezeUser('" + r.id + "', '" + htmlspecialchars(r.nickName) + "')\"> 冻结 </a>";
      } else {
        theHtml += "  <a class=\"btn btn-primary btn-sm\"  href=\"javascript:unfreezeUser('" + r.id + "', '" + htmlspecialchars(r.nickName) + "')\"> 解冻 </a>";
      }
      theHtml += "  <a class=\"btn btn-danger btn-sm\"  href=\"javascript:deleteUser('" + r.id + "', '" + htmlspecialchars(r.nickName) + "')\"> 删除 </a>";
      theHtml += "</td>";
      theHtml += "</tr>";
    }
    theHtml += "</tbody>";
    //print page
    var count = response.size;
    setPage(panel, document.getElementById("paging_btn"), count, url, data, pageNo, pageSize, "printUserList");
    return theHtml;
  });
}
function addUser() {
  doSetDefaultUser();
  $("#add-user-modal").modal("show");
}
function addUserCancel() {
  // do nothing
}
function addUserGoReview() {
  doSetAddReview();
  $("#add-user-modal").modal("hide");
  $("#add-user-review-modal").modal("show");
}
function addUserReturnEdit() {
  $("#add-user-modal").modal("show");
  $("#add-user-review-modal").modal("hide");
}
function addUserSubmit() {
  if (validateAddUser()) {
    ajaxSubmitRefresh("#add-user-form");
    $("#add-user-review-modal").modal("hide");
  }
}
function validateAddUser() {
  /*
  var name = $("#add-name").val();
  var starts = $("#add-starts").val();
  var ends = $("#add-ends").val();
  var participants = $("#add-participants").val();
  var r;
  ajaxUtil({"name":name,"starts":starts,"ends":ends,"participants":participants,"type":"add"}, 
    userPath+"/validateUser.shtml", function(response) { r = response;}
  );
  if (r.success == "false") {
    $("#add-user-note").text(r.reason);
    $("#add-user-note").show();
    return false;
  } else {
    $("#add-user-note").hide();
    return true;
  }
  */
  return true;
}
function updateUser(id, name) {
  ajaxUtil({"id":id}, userPath+"/getUser.shtml", function(response) {
    var r = response;
    $("#update-id").val(r.id);
    $("#update-userName").val(r.userName);
    readOnly("#update-userName");
    $("#update-password").val(r.password);
    $("#update-nickName").val(r.nickName);
    $("#update-employeeNo").val(r.employeeNo);
    $("#update-phone").val(r.phone);
    $("#update-department").val(r.department);
    $("#update-email").val(r.email);
    $("#update-businessType").val(r.businessType);
    $("#update-position").val(r.position);
    checkList("#update-approvalRole", r.approvalRole);
    checkList("#update-adminRole", r.adminRole);
    $("#update-status").val(r.status);
  });
  $("#update-user-modal").modal("show");
}
function updateUserCancel() {
  // do nothing
}
function updateUserGoReview() {
  doSetUpdateReview();
  $("#update-user-modal").modal("hide");
  $("#update-user-review-modal").modal("show");
}
function updateUserReturnEdit() {
  $("#update-user-modal").modal("show");
  $("#update-user-review-modal").modal("hide");
}
function updateUserSubmit() {
  if (validateUpdateUser()) {
    ajaxSubmitRefresh("#update-user-form");
    $("#update-user-review-modal").modal("hide");
  }
}
function validateUpdateUser() {
  /*
  var name = $("#update-name").val();
  var starts = $("#update-starts").val();
  var ends = $("#update-ends").val();
  var participants = $("#update-participants").val();
  var r;
  ajaxUtil({"name":name,"starts":starts,"ends":ends,"participants":participants,"type":"update"}, 
    userPath+"/validateUser.shtml", function(response) { r = response;}
  );
  if (r.success == "false") {
    $("#update-user-note").text(r.reason);
    $("#update-user-note").show();
    return false;
  } else {
    $("#update-user-note").hide();
    return true;
  }
  */
  return true;
}
function deleteUser(id, name) {
  if(!confirm("您确定要删除 “"+name+"”？")) {
    return;
  }
  ajaxUtil({"id" : id}, userPath+"/deleteUser.shtml", function() {
    showMsg("删除成功");
  }, function() {
    showMsg("删除失败");
  });
}
function freezeUser(id, name) {
  if(!confirm("您确定要冻结 “"+name+"”？")) {
    return;
  }
  ajaxUtil({"id" : id}, userPath+"/freezeUser.shtml", function() {
    showMsg("冻结成功");
  }, function() {
    showMsg("冻结失败");
  });
}
function unfreezeUser(id, name) {
  if(!confirm("您确定要解冻 “"+name+"”？")) {
    return;
  }
  ajaxUtil({"id" : id}, userPath+"/unfreezeUser.shtml", function() {
    showMsg("解冻成功");
  }, function() {
    showMsg("解冻失败");
  });
}
function doSetDefaultUser() {
  $("#add-userName").val("szhang");
  $("#add-password").val("123");
  $("#add-nickName").val("张三");
  $("#add-employeeNo").val("工号00001");
  $("#add-phone").val("13612345678");
  $("#add-department").val("销售部门");
  $("#add-email").val("szhang@company.com");
  $("#add-businessType").val("销售管理");
  $("#add-position").val("职员");
  checkOnly("#add-approvalRole", "一级审批");
  checkOnly("#add-adminRole", "无权限");
  $("#add-status").val("正常");
}
function doSetAddReview() {
  $("#add-review-userName").text($("#add-userName").val());
  $("#add-review-nickName").text($("#add-nickName").val());
  $("#add-review-employeeNo").text($("#add-employeeNo").val());
  $("#add-review-phone").text($("#add-phone").val());
  $("#add-review-department").text($("#add-department").val());
  $("#add-review-email").text($("#add-email").val());
  $("#add-review-businessType").text($("#add-businessType").val());
  $("#add-review-position").text($("#add-position").val());
  $("#add-review-approvalRole").html(formatLine(getCheckboxValue("#add-approvalRole")));
  $("#add-review-adminRole").html(formatLine(getCheckboxValue("#add-adminRole")));
  $("#add-review-status").text($("#add-status").val());
}
function doSetUpdateReview() {
  $("#update-review-userName").text($("#update-userName").val());
  $("#update-review-nickName").text($("#update-nickName").val());
  $("#update-review-employeeNo").text($("#update-employeeNo").val());
  $("#update-review-phone").text($("#update-phone").val());
  $("#update-review-department").text($("#update-department").val());
  $("#update-review-email").text($("#update-email").val());
  $("#update-review-businessType").text($("#update-businessType").val());
  $("#update-review-position").text($("#update-position").val());
  $("#update-review-approvalRole").html(formatLine(getCheckboxValue("#update-approvalRole")));
  $("#update-review-adminRole").html(formatLine(getCheckboxValue("#update-adminRole")));
  $("#update-review-status").text($("#update-status").val());
}
