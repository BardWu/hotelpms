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
        <link rel="stylesheet" type="text/css" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap.min.css"/>
        <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table.js" ></script>
        <link rel="stylesheet" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap-table.css" />
        <link rel="stylesheet" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap-datetimepicker.min.css" />
        <script type="text/javascript" src="<%=path%>/js/middleware/pms_hotel_request.js" ></script>
        <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table-zh-CN.js" ></script>

</head>
</head>
<body>
<div>
    <div class="panel panel-default">
        <div class="panel-heading" style="display: flex;justify-content: space-between">酒店PMS请求详情</div>
    </div>

    <div id="toolbar">
        <form class="form-inline">
            <div class="form-group">

                <div class="form-group">
                    <div class="input-group">
                       <div class="input-group-addon">接收时间</div>
                        <input type="text" class="form-control" placeholder="开始时间" id="start">
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <%--<div class="input-group-addon">接收时间</div>--%>
                        <input type="text" class="form-control" placeholder="结束时间" id="end" >
                    </div>
                </div>

               <%-- <div class="input-group">
                    <div class="input-group-addon">接口类型</div>
                    <select class="form-control" name="port_type" id="port_type">
                        <option value="">请选择接口类型...</option>
                        <option value="0">fcs</option>
                        <option value="1">opera</option>
                    </select>
                </div>--%>
                <div class="input-group">
                    <div class="input-group-addon">状态</div>
                    <select class="form-control" name="status" id="status">
                        <option value="">请选择状态...</option>
                        <option value="0">未发送</option>
                        <option value="1">发送云端成功</option>
                        <option value="2">发送云端失败</option>
                    </select>
                </div>
                <div class="input-group">
                    <div class="input-group-addon">信息类型</div>
                    <select class="form-control" name="msg_Type" id="msg_Type">
                        <option value="">请选择请求类型...</option>
                        <option value="0">checkIn</option>
                        <option value="1">checkout</option>
                        <option value="2">GuestInfo</option>
                        <option value="3">roomChange</option>
                        <option value="9">RoomStatus(OperaFMF)</option>
                    </select>
                </div>
                <div class="input-group">
                    <div class="input-group-addon">uuid</div>
                    <input type="text" class="form-control" name="searchTexts" id="uuid" placeholder="请输入信息的uuid...">
                </div>
                <button type="button" class="btn btn-primary queryButton">查询</button>
            </div>
        </form>
    </div>

    <div id="divTable">
        <table id="fcsRequest_Table"></table>
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


</div>

</body>
</html>
