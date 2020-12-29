package com.networks.pms.common.util;

/**
 * @program: hotelpms
 * @description: 常熟酒店房态和minibar Response
 * @author: Bardwu
 * @create: 2019-06-14 14:33
 **/
public class MinibarRoomStatusResponse {

    private String status;

    private String msg;

    private String data;


    public MinibarRoomStatusResponse(String status, String msg, String data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public MinibarRoomStatusResponse() {
    }

    public static MinibarRoomStatusResponse successResponse(){

        return new MinibarRoomStatusResponse("0","ok","ok");

    }

    public static MinibarRoomStatusResponse errorResponse(String errorMsg){
        return new MinibarRoomStatusResponse("1",errorMsg,"ok");
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MinibarRoomStatusResponse{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
