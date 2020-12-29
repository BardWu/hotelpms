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
import java.util.concurrent.*;

/**
 * @program: hotelpms
 * @description: 处理fcs发送的信息
 * @author: Bardwu
 * @create: 2019-01-07 18:16
 **/
public class DealFCSMsgThreadPool implements Runnable{
    Logger logger = Logger.getLogger(DealFCSMsgThreadPool.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    private String message;
    private MessageDeal messageDeal;
    private PmsLogRecordService pmsLogRecordService;
    private static ThreadPoolExecutor executor = new ThreadPool(4,8,60,TimeUnit.SECONDS);

    public DealFCSMsgThreadPool( String message, MessageDeal messageDeal,PmsLogRecordService pmsLogRecordService){
        this.message = message;
        this.messageDeal = messageDeal;
        this.pmsLogRecordService = pmsLogRecordService;
    }

    @Override
    public void run() {
        try{
           if(messageDeal.messageAcceptType(message)) {
              String  acceptTime =String.valueOf(new Date().getTime());
              messageDeal.dealMessage(message);
           }
        }catch (Exception e){
            logger.error("处理Fcs发送数据的线程发生异常,原因:"+ Msg.getExceptionDetail(e));
            loggerMessageQueue.error("处理Fcs发送数据的线程发生异常,原因:"+ Msg.getExceptionDetail(e));
            pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(e,"处理Fcs 处理Fcs发送数据的线程发生异常",SysConf.PMS_HOTELNAME));
        }

    }


    public static ThreadPoolExecutor getExecutor(){
        return executor;
   }


    /* if(message.contains("<Flag>ST</Flag>")){
                logger.info("Fcs 发送开始同步的指令");
                loggerMessageQueue.info("Fcs 发送开始同步的指令");
                socketService.setDatabaseSync(true);
            }else  if(message.contains("<Flag>ED</Flag>")){
                logger.info("Fcs 发送结束同步的指令");
                loggerMessageQueue.info("Fcs 发送结束同步的指令");
                socketService.setDatabaseSync(false);
            }else */
}
