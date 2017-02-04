<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="modal fade" id="message-block" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
    aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close refresher" data-dismiss="modal">×</button>
                <h3 id="alertmessage"></h3>
            </div>
            <div class="modal-footer">
                <a class="btn btn-primary refresher" data-dismiss="modal"> 确 定 </a>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="message-block-norefresh" tabindex="-1" role="dialog"
    aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close refresher" data-dismiss="modal">×</button>
                <h3 id="alertmessage-norefresh"></h3>
            </div>
            <div class="modal-footer">
                <a class="btn btn-primary" data-dismiss="modal"> 确 定 </a>
            </div>
        </div>
    </div>
</div>

<hr>

<footer class="row">
    <p class="col-md-9 col-sm-9 col-xs-12 copyright">
        &copy; <a>HQJL</a> 2015
    </p>
    <p class="col-md-3 col-sm-3 col-xs-12 powered-by">
        Powered by: <a>文件管理系统项目组</a>
    </p>
</footer>
