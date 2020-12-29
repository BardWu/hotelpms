/**此js为公共js 放一些公共方法和参数**/
//重写ajax方法
/*jQuery(function($){
    // 备份jquery的ajax方法  
    var _ajax=$.ajax;
    // 重写ajax方法，先判断登录在执行success函数 
    $.ajax=function(opt){
    	var _success = opt && opt.success || function(a, b){};
        var _opt = $.extend(opt, {
        	success:function(data, textStatus){
        		// 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)

        		if(data != null && data != undefined && data!=''){
        			try{
                        alert("123"+data);
            			if(data.indexOf('loginTag') != -1) {
            				//top.location.href= webPath+"/api/manager/login?message=登录信息超时请重新登录!";
                            alert("登录信息失效");
                			return;
                		}
        			}catch  (e)   {
        			   /!* alert(e.name  +   " :  "   +  e.message);*!/
        			} 
        			
        		}
        		_success(data, textStatus);  
            }  
        });
        _ajax(_opt);
    };
});*/



jQuery.requestMethod = {

    getUrlToken:function(url){
        var token = null;
        var menuId = null;
        try{
            token = frame.token;
            menuId = frame.menuId;
        } catch(err){
            token = window.parent.frame.token;
            menuId = window.parent.frame.menuId;
        }

        var strUrl = url+"?token="+token+"&menuId="+menuId;
        return strUrl;
    },
    postAjax:function(url,param,callbackOk,callbackError){

         url = this.getUrlToken(url);

         $.ajax({
             async : false,
             type : "POST",
             data:param,
             dataType : "json",
             url :url,
             success:function(msg){

                 if(msg.status == 'success'){

                     if(callbackOk){
                         callbackOk(msg);
                     }
                 }else{

                     if(msg.status == 'csc_x0000'){

                         alert("未登录");

                         return;
                     }else{

                         if(callbackError){
                             callbackError();
                         }
                     }
                 }
             }
          });
    }, post:function(url,param,callbackOk,callbackError){

        url = this.getUrlToken(url);

        $.post(url,param,function(msg){

            if(msg.status == 'success'){

                if(callbackOk){
                    callbackOk(msg);
                }
            }else{

                if(msg.status == 'csc_x0000'){

                   // alert("未登录");
                    top.location.href= webPath+"/login/login.go?message=登录信息超时请重新登录!";
                    return;
                }else if(msg.status == 'csc_x0002'){

                    showBox(msg.message,"warn");//没有操作权限
                }else{

                    if(callbackError){
                        callbackError(msg);
                    }
                }
            }

        });

    }

}


/**
 * 通用删除提示框
 * @param options
 * @param callback
 */
$.fn.comModalBox = function(options,callback){

    var defaults = {
        modalId:'',
        title:'',
        modalBody:'',
        btnText:'',
        dataParam:{}
    };
    var settings = $.extend(defaults, options);

    var init = function(){

        $('#'+settings.modalId+' h4[name="title"]').html(settings.title);
        $('#'+settings.modalId+' div[name="modal_body"]').html(settings.modalBody);
        $('#'+settings.modalId+' #btn_box_submit').html(settings.btnText);

        $('#'+settings.modalId+' #btn_box_submit').click(function(){

            if(callback) {
                callback(settings.dataParam);
            }
        });

        $('#'+settings.modalId).modal('show');
    }

    init();
}

