package com.networks.pms.bean.model;

import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.com.SysConf;

import java.util.Date;

public class PmsLogRecord {
    //日志流水id
    private int logId;
    //酒店name
    private String hotelName;
    //日志类型 0-信息流水 1-错误信息
    private int logType;
    //描述
    private String logDesc;
    //流水内容
    private String content;
    //状态 success-成功 error-错误
    private String status;
    //错误信息
    private String errMsg;
    //创建时间
    private String createTime;


    public PmsLogRecord() {
    }

    public PmsLogRecord(String hotelName ,int logType, String status) {
        this.hotelName =   hotelName;
        this.logType = logType;
        this.status = status;
        this.createTime = String.valueOf(new Date().getTime());
    }

    public static PmsLogRecord errorLog(Exception e,String logDesc,String hotelName){
        PmsLogRecord  pmsLogRecord = new PmsLogRecord(hotelName,1,"error");
        String errMsg =  Msg.getExceptionDetail(e);
        pmsLogRecord.setErrMsg(errMsg);
        pmsLogRecord.setLogDesc(logDesc);
        return pmsLogRecord;
    }

    public static PmsLogRecord errorLog(String hotelName,String errMsg,String logDesc){
        PmsLogRecord  pmsLogRecord = new PmsLogRecord(hotelName,1,"error");
        pmsLogRecord.setErrMsg(errMsg);
        pmsLogRecord.setLogDesc(logDesc);
        return pmsLogRecord;
    }
    public static PmsLogRecord errorLog(String hotelName,String errMsg,String logDesc,String content){
        PmsLogRecord  pmsLogRecord = new PmsLogRecord(hotelName,1,"error");
        pmsLogRecord.setErrMsg(errMsg);
        pmsLogRecord.setLogDesc(logDesc);
        pmsLogRecord.setContent(content);
        return pmsLogRecord;
    }
    public static PmsLogRecord successLog(String hotelName,String logDesc){
        PmsLogRecord  pmsLogRecord = new PmsLogRecord(hotelName,0,"success");
        pmsLogRecord.setLogDesc(logDesc);
        return pmsLogRecord;
    }


    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getLogType() {
        return logType;
    }

    public void setLogType(int logType) {
        this.logType = logType;
    }

    public String getLogDesc() {
        return logDesc;
    }

    public void setLogDesc(String logDesc) {
        this.logDesc = logDesc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        Date date = new Date();
        date.setTime(Long.parseLong(createTime));
        createTime = DateUtil.DateToString(date,"yyyy-MM-dd HH:mm:ss");
        this.createTime = createTime;
    }




    @Override
    public String toString() {
        return "PmsLogRecord{" +
                "logId=" + logId +
                ", hotelName=" + hotelName +
                ", logType=" + logType +
                ", logDesc='" + logDesc + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", errMsg='" + errMsg + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
