<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.hqjl.crm.model.User"%>
<%@page import="com.hqjl.crm.utils.SessionUtil"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    User user = SessionUtil.getLoginUser(request);
%>

<script>
  $(document).ready(function() {
    prepareSystemConfigInMenu();
    $("#change-admin-user-pw-btn").click(changePassword);
    $("#change-system-config-btn").click(changeSystemConfig);
  });
  function prepareSystemConfigInMenu() {
    var userId = getHeaderUserName();
    if (userId == "admin") {
      $("#system-config").show();
    }
  }
  function changePassword() {
    var oldpassword = $("#oldpassword").val();
    var newPassword = $("#newpassword").val();
    var newPasswordag = $("#newpasswordag").val();
    if (newPassword == "") {
      $("#change-password-hint").text("新密码不能为空");
      $("#change-password-hint").show();
    } else if (newPassword != newPasswordag) {
      $("#change-password-hint").text("两次输入密码不一致");
      $("#change-password-hint").show();
    } else {
      var json = {"password":newPassword,"oldPassword":oldpassword};
      ajaxUtil(json, path+"/changeUserPassword.shtml", function(r) {
        if (r.success == "true") {
          showMsgWithoutRefresh("修改成功！");
          $("#change-user-password-modal").modal("hide");
        } else {
          $("#change-password-hint").text(r.reason);
          $("#change-password-hint").show();
        }
      });
    }
  }
  function changeSystemConfig() {
    // calculate system config string
    var s = "";
    $("#system-config-table").find('input').each(function(index) {
      var v = $(this).val();
      var i = $(this);
      var tdr = i.parent();
      var tdl = tdr.prev();
      var k = tdl.text();
      s = s + k + "$$=$$" + v + "$$,$$";
    });
    var systemConfig = s.substring(0, s.length-5);
    // set to hidden form
    $("#update-system-config").val(systemConfig);
    // submit form
    $("#change-system-config-modal").modal("hide");
    ajaxSubmitRefresh("#system-config-form");
  }
  function logout() {
    if (!confirm("确定要退出系统?")) {
      return ;
    }
    ajaxUtil(null, path+"/logout.shtml", null, null);
    window.location.href = "<%=path%>/admin/login.shtml";
  }
  function popupChangePasswordModal() {
    $("#change-password-hint").hide();
    $("#change-user-password-modal").modal('show');
  }
  function popupSystemConfigModal() {
    prepareSystemConfig();
    $("#change-system-config-modal").modal('show');
  }
  function prepareSystemConfig() {
    printUtil("#system-config-table", null, "/crm/config/getConfig.shtml", function(r) {
      var theHtml = "";
      theHtml += "<tbody>";
      var cs = r.list;
      for (var i in cs) {
        var c = cs[i];
        theHtml += "<tr>";
        theHtml += "<td class=\"cleft\">"+c.ckey+"</td>";
        theHtml += "<td class=\"cright\"><input type=\"text\" value=\""+c.cvalue+"\" size=\"60\"/></td>";
        theHtml += "</tr>";
      }
      theHtml += "</tbody>";
      return theHtml;
    });
  }
  function getHeaderUserName() {
    return $.trim($("#header-userName").text());
  }
</script>

<div class="navbar navbar-default" role="navigation">
    <div class="navbar-inner">
        <button type="button" class="navbar-toggle pull-left animated flip">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button> 
        <a class="navbar-brand" href="index.shtml" style="padding: 5px;"> 
          <img alt="文件管理系统" src="" class="hidden-xs"  style="height:50px;width:200px"/>
        </a>

        <!-- user dropdown starts -->
        <div class="btn-group pull-right">
            <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                <i class="glyphicon glyphicon-user"></i><span id="header-userName" class="hidden-sm hidden-xs"> <%=user.getUserName() %></span>
                <span id="header-userId" class="hd"><%=user.getId()%></span>
                <span id="header-businessType" class="hd"><%=user.getBusinessType()%></span>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
                <li><a href="javascript:popupChangePasswordModal()">修改密码</a></li>
                <li id="system-config" class="hd"><a href="javascript:popupSystemConfigModal()">系统设置</a></li>
                <li class="divider"></li>
                <li><a href="javascript:logout()">退出系统</a></li>
            </ul>
        </div>
        <!-- user dropdown ends -->

        <h2 style="margin-left:40%;color:white"> 文件管理系统</h2>
    </div>
</div>

<!-- modal begin -->
<div class="modal fade" id="change-user-password-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3>修改密码</h3>
            </div>
            <div class="modal-body">
                <div role="form">
                 <div class="form-group">
                     <label for="oldpassword">原密码</label>
                     <input type="password" placeholder="请输入原密码"  id="oldpassword" class="form-control">
                 </div>
                 <div class="form-group">
                     <label for="newpassword">新密码</label>
                     <input type="password" placeholder="请输入新密码" id="newpassword" class="form-control">
                     
                 </div>
                 <div class="form-group">
                     <label for="newpasswordag">再输一次新密码</label>
                     <input type="password" placeholder="请再次输入新密码" id="newpasswordag" class="form-control">
                     
                 </div>
                 <div class="form-group">
                     <label id="change-password-hint" class="cr hd"></label>
                 </div>
             </div>
            </div>
            <div class="modal-footer">
                <a id="change-admin-user-pw-btn" class="btn btn-primary"> 确 定 </a>
                <a href="#" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="change-system-config-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:800px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3>修改系统配置</h3>
            </div>
            <div class="modal-body">
                <table id="system-config-table" class="table table-striped">
                </table>
                <form id="system-config-form" action="/crm/config/updateConfig.shtml" method="post" class="hd">
                    <div class="form-group hide">
                        <label>系统配置</label>
                        <input type="text" placeholder="系统配置" id="update-system-config" name="systemConfig" class="form-control">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a id="change-system-config-btn" class="btn btn-primary"> 确 定 </a>
                <a href="#" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
            </div>
        </div>
    </div>
</div>
<!-- modal end -->
