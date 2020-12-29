<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
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
        <link rel="stylesheet" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap-datetimepicker.min.css" />
        <link rel="stylesheet"  href="<%=path%>/css/middleware/pms_cloud_command.css" />
        <script type="text/javascript" src="<%=path%>/js/middleware/pms_cloud_command.js" ></script>
        <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table-zh-CN.js" ></script>
    </head>
</head>
<body>
<div>
    <div class="panel panel-default">
        <div class="panel-heading" style="display: flex;justify-content: space-between">云端工单请求详情</div>
    </div>
   <%-- <div id="button" style="text-align: left;">
        <button id="btn1" class="btn btn-default" type="submit" data-toggle="modal" >发送测试请求</button>
    </div>--%>
    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">云端请求时间</div>
                        <input type="text" class="form-control" placeholder="开始时间" id="start"  >
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="结束时间" id="end"  >
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">uuid</div>
                        <input type="text" class="form-control" name="searchTexts" id="uuid" placeholder="请输入信息的uuid...">
                    </div>
                </div>
                <div class="input-group">
                    <div class="input-group-addon">状态</div>
                    <select class="form-control" name="status" id="status">
                        <option value="">请选择状态...</option>
                        <option value="0">未发送</option>
                        <option value="1">发送到工单系统成功</option>
                        <option value="2">发送到工单系统失败</option>
                        <option value="3">发送云端成功</option>
                        <option value="4">发送云端失败</option>
                      <%--  态 0：未发送 1：发送fcs成功 2：发送fcs失败 3：发送云端成功 4：发送云端失败--%>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon">请求类型</div>
                    <select class="form-control" name="request_Type" id="requestType">
                        <option value="">请选择请求类型...</option>
                        <option value="7">createjob</option>
                    </select>
                </div>
            </div>
            <button type="button" class="btn btn-primary queryButton">查询</button>
        </form>
    </div>
    <div id="divTable">
        <table id="command_Table"></table>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="myModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">请求内容</h4>
            </div>
            <div class="modal-body" style="text-align: center;">
                <textarea rows="15" cols="35" class="form-control"></textarea>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary">提交请求</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


<div class="modal fade" tabindex="-1" role="dialog" id="box">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body" style="text-align: center;">
                <img src="<%=path %>/images/cricle.png" class="rotate"/>
                <h3>请求中...</h3>
            </div>
        </div>
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
