<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title> 三级审批</title>
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
    <script src="<%=path%>/admin/adminjs/reviewmanage3.js"></script>
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
                        <li><a>三级审批</a></li>
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
                        <li class="active"><a>三级审批</a></li>
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
                                          <td>三级审批</td>
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
                                          <td>三级审批</td>
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
                                          <td>三级审批</td>
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
                                          <td>三级审批</td>
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
                                <label>审批结论</label>
                                <input type="checkbox" checked="true">通过
                                <input type="checkbox">不通过
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
                        <a id="review-approval-close-btn" class="btn btn-primary"> 结案 </a>
                        <a id="review-approval-auth-btn" class="btn btn-primary"> 设定权限 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="review-approval-do-auth-modal" tabindex="-1"
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
                        <label id="review-approval-note" class="cr hd"></label>
                        <a id="review-approval-review-auth-btn" class="btn btn-primary"> 预 览 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="review-approval-do-review-auth-modal" tabindex="-1"
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
                        <a id="review-approval-review4-btn" class="btn btn-primary"> 提交总经理审批 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="review-approval-do-review4-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>提交总经理审批</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table">
                            <tbody>
                                <tr>
                                    <td width="30%">提交总经理审批</td>
                                    <td width="70%">
                                        <input id="r4c1" type="checkbox" checked="true"/>需总经理审批
                                        <input id="r4c2" type="checkbox"/>无需总经理审批
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
                        <a id="review-approval-do-review4-submit-btn" class="btn btn-primary"> 提 交 </a>
                        <a id="review-approval-do-review4-close-btn" class="btn btn-primary" style="display:none;"> 结 案 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 退 出 </a>
                    </div>
                </div>
            </div>
        </div>
        
        
        
        
        <div class="modal fade" id="add-approval-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>添加审批业务</h3>
                    </div>
                    <div class="modal-body">
                        <form id="add-approval-form" action="<%=path%>/admin/addApproval" method="post">
                            <div class="form-group col-md-3">
                                <label>申请号</label>
                                <input type="text" placeholder="申请号" id="add-name" name="name" class="form-control" readonly="true" value="AS2016000001">
                            </div>
                            <div class="form-group col-md-3">
                                <label>申请人工号</label>
                                <input type="text" placeholder="申请人工号" id="add-name" name="name" class="form-control" readonly="true" value="SQR0000001">
                            </div>
                            <div class="form-group col-md-3">
                                <label>申请人姓名</label>
                                <input type="text" placeholder="申请人姓名" id="add-name" name="name" class="form-control" readonly="true" value="张三">
                            </div>
                            <div class="form-group col-md-3">
                                <label>所在部门</label>
                                <input type="text" placeholder="所在部门" id="add-name" name="name" class="form-control" readonly="true" value="销售部门">
                            </div>
                            <div class="form-group">
                                <label>业务类型</label>
                                <select id="add-award-coupon" name="coupon" class="form-control" title="选择业务类型">
                                    <option value='销售管理'>销售管理</option>
                                    <option value='生产管理'>生产管理</option>
                                    <option value='技术管理'>技术管理</option>
                                    <option value='实验室管理'>实验室管理</option>
                                    <option value='采购管理'>采购管理</option>
                                    <option value='人事管理'>人事管理</option>
                                    <option value='计划物控管理'>计划物控管理</option>
                                    <option value='财务管理'>财务管理</option>
                                    <option value='工艺管理'>工艺管理</option>
                                    <option value='设备管理'>设备管理</option>
                                    <option value='品质管理'>品质管理</option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>文件类型</label>
                                <input type="text" placeholder="文件类型" id="add-name" name="name" class="form-control" value="作业指导书">
                            </div>
                            <div class="form-group col-md-6">
                                <label>评审文件名称</label>
                                <input type="text" placeholder="评审文件名称" id="add-name" name="name" class="form-control" value="销售作业指导书">
                            </div>
                            <div class="form-group col-md-6">
                                <label>文件版本号</label>
                                <input type="text" placeholder="文件版本号" id="add-name" name="name" class="form-control" value="v1.0">
                            </div>
                            <div class="form-group col-md-6">
                                <label>表单号</label>
                                <input type="text" placeholder="表单号" id="add-name" name="name" class="form-control" value="BD000001">
                            </div>
                            <div class="form-group">
                                <label>备注</label>
                                <textarea placeholder="备注" id="add-description" name="description" class="form-control" rows="5" cols="60"></textarea>
                            </div>
                            <div class="form-group">
                                <label>上传附件</label>
                                <input type="file" name="coverFile" id="add-cover">
                                <img src="../../admin/img/upload01.jpg" id="add-activityCoverPic">
                                <img src="../../admin/img/upload01.jpg" id="add-activityCoverPic">
                                <img src="../../admin/img/upload01.jpg" id="add-activityCoverPic">
                                <img src="../../admin/img/upload01.jpg" id="add-activityCoverPic">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <label id="add-approval-note" class="cr hd"></label>
                        <a id="add-approval-go-review-btn" class="btn btn-primary"> 预 览 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="update-approval-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>编辑审批业务</h3>
                    </div>
                    <div class="modal-body">
                        <form id="update-approval-form" action="<%=path%>/admin/updateApproval" method="post">
                            <div class="form-group col-md-3">
                                <label>申请号</label>
                                <input type="text" placeholder="申请号" id="update-name" name="name" class="form-control" readonly="true" value="AS2016000001">
                            </div>
                            <div class="form-group col-md-3">
                                <label>申请人工号</label>
                                <input type="text" placeholder="申请人工号" id="update-name" name="name" class="form-control" readonly="true" value="SQR0000001">
                            </div>
                            <div class="form-group col-md-3">
                                <label>申请人姓名</label>
                                <input type="text" placeholder="申请人姓名" id="update-name" name="name" class="form-control" readonly="true" value="张三">
                            </div>
                            <div class="form-group col-md-3">
                                <label>所在部门</label>
                                <input type="text" placeholder="所在部门" id="update-name" name="name" class="form-control" readonly="true" value="销售部门">
                            </div>
                            <div class="form-group">
                                <label>业务类型</label>
                                <select id="update-award-coupon" name="coupon" class="form-control" title="选择业务类型">
                                    <option value='销售管理'>销售管理</option>
                                    <option value='生产管理'>生产管理</option>
                                    <option value='技术管理'>技术管理</option>
                                    <option value='实验室管理'>实验室管理</option>
                                    <option value='采购管理'>采购管理</option>
                                    <option value='人事管理'>人事管理</option>
                                    <option value='计划物控管理'>计划物控管理</option>
                                    <option value='财务管理'>财务管理</option>
                                    <option value='工艺管理'>工艺管理</option>
                                    <option value='设备管理'>设备管理</option>
                                    <option value='品质管理'>品质管理</option>
                                </select>
                            </div>
                            <div class="form-group col-md-6">
                                <label>文件类型</label>
                                <input type="text" placeholder="文件类型" id="update-name" name="name" class="form-control" value="作业指导书">
                            </div>
                            <div class="form-group col-md-6">
                                <label>评审文件名称</label>
                                <input type="text" placeholder="评审文件名称" id="update-name" name="name" class="form-control" value="销售作业指导书">
                            </div>
                            <div class="form-group col-md-6">
                                <label>文件版本号</label>
                                <input type="text" placeholder="文件版本号" id="update-name" name="name" class="form-control" value="v1.0">
                            </div>
                            <div class="form-group col-md-6">
                                <label>表单号</label>
                                <input type="text" placeholder="表单号" id="update-name" name="name" class="form-control" value="BD000001">
                            </div>
                            <div class="form-group">
                                <label>备注</label>
                                <textarea placeholder="备注" id="update-description" name="description" class="form-control" rows="5" cols="60"></textarea>
                            </div>
                            <div class="form-group">
                                <label>上传附件</label>
                                <input type="file" name="coverFile" id="update-cover">
                                <img src="../../admin/img/upload01.jpg" id="update-activityCoverPic">
                                <img src="../../admin/img/upload01.jpg" id="update-activityCoverPic">
                                <img src="../../admin/img/upload01.jpg" id="update-activityCoverPic">
                                <img src="../../admin/img/upload01.jpg" id="update-activityCoverPic">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <label id="update-approval-note" class="cr hd"></label>
                        <a id="update-approval-go-review-btn" class="btn btn-primary" data-dismiss="modal"> 确 认 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="list-approval-award-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>设置抽奖活动奖项</h3>
                    </div>
                    <div class="modal-body">
                        <div style="float:right;margin-right:30px">
                            <a id="add-approval-award-btn"  class="btn btn-success"><i class="glyphicon glyphicon-plus icon-white"></i> 添加奖项</a>
                        </div>
                        <div style="clear: both;"></div>
                        <hr style="margin-top:2px;margin-bottom:2px;">
                        <div>
                            <table id="approval-award-table" class="display" cellspacing="0" width="100%"></table>
                            <div class="page tac yahei" id="award_paging_btn"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="add-approval-award-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:600px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>添加奖项</h3>
                    </div>
                    <div class="modal-body">
                        <!-- form -->
                        <form id="add-approval-award-form" action="<%=path%>/admin/addApprovalAward" method="post">
                            <div class="form-group hide">
								                <input readonly="true" type="text" id="add-award-activity-id" name="activityId" class="form-control">
							              </div>
                            <div class="form-group" id="add-award-category-div">
                                <label>奖品类型&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <label class="radio-inline"><input type="radio" name="category" id="add-award-category-thing" value="实物" onchange="addAwardCategoryRadioChange(this.id)"> 实物</label>
                                <label class="radio-inline"><input type="radio" name="category" id="add-award-category-coupon" value="优惠券" onchange="addAwardCategoryRadioChange(this.id)"> 优惠券</label>
                            </div>
                            <div class="form-group col-md-4" id="add-award-name-div" style="padding-left:0px;width:180px">
                                <label>奖品名称</label>
                                <input type="text" placeholder="例：一等奖" id="add-award-name" name="name" class="form-control" >
                            </div>
                            <div class="form-group col-md-4" id="add-award-level-div" style="width:195px">
                                <label>奖品级别</label>
                                <input type="number" placeholder="" id="add-award-level" name="level" class="form-control" min="1" max="10" value="1" title="设置奖品级别。比如：一等奖为1，二等奖为2...">
                            </div>
                            <div class="form-group col-md-4" id="add-award-quantity-div" style="padding-right:0px;width:180px">
                                <label>奖品数量</label>
                                <input type="number" placeholder="奖品数量" id="add-award-quantity" name="quantity" class="form-control" min="1" value="1" >
                            </div>
                            <div class="form-group hide" id="add-award-coupon-div">
                                <label>选择优惠券</label>
                                <select id="add-award-coupon" name="coupon" class="form-control" title="请先去“优惠券管理”添加和导入优惠券">
                                </select>
                            </div>
                            <div class="form-group" id="add-award-way-div">
                                <label>领奖方式</label>
                                <select id="add-award-way" name="way" class="form-control" title="请选择实物领奖方式">
                                    <option value="自取" selected="selected">自取</option>
                                    <option value="邮寄">邮寄</option>
                                </select>
                            </div>
                            <div class="form-group" id="add-award-description-div">
                                <label>领奖信息</label>
                                <input type="text" placeholder="例：请于2015年x月x日到xxx支行领取" id="add-award-description" name="description" class="form-control" >
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <a id="add-approval-award-do-btn" class="btn btn-primary" data-dismiss="modal"> 确 定 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="update-approval-award-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:600px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>更新奖项</h3>
                    </div>
                    <div class="modal-body">
                        <!-- form -->
                        <form id="update-approval-award-form" action="<%=path%>/admin/updateApprovalAward" method="post">
                            <div class="form-group hide">
                                <input readonly="true" type="text" id="update-award-id" name="id" class="form-control">
								                <input readonly="true" type="text" id="update-award-activity-id" name="activityId" class="form-control">
							              </div>
                            <div class="form-group" id="update-award-category-div">
                                <label>奖品类型&nbsp;&nbsp;&nbsp;&nbsp;</label>
                                <label class="radio-inline"><input type="radio" name="category" id="update-award-category-thing" value="实物" onchange="updateAwardCategoryRadioChange(this.id)"> 实物</label>
                                <label class="radio-inline"><input type="radio" name="category" id="update-award-category-coupon" value="优惠券" onchange="updateAwardCategoryRadioChange(this.id)"> 优惠券</label>
                            </div>
                            <div class="form-group col-md-4" id="update-award-name-div" style="padding-left:0px;width:180px">
                                <label>奖品名称</label>
                                <input type="text" placeholder="例：一等奖" id="update-award-name" name="name" class="form-control" >
                            </div>
                            <div class="form-group col-md-4" id="update-award-level-div" style="width:195px">
                                <label>奖品级别</label>
                                <input type="number" placeholder="" id="update-award-level" name="level" class="form-control" min="1" max="10" value="1" title="设置奖品级别。比如：一等奖为1，二等奖为2...">
                            </div>
                            <div class="form-group col-md-4" id="update-award-quantity-div" style="padding-right:0px;width:180px">
                                <label>奖品数量</label>
                                <input type="number" placeholder="奖品数量" id="update-award-quantity" name="quantity" class="form-control" min="1" value="1" >
                            </div>
                            <div class="form-group hide" id="update-award-coupon-div">
                                <label>选择优惠券</label>
                                <select id="update-award-coupon" name="coupon" class="form-control" title="请先去“优惠券管理”添加和导入优惠券">
                                </select>
                            </div>
                            <div class="form-group" id="update-award-description-div">
                                <label>领奖信息</label>
                                <input type="text" placeholder="例：请于2015年x月x日到xxx支行领取" id="update-award-description" name="description" class="form-control" >
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <a id="update-approval-award-do-btn" class="btn btn-primary" data-dismiss="modal"> 确 定 </a>
                        <a href="#" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="list-approval-record-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:1200px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>抽奖记录</h3>
                    </div>
                    <div class="modal-body">
                        <div>
                            <table id="approval-record-table" class="display" cellspacing="0" width="100%"></table>
                            <div class="page tac yahei" id="record_paging_btn"></div>
                        </div>
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
