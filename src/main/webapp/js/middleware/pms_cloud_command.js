$(document).ready(function(){
	//初始化Button点击事件
	buttonClick();

    initTable();
    $('#command_Table').on('click','tr td',function () {

        var tdHtml=$(this).html();
        $('#detailModal').modal('show');
        $('#detailModal textarea').html($(this).html());
    });
});
function initTable(){
    //初始化Table
    var oTable = new TableInit();
    oTable.Init();
}
function buttonClick(){
    //请求按钮
	$("#btn1").click(function(){
		$(".modal-body textarea").val(createJob)
  		$('#myModal').modal('show')
	});
    //查询按钮
	$(".queryButton").click(function(){
       $('#command_Table').bootstrapTable('refresh');
	});
    /* //请求按钮
     $("#btn3").click(function(){
         $(".modal-body textarea").val(synch)
           $('#myModal').modal('show')
     });*/
	//提交请求按钮
    $('#myModal button:last').click(function(){
        if(true){
            var command = $(".modal-body textarea").val();
            $('#myModal').modal('hide');
            $('#box').modal('show');

            submitCommand(command);
        }

    });
    //置空模态框中的内容
	$('#myModal button').click(function(){
		$(".modal-body textarea").val("")
	});
    dataTime();//时间选择
	
}
/*function paging(){
	$('.pagination').on('click','li',function(){
		activePaging();
		var pageNum = $(this).text().trim();
		$(this).addClass('active');
		
	})
}
function activePaging(){
	$('.pagination .active').removeClass('active');
}*/

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#command_Table').bootstrapTable({
            url: '/hotelpms/hs_bin/commandResponse',         //请求后台的URL（*）
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
                field: 'uuid',
                title: 'uuid'
            }, {
                field: 'status',
                title: '状态',
                formatter: toStatus
            },{
                field: 'error',
                title: '错误内容'
            },{
                field: 'oriCloudRequest',
                title: '云端请求',
               // formatter: toText
            }, {
                field: 'tranCloudRequest',
                title: '发送的工单信息',
                formatter: toText
            }, {
                field: 'oriWorkSystemResponse',
                title: '工单系统响应',
                formatter: toText
            },{
                field: 'tranWorkSystemResponse',
                title: '发送给云端的响应'
            },{
                field: 'requestType',
                title: '请求的类型',
                formatter: toRequestType
            },{
                field: 'requestTime',
                title: '云端请求时间',
                formatter:toDate
            },{
                field: 'sendWorkSystemTime',
                title: '发送给工单系统的时间',
                formatter:toDate
            },{
                field: 'responseTime',
                title: '响应云端的时间',
                formatter:toDate
            }]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit: params.limit,  //页面大小
            offset: params.offset, //页码
            uuid: $("#uuid").val(),
            status: $("#status").val(),
            cloudBegTime:$("#start").val(),
            cloudEndTime:$("#end").val(),
            requestType: $("#requestType").val(),
        };
        return temp;
    };
    return oTableInit;
};
function toText(value){
    return "<xmp>"+value+"</xmp>";
}
function toRequestType(value) {
    if(value === '7'){
        value = 'createJob'
    }
    return value
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

function toStatus(value){
    if(value==='0'){
        value='未发送';
    }else if(value==='1'){
        value='发送fcs成功';
    }
    else if(value==='2'){
        value='发送fcs失败';
    }
    else if(value==='3'){
        value='发送云端成功';
    }
    else if(value==='4'){
        value='发送云端失败';
    }
    return value;
}

function submitCommand(command){
	  $.ajax({
         async : true,
         cache : false,
         type : 'POST',
         url : "/hotelpms/hs_bin/commandRequestTest",
         data: {command:command},
         error : function(error) {
             $('#box').modal('hide');
             alert('内部错误');
         },
         success : function(msg) {
            // alert(msg.length);
           //  alert(data[0].status);
         //    alert(data[0].context);
             $('#box').modal('hide');
             if(msg==='true'){
                 alert("发送成功");
             }else if(msg==='false'){
                 alert("发送失败")
             }
             getPage();
         }
     });
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


var createJob="<CreateJob>\n" +
    "  <JobAction>Immediate</JobAction>\n" +
    "  <UserName>fcs</UserName>\n" +
    "  <Password>pwd</Password>\n" +
    "  <PId>1</PId>\n" +
    "  <Rm>1001</Rm>\n" +
    "  <ItemCode>10100</ItemCode>\n" +
    "  <Rem>This is urgent</Rem>\n" +
    "  <CreatedByUserName>Jason Chan</CreatedByUserName>\n" +
    "  <ReportedbyName>Mr. Lim</ReportedbyName>\n" +
    "  <RunnerUserName>81088</RunnerUserName>\n" +
    "  <SysDaTi>2012/8/408:11:07</SysDaTi>\n" +
    "  <GUID>E1A35954-41CC-40f7-BDF1-A7B2CE1EE5B6</GUID>\n" +
    "</CreateJob>";



/*var createJob ="<CreateJob>\n" +
    "  <Ex></Ex>\n" +
    "  <Rm></Rm>\n" +
    "  <PId></PId>\n" +
    "  <JobAction></JobAction>\n" +
    "  <UserId></UserId>\n" +
    "  <PassWord></PassWord>\n" +
    "  <LangCode></LangCode>\n" +
    "  <ServiceType></ServiceType>\n" +
    "  <ItemCode></ItemCode>\n" +
    "  <LocationCode></LocationCode>\n" +
    "  <ScheduledDaTi></ScheduledDaTi>\n" +
    "  <StartDaTi></StartDaTi>\n" +
    "  <EndDaTi></EndDaTi>\n" +
    "  <Rem></Rem>\n" +
    "  <Qty></Qty>\n" +
    "  <RunnerUserName></RunnerUserName>\n" +
    "  <RunnerId></RunnerId>\n" +
    "  <CreatedByUserName></CreatedByUserName>\n" +
    "  <ReportedByName></ReportedByName>\n" +
    "  <SysDaTi></SysDaTi>\n" +
    "  <GUID></GUID>\n" +
    "</CreateJob>";*/


var roomInfo ="<GuestFolioDetail>\n" +
    "  <Rm>1002</Rm>\n" +
    "  <Ex>1002</Ex>\n" +
    "  <PId>1</PId>\n" +
    "  <Flag>RQ</Flag>\n" +
    "  <GFolNo>123456</GFolNo>\n" +
    "  <SysDaTi>2012/8/4 08:11:07</SysDaTi>\n" +
    "  <GUID>E1A35954-41CC-40f7-BDF1-A7B2CE1EE5B6</GUID>\n" +
    "</GuestFolioDetail>";

var synch="<DBSwap>\n" +
    "  <PId>1</PId>\n" +
    "  <Flag>RQ</Flag>\n" +
    "  <SysDaTi>2012/12/15 07:00:17</SysDaTi>\n" +
    "  <GUID>E1A35954-41CC-40f7-BDF1-A7B2CE1EE5B6</GUID>\n" +
    "</DBSwap>";




