var websocket = null;
var hostAddress = null;
var commandTestUrl = null;//发送测试请求的url
$(document).ready(function() {
    $('.div-left .div-item').on('click', function() {
        $('.div-item').removeClass('active');
        $(this).addClass('active');
    });
    hostAddress = 'ws://'+location.hostname+':'+location.port+'/hotelpms/websocket';

    initFunction();//初始化function
});

function initFunction(){
    //接口设置
    $("#port_command").click(function(){
        // $(".modal-body textarea").val()
        $('#port-modal').modal('show')

    });

    //工单状态队列
    $("#status_list").click(function(){
        var oTable = new TableInit();
        oTable.Init();
        $('#queue-modal').modal('show');
    })

    btnRestart();
    btnClose();
    createWebSocket();
    splitDiv();//截取div
    btnRelink();//取消socket重新连接
    initPortMsg();
    getConnectsStatus();//获取Fcs连接状态
    databaseSyncPMS();//同步
}




function splitDiv(){
    setInterval(function(){
        var divLen=$("#div-console div").length-100;
        $("#div-console div:lt("+divLen+")").remove();
    }, 200000);
}
function btnClose(){
    $("#fcs_btn_close").click(function(){
        $("#fcs_btn_close").addClass("hide");
        $(".fcs_msg-status").text("关闭中....");
        changeStatus("0","fcs");
    });
    $("#tl_btn_close").click(function(){
        $("#tl_btn_close").addClass("hide");
        $(".tl_msg-status").text("关闭中....");
        changeStatus("0","tl");
    });
}
function btnRestart() {
    $("#fcs_btn_restart").unbind('click').one('click',function(){
        $(".fcs_msg-status").text("连接中....");
        $("#fcs_btn_restart span").addClass('rotate');
        changeStatus("1","fcs");
    });
    $("#tl_btn_restart").unbind('click').one('click',function(){
        $(".tl_msg-status").text("连接中....");
        $("#tl_btn_restart span").addClass('rotate');
        changeStatus("1","tl");
    });
}
//取消重新连接
function btnRelink(){
    /*$("#fcs_btn_relink").unbind('click').one('click',function(){
      /!*  $(".msg-status").text("重连中....");
        $("#btn_restart span").addClass('rotate');*!/
        $("#fcs_btn_relink").addClass("hide");
        $(".fcs_msg-status").text("取消中....");
        changeStatus("2","fcs");
    });*/
  /*  $("#opera_btn_relink").unbind('click').one('click',function(){
        /!*  $(".msg-status").text("重连中....");
          $("#btn_restart span").addClass('rotate');*!/
        $("#opera_btn_relink").addClass("hide");
        $(".opera_msg-status").text("取消中....");
        changeStatus("2","opera");
    });*/
    $("#fcs_btn_relink").click(function(){
        $("#fcs_btn_relink").addClass("hide");
        $(".fcs_msg-status").text("取消中....");
        changeStatus("2","fcs");
    });
    $("#tl_btn_relink").click(function(){
        $("#tl_btn_relink").addClass("hide");
        $(".tl_msg-status").text("取消中....");
        changeStatus("2","ucs");
    });

}
function createWebSocket(){
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket'in window) {
        try {
         websocket = new WebSocket(hostAddress);
           /* websocket = new WebSocket("ws://192.168.1.205:8033/websocket");*/
         init();
         console.info("开始链接");
        } catch(e) {
            console.log('connect failed');
        websocket.close();
        }
    } else {
        alert('当前浏览器 Not support websocket')
    }
}
function reconnect(){
    createWebSocket();
}

function init(){
    //连接发生错误的回调方法
    websocket.onerror = function () {
        $("#div_cricle").removeClass("hide");
        setMessageInnerHTML("WebSocket连接发生错误");
    };

//连接成功建立的回调方法
    websocket.onopen = function () {
        $("#div_cricle").addClass("hide")
        setMessageInnerHTML("控制台连接成功");
        heartCheck.reset().start();
    }

//接收到消息的回调方法
    websocket.onmessage = function (event) {
        console.log("重置");
        heartCheck.reset().start();
        setMessageInnerHTML(event.data);
    }

//连接关闭的回调方法
    websocket.onclose = function (type) {
        console.info("客户端已关闭，开始重连");
        $("#div_cricle").removeClass("hide");
       createWebSocket();
    }
}


