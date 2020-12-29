package com.networks.pms.bean.model;

import com.networks.pms.common.util.KV;

import java.util.Date;

/**
 * FCS 发送给 通利 的pms信息
 */
public class PmsFcsTL {
    private int id;

    private String oriMessage;

    private String tranMessage;

    private String dataFlow ;//数据流向 0：fcs->通利  1：通利->Fcs

    private String sendStatus;// 0:未发送 1： 已发送 2：处理失败

    private String error;

    private String messageType;//信息类型

    private String acceptTime;//接收时间

    private String sendTime;//发送时间

    private int sendNumbers;//发送次数(3次发送不成功，就不会发送)

    /**
     * fcs发送给TL初始化的信息
     * @param oriMessage
     * @param kv
     * @return
     */
    public static PmsFcsTL getFcsToTLInit(String  oriMessage,KV kv,String tranMessage){
        return  new PmsFcsTL(oriMessage,KV.FCSToTL.getMessage(),kv.getMessage(), tranMessage);
    }

    /**
     * TL发送给FCS的初始化信息
     * @param oriMessage
     * @param kv
     * @return
     */
    public static PmsFcsTL getTLToFCSInit(String  oriMessage ,KV kv,String tranMessage){
        return  new PmsFcsTL(oriMessage,KV.TLToFCS.getMessage(),kv.getMessage(), tranMessage);
    }

    public PmsFcsTL( String oriMessage, String dataFlow,   String messageType,String tranMessage) {
        this.oriMessage = oriMessage;
        this.dataFlow = dataFlow;
        this.sendStatus = KV.NotSend.getMessage();
        this.messageType = messageType;
        this.acceptTime = String.valueOf(new Date().getTime());
        this.tranMessage = tranMessage;
    }

    public PmsFcsTL() {
    }

    public int getSendNumbers() {
        return sendNumbers;
    }

    public void setSendNumbers(int sendNumbers) {
        this.sendNumbers = sendNumbers;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriMessage() {
        return oriMessage;
    }

    public void setOriMessage(String oriMessage) {
        this.oriMessage = oriMessage;
    }

    public String getTranMessage() {
        return tranMessage;
    }

    public void setTranMessage(String tranMessage) {
        this.tranMessage = tranMessage;
    }

    public String getDataFlow() {
        return dataFlow;
    }

    public void setDataFlow(String dataFlow) {
        this.dataFlow = dataFlow;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public String toString() {
        return "PmsFcsTL{" +
                "id=" + id +
                ", oriMessage='" + oriMessage + '\'' +
                ", tranMessage='" + tranMessage + '\'' +
                ", dataFlow='" + dataFlow + '\'' +
                ", sendStatus='" + sendStatus + '\'' +
                ", error='" + error + '\'' +
                ", messageType='" + messageType + '\'' +
                ", acceptTime='" + acceptTime + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", sendNumbers='" + sendNumbers + '\'' +
                '}';
    }
}