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
    <meta http-equiv="description" content="This is my page">

    <script src="<%=path%>/js/com/jquery-1.9.1.js"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            top.location.href = "menu/login";
        });
    </script>
</head>

<body>

</body>
</html>