//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
/*window.onbeforeunload = function () {
    closeWebSocket();
}*/
var heartCheck={
    timeout :60000,
    timeoutObj:null,
    serverTimeoutObj:null,
    reset:function () {
        clearTimeout(this.timeoutObj);
        clearTimeout(this.serverTimeoutObj);
        return this;
    },
    start:function(){
        var self = this;
        this.timeoutObj = setTimeout(function(){
            console.info("客户端发送了：ping");
            websocket.send("ping");
            self.serverTimeoutObj = setTimeout(function () {
                console.info("客户端关闭了");
                websocket.close();
            },self.timeout)
        },this.timeout)
    }
}

//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    //根据websocket 发送的信息来判断链接的状态
    if(innerHTML ==='pong'){
        return ;
    }
    console.log("xxxx"+innerHTML);
    //根据websocket 发送的信息来判断链接的状态
    //2：fcs连接失败
    if(innerHTML.indexOf("MessagePoint.FCS_CONNECT_TYPE=2")>=0 ){
        $(".fcs_msg-status").text("断开");
        $(".fcs_msg-status").css("background","red");
        btnRestart();
        $("#fcs_btn_restart span").removeClass('rotate');
        $("#fcs_btn_restart").removeClass("hide");
        $("#fcs_btn_close").addClass("hide");
        $("#fcs_btn_relink").addClass("hide");
    }
    //2：tl连接失败
    if(innerHTML.indexOf("MessagePoint.UCS_CONNECT_TYPE=2")>=0 ){
        $(".tl_msg-status").text("断开");
        $(".tl_msg-status").css("background","red");
        btnRestart();
        $("#tl_btn_restart span").removeClass('rotate');
        $("#tl_btn_restart").removeClass("hide");
        $("#tl_btn_close").addClass("hide");
        $("#tl_btn_relink").addClass("hide");
    }

    //1：fcs
    if(innerHTML.indexOf("MessagePoint.FCS_CONNECT_TYPE=1")>=0 ){
        $(".fcs_msg-status").text("在线");
        $(".fcs_msg-status").css("background","green");
        $("#fcs_btn_close").removeClass("hide");
        $("#fcs_btn_restart").addClass("hide");
        $("#fcs_btn_relink").addClass("hide");
    }
    //tl
    if(innerHTML.indexOf("MessagePoint.UCS_CONNECT_TYPE=1")>=0 ){
        $(".tl_msg-status").text("在线");
        $(".tl_msg-status").css("background","green");
        $("#tl_btn_close").removeClass("hide");
        $("#tl_btn_restart").addClass("hide");
        $("#tl_btn_relink").addClass("hide");
    }



    //3：取消重连  3->0
    if(innerHTML.indexOf("MessagePoint.FCS_CONNECT_TYPE=0")>=0 ){
        $(".fcs_msg-status").text("正在连接....");
        $(".fcs_msg-status").css("background","red");
        $("#fcs_btn_relink").removeClass("hide");
        $("#fcs_btn_close").addClass("hide");
        $("#fcs_btn_restart").addClass("hide");
    }

    if(innerHTML.indexOf("MessagePoint.UCS_CONNECT_TYPE=0")>=0 ){
        $(".tl_msg-status").text("正在连接....");
        $(".tl_msg-status").css("background","red");
        $("#tl_btn_relink").removeClass("hide");
        $("#tl_btn_close").addClass("hide");
        $("#tl_btn_restart").addClass("hide");
    }

    //根据log日志的级别来显示不同的颜色
    var divType;
    if(innerHTML.indexOf("ERROR")>0){
        divType = "<div style='color: red;font-weight: bold'></div>";
    }else{
        divType = "<div style='color: green ;font-weight: bold'></div>"
    }
    $('#div-console').append(divType);
    $('#div-console div:last').text(innerHTML);
    //滚动条最底部
    var divHeight = document.getElementById('div-console');
    $('#div-console').scrollTop(divHeight.scrollHeight);
}

