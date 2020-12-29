$(document).ready(function(){
	//初始化Button点击事件
    initTable();
    $('#fcsRequest_Table').on('click','tr td',function () {

        var tdHtml=$(this).html();
        $('#detailModal').modal('show');
        $('#detailModal textarea').html($(this).html());
    });
    buttonInit();
});

function buttonInit(){
    //查询按钮
    $(".queryButton").click(function(){
        $('#fcsRequest_Table').bootstrapTable('refresh');
    });
    dataTime();
}

function initTable(){
    //初始化Table
    var oTable = new TableInit();
    oTable.Init();
}
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#fcsRequest_Table').bootstrapTable({
            url: '/hotelpms/hotelRequest/requestMessage',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
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
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 1000,                       //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
          /*  uniqueId: "ID",   */                  //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [/*{
                checkbox: true
            }, */{
                field: 'id',
                title: 'id'
            }, {
                field: 'portType',
                title: '接口类型',
                formatter:toPortType
            }, {
                field: 'msgType',
                title: '信息类型',
                formatter:toMsgType
            },{
                field:'status',
                title:'状态',
                formatter:toStatus
            }, {
                field: 'error',
                title: '错误'
            }, {
                field: 'acceptTime',
                title: '接收时间',
                formatter:toDate
            },{
                field: 'sendCloudTime',
                title: '发送到云端的时间',
                formatter:toDate
            },{
                field: 'oriHotelRequest',
                title: '酒店发送的请求',
               formatter: toText
            }, {
                field: 'tranHotelRequest',
                title: '转换后的信息',
            }, {
                field: 'sendCloudRequest',
                title: '发送到云端的信息',
            }, {
                field: 'uuid',
                title: 'uuid'
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            uuid: $("#uuid").val(),
            status:$("#status").val(),
            msgType:$("#msg_Type").val(),
            cloudBegTime:$("#start").val(),
            cloudEndTime:$("#end").val(),
            portType:$("#port_type").val(),
          //  departmentname: $("#txt_search_departmentname").val(),
          //  statu: $("#txt_search_statu").val()
        };
      //  alert($("#msg_Type").val());
        return temp;
    };
    return oTableInit;
};
function toText(value){
    return "<xmp>"+value+"</xmp>";
}
function toPortType(value) {
    if(value==='0'){
        return "FCS";
    }else if(value==='1'){
        return "OperaFPB";
    }else if(value==='7'){
        return "OperaFMF";
    }else{
        return value;
    }
}
function toMsgType(value){
    if(value ==='0'){
        value='CheckIn';
    }else if(value ==='1'){
        value='CheckOut';
    }else if(value ==='2'){
        value='GuestInfo';
    }else if(value ==='3'){
        value='RoomChange';
    }else if(value ==='9'){
        value='RoomStatus';
    }
    return value;
}
function toDate(value){
    if(value===undefined || value===''){
        return "";
    }
    var dt = new Date(parseInt(value));
    var year = dt.getFullYear();
    var month = dt.getMonth() + 1;
    var date = dt.getDate();
    var hour = dt.getHours();
    var minute = dt.getMinutes();
    var second = dt.getSeconds();
    /* if(type===undefined){
         return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
     }*/
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
//    return year + "-" + month + "-" + date;

}
function toStatus(value) {
    if(value==='0'){
        return "未发送"
    }else if(value==='1'){
        return "发送云端成功"
    }else if(value==='2'){
        return "发送云端失败"
    }
}

function getPage() {
    $("#command_Table").bootstrapTable('destroy');//先要将table销毁，否则会保留上次加载的内容
    initTable();//重新初使化表格。
}

function dataTime() {
    $("#start").datetimepicker({
        format:'yyyy-mm-dd',  //格式  如果只有yyyy-mm-dd那就是0000-00-00  yyyy-mm-dd hh:ii:ss
        autoclose:true,//选择后是否自动关闭
        minView:2,//最精准的时间选择为日期  0-分 1-时 2-日 3-月
        language:  'zh-CN', //中文
        weekStart: 1, //一周从星期几开始
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        // daysOfWeekDisabled:"1,2,3", //一周的周几不能选 格式为"1,2,3"  数组格式也行
        todayBtn : true,  //在底部是否显示今天
        todayHighlight :false, //今天是否高亮显示
        keyboardNavigation:true, //方向图标改变日期  必须要有img文件夹 里面存放图标
        showMeridian:false,  //是否出现 上下午
        initialDate:new Date()
        //startDate: "2017-01-01" //日期开始时间 也可以是new Date()只能选择以后的时间
    }).on("changeDate",function(){
        var start = $("#start").val();
        $("#end").datetimepicker("setStartDate",start);/*开始时间的值是结束时间值的开始*/
    });
    $("#end").datetimepicker({
        format:'yyyy-mm-dd',  //格式  如果只有yyyy-mm-dd那就是0000-00-00
        autoclose:true,//选择后是否自动关闭
        minView:2,//最精准的时间选择为日期  0-分 1-时 2-日 3-月
        language:  'zh-CN', //中文
        weekStart: 1, //一周从星期几开始
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        //daysOfWeekDisabled:"1,2,3", //一周的周几不能选
        todayBtn : true,  //在底部是否显示今天
        todayHighlight :false, //今天是否高亮显示
        keyboardNavigation:true, //方向图标改变日期  必须要有img文件夹 里面存放图标
        showMeridian:false  //是否出现 上下午
        // startDate: "2017-01-01"  //开始时间  ENdDate 结束时间
    }).on("changeDate",function(){
        var end = $("#end").val();
        $("#start").datetimepicker("setEndDate",end);/*结束时间的值是开始时间值的结束*/
    });
}



