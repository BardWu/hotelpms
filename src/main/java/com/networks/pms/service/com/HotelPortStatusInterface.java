package com.networks.pms.service.com;

import com.networks.pms.common.util.MessagePoint;

public interface HotelPortStatusInterface {
    /**
     *
     * @param statusType 获取方式 0：中间件  1：cloud
     * @return
     */
     int getPortStatus(String statusType);

    /**
     *
     * @param status 设置接口的状态
     * @param hotelId  酒店id
     * @param portType 接口类型
     * @param setType 设置接口状态的类型 0： 中间件  1：cloud
     */
    void setPortStatus(int status,String hotelId,String portType,String setType);

    /**
     * 中间件发送修改接口状态的请求
     * @param status
     */
     void setPortStatusByMiddleWare(int status,String hotelPortType);

    /**
     * 云端发送修改接口状态的请求
     * @param status
     * @param portType
     * @param setType
     */
     void setPortStatusByCloud(int status,String portType,String setType,String hotelPortType);


    /**
     * 接口变化监听
     * @param status 云端识别的状态
     * @param hotelId
     * @param portType
     */
    void portChangeListener(int status,String hotelId,String portType);

    /**
     * 中间件直接修改fcs状态
     * @param status
     */
    void changeStatusByMiddleWare(int status);

    /**
     *
     * @param status 将要修改的状态（云端的状态）
     */
    void changeStatusByCloud(int status,int currentStatus);

    /**
     * 关闭接口
     */
     void breakConnect();

    /**
     * 连接接口
     */
    void connect();
}
