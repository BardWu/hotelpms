package com.networks.pms.service.fcs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FcsConnect {
   // HotelpmsLogger logger = HotelpmsLogger.getLogger(FcsConnect.class);
    Logger logger = Logger.getLogger(FcsConnect.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    @Autowired
    private SocketService socketService;
    @Autowired
    private PmsLogRecordService pmsLogRecordService;

    @Autowired
    private FcsPortStatusServiceMQ fcsPortStatusService;

    public boolean isConnect(){//0:链接中(包括从新连接) 1：链接成功  2：链接失败
        return fcsPortStatusService.isConnected();
    }

    public void close(){
        socketService.ioClose();
    }
    public boolean sendMessage(String message) {
       return socketService.sendMessageWithACK(message);
    }




    public void run(int time) {
            try {
                logger.info("----------------connect fcs "+ time +" time-----------------------");
                loggerMessageQueue.info("----------------connect fcs "+ time +" time-----------------------");
                close();//保证当前没有连接/保证上一个线程的连接已经关闭了
                socketService.setHost(SysConf.PMS_FCSSERVICEIP);
                socketService.setPort(Integer.parseInt(SysConf.PMS_FCSPORT));
                //初始化连接次数
                socketService.getMessage();
                logger.info("--------------"+time+" time close fcs -----------------------");
                loggerMessageQueue.info("--------------"+time+" time close fcs -----------------------");
            } catch (Exception e) {
                e.printStackTrace();
                pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(e, "对接Fcs接口功能异常结束", SysConf.PMS_HOTELNAME));
                logger.info("----------------"+time+" time connect fcs error end-----------------------");
                loggerMessageQueue.info("----------------"+time+" time connect fcs error end-----------------------");
            } finally {
                socketService.ioClose();
            }
    }
}

