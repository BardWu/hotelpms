package com.networks.pms.bean.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 云端的请求
 */
public class CloudRequest {
    private int id;
    private String uuid;
    //云端的请求
    private String oriCloudRequest;
    //转换后云端的请求
    private String tranCloudRequest;
    //工单系统的响应
    private String oriWorkSystemResponse;
    //工单系统转换后的响应
    private String tranWorkSystemResponse;
    //请求的类型
    private String requestType;
    //状态 0：未发送 1：发送fcs成功 2：发送fcs失败 3：发送云端成功 4：发送云端失败
    private String status;
    //请求的时间
    private String requestTime;
    //请求发送给工单系统的时间
    private String sendWorkSystemTime;
    //响应发送给云端的时间
    private String responseTime;
    //error
    private String error;

    private String pid;

    private String apiKey;

    private String type;

    private String invalid;//For Fcs.default :0 。0：有效 1：无效(发送Fcs成功，但Fcs没有响应)

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    //存储云端请求
    public static CloudRequest storeCloudRequest(String uuid,String oriCloudRequest,String tranCloudRequest,String requestType){
        String requestTime = String.valueOf(new Date().getTime());
        return new CloudRequest(uuid, oriCloudRequest, tranCloudRequest,null,null, requestType, "0", requestTime,  null, null);
    }


    /**
     *    云端请求发送给fcs的结果 发送成功/失败
     * @param uuid
     * @param error
     * @param status
     * @return
     */
    public static Map<String,Object> SendFcs(String uuid,String error,String status){
        Map<String,Object> map = new HashMap<String,Object>();
        String sendFcsTime = String.valueOf(new Date().getTime());
        map.put("sendWorkSystemTime",sendFcsTime);
        map.put("status",status);
        map.put("uuid",uuid);
        map.put("error",error);
       return  map;
    }

    /**
     * 发送fcs的响应给云端 发送成功/失败
     * @param uuid
     * @param oriFcsResponse
     * @param tranFcsResponse
     * @return
     */
    public static Map<String,Object> fcsResponse(String uuid, String oriFcsResponse,String tranFcsResponse,String error,String status){
        Map<String,Object> map = new HashMap<String,Object>();
        String responseTime = String.valueOf(new Date().getTime());
        map.put("responseTime",responseTime);
        map.put("oriWorkSystemResponse",oriFcsResponse);
        map.put("uuid",uuid);
        map.put("tranWorkSystemResponse",tranFcsResponse);
        map.put("status",status);
        map.put("error",error);
        return  map;
    }

    //发送fcs的响应给云端 发送成功/失败
    public static Map<String,Object> SendCloud(String uuid,String error,String status ){
        Map<String,Object> map = new HashMap<String,Object>();
        String responseTime = String.valueOf(new Date().getTime());
        map.put("responseTime",responseTime);
        map.put("status",status);
        map.put("uuid",uuid);
        map.put("error",error);
        return  map;
    }

    public CloudRequest(String uuid, String oriCloudRequest, String tranCloudRequest, String oriWorkSystemResponse,String tranWorkSystemResponse, String requestType, String status, String requestTime, String sendWorkSystemTime, String responseTime) {
        this.uuid = uuid;
        this.oriCloudRequest = oriCloudRequest;
        this.tranCloudRequest = tranCloudRequest;
        this.oriWorkSystemResponse = oriWorkSystemResponse;
        this.tranWorkSystemResponse = tranWorkSystemResponse;
        this.requestType = requestType;
        this.status = status;
        this.requestTime = requestTime;
        this.sendWorkSystemTime = sendWorkSystemTime;
        this.responseTime = responseTime;
    }

    public CloudRequest() {
    }

    public String getTranWorkSystemResponse() {
        return tranWorkSystemResponse;
    }

    public void setTranWorkSystemResponse(String tranWorkSystemResponse) {
        this.tranWorkSystemResponse = tranWorkSystemResponse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOriCloudRequest() {
        return oriCloudRequest;
    }

    public void setOriCloudRequest(String oriCloudRequest) {
        this.oriCloudRequest = oriCloudRequest;
    }

    public String getTranCloudRequest() {
        return tranCloudRequest;
    }

    public void setTranCloudRequest(String tranCloudRequest) {
        this.tranCloudRequest = tranCloudRequest;
    }

    public String getOriWorkSystemResponse() {
        return oriWorkSystemResponse;
    }

    public void setOriWorkSystemResponse(String oriWorkSystemResponse) {
        this.oriWorkSystemResponse = oriWorkSystemResponse;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getSendWorkSystemTime() {
        return sendWorkSystemTime;
    }

    public void setSendWorkSystemTime(String sendWorkSystemTime) {
        this.sendWorkSystemTime = sendWorkSystemTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    @Override
    public String toString() {
        return "CloudRequest{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", oriCloudRequest='" + oriCloudRequest + '\'' +
                ", tranCloudRequest='" + tranCloudRequest + '\'' +
                ", oriWorkSystemResponse='" + oriWorkSystemResponse + '\'' +
                ", tranWorkSystemResponse='" + tranWorkSystemResponse + '\'' +
                ", requestType='" + requestType + '\'' +
                ", status='" + status + '\'' +
                ", requestTime='" + requestTime + '\'' +
                ", sendWorkSystemTime='" + sendWorkSystemTime + '\'' +
                ", responseTime='" + responseTime + '\'' +
                ", error='" + error + '\'' +
                ", pid='" + pid + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
