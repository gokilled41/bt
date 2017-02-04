<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.hqjl.crm.model.User"%>
<%@page import="com.hqjl.crm.utils.SessionUtil"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    User user = SessionUtil.getLoginUser(request);
%>

<div class="col-sm-2 col-lg-2">
    <div class="sidebar-nav">
        <div id="left-menu" class="nav-canvas">
        </div>
    </div>
</div>

<script>
$('.accordion > a').click(function (e) {
  e.preventDefault();
  var $ul = $(this).siblings('ul');
  var $li = $(this).parent();
  if ($ul.is(':visible')) {
    $li.removeClass('active');
  } else {
    $li.addClass('active');
  }
  $ul.slideToggle();
});
</script>
