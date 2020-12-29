package com.networks.pms.service.ucs;

import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @program: hotelpms
 * @description: Fcs port change
 * @author: Bardwu
 * @create: 2019-02-19 14:38
 **/
@Service("UCSConnectStatusService")
public class UCSConnectStatusService {

    Logger logger = Logger.getLogger(UCSConnectStatusService.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();

    /**
     *
     *
     * @return
     */
    public int getPortStatus() {
        synchronized (UCSConnectStatusService.class) {
            return MessagePoint.UCS_CONNECT_STATUS;
        }
    }

    /**
     * 中间件修改fcs状态
     */
    public void changeStatus(int status){
        synchronized (UCSConnectStatusService.class){
            if(getPortStatus() != status){
                MessagePoint.UCS_CONNECT_STATUS = status;
            }
        }
    }

}