$.fn.comTableBasic = function(options,paramInit,callback,getSearch,showUpdateModal,deleteData,customFunction){

    var defaults = {
        tableAreaId:'',
        url: null,
        title:[],
        dataList:{},
        dataParam:{}
    };
    var settings = $.extend(defaults, options);

    var init = function(tableArea){

        //初始化表格标题信息
        var beanTable = $(tableArea).find('table');
        var titleHtml = '<thead><tr>'

        for(var i=0;i<settings.title.length;i++){

            titleHtml = titleHtml+'<th>'+settings.title[i]+'</th>';
        }
        titleHtml = titleHtml+'</tr></thead><tbody class="custom-odd-bg"></tbody>';
        $(beanTable).html(titleHtml);
        //开始加载数据
        getPageData(null);
        var pageLi = $('#'+settings.tableAreaId+' li[name="page_li"]');
        pageLi.unbind();
        pageLi.click(function(){
            if($(this).hasClass("btn-disable")){

                return;
            }
            getPageData(this);
        });

        var searchBtn = $('#'+settings.tableAreaId+' button[name="btn_search"]');

        if(searchBtn != undefined){

            searchBtn.unbind();
            searchBtn.click(function(){

                var nowSearchBtn = $(this);
                if(searchBtn.hasClass("disabled")){

                    return;
                }

                searchBtn.addClass("disabled")
                var loadingHtml = '<div class="loading">';
                loadingHtml = loadingHtml+ '<span>加载中</span>';
                loadingHtml = loadingHtml+ '</div>';
                searchBtn.html(loadingHtml);

                getPageData(null);

            });

        }

        if(paramInit){

            paramInit();

        }

    }

    var getPageData = function(liObj){

        var nowPage = pageHandUtil(liObj);
        var searchParam = {};

        if(getSearch) {
            searchParam = getSearch();
        }

        var pageSize = $('#'+settings.tableAreaId+' select[name="display_num"]').val();
        var paramJSON = {};
        paramJSON["curPager"] = nowPage;
        paramJSON["pageSize"] = pageSize;

        $.each(searchParam,function(k,v){
            paramJSON[k] = v;
        });

        var paramsData = {
            "paramJSON" : JSON.stringify(paramJSON)
        };

        var beanTbody = $('#'+settings.tableAreaId+' table tbody');
        beanTbody.html("");

        $.requestMethod.post(settings.url, paramsData, function(msg) {

            var pageData = msg.data;
            var searchBtn = $('#'+settings.tableAreaId+' button[name="btn_search"]');
            searchBtn.removeClass("disabled");
            searchBtn.html("查询");

            var pageArea = $('#'+settings.tableAreaId+' div[name="page-area"]');
            if(pageData == undefined || pageData == null || pageData.list == null || pageData.list.length <=0){
                pageArea.addClass("hide");
                return;
            }else{

                $('#'+settings.tableAreaId+' #page_string').html(pageData.curPager + '/' + pageData.totalPage);
                $('#'+settings.tableAreaId+' #count_sum').html(pageData.countSum);
                $('#'+settings.tableAreaId+' #total_page').val(pageData.totalPage);
                $('#'+settings.tableAreaId+' #now_page').val(pageData.curPager);
                pageArea.removeClass("hide");
            }
            settings.dataList = pageData.list;


            var arrayData = new Array();
            if(callback) {
                arrayData = callback(settings.dataList);
            }
            //填充数据
            addTrData(arrayData);
            //设置上一页下一页 按钮样式
            var arrowLeft  = $("#"+settings.tableAreaId+' li[page_tag="arrow_left"]');
            var arrowRight = $("#"+settings.tableAreaId+' li[page_tag="arrow_right"]');

            if(liObj == null && pageData.totalPage <= 1){

                arrowRight.removeClass("btn-color");
                arrowRight.addClass("btn-disable");

            }else{

                if(pageData.curPager == pageData.totalPage){
                    arrowRight.removeClass("btn-color");
                    arrowRight.addClass("btn-disable");
                }else{

                    arrowRight.removeClass("btn-disable");
                    arrowRight.addClass("btn-color");

                }

            }

            //设置点击修改按钮事件
            $("#"+settings.tableAreaId+' a[name="btn_update_show"]').click(function(){
                var trBean = $(this).parent().parent();
                if(showUpdateModal){
                    showUpdateModal(trBean);
                }

            });
            //自定义函数
            if(customFunction){
                customFunction();
            }
            //设置点击删除按钮事件
            $("#"+settings.tableAreaId+' a[name="btn_delete"]').click(function(){
                var trBean = $(this).parent().parent();
                if(deleteData){
                    deleteData(trBean);
                }

            });


        },function(){


        });

    }

    var addTrData = function(arrayData){

        for(var i =0;i<arrayData.length;i++){

            var rowParam = arrayData[i];
            var paramHtml = '';

            $.each(rowParam.dataParam,function(k,v){
                paramHtml = paramHtml +'  data-'+k+'="'+v+'"';
            });

            var trHtml = '<tr '+paramHtml+'>'
            var column = rowParam.column;
            for(var j=0;j<column.length;j++){

                if(column[j] == undefined){
                    column[j] = '';
                }
                trHtml = trHtml+'<td>'+column[j]+'</td>';
            }
            trHtml = trHtml+'</tr>'

            $('#'+settings.tableAreaId+' table tbody').append(trHtml);
        }
    }

    var pageHandUtil = function(liObj){

        var arrowLeft  = $("#"+settings.tableAreaId+' li[page_tag="arrow_left"]');
        var arrowRight = $("#"+settings.tableAreaId+' li[page_tag="arrow_right"]');

        var totalPage = $("#"+settings.tableAreaId+" #total_page").val();
        var onnext = parseInt($("#"+settings.tableAreaId+" #now_page").val());

        var onbtn = $(liObj).attr("data-type");

        if(onbtn == undefined){
            onbtn = 'firstPage';
        }

        if(onbtn == "next"){

            onnext = onnext+1;
            if(onnext > totalPage){
                onnext = totalPage;
            }

        }else if(onbtn == "previous"){

            onnext = onnext-1;
            if(onnext < 1){
                onnext = 1;
            }

        }else if(onbtn == "lastPage"){
            onnext = totalPage;
        }else if(onbtn == "firstPage"){
            onnext = 1;
        }

        if(onnext == 1){

            arrowLeft.removeClass("btn-color");
            arrowLeft.addClass("btn-disable");

        }else{

            arrowLeft.removeClass("btn-disable");
            arrowLeft.addClass("btn-color");

        }

        if(onnext == totalPage){

            arrowRight.removeClass("btn-color");
            arrowRight.addClass("btn-disable");
        }else{

            arrowRight.removeClass("btn-disable");
            arrowRight.addClass("btn-color");
        }

        return onnext;
    }

    init(this);
}


