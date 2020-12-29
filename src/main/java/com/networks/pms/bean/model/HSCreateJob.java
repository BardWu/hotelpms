package com.networks.pms.bean.model;

import com.networks.pms.common.util.ErrorControl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: hotelpms
 * @description: hotSOS工单
 * @author: Bardwu
 * @create: 2019-01-15 16:10
 **/
public class HSCreateJob {
    private String roomId;
    private String remark;
    private String itemCode;

    private static Map<String,Integer>CreateJobErrCode;
    private static Map<String,String> jobEnquiryCode;
    static{
        CreateJobErrCode = new HashMap<String,Integer>();
        CreateJobErrCode.put("OBJECT_NOT_FOUND",ErrorControl.CREATE_FAILURE);//创建失败
        CreateJobErrCode.put("INVALID_REQUEST",ErrorControl.CREATE_FAILURE);//创建失败
        CreateJobErrCode.put("DUPLICATE_LIMIT_EXCEEDED",ErrorControl.DUPLICATES_JOB);//重复工单
        CreateJobErrCode.put("REQUEST_LIMIT_EXCEEDED", ErrorControl.SYSTEM_BUSY);//每分钟工单超过30个
        /**
         * 已创建 0-已提交
         * 已启动 3-进行中
         * 已关闭 7-已取消
         * 已取消 7-已取消
         * 已延迟 3-进行中
         * 已分配 3-进行中
         * 已完成 5-已完成
         */
        jobEnquiryCode = new HashMap<String,String>();
        jobEnquiryCode.put("CREATED","0");//已创建
        jobEnquiryCode.put("STARTED","3");//已启动
        jobEnquiryCode.put("STOPPED","7");//已取消
        jobEnquiryCode.put("VOIDED","7");//已取消
        jobEnquiryCode.put("DEFERRED","3");//已延迟
        jobEnquiryCode.put("DIRECTED","3");//已分配
        jobEnquiryCode.put("COMPLETED","5");//已完成
        jobEnquiryCode.put("CLOSED","5");//完成
    }




    public HSCreateJob() {
    }

    public HSCreateJob(String roomId, String remark, String itemCode) {
        this.roomId = roomId;
        this.remark = remark;
        this.itemCode = itemCode;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return "HSCreateJob{" +
                "roomCode='" + roomId + '\'' +
                ", remark='" + remark + '\'' +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }


    public static String  createJobRequest(HSCreateJob createJob) throws Exception{

        if(createJob == null || StringUtils.isEmpty(createJob.getItemCode()) || StringUtils.isEmpty(createJob.getRoomId())){
            throw new Exception("请求数据有误");
        }
        return  "<ServiceOrder xmlns=\"urn:serviceorder.api.m-tech.com\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                " \t<Issue xmlns:a=\"urn:issue.api.m-tech.com\">\n" +
                " \t\t<ID xmlns=\"urn:api.m-tech.com\">"+createJob.itemCode+"</ID>\n" +
                " \t</Issue>\n" +
                " \t<Location xmlns:a=\"urn:room.api.m-tech.com\">\n" +
                " \t\t<ID xmlns=\"urn:api.m-tech.com\">"+createJob.roomId+"</ID>\n" +
                " \t</Location>\n" +
                "  <NewRemark>"+createJob.remark+"</NewRemark>\n" +
                " </ServiceOrder>";
    }

    public static void main(String[] args) throws Exception{
        String response = "<Fault xmlns=\"http://schemas.microsoft.com/ws/2005/05/envelope/none\"><Code><Value>DUPLICATE_LIMIT_EXCEEDED</Value></Code><Reason><Text xml:lang=\"en-US\">Too many duplicates of this service order, max allowed = 10, found at least 10</Text></Reason><Detail><apiFault xmlns=\"urn:fault.api.m-tech.com\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\"><code>DUPLICATE_LIMIT_EXCEEDED</code><message>Too many duplicates of this service order, max allowed = 10, found at least 10</message></apiFault></Detail></Fault>";
       /* Document dom=DocumentHelper.parseText(response);
        Element root = dom.getRootElement();
        root = root.element("Code");
        String value =  root.element("Value").getText();
        System.out.println(value);*/
        System.out.println(parseCreateJobResponse(response));
    }

    /**
     * 转换hotSOS响应
     * @param response
     * @return
     *
     */
    public static Map<String,Object> parseCreateJobResponse(String response)throws Exception{
       if(StringUtils.isEmpty(response)){
         return null;
       }
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Document dom=DocumentHelper.parseText(response);
            Element root = dom.getRootElement();
            String value = null;
            if(response.startsWith("<Fault")){//响应错误
                root = root.element("Code");
                value = root.element("Value").getText();
                map.put("code","500");
                map.put("msg",value);
                map.put("reason",String.valueOf(getErrorCodeByReason(value)));
            }else if(response.startsWith("<ServiceOrder")){
                 value =  root.element("ID").getText();
                map.put("code","200");
                map.put("msg",value);
            }else{
                map.put("code",response);
                map.put("msg","响应码:"+response);
            }
        } catch (Exception e) {
            throw  new Exception("解析工单异常:"+e.getMessage());
        }
        return  map;
    }

    private static int getErrorCodeByReason(String reason){
       Object obj =  CreateJobErrCode.get(reason);
       if(StringUtils.isEmpty(obj)){
           return ErrorControl.UNKNOWN_ERROR;//服务器出错
       }else{
           return Integer.parseInt(obj.toString());
       }
    }

    private static String getJobEnquiryCodeByReason(String reason)throws Exception{
        Object obj =  jobEnquiryCode.get(reason);
        if(StringUtils.isEmpty(obj)){
           throw new Exception("未知工单的状态:"+reason);
        }else{
            return obj.toString();
        }
    }
    /**
     * 转换hotSOS jobEnquiry的响应
     * @param response
     * @return
     *
     */
    public static Map<String,Object> parseJobEnquiryResponse(String response)throws Exception{
        if(StringUtils.isEmpty(response)){
            throw new Exception("请求hotSOS失败");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            Document dom=DocumentHelper.parseText(response);
            Element root = dom.getRootElement();
            String value = null;
            if(response.startsWith("<Fault")){//响应错误
                root = root.element("Code");
                value = root.element("Value").toString();
                map.put("code","500");
                map.put("msg",value);
            }else if(response.startsWith("<ServiceOrder")){
                value =  root.element("Status").getText();
                map.put("code","200");
                map.put("msg",getJobEnquiryCodeByReason(value));
            }else{
                map.put("code",response);
                map.put("msg","响应码:"+response);
            }
        } catch (Exception e) {
            throw  new Exception("解析工单异常:"+e.getMessage());
        }
        return  map;
    }


}
