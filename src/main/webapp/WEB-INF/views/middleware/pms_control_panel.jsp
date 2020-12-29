<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        String path = request.getContextPath();
    %>
    <title>Title</title>
    <script type="text/javascript" src="<%=path %>/js/com/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap.min.css"/>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table.js" ></script>
    <link rel="stylesheet" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap-table.css" />
   <script src="<%=path%>/js/middleware/pms_control_panel.js"></script>
   <link href="<%=path%>/css/middleware/pms_control_panel.css" rel="stylesheet"  />
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap-table-zh-CN.js" ></script>
</head>
<script>
    $(document).ready(function(){
        var clientHeight = document.documentElement.clientHeight-65;
        $('.div-console').height(clientHeight);
    });
</script>
<body>


<div class="panel panel-default">
    <div class="panel-heading" style="display: flex;justify-content: space-between"><%--<h4 style="font-weight:bold">控制台</h4>--%>
        <div id="error_msg" style="margin: 0 auto;display: none;background-color: red" ></div>
        <div id="fcs_conn_msg" style="display: none">
            <div style="float:left">
                <div  style="display:inline">
                    <h4 style="font-weight:bold;display:inline-block" >FCS: </h4>&nbsp;&nbsp;
                    IP : <h4 id="fcsIp" style=" display:inline-block">192.167.3.2</h4> &nbsp;&nbsp;
                    PORT : <h4 id="fcsPort"  style=" display:inline-block">2323</h4>
                </div>

                <div class="div-status" style="display:inline">
                    <h4 style="font-weight:bold;display:inline-block" >状态:</h4> <span class="fcs_msg-status" style="margin-right: 20px;">断开</span>  <h4 style="font-weight:bold;display:inline-block" >操作:</h4>:
                    <div id="fcs_btn_restart" style="cursor: pointer;display: inline-block;"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>&nbsp;&nbsp;连接</div>
                    <div id="fcs_btn_close" style="cursor: pointer;display: inline-block;" class="hide"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;断开</div>
                    <div id="fcs_btn_relink" style="cursor: pointer;display: inline-block;"class="hide"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;取消连接</div>
                </div>
            </div>
        </div>
        <div id="tl_conn_msg" style="display: none">
            <div style="float:left">
                <div  style="display:inline">
                    <h4 style="font-weight:bold;display:inline-block" >PBX(UCS): </h4>&nbsp;&nbsp;
                    IP : <h4 id="tlIp" style=" display:inline-block">192.167.3.2</h4> &nbsp;&nbsp;
                    PORT : <h4 id="tlPort"  style=" display:inline-block">2323</h4>
                </div>

                <div class="div-status" style="display:inline">
                    <h4 style="font-weight:bold;display:inline-block" >状态:</h4> <span class="tl_msg-status" style="margin-right: 20px;">断开</span>  <h4 style="font-weight:bold;display:inline-block" >操作:</h4>:
                    <div id="tl_btn_restart" style="cursor: pointer;display: inline-block;"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span>&nbsp;&nbsp;连接</div>
                    <div id="tl_btn_close" style="cursor: pointer;display: inline-block;" class="hide"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;断开</div>
                    <div id="tl_btn_relink" style="cursor: pointer;display: inline-block;"class="hide"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>&nbsp;&nbsp;取消连接</div>
                </div>
            </div>
        </div>
    </div>

    <div class="panel-body">
        <div  style="position: relative">
            <div class="div-console" id="div-console" style="background-color: black">
            </div>
            <div id ="div_cricle" class="hide" style="flex-direction:column;display:flex;justify-content:center;align-items:center;background-color: #fff;height: 100%;width: 100%;position: absolute;top: 0;left: 0;z-index: 9999">
                <img src="<%=path %>/images/cricle.png" class="rotate"/>
                <h3>链接中...</h3>
            </div>
        </div>
    </div>
</div>

</body>
</html>