$.fn.comupload = function(options){

    var defaults = {
        controlId:'', //控件区域Id必填
        fileId:'',   // 内部使用不需要填
        resultId:'', // 内部使用不需要填
        deleteUrl:'', //删除文件请求url
        deleteParam:'',//删除时的参数
        imgUrl:'',     //初始化时的图片地址
        progressBarId:'',
        fileType:''  //类型 image-图片 video -视频
    };
    var settings = $.extend(defaults, options);

    var init = function(){

        settings.fileId = settings.controlId+"_file";
        settings.resultId = settings.controlId+"_result";
        settings.progressBarId = settings.controlId+"_progress";

        var imageHtml = '';
        var imageStatus = 'upload';
        var aText = '上传';

        if(settings.imgUrl != null && settings.imgUrl != ''){

            imageHtml = imageHtml+'  <img data-url="'+settings.imgUrl+'" fieldType = "'+settings.fileType+'" style="max-height:150px;" src="'+image_url_prefix+settings.imgUrl+'" alt="" />';
            imageStatus = 'delete';
            aText = '删除';

        }

        var initHtml = '<div>';

        initHtml = initHtml+' <div style="float:left; width: 40%;">';
        initHtml = initHtml+'     <a status="'+imageStatus+'" href="#">'+aText+'</a>';
        initHtml = initHtml+'     <div class="hide">';
        initHtml = initHtml+'       <input type="file" name="file" id="'+settings.fileId+'" value=""  placeholder=""/>';
        initHtml = initHtml+'     </div>';
        initHtml = initHtml+'</div>';

        initHtml = initHtml+'    <div id="'+settings.resultId+'" style="float:left;width: 50%;">';

        if(settings.imgUrl != null && settings.imgUrl != ''){

            initHtml = initHtml+imageHtml;
        }

        initHtml = initHtml+'    </div>';
        initHtml = initHtml+'    <div class="c-float"></div>';
        initHtml = initHtml+'</div>';

        initHtml = initHtml+'<div id="'+settings.progressBarId+'"  class="progress hide" style="margin-top:10px;" >';
        initHtml = initHtml+'  <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 0%;">';
        initHtml = initHtml+'     0%';
        initHtml = initHtml+'  </div>';
        initHtml = initHtml+'</div>';

        $("#"+settings.controlId).html(initHtml);

        if(settings.imgUrl != null && settings.imgUrl != ''){

            var uploadABean = $("#"+settings.controlId +" a");
            uploadABean.attr("href","#");
            uploadABean.click(function(){

                var deleteParam = {};
                deleteParam['url'] = settings.deleteUrl;
                deleteParam['data'] = settings.deleteParam;

                deleteFile(deleteParam);

            });

        }else{

            selectWindow();

        }

        $("#"+settings.fileId).change(function(){

            uploadFile();

        });

    }

    var selectWindow = function(){
        $("#"+settings.controlId +" a").attr("href",'javascript:$("#'+settings.controlId+'_file").click()');
    }

    var uploadFile = function(){

        var formData = new FormData();//构造空对象，下面用append 方法赋值。
        var file = document.getElementById(settings.fileId).files;

        formData.append("file", file[0]);

        var url = webPath+"/csc_com/upload_file.go";
        url = $.requestMethod.getUrlToken(url);
        $.ajax({
            xhr: function(){
                var xhr = new window.XMLHttpRequest();

                xhr.upload.addEventListener("progress", function(evt){
                    if (evt.lengthComputable) {
                        var percentComplete = Math.round(evt.loaded*100/evt.total);

                        $("#"+settings.progressBarId).removeClass("hide");
                        var progressDiv = $("#"+settings.progressBarId +" div");

                        progressDiv.text("上传进度"+percentComplete+"%");
                        progressDiv.css("width",percentComplete+"%");

                    }
                }, false);
                return xhr;
            },
            type: 'POST',
            url: url,
            data: formData,
            processData : false,
            contentType : false,
            success: function(msgData){

                if(msgData.status == 'success'){

                    var filePath = msgData.data.path;

                    var beanImg = filePath;
                    var newTemp = beanImg.substring(beanImg.indexOf('temp')+5,beanImg.length);

                    var htmlStr = '';
                    if(settings.fileType == "video"){

                        htmlStr = '<video data-url="'+newTemp+'" src="'+temp_url_prefix+newTemp+'" controls="controls" style="width:258px;height:140px;"></video>';

                    }else{

                        htmlStr = '<img data-url="'+newTemp+'" fieldType = "'+settings.fileType+'" width="100%" style="max-height:150px;" imgType="new" src="'+temp_url_prefix+newTemp+'" alt="" />';
                    }

                    $("#"+settings.resultId).html(htmlStr);

                    var uploadABean = $("#"+settings.controlId +" a");
                    uploadABean.html("删除");
                    uploadABean.attr("status","delete");
                    uploadABean.attr("href","#");
                    uploadABean.click(function(){

                        deleteTempFile(newTemp);


                    });
                    setTimeout(function(){

                        var progressDiv = $("#"+settings.progressBarId +" div");
                        progressDiv.text("上传进度0%");
                        progressDiv.css("width","0%");
                        $("#"+settings.progressBarId).addClass("hide");

                    },3000);
                }

                $("#"+settings.fileId).val("");
            }
        });
    }

    var deleteTempFile = function(tempFilePath){

        var url =webPath+"/csc_com/delete_temp_file.go";
        var data = {tempFilePath:tempFilePath};

        $.requestMethod.post(url, data,function(msg) {

            showBox("删除成功","ok");
            init();

        },function (msg) {

            showBox(msg.message,"no");
        });

    }

    var deleteFile = function(deleteParam){

        var url = deleteParam['url'];
        var data = deleteParam['data'];

        $.requestMethod.post(url, data,function(msg) {

            showBox("删除成功","ok");
            settings.imgUrl = null;
            init();
        },function (msg) {

            showBox(msg.message,"no");
        });

    }

    init();
}

