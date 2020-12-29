package com.networks.pms.service.com;

public interface HotelPortStatusInterfaceMQ {
    /**
     * 获取接口的状态
     * @return
     */
     int getPortStatus();

    /**
     * 设置接口状态
     * @param status 设置接口的状态
     */
    void setPortStatus(int status);

    /**
     * 云端设置接口状态
     * @param status
     */
    void setPortStatusByCloud(int status);

    /**
     * 关闭接口
     */
     void setDisConnect();

    /**
     * 关闭接口byCloud
     */
    void setDisConnectByCloud();
    /**
     * 连接接口
     */
    void setConnecting();
    /**
     * 连接接口
     */
    void setConnectingByCloud();

    /**
     * 已连接
     */
    void setConnected();

    /**
     * @return
     */
    Object getSynchForStatus();

    /**
     * 接口变化时
     */
    void synchPortStatusToCloud();

    boolean isConnected();
    boolean isDisConnected();

    boolean isConnecting();
}
