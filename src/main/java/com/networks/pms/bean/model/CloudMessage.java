package com.networks.pms.bean.model;

import net.sf.json.JSONObject;

//处理云端的信息
public class CloudMessage {
    private String id;
    private String uuid;
    private String requestMessage;
    private String responseMessage;
    private String status;//0:执行中 1:执行成功   2:执行失败
    private String errorReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }

    public CloudMessage(String uuid, String requestMessage, String status) {
        this.uuid = uuid;
        this.requestMessage = requestMessage;
        this.status = status;
    }

    public CloudMessage() {
    }


    @Override
    public String toString() {
        return "CloudMessage{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", requestMessage='" + requestMessage + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", status='" + status + '\'' +
                ", errorReason='" + errorReason + '\'' +
                '}';
    }
}