/**
 *
 * @param msg 提示消息
 * @param ioc 提示图片
 */
function showBox(msg,ioc){

    $.msgbox.show({
        message: msg,
        icon: ioc,
        timeOut: 3000

    });

}

jQuery.comMethod = {

    /**
     * long时间转换成本地日期
     * @param time
     * @returns
     */
    dateConvertTime:function (time) {

        if (time <= 0 || time == undefined || time =='') {
            return '';
        } else {
            var date = new Date();
            date.setTime(time);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            month = month < 10 ? '0' + month : month;
            var day = date.getDate();
            var hours = date.getHours();       //获取当前小时数(0-23)
            hours = hours < 10 ? '0' + hours : hours;
            var minutes = date.getMinutes();     //获取当前分钟数(0-59)
            minutes = minutes < 10 ? '0' + minutes : minutes;
            var seconds = date.getSeconds();     //获取当前秒数(0-59)
            seconds = seconds < 10 ? '0' + seconds : seconds;
            day = day < 10 ? '0' + day : day;
            date = year + '-' +  month + '-' + day +' '+hours+':'+minutes+':'+seconds;
            return date;
           }
    },dateConvert:function (time) {

        if (time <= 0 || time == undefined || time =='') {

            return '';
        } else {

            var date = new Date();
            date.setTime(time);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            month = month < 10 ? '0' + month : month;
            var day = date.getDate();
            day = day < 10 ? '0' + day : day;
            date = year + '-' +  month + '-' + day;

            return date;
        }

    },subStr:function(str,len){

        var newStr = str;
        if(str != undefined && str != null && str != ''){

            if(str.length > len){

                newStr = str.substring(0,len)+' ...';
            }
        }

        return newStr;

    },getCaseStatus:function(status){

        var statusStr = '';
        if(status == 0){
            statusStr = '已提交';
        }else if(status == 1){
            statusStr = '已确认';
        }else if(status == 2){
            statusStr = '处理中';
        }else if(status == 3){
            statusStr = '已完成';
        }else if(status == 4){
            statusStr = '已超时';
        }else if(status == 5){
            statusStr = '超时完成';
        }
        return statusStr;
    }


}


jQuery.comTitle = {

    init:function(controlId){

        var that = this;
        var titleList = $('#'+controlId+' ul li[name="opt_title"]');
        $(titleList[0]).attr("status",1);

        titleList.click(function(){

            var nowli = $(this);
            var dataControlid = nowli.attr("data-controlid");
            that.change(controlId,dataControlid);

        });

    }, switch:function(controlId,dataControlid){

        this.change(controlId,dataControlid);

    },change:function(controlId,dataControlid){

        var oldli = $('#'+controlId+' ul li[name="opt_title"][status="1"]');
        oldli.attr("status",0);
        oldli.addClass("click-text");
        oldli.removeClass("select-text");

        var nowli = $('#'+controlId+' ul li[data-controlid="'+dataControlid+'"]');
        nowli.attr("status",1);
        nowli.removeClass("click-text");
        nowli.removeClass("hide");
        nowli.addClass("select-text");

        $('#'+oldli.attr("data-controlid")).addClass("hide");
        $('#'+nowli.attr("data-controlid")).removeClass("hide");

    }

}


    


