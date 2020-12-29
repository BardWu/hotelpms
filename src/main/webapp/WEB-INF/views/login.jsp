<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>hotelpms后台管理平台</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="后台登录">

    <script type="text/javascript" src="<%=path %>/js/com/jquery-1.9.1.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/plugin/bootstrap-3.3.5/css/bootstrap.min.css">

    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/transition.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/modal.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/com/style.css">
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/com/login.css">
</head>
<%--onsubmit="return login();"--%>
<body>
<form method="post"  action="" onsubmit="return false" class="form-signin form-horizontal ">
    <div class="modal modal-login" data-backdrop="false" data-show="true">
        <div class="modal-dialog modal-width">
            <div class="modal-content">
                <div class="modal-header modal-title">
                    <h4 class="modal-title">控制台登陆</h4>
                </div>
                <div class="modal-body">
                    <div id="login_error">

                    </div>
                    <div class="login-div div-login-password" >
                        <div class="div-left">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                        </div>
                        <div class="div-right">
                            <input type="text" class="input" name="username" id="username"  placeholder="账号" />
                        </div>
                        <div class="c-float"></div>
                    </div>

                    <div class="login-div div-password div-login-password">
                        <div class="div-left" style="height: 100%;" >
                            <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                        </div>
                        <div class="div-right">
                            <%--<input type="password" placeholder="密码" name="password" value="${requestScope.password}" class="input"/>--%>
                            <input type="password" placeholder="密码" name="password" id="password"  class="input"/>
                        </div>
                        <div class="c-float"></div>
                    </div>
                    <div class="btn-div">
                        <button type="submit" onclick="login()" class="btn btn-primary btn-block">登录</button>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</form>
</body>


<script type="text/javascript">

    function login() {

        var username = $('#username').val()+"";
        username=username.trim();
        var password = $('#password').val()+"";
        password=password.trim();
        if (username === ""||username === "undefined" ) {
            $('#login_error').html('请输入账号');
            return false;
        }
        if (password === ""||password === "undefined") {
            $('#login_error').html('请输入密码');
            return false;
        }

        var json = {username: username, password: password};
        $.ajax({
            async:false,
            cache: false,
            data: {json: JSON.stringify(json)},
            type: 'post',
            url: '/hotelpms/menu/login',
            error: function (error) {
                alert('请求失败 ' + error);
            },
            success: function (data) {
                if (data === "true") {
                    window.location.replace("/hotelpms/menu/main");
                } else {
                    $('#login_error').html("账号或密码错误");
                }
            }
        });
    }
</script>
</html>
