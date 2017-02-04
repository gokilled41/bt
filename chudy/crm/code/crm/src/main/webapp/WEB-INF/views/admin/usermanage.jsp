<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title> 用户管理</title>
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
    <link href='<%=path%>/admin/css/crm.css' rel='stylesheet'>

    <script src='<%=path%>/admin/bower_components/jquery/jquery.min.js'></script>
    <!--<script src='<%=path%>/admin/bower_components/jquery/jquery.js'></script>-->
    <script src="<%=path%>/admin/adminjs/common.js"></script>
    <script src="<%=path%>/admin/adminjs/usermanage.js"></script>
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
    <!--<script src="<%=path%>/admin/js/jquery.history.js"></script>-->
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
                        <li><a href="<%=path%>/page/admin/usermanage.shtml">用户管理</a></li>
                    </ul>
                </div>
                <!-- breadcrumb end -->

                <!--正文开始-->
                <div class="box-content">
                    <div style="float:right;margin-right:30px">
                        <a id="add-user-btn"  class="btn btn-success"><i class="glyphicon glyphicon-zoom-in icon-white"></i> 创建用户</a>
                    </div>
                    <ul class="nav nav-tabs" id="myTab">
                        <li class="active"><a>用户管理</a></li>
                    </ul>
                    <div id="myTabContent" class="tab-content">
                        <div class="tab-pane active">
                            <div>&nbsp;</div>
                            <div id="search-panel">
                                <div id="search-user-form">
                                    <div class="form-group col-md-2 searchbox">
                                        <label for="name" class="control-label">&nbsp;</label>
                                        <input type="text" id="search-name" name="name" placeholder="搜索用户" class="form-control">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label for="key" class="control-label">&nbsp;</label>
                                        <a id="list-user-btn" class="btn btn-primary form-control"> 查找 </a>
                                    </div>
                                </div>
                            </div>
                            
                            <div style="clear: both;"></div>
                            <hr>
                            <div>
                                <table id="user-table" class="table table-striped" cellspacing="0" width="100%"></table>
                                <div class="page tac yahei" id="paging_btn"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!--正文结束-->
            </div>
        </div>

        <!-- modal starts -->
        <div class="modal fade" id="add-user-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>添加用户</h3>
                    </div>
                    <div class="modal-body">
                        <form id="add-user-form" action="<%=path%>/user/addUser.shtml" method="post">
                            <div class="form-group col-md-4">
                                <label>用户名</label>
                                <input type="text" placeholder="用户名" id="add-userName" name="userName" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>密码</label>
                                <input type="password" placeholder="密码" id="add-password" name="password" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>姓名</label>
                                <input type="text" placeholder="姓名" id="add-nickName" name="nickName" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>工号</label>
                                <input type="text" placeholder="工号" id="add-employeeNo" name="employeeNo" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>电话</label>
                                <input type="text" placeholder="电话" id="add-phone" name="phone" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>所在部门</label>
                                <input type="text" placeholder="所在部门" id="add-department" name="department" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>邮箱</label>
                                <input type="text" placeholder="邮箱" id="add-email" name="email" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>业务类型</label>
                                <select id="add-businessType" name="businessType" class="form-control" title="选择业务类型">
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
                            <div class="form-group col-md-4">
                                <label>岗位</label>
                                <select id="add-position" name="position" class="form-control" title="选择岗位">
                                    <option value='职员'>职员</option>
                                    <option value='主管'>主管</option>
                                    <option value='经理'>经理</option>
                                    <option value='总经理'>总经理</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>审批角色</label>
                                <div>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-approvalRole" name="approvalRole" value="一级审批"> 一级审批
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-approvalRole" name="approvalRole" value="二级审批"> 二级审批
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-approvalRole" name="approvalRole" value="三级审批"> 三级审批
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-approvalRole" name="approvalRole" value="四级审批"> 四级审批
                                   </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>管理员角色</label>
                                <div>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-adminRole" name="adminRole" value="无权限"> 无权限
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-adminRole" name="adminRole" value="基本账号管理"> 基本账号管理
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-adminRole" name="adminRole" value="文件查询权限管理"> 文件查询权限管理
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="add-adminRole" name="adminRole" value="文件有效性管理"> 文件有效性管理
                                   </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>账号状态</label>
                                <select id="add-status" name="status" class="form-control" title="选择账号状态">
                                    <option value='正常'>正常</option>
                                    <option value='冻结'>冻结</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <label id="add-user-note" class="cr hd"></label>
                        <a id="add-user-go-review-btn" class="btn btn-primary"> 预 览 </a>
                        <a id="add-user-cancel-btn" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="add-user-review-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>预览用户</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <td width="50%">用户名</td>
                                    <td width="50%" id="add-review-userName"></td>
                                </tr>
                                <tr>
                                    <td>姓名</td>
                                    <td id="add-review-nickName"></td>
                                </tr>
                                <tr>
                                    <td>工号</td>
                                    <td id="add-review-employeeNo"></td>
                                </tr>
                                <tr>
                                    <td>电话</td>
                                    <td id="add-review-phone"></td>
                                </tr>
                                <tr>
                                    <td>所在部门</td>
                                    <td id="add-review-department"></td>
                                </tr>
                                <tr>
                                    <td>邮箱</td>
                                    <td id="add-review-email"></td>
                                </tr>
                                <tr>
                                    <td>业务类型</td>
                                    <td id="add-review-businessType"></td>
                                </tr>
                                <tr>
                                    <td>岗位</td>
                                    <td id="add-review-position"></td>
                                </tr>
                                <tr>
                                    <td>审批角色</td>
                                    <td id="add-review-approvalRole"></td>
                                </tr>
                                <tr>
                                    <td>管理员角色</td>
                                    <td id="add-review-adminRole"></td>
                                </tr>
                                <tr>
                                    <td>账号状态</td>
                                    <td id="add-review-status"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="add-user-note" class="cr hd"></label>
                        <a id="add-user-return-edit-btn" class="btn btn-primary"> 返回编辑 </a>
                        <a id="add-user-submit-btn" class="btn btn-primary"> 提交 </a>
                        <a id="add-user-review-cancel-btn" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="update-user-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>添加用户</h3>
                    </div>
                    <div class="modal-body">
                        <form id="update-user-form" action="<%=path%>/user/updateUser.shtml" method="post">
                            <div class="form-group hd">
                                <input type="text" placeholder="ID" id="update-id" name="id" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>用户名</label>
                                <input type="text" placeholder="用户名" id="update-userName" name="userName" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>密码</label>
                                <input type="password" placeholder="密码" id="update-password" name="password" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>姓名</label>
                                <input type="text" placeholder="姓名" id="update-nickName" name="nickName" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>工号</label>
                                <input type="text" placeholder="工号" id="update-employeeNo" name="employeeNo" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>电话</label>
                                <input type="text" placeholder="电话" id="update-phone" name="phone" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>所在部门</label>
                                <input type="text" placeholder="所在部门" id="update-department" name="department" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>邮箱</label>
                                <input type="text" placeholder="邮箱" id="update-email" name="email" class="form-control">
                            </div>
                            <div class="form-group col-md-4">
                                <label>业务类型</label>
                                <select id="update-businessType" name="businessType" class="form-control" title="选择业务类型">
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
                            <div class="form-group col-md-4">
                                <label>岗位</label>
                                <select id="update-position" name="position" class="form-control" title="选择岗位">
                                    <option value='职员'>职员</option>
                                    <option value='主管'>主管</option>
                                    <option value='经理'>经理</option>
                                    <option value='总经理'>总经理</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>审批角色</label>
                                <div>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-approvalRole" name="approvalRole" value="一级审批"> 一级审批
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-approvalRole" name="approvalRole" value="二级审批"> 二级审批
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-approvalRole" name="approvalRole" value="三级审批"> 三级审批
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-approvalRole" name="approvalRole" value="四级审批"> 四级审批
                                   </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>管理员角色</label>
                                <div>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-adminRole" name="adminRole" value="无权限"> 无权限
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-adminRole" name="adminRole" value="基本账号管理"> 基本账号管理
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-adminRole" name="adminRole" value="文件查询权限管理"> 文件查询权限管理
                                   </label>
                                   <label class="checkbox-inline">
                                      <input type="checkbox" id="update-adminRole" name="adminRole" value="文件有效性管理"> 文件有效性管理
                                   </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>账号状态</label>
                                <select id="update-status" name="status" class="form-control" title="选择账号状态">
                                    <option value='正常'>正常</option>
                                    <option value='冻结'>冻结</option>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <label id="update-user-note" class="cr hd"></label>
                        <a id="update-user-go-review-btn" class="btn btn-primary"> 预 览 </a>
                        <a id="update-user-cancel-btn" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="update-user-review-modal" tabindex="-1"
            role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

            <div class="modal-dialog" style="width:800px">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">×</button>
                        <h3>预览用户</h3>
                    </div>
                    <div class="modal-body">
                        <table class="table table-striped">
                            <tbody>
                                <tr>
                                    <td width="50%">用户名</td>
                                    <td width="50%" id="update-review-userName"></td>
                                </tr>
                                <tr>
                                    <td>姓名</td>
                                    <td id="update-review-nickName"></td>
                                </tr>
                                <tr>
                                    <td>工号</td>
                                    <td id="update-review-employeeNo"></td>
                                </tr>
                                <tr>
                                    <td>电话</td>
                                    <td id="update-review-phone"></td>
                                </tr>
                                <tr>
                                    <td>所在部门</td>
                                    <td id="update-review-department"></td>
                                </tr>
                                <tr>
                                    <td>邮箱</td>
                                    <td id="update-review-email"></td>
                                </tr>
                                <tr>
                                    <td>业务类型</td>
                                    <td id="update-review-businessType"></td>
                                </tr>
                                <tr>
                                    <td>岗位</td>
                                    <td id="update-review-position"></td>
                                </tr>
                                <tr>
                                    <td>审批角色</td>
                                    <td id="update-review-approvalRole"></td>
                                </tr>
                                <tr>
                                    <td>管理员角色</td>
                                    <td id="update-review-adminRole"></td>
                                </tr>
                                <tr>
                                    <td>账号状态</td>
                                    <td id="update-review-status"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <label id="update-user-note" class="cr hd"></label>
                        <a id="update-user-return-edit-btn" class="btn btn-primary"> 返回编辑 </a>
                        <a id="update-user-submit-btn" class="btn btn-primary"> 提交 </a>
                        <a id="update-user-review-cancel-btn" class="btn btn-default" data-dismiss="modal"> 取 消 </a>
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