//关闭WebSocket连接
function closeWebSocket() {
    websocket.close();
}

//发送消息
function send() {
    var message = document.getElementById('text').value;
    websocket.send(message);
}
function connectStatus() {
     $.ajax({
         async : false,
         cache : false,
         type : 'POST',
         url : "/hotelpms/fcs/connectStatus",
         error : function(error) {
             alert('请求失败 '+error);
         },
         success : function(data) {
             var status;
             if(data === '1'){
                 $(".msg-status").text("在线");
                 $("#btn_restart").addClass("hide");
                 $("#btn_close").removeClass("hide");
             }else if(data === '0'){
                 $(".msg-status").text("断开");
                 $("#btn_restart").removeClass("hide");
                 $("#btn_close").addClass("hide");
             }
         }
     });
}

function changeStatus(type,portType) {
    var url = "/hotelpms/portConnect/connect";
    if("0"===type){
        url = "/hotelpms/portConnect/disConnect";
    }else if("1"===type){
        url = "/hotelpms/portConnect/connect";
    }else if("2"===type){
        url = "/hotelpms/portConnect/stopHeartBeat";
    }
    $.ajax({
        async : false,
        cache : false,
        type : 'POST',
        url : url,
        data: {portType:portType},
        error : function(error) {
            alert('请求失败 '+error);
        },
        success : function(data) {
        }
    });
}
function getConnectsStatus() {
    $("#connect_status").click(function () {
        var url = "/hotelpms/portConnect/getConnectStatus";
        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            url : url,
            error : function(error) {
                alert('请求失败 '+error);
            },
            success : function(data) {
                alert(data.status)
            }
        });
    });

}

//同步接口的PMS数据
function databaseSyncPMS() {
    $("#database_sync").click(function () {
        $('#synchModal').modal('show');
    });

    $("#synch-pms").click(function () {
        var url = "/hotelpms/hotelRequest/databaseSync";

        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            url : url,
            error : function(error) {
                alert('请求失败 '+error);
            },
            success : function(data) {
                $('#synchModal').modal('hide');
                $('#synch-warn').text(data.sendInfo);
                $('#warnModal').modal('show');
            }
        });
    });
}




//工单详情
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#queue-body').bootstrapTable({
            url: '/hotelpms/portConnect/queueMessage',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            /*    toolbar: '#button',   */             //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
        //    showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
      //      height: 600,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            /*  uniqueId: "ID",   */                  //每一行的唯一标识，一般为主键列
        //    showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [/*{
                checkbox: false
            },*/ {
                field: 'id',
                title: 'id'
            }, {
                field: 'cloudCommand',
                title: '云端请求信息',
            }, {
                field: 'content',
                title: '待处理的内容',
                formatter: toText
            }
            ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
};
function toText(value){
    return "<xmp>"+value+"</xmp>";
}


//获取端口连接的状态
function initPortMsg(){
    $.ajax({
        async : true,
        cache : false,
        type : 'POST',
        url : "/hotelpms/portConnect/getPortConfig",
        error : function(error) {
            alert('请求失败 '+error);
        },
        success : function(msg) {//接口使用类型
            if(msg.open_fcs==='true'){
               /* $("#fcs_open").attr("class","open1");
                $("#fcs_close").attr("class","open2");*/
                $("#fcs_conn_msg").css("display","block");
                $("#fcsIp").text(msg.fcsIp);
                $("#fcsPort").text(msg.fcsPort);
                commandTestUrl="/hotelpms/fcs/fcsRequestTest";
                $("#status_list").css("display","block");

            }
            if(msg.open_UCS==='true'){
                $("#tl_conn_msg").css("display","block");
                $("#tlIp").text(msg.tlIp);
                $("#tlPort").text(msg.tlPort);
            }else{
                $("#error_msg").css("display","block");
                $("#error_msg").html("<h4>请设置中间件连接的端口信息,然后重新启动服务！！！</h4>");
            }
        }
    });
}
