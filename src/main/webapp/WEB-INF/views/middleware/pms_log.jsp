<%--
  Created by IntelliJ IDEA.
  User: hp
  Date: 2018/10/23
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>日志信息</title>
    <%
        String path = request.getContextPath();
    %>
    <meta charset="UTF-8">
    <title></title>
    <script type="text/javascript" src="<%=path %>/js/com/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap.min.css"/>

    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table.js" ></script>
    <link rel="stylesheet" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap-table.css" />
   <%-- <link rel="stylesheet"  href="<%=path%>/css/backstage/fcs/pms_log.css" />--%>
    <link rel="stylesheet" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap-datetimepicker.min.css" />
    <script type="text/javascript" src="<%=path%>/js/middleware/pms_log.js" ></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table-zh-CN.js" ></script>
</head>
<style>
    table > tr td:nth-child(6){
        cursor: pointer !important;
    }
    td{
        max-width: 400px;
        overflow: hidden;
        max-height: 300px;
    }
</style>
<body>
<div>
    <div class="panel panel-default">
        <div class="panel-heading" style="display: flex;justify-content: space-between">后台日志信息</div>
    </div>

    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">时间</div>
                        <input type="text" class="form-control" placeholder="开始时间" id="start" >
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="结束时间" id="end" >
                    </div>
                </div>
                <div class="input-group">
                    <div class="input-group-addon">状态</div>
                    <select class="form-control" name="status" id="status">
                        <option value="">请选择状态...</option>
                        <option value="success">流水信息</option>
                        <option value="error">异常信息</option>
                    </select>
                </div>
            </div>
            <button type="button" class="btn btn-primary queryButton">查询</button>
        </form>
    </div>
  <%--  <div id="button" style="text-align: left;">
        <button id="btn1" class="btn btn-default" type="submit" data-toggle="modal">创建工单</button>
        <button id="btn2" class="btn btn-default" type="submit">获取客房信息</button>
        <button id="btn3" class="btn btn-default" type="submit">同步</button>
    </div>--%>
    <div id="divTable">
        <table id="log_Table"></table>
    </div>
</div>


<div class="modal fade" tabindex="-1" role="dialog" id="detailModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">字段内容</h4>
            </div>
            <div class="modal-body" style="text-align: center;">
                <textarea rows="15" cols="35" class="form-control"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
</body>
</html>
