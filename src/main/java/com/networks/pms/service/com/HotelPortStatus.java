package com.networks.pms.service.com;

/**
 * 酒店接口状态
 */
public enum HotelPortStatus {
    STARTED("启动","0"),STARTING("启动中","1"),BREAKED("断开","2"),BREAKING("断开中","3"),RESTART("重启","4");

    private String portName;
    private String portType;

    HotelPortStatus(String portName, String portType) {
        this.portName = portName;
        this.portType = portType;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getPortType() {
        return portType;
    }

    public void setPortType(String portType) {
        this.portType = portType;
    }
}
