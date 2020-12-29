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
    <meta http-equiv="description" content="后台管理主页">

    <link rel="stylesheet" type="text/css" href="<%=path %>/plugin/bootstrap-3.3.5/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/plugin/menusilde/css/matrix-media.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/plugin/menusilde/css/matrix-style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/css/com/style.css"/>

    <script type="text/javascript" src="<%=path %>/js/com/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/bootstrap-3.3.5/js/modal.js"></script>
    <script type="text/javascript" src="<%=path %>/plugin/menusilde/js/matrix.js"></script>
    <script>

        $(document).ready(function(){
            var clientHeight = document.documentElement.clientHeight-45;
            $('.overflow').height(clientHeight);
            $(function(){
                $("#menuSilde li[class!='submenu']").on("click",function(){
                    $("#menuSilde li").not('.submenu').css("background-color","#e6eaee");
                    $(this).css("background-color","#d5d8dc");
                })
            });
        });
    </script>
    <style>
        .overflow,body,html{
            overflow-y: hidden;
        }
    </style>
</head>

<body>

      <div style="height:42px; line-height:40px; margin:0;padding:0; background-color:#dfe5eb;  border-bottom: #9eb2c7 solid 1px;">

          <div style="float: left; width:50%">
              <a class="brand" href="#"  style="color: #08223b;margin-left:10px; font-size: 16px; font-weight: bold;text-shadow:none;">
                  后台管理平台
              </a>
          </div>
          <div style="float:left;width:50%; text-align: right; padding-right:30px;">欢迎您</div>
          <div class="common-clear-float"></div>
      </div>
      <div class="container-fluid" style="padding:0; margin:0;">
          <div class="row-fluid" style="padding:0; margin:0;">
              <div  style="position:absolute; left:0;top:42px; padding:0; margin:0; width:15%;">
                  <div id="divMenu" class="well sidebar-nav overflow" style="padding:0;border:1px solid #c4c7cb;border-top:0;border-radius: 0;-webkit-border-radius: 0;-moz-border-radius:0;">
                      <div id="sidebar" >

                          <ul id="menuSilde"style="display: block; ">
                              <li class="submenu"><a href="#"><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                                  <span>首页</span> </a></li>
                              <li class="submenu" ><a href="#"><span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
                                  <span>系统管理</span></a>
                                  <ul style="list-style: none;">
                                      <li><a style="font-size: 12px;" href="/hotelpms/menu/console" target="content">
                                          <span class="glyphicon glyphicon-play" aria-hidden="true"></span>&nbsp;<span>接口管理</span> </a>
                                      </li>
                                      <%--<li><a style="font-size: 12px;"href="/hotelpms/menu/command" target="content">
                                          <span class="glyphicon glyphicon-play" aria-hidden="true"></span>&nbsp;<span>云端工单请求详情</span> </a>
                                      </li>--%>
                                      <li><a style="font-size: 12px;"href="/hotelpms/menu/fcsMessage" target="content">
                                          <span class="glyphicon glyphicon-play" aria-hidden="true"></span>&nbsp;<span>酒店PMS请求详情</span> </a>
                                      </li>
                                     <%-- <li><a style="font-size: 12px;"href="/hotelpms/menu/resend" target="content">
                                          <span class="glyphicon glyphicon-play" aria-hidden="true"></span>&nbsp;<span>重发至云端详情</span> </a>
                                      </li>--%>
                                      <li><a style="font-size: 12px;"href="/hotelpms/menu/logRecord" target="content">
                                          <span class="glyphicon glyphicon-play" aria-hidden="true"></span>&nbsp;<span>系统日志</span> </a>
                                      </li>
                                     <%-- <li><a style="font-size: 12px;"href="">
                                          <span class="glyphicon glyphicon-play" aria-hidden="true"></span>&nbsp;<span>系统配置</span> </a>
                                      </li>--%>
                                  </ul>
                              </li>
                          </ul>

                      </div>

                  </div><!--/.well -->
              </div><!--/span-->
              <div style="padding: 0;margin:0; width:85%; position:absolute; left:15%; top:42px; text-align: left;">

                  <iframe id="iframeIdContent" name="content"  class="overflow" allowfullscreen mozallowfullscreen webkitallowfullscreen src="" scrolling="auto" width="100%" style="margin:0; padding:0;border:red solid 0;" frameborder="0">
                  </iframe>

              </div><!--/span-->

          </div><!--/row-->

      </div><!--/.fluid-container-->



</body>
</html>
