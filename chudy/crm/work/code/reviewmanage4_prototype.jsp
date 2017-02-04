<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title> 四级审批</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="file manage system">
    <meta name="author" content="zgf">
    
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
    <script src="<%=path%>/admin/adminjs/reviewmanage4.js"></script>
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
                        <li><a>四级审批</a></li>
                    </ul>
                </div>
                <!-- breadcrumb end -->

                <!--正文开始-->
                <div class="box-content">
                    <!--
                    <div style="float:right;margin-right:30px">
                        <a id="add-approval-btn"  class="btn btn-success"><i class="glyphicon glyphicon-zoom-in icon-white"></i> 创建审批业务</a>
                    </div>
                    -->
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a>四级审批</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane active" >
                            <div>&nbsp;</div>
                            <div id="search-panel">
                                <div id="search-approval-form">
                                    <div class="form-group col-md-2 searchbox">
                                        <label for="name" class="control-label">&nbsp;</label>
                                        <input type="text" id="search-name" name="name" placeholder="搜索审批业务" class="form-control">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label for="key" class="control-label">&nbsp;</label>
                                        <a id="list-approval-btn" class="btn btn-primary form-control"> 查找 </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div style="clear: both;"></div>
                            <hr>
                            <div>
                                <table id="approval-table" class="table table-striped" cellspacing="0" width="100%">
                                    <thead>
                                       <tr>
                                          <th>申请号</th>
                                          <th>申请人工号</th>
                                          <th>申请人姓名</th>
                                          <th>申请人部门</th>
                                          <th>日期</th>
                                          <th>业务类型</th>
                                          <th>文件类型</th>
                                          <th>名称</th>
                                          <th>版本号</th>
                                          <th>状态</th>
                                          <th>操作</th>
                                       </tr>
                                    </thead>
                                    <tbody>
                                       <tr>
                                          <td>AS2016000001</td>
                                          <td>SQR0000001</td>
                                          <td>张三</td>
                                          <td>销售部门</td>
                                          <td>2016-01-18 10:00:00</td>
                                          <td>销售管理</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>四级审批</td>
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:showApprovalDetail(1, 1)"> 审批 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>AS2016000001</td>
                                          <td>SQR0000001</td>
                                          <td>张三</td>
                                          <td>销售部门</td>
                                          <td>2016-01-18 10:00:00</td>
                                          <td>销售管理</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>四级审批</td>
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:showApprovalDetail(1, 1)"> 审批 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>AS2016000001</td>
                                          <td>SQR0000001</td>
                                          <td>张三</td>
                                          <td>销售部门</td>
                                          <td>2016-01-18 10:00:00</td>
                                          <td>销售管理</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>四级审批</td>
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:showApprovalDetail(1, 1)"> 审批 </a>
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>AS2016000001</td>
                                          <td>SQR0000001</td>
                                          <td>张三</td>
                                          <td>销售部门</td>
                                          <td>2016-01-18 10:00:00</td>
                                          <td>销售管理</td>
                                          <td>作业指导书</td>
                                          <td>销售作业指导书</td>
                                          <td>v1.0</td>
                                          <td>四级审批</td>
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:showApprovalDetail(1, 1)"> 审批 </a>
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
        <div class="modal fade" id="review-approval-detail-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>审批业务详情</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <td width="50%">申请号</td>
                                    <td width="50%">AS2016000001</td>
                                </tr>
                                <tr>
                                    <td>申请人工号</td>
                                    <td>SQR0000001</td>
                                </tr>
                                <tr>
                                    <td>申请人工号</td>
                                    <td>SQR0000001</td>
                                </tr>
                                <tr>
                                    <td>申请人姓名</td>
                                    <td>张三</td>
                                </tr>
                                <tr>
                                    <td>所在部门</td>
                                    <td>销售部门</td>
                                </tr>
                                <tr>
                                    <td>业务类型</td>
                                    <td>销售管理</td>
                                </tr>
                                <tr>
                                    <td>文件类型</td>
                                    <td>作业指导书</td>
                                </tr>
                                <tr>
                                    <td>评审文件名称</td>
                                    <td>销售作业指导书</td>
                                </tr>
                                <tr>
                                    <td>文件版本号</td>
                                    <td>v1.0</td>
                                </tr>
                                <tr>
                                    <td>表单号</td>
                                    <td>BD000001</td>
                                </tr>
                                <tr>
                                    <td>备注</td>
                                    <td>备注: <br>&nbsp;&nbsp;&nbsp;&nbsp;1. abc<br>&nbsp;&nbsp;&nbsp;&nbsp;2. def<br></td>
                                </tr>
                                <tr>
                                    <td>上传附件</td>
                                    <td>
                                        <img src="../../admin/img/upload02.jpg" id="add-activityCoverPic">
                                        <img src="../../admin/img/upload02.jpg" id="add-activityCoverPic">
                                        <img src="../../admin/img/upload02.jpg" id="add-activityCoverPic">
                                        <img src="../../admin/img/upload02.jpg" id="add-activityCoverPic">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
                        <a id="review-approval-review-btn" class="btn btn-primary"> 审 批 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="review-approval-do-review-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>审批</h3>
                    </div>
                    <div class="modal-body">
                        <form id="add-approval-form" action="<%=path%>/admin/addApproval" method="post">
                            <div class="form-group">
                                <label>审批意见</label>
                                <textarea placeholder="审批意见" id="add-description" name="description" class="form-control" rows="10" cols="60"></textarea>
                            </div>
                            <div class="form-group">
                                <label>结案</label>
                                <input id="c1" type="checkbox" checked="true">通过
                                <input id="c2" type="checkbox">不通过
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
                        <a id="review-approval-submit-btn" class="btn btn-primary"> 提交 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <!-- modal ends -->

        <!-- footer starts -->
        <jsp:include page="footer.jsp" />
        <!-- footer ends -->

    </div>

</body>
</html>
