<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.hqjl.crm.utils.SessionUtil"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    
    String loginUserName = (String) SessionUtil.getAtt(request, "loginUserName");
    if (loginUserName == null) {
        loginUserName = "";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>文件管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="zgf">

    <!-- The styles -->
    <link id="bs-css" href="<%=path%>/admin/css/bootstrap-cerulean.min.css" rel="stylesheet">

    <link href="<%=path%>/admin/css/charisma-app.css" rel="stylesheet">
    <link href="<%=path%>/admin/bower_components/fullcalendar/dist/fullcalendar.css" rel="stylesheet">
    <link href="<%=path%>/admin/bower_components/fullcalendar/dist/fullcalendar.print.css" rel="stylesheet" media="print">
    <link href="<%=path%>/admin/bower_components/chosen/chosen.min.css" rel="stylesheet">
    <link href="<%=path%>/admin/bower_components/colorbox/example3/colorbox.css" rel="stylesheet">
    <link href="<%=path%>/admin/bower_components/responsive-tables/responsive-tables.css" rel="stylesheet">
    <link href="<%=path%>/admin/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/jquery.noty.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/noty_theme_default.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/elfinder.min.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/elfinder.theme.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/jquery.iphone.toggle.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/animate.min.css" rel="stylesheet">
    <link href='<%=path%>/admin/css/crm.css' rel='stylesheet'>

    <!-- jQuery -->
    <script src="<%=path%>/admin/bower_components/jquery/jquery.min.js"></script>

    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- The fav icon -->
    <link rel="shortcut icon" href="<%=path%>/admin/img/favicon.ico">
    <script>
        document.onkeydown = function(e) {
            var theEvent = e || window.event;
            var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
            if (code == 13) {
                $("#login_btn").focus();
                document.all("login_btn").click();
                return false;
            }
        };
        $(document).ready(function() {
          var u = $("#login-user").text();
          if (!isEmpty(u)) {
            $("#login-userName").prop('readonly',true);
            $("#login-password").focus();
          } else {
            $("#login-userName").focus();
          }
        });
        function isEmpty(src) { //判断为空
          if(("undefined" == typeof src)  || (src == null) || ($.trim(src) == "") ) {
            return true;
          }
          return false;
        }
    </script>
</head>

<body>
<div class="ch-container">
    <div class="row">
        
    <div class="row">
        <div class="col-md-12 center login-header">
            <h2>欢迎使用文件管理系统</h2>
        </div>
        <!--/span-->
    </div><!--/row-->

    <div class="row">
        <div class="well col-md-5 center login-box">
            <div class="alert alert-info">
             <%
                 String message = (String)request.getAttribute("message");
                 if (message != null) {
                     out.print(message);
                 } else {
                     out.print("请输入用户名密码");
                 }
             %>
            </div>
            <div id="login-user" class="hd"><%=loginUserName%></div>
            <form class="form-horizontal" action="<%=path%>/admin/login.shtml" method="post">
                <fieldset>
                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user red"></i></span>
                        <input id="login-userName" name="userName" type="text" class="form-control" placeholder="用户名" value="<%=loginUserName%>">
                    </div>
                    <div class="clearfix"></div><br>

                    <div class="input-group input-group-lg">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock red"></i></span>
                        <input id="login-password" name="password" type="password" class="form-control" placeholder="密码">
                    </div>
                    <div class="clearfix"></div>

                    <div class="input-prepend">
                        <label class="remember" for="remember"><input type="checkbox" id="remember"> 记住登录信息</label>
                    </div>
                    <div class="clearfix"></div>

                    <p class="center col-md-5">
                        <button type="submit" class="btn btn-primary">登  录</button>
                    </p>
                </fieldset>
            </form>
        </div>
        <!--/span-->
    </div><!--/row-->
</div><!--/fluid-row-->

</div><!--/.fluid-container-->

<!-- external javascript -->

<script src="<%=path%>/admin/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

<!-- library for cookie management -->
<script src="<%=path%>/admin/js/jquery.cookie.js"></script>
<!-- calender plugin -->
<script src="<%=path%>/admin/bower_components/moment/min/moment.min.js"></script>
<script src="<%=path%>/admin/bower_components/fullcalendar/dist/fullcalendar.min.js"></script>
<!-- data table plugin -->
<script src="<%=path%>/admin/js/jquery.dataTables.min.js"></script>

<!-- select or dropdown enhancer -->
<script src="<%=path%>/admin/bower_components/chosen/chosen.jquery.min.js"></script>
<!-- plugin for gallery image view -->
<script src="<%=path%>/admin/bower_components/colorbox/jquery.colorbox-min.js"></script>
<!-- notification plugin -->
<script src="<%=path%>/admin/js/jquery.noty.js"></script>
<!-- library for making tables responsive -->
<script src="<%=path%>/admin/bower_components/responsive-tables/responsive-tables.js"></script>
<!-- tour plugin -->
<script src="<%=path%>/admin/bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
<!-- star rating plugin -->
<script src="<%=path%>/admin/js/jquery.raty.min.js"></script>
<!-- for iOS style toggle switch -->
<script src="<%=path%>/admin/js/jquery.iphone.toggle.js"></script>
<!-- autogrowing textarea plugin -->
<script src="<%=path%>/admin/js/jquery.autogrow-textarea.js"></script>
<!-- history.js for cross-browser state change on ajax -->
<!--<script src="<%=path%>/admin/js/jquery.history.js"></script>-->
<!-- application script for Charisma demo -->
<!--<script src="<%=path%>/admin/js/charisma.js"></script>-->

</body>
</html>

