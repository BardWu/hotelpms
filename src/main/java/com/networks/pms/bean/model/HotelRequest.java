package com.networks.pms.bean.model;

/**
 * 酒店请求的信息
 */
public class HotelRequest {
    private int id;
    //原hotel的请求
    private String oriHotelRequest;
    //转换后的hotel请求
    private String tranHotelRequest;
    //请求的类型
    private String msgType;
    //接收请求的时间
    private String acceptTime;
    //发送到云端的时间
    private String sendCloudTime;
    //请求的状态 0：发送成功 1：发送失败
    private String status;
    //error
    private String error;
    //接口类型 0:fcs 1:opera
    private String portType;
    //FCS请求特有的uuid
    private String uuid;
    //发送到云端的信息
    private String sendCloudRequest;

    public String getSendCloudRequest() {
        return sendCloudRequest;
    }

    public void setSendCloudRequest(String sendCloudRequest) {
        this.sendCloudRequest = sendCloudRequest;
    }

    public static HotelRequest acceptHotelRequest(String oriHotelRequest, String tranHotelRequest, String messageType, String acceptTime, String uuid, String portType){

        return new HotelRequest(oriHotelRequest,  tranHotelRequest, messageType,  acceptTime,portType,  uuid);
    }



    public HotelRequest(String oriHotelRequest, String tranHotelRequest, String msgType, String acceptTime, String portType, String uuid) {
        this.oriHotelRequest = oriHotelRequest;
        this.tranHotelRequest = tranHotelRequest;
        this.msgType = msgType;
        this.acceptTime = acceptTime;
        this.portType = portType;
        this.uuid = uuid;
    }

    public HotelRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriHotelRequest() {
        return oriHotelRequest;
    }

    public void setOriHotelRequest(String oriHotelRequest) {
        this.oriHotelRequest = oriHotelRequest;
    }

    public String getTranHotelRequest() {
        return tranHotelRequest;
    }

    public void setTranHotelRequest(String tranHotelRequest) {
        this.tranHotelRequest = tranHotelRequest;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getSendCloudTime() {
        return sendCloudTime;
    }

    public void setSendCloudTime(String sendCloudTime) {
        this.sendCloudTime = sendCloudTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
