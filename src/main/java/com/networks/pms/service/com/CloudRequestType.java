package com.networks.pms.service.com;

/**
 * @program: hotelpms
 * @description: 云端请求信息类型
 * @author: Bardwu
 * @create: 2019-01-21 15:02
 **/
public enum CloudRequestType {
    NOT_SEND("0"), SEND_HOTEL_SUCCESS("1"),SEND_HOTEL_FAILURE("2") ,SEND_CLOUD_SUCCESS("3"),SEND_CLOUD_FAILURE("4");
    // 成员变量
    private String type;
    // 构造方法
    private CloudRequestType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
