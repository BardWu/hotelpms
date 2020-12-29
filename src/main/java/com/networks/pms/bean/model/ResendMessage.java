package com.networks.pms.bean.model;

import com.networks.pms.common.util.DateUtil;

import java.util.Date;

/**
 * 存储发送失败的记录
 */
public class ResendMessage {
    private  int id;
    //发送的数据
    private String message;
    //存储的时间
    private String depositTime;
    //是否从新发送
    private String resendResult;
    //重发类型
    private String type;
    //错误原因
    private String error;
    //url 的apik
    private String apiKey;
    //重发的次数
    private int resendTotalTime;

    public ResendMessage(String apiKey,String type,String message) {
        this.apiKey = apiKey;
        this.type = type;
        this.message = message;
     //   this.depositTime = DateUtil.getPresentTime("yyyy-MM-dd HH:mm:");
        this.depositTime = String.valueOf(new Date().getTime());
    }

    public ResendMessage() {

    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(String depositTime) {
        this.depositTime = depositTime;
    }

    public String getResendResult() {
        return resendResult;
    }

    public void setResendResult(String resendResult) {
        this.resendResult = resendResult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getResendTotalTime() {
        return resendTotalTime;
    }

    public void setResendTotalTime(int resendTotalTime) {
        this.resendTotalTime = resendTotalTime;
    }

    @Override
    public String toString() {
        return "ResendMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", depositTime='" + depositTime + '\'' +
                ", resendResult='" + resendResult + '\'' +
                ", type='" + type + '\'' +
                ", error='" + error + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", resendTotalTime=" + resendTotalTime +
                '}';
    }
}
