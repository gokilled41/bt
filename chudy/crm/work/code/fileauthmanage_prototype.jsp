<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title> 文件查询权限管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="file manage system">
    <meta name="fileauthor" content="zgf">
    
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
    <script src="<%=path%>/admin/adminjs/fileauthmanage.js"></script>
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
                        <li><a>文件查询权限管理</a></li>
                    </ul>
                </div>
                <!-- breadcrumb end -->

                <!--正文开始-->
                <div class="box-content">
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a>文件查询权限管理</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane active" >
                            <div>&nbsp;</div>
                            <div id="search-panel">
                                <div id="search-fileauth-activity-form">
                                    <div class="form-group col-md-2 searchbox">
                                        <label for="name" class="control-label">&nbsp;</label>
                                        <input type="text" id="search-name" name="name" placeholder="搜索文件" class="form-control">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label for="key" class="control-label">&nbsp;</label>
                                        <a id="list-fileauth-activity-btn" class="btn btn-primary form-control"> 查找 </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div style="clear: both;"></div>
                            <hr>
                            <div>
                                <table id="fileauth-activity-table" class="table table-striped" cellspacing="0" width="100%">
                                    <thead>
                                       <tr>
                                          <th>序号</th>
                                          <th>业务类型</th>
                                          <th>文件类型</th>
                                          <th>文件名称</th>
                                          <th>文件版本号</th>
                                          <th>表单号</th>
                                          <th>链接</th>
                                          <th>查询权限</th>
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
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:viewAuth(1, 1)"> 查看 </a>
                                             <a class="btn btn-primary btn-sm"  href="javascript:editAuth(2, 2)"> 编辑 </a>
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
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:updateFileauthActivity(1, 1)"> 查看 </a>
                                             <a class="btn btn-primary btn-sm"  href="javascript:updateFileauthActivity(2, 2)"> 编辑 </a>
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
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:updateFileauthActivity(1, 1)"> 查看 </a>
                                             <a class="btn btn-primary btn-sm"  href="javascript:updateFileauthActivity(2, 2)"> 编辑 </a>
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
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:updateFileauthActivity(1, 1)"> 查看 </a>
                                             <a class="btn btn-primary btn-sm"  href="javascript:updateFileauthActivity(2, 2)"> 编辑 </a>
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
                                          <td>
                                             <a class="btn btn-primary btn-sm" href="javascript:updateFileauthActivity(1, 1)"> 查看 </a>
                                             <a class="btn btn-primary btn-sm"  href="javascript:updateFileauthActivity(2, 2)"> 编辑 </a>
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
        <div class="modal fade" id="edit-auth-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>设定权限</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <tbody>
                                <tr style="background-color:lightgrey;" class="tl1">
                                    <td colspan="2">读取权限设定</td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox" checked="true">销售管理</td>
                                    <td width="70%">
                                        <input type="checkbox" checked="true">审批人1
                                        <input type="checkbox">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox">审批人4
                                    </td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox" checked="true">生产管理</td>
                                    <td width="70%">
                                        <input type="checkbox" checked="true">审批人1
                                        <input type="checkbox">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox">审批人4
                                    </td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox" checked="true">技术管理</td>
                                    <td width="70%">
                                        <input type="checkbox" checked="true">审批人1
                                        <input type="checkbox">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox">审批人4
                                    </td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox">实验室管理</td>
                                    <td width="70%">
                                    </td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox">采购管理</td>
                                    <td width="70%">
                                    </td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox" checked="true">人事管理</td>
                                    <td width="70%">
                                        <input type="checkbox">审批人1
                                        <input type="checkbox" checked="true">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox" checked="true">审批人4
                                    </td>
                                </tr>
                                <tr class="hr1">
                                    <td width="30%"><input type="checkbox">计划物控管理</td>
                                    <td width="70%">
                                    </td>
                                </tr>
                                <tr style="background-color:lightgrey;" class="tl2">
                                    <td colspan="2">下载权限设定</td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox" checked="true">销售管理</td>
                                    <td width="70%">
                                        <input type="checkbox" checked="true">审批人1
                                        <input type="checkbox">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox">审批人4
                                    </td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox" checked="true">生产管理</td>
                                    <td width="70%">
                                        <input type="checkbox" checked="true">审批人1
                                        <input type="checkbox">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox">审批人4
                                    </td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox" checked="true">技术管理</td>
                                    <td width="70%">
                                        <input type="checkbox" checked="true">审批人1
                                        <input type="checkbox">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox">审批人4
                                    </td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox">实验室管理</td>
                                    <td width="70%">
                                    </td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox">采购管理</td>
                                    <td width="70%">
                                    </td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox" checked="true">人事管理</td>
                                    <td width="70%">
                                        <input type="checkbox">审批人1
                                        <input type="checkbox" checked="true">审批人2
                                        <input type="checkbox" checked="true">审批人3
                                        <input type="checkbox" checked="true">审批人4
                                    </td>
                                </tr>
                                <tr class="hr2">
                                    <td width="30%"><input type="checkbox">计划物控管理</td>
                                    <td width="70%">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="add-file-activity-note" class="cr hd"></label>
                        <a id="edit-auth-review-btn" class="btn btn-primary"> 预 览 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="edit-auth-review-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>预览权限设定</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <td width="30%">部门</td>
                                    <td width="30%">人员</td>
                                    <td width="40%">权限</td>
                                </tr>
                                <tr>
                                    <td>销售管理</td>
                                    <td>李四</td>
                                    <td>阅读</td>
                                </tr>
                                <tr>
                                    <td>生产管理</td>
                                    <td>张三</td>
                                    <td>阅读 下载</td>
                                </tr>
                                <tr>
                                    <td>技术管理</td>
                                    <td>王二</td>
                                    <td>阅读</td>
                                </tr>
                                <tr>
                                    <td>销售管理</td>
                                    <td>麻子</td>
                                    <td>阅读 下载</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
                        <a id="edit-auth-submit-btn" class="btn btn-primary"> 提 交 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="view-auth-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>预览权限设定</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <td width="30%">部门</td>
                                    <td width="30%">人员</td>
                                    <td width="40%">权限</td>
                                </tr>
                                <tr>
                                    <td>销售管理</td>
                                    <td>李四</td>
                                    <td>阅读</td>
                                </tr>
                                <tr>
                                    <td>生产管理</td>
                                    <td>张三</td>
                                    <td>阅读 下载</td>
                                </tr>
                                <tr>
                                    <td>技术管理</td>
                                    <td>王二</td>
                                    <td>阅读</td>
                                </tr>
                                <tr>
                                    <td>销售管理</td>
                                    <td>麻子</td>
                                    <td>阅读 下载</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
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
