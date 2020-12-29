package com.networks.pms.service.fcs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.LogRemark;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.com.ThreadPool;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hotelpms
 * @description: 处理fcs发送的信息
 * @author: Bardwu
 * @create: 2019-01-07 18:16
 **/
public class DealFCSACKMsgThreadPool implements Runnable{
    Logger logger = Logger.getLogger(DealFCSACKMsgThreadPool.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    private SocketService socketService;
    private String message;
    PmsLogRecordService pmsLogRecordService;
    private static ThreadPoolExecutor executor = new ThreadPool(4,8,60,TimeUnit.SECONDS);

    public DealFCSACKMsgThreadPool(SocketService socketService, String message,PmsLogRecordService pmsLogRecordService){
        this.socketService = socketService;
        this.message = message;
        this.pmsLogRecordService = pmsLogRecordService;
    }

    @Override
    public void run() {
        try{

            if(String.valueOf(MessagePoint.ACK).equals(message)){
                socketService.setSendSuccess(true);//中间件发送成功
                message = "ACK";
            }else {//如果信息不是ACK
               socketService.send(""+MessagePoint.ACK);//响应ACK
           }
            loggerMessageQueue.info(LogRemark.RX_ORIGINAL_FROM_FCS+message);
            logger.info(LogRemark.RX_ORIGINAL_FROM_FCS+message);
        }catch (Exception e){
            logger.error("处理Fcs ACK数据发送异常,原因:"+ Msg.getExceptionDetail(e));
            loggerMessageQueue.error("处理Fcs ACK数据发送异常,原因:"+ Msg.getExceptionDetail(e));
            pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(e,"处理Fcs ACK数据发送异常",SysConf.PMS_HOTELNAME));
        }

    }

    public static ThreadPoolExecutor getExecutor(){
        return executor;
   }
}
