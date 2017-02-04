<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title> 文件有效性管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="file manage system">
    <meta name="fileavailabilityor" content="zgf">
    
    <link rel="shortcut icon" href="<%=path%>/admin/img/favicon.ico">
    
    <link href="<%=path%>/admin/css/bootstrap-cerulean.min.css" rel="stylesheet">
    <link href="<%=path%>/admin/css/charisma-app.css" rel="stylesheet">
    <link href='<%=path%>/admin/bower_components/responsive-tables/responsive-tables.css' rel='stylesheet'>
    <link href='<%=path%>/admin/bower_components/bootstrap/css/datepicker.css' rel='stylesheet'>
    <link href='<%=path%>/admin/bower_components/bootstrap-tour/build/css/bootstrap-tour.min.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/jquery.noty.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/noty_theme_default.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/elfinder.min.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/elfinder.theme.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/jquery.iphone.toggle.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/animate.min.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/admin.css' rel='stylesheet'>
    <link href='<%=path%>/admin/css/fms.css' rel='stylesheet'>

    <script src='<%=path%>/admin/bower_components/jquery/jquery.min.js'></script>
    <!--<script src='<%=path%>/admin/bower_components/jquery/jquery.js'></script>-->
    <script src="<%=path%>/admin/adminjs/common.js"></script>
    <script src="<%=path%>/admin/adminjs/fileavailabilitymanage.js"></script>
    <script src="<%=path%>/admin/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="<%=path%>/admin/js/jquery.cookie.js"></script>
    <script src='<%=path%>/admin/js/jquery.dataTables.min.js'></script>
    <script src="<%=path%>/admin/bower_components/chosen/chosen.jquery.min.js"></script>
    <script src="<%=path%>/admin/bower_components/colorbox/jquery.colorbox-min.js"></script>
    <script src="<%=path%>/admin/js/jquery.noty.js"></script>
    <script src="<%=path%>/admin/bower_components/responsive-tables/responsive-tables.js"></script>
    <script src="<%=path%>/admin/bower_components/bootstrap-tour/build/js/bootstrap-tour.min.js"></script>
    <script src="<%=path%>/admin/js/jquery.raty.min.js"></script>
    <script src="<%=path%>/admin/js/jquery.iphone.toggle.js"></script>
    <script src="<%=path%>/admin/js/jquery.autogrow-textarea.js"></script>
    <script src="<%=path%>/admin/js/jquery.history.js"></script>
    <script src="<%=path%>/admin/js/jquery.form.js"></script>
    <!--<script src="<%=path%>/admin/js/charisma.js"></script>-->

</head>
<body>
    <!-- topbar starts -->
    <jsp:include page="header.jsp" />
    <!-- topbar ends -->
    <div class="ch-container">
        <div class="row">
            <!-- leftmenu starts -->
            <jsp:include page="leftmenu.jsp" />
            <!-- leftmenu ends -->
            <div id="content" class="col-lg-10 col-sm-10">
                <!-- content starts -->
                <!-- breadcrumb starts -->
                <div>
                    <ul class="breadcrumb">
                        <li><a href="<%=path%>/page/admin/index.shtml">管理首页</a></li>
                        <li><a>文件有效性管理</a></li>
                    </ul>
                </div>
                <!-- breadcrumb end -->

                <!--正文开始-->
                <div class="box-content">
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a>文件有效性管理</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane active" >
                            <div>&nbsp;</div>
                            <div id="search-panel">
                                <div id="search-fileavailability-activity-form">
                                    <div class="form-group col-md-2 searchbox">
                                        <label for="name" class="control-label">&nbsp;</label>
                                        <input type="text" id="search-name" name="name" placeholder="搜索文件" class="form-control">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label for="key" class="control-label">&nbsp;</label>
                                        <a id="list-fileavailability-activity-btn" class="btn btn-primary form-control"> 查找 </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div style="clear: both;"></div>
                            <hr>
                            <div>
                                <table id="fileavailability-activity-table" class="table table-striped" cellspacing="0" width="100%">
                                    <thead>
                                       <tr>
                                          <th>序号</th>
                                          <th>业务类型</th>
                                          <th>文件类型</th>
                                          <th>文件名称</th>
                                          <th>文件版本号</th>
                                          <th>表单号</th>
                                          <th>链接</th>
                                          <th>文件状态</th>
                                          <th>有效性</th>
                                       </tr>
                                    </thead>
                                    <tbody>
                                       <tr>
                                          <td>1</td>
                                          <td>销售部门</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>BD00001</td>
                                          <td><a>下载链接</a></td>
                                          <td id="c1">正常</td>
                                          <td>
                                             <a id="b11" class="btn btn-primary btn-sm hide" href="javascript:unfreezeFile(1, '销售作业指导书', 'c1')"> 解冻 </a>
                                             <a id="b12" class="btn btn-danger btn-sm" href="javascript:freezeFile(1, '销售作业指导书', 'c1')"> 冻结 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>1</td>
                                          <td>销售部门</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>BD00001</td>
                                          <td><a>下载链接</a></td>
                                          <td>正常</td>
                                          <td>
                                             <a class="btn btn-danger btn-sm" href="javascript:freezeFile(1, '销售作业指导书')"> 冻结 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>1</td>
                                          <td>销售部门</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>BD00001</td>
                                          <td><a>下载链接</a></td>
                                          <td>正常</td>
                                          <td>
                                             <a class="btn btn-danger btn-sm" href="javascript:freezeFile(1, '销售作业指导书')"> 冻结 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>1</td>
                                          <td>销售部门</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>BD00001</td>
                                          <td><a>下载链接</a></td>
                                          <td>正常</td>
                                          <td>
                                             <a class="btn btn-danger btn-sm" href="javascript:freezeFile(1, '销售作业指导书')"> 冻结 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>1</td>
                                          <td>销售部门</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>BD00001</td>
                                          <td><a>下载链接</a></td>
                                          <td>正常</td>
                                          <td>
                                             <a class="btn btn-danger btn-sm" href="javascript:freezeFile(1, '销售作业指导书')"> 冻结 </a>
                                          </td>
                                       </tr>
                                    </tbody>
                                </table>
                                <div class="page tac yahei" id="paging_btn"><a href="" class="btn_bg_1">上一页</a><a href="">1</a><a href="" class="on">2</a><a href="">3</a><a href="">4</a><a href="">5</a><a href="">6</a><a href="" class="btn_bg_1">下一页</a></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--正文结束-->
            </div>
        </div>

        <!-- modal starts -->
        <!-- modal ends -->

        <!-- footer starts -->
        <jsp:include page="footer.jsp" />
        <!-- footer ends -->

    </div>

</body>
</html>
