$(document).ready(function(){
	//初始化Button点击事件
    initTable();
    buttonInit();
});
function initTable(){
    //初始化Table
    var oTable = new TableInit();
    oTable.Init();
}

function buttonInit(){
    //查询按钮
    $(".queryButton").click(function(){
        $('#resend_Table').bootstrapTable('refresh');
    });
    //列表显示
    $('#resend_Table').on('click','tr td',function () {

        var tdHtml=$(this).html();
        $('#detailModal').modal('show');
        $('#detailModal textarea').html($(this).html());
    });
}
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#resend_Table').bootstrapTable({
            url: '/hotelpms/resend/resendMessage',         //请求后台的URL（*）
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
            height: 1000,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                field: 'type',
                title: '重发类型',
                formatter: toType
            }, {
                field: 'depositTime',
                title: '存入时间',
               formatter: toDate
            }, {
                field: 'resendResult',
                title: '状态',
                formatter:toStatus
            },{
                field: 'error',
                title: '错误原因'
            },{
                field: 'message',
                title: '重发信息',
                // formatter: toText
            } ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            status: $("#status").val(),
            requestType: $("#requestType").val(),
          //  departmentname: $("#txt_search_departmentname").val(),
          //  statu: $("#txt_search_statu").val()
        };
       // alert("1223");
        return temp;
    };
    return oTableInit;
};
function toText(value){
    return "<xmp>"+value+"</xmp>";
}
function toStatus(value){
    if(value==='0'){
        value='未发送';
    }else if(value==='1'){
        value='发送云端成功';
    }
    else if(value==='2'){
        value='发送云端失败';
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
function toType(value) {
    if(value==='0'){
        value='CheckIn';
    }else if(value==='2'){
        value='GuestInfo'
    }else if(value==='3'){
        value='RoomChange'
    }else if(value==='7'){
        value='CreateJob'
    }else if(value==='8'){
        value='JobEnquiry'
    }else if(value ==='9'){
        value='RoomStatus(OperaFMF)';
    }
    return value;
}
function getPage() {
    $("#command_Table").bootstrapTable('destroy');//先要将table销毁，否则会保留上次加载的内容
    initTable();//重新初使化表格。
}





