package com.networks.pms.service.ucs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.util.KV;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UCSConnect {
   // HotelpmsLogger logger = HotelpmsLogger.getLogger(FcsConnect.class);
    Logger logger = Logger.getLogger(UCSConnect.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    @Autowired
    private UCSSocketService UCSSocketService;
    @Autowired
    private PmsLogRecordService pmsLogRecordService;

    @Autowired
    private UCSConnectStatusService UCSConnectStatusService;

    private int getPortStatus() {
       return UCSConnectStatusService.getPortStatus();
    }

    public boolean isConnect(){
    /*   return true;*/
       return getPortStatus()==Integer.parseInt(KV.TLConnected.getMessage())?true:false;
    }

    int time = 0;
    public void close(){
        UCSSocketService.ioClose();
    }
    public boolean sendMessage(String message,String hotelName) {
        return UCSSocketService.sendMessage(message);
    }
    public boolean defaultSendMessage(String message) {
        String hotelName = SysConf.PMS_HOTELNAME;
        return UCSSocketService.sendMessage(message);
    }
    public void run(int time) {
        loggerMessageQueue.info("初始化UCS连接次数:"+time++);;
        try {
            close();
            logger.info("----------------connect UCS start-----------------------");
            loggerMessageQueue.info("----------------connect UCS start-----------------------");
            //日志
            UCSSocketService.setHost(SysConf.UCS_SERVICE_IP);
            UCSSocketService.setPort(Integer.parseInt(SysConf.UCS_SERVICE_PORT));
            //初始化连接次数
            UCSSocketService.getMessage();
            logger.info("----------------connect UCS end-----------------------");
            loggerMessageQueue.info("----------------connect UCS end-----------------------");
        } catch (Exception e) {
            pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(e, "连接通力的任务异常结束", SysConf.PMS_HOTELNAME));
            logger.info("---------------- 任务异常结束  connect UCS end-----------------------");
            loggerMessageQueue.info("---------------- 任务异常结束  connect UCS end-----------------------");
        } finally {
            UCSSocketService.ioClose();
            MessagePoint.UCS_STOP_THREAD = false;
        }
    }
}

