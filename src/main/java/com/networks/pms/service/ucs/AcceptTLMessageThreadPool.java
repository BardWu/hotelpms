package com.networks.pms.service.ucs;

import com.networks.pms.bean.condition.usc.CallCharges;
import com.networks.pms.bean.condition.usc.PhoneCharge;
import com.networks.pms.bean.model.PmsFcsTL;
import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.KV;
import com.networks.pms.common.util.LogRemark;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.com.ThreadPool;
import com.networks.pms.service.fcs.FcsConnect;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;

/**
 * @program: hotelpms
 * @description: 处理fcs发送的信息
 * @author: Bardwu
 * @create: 2019-01-07 18:16
 **/
public class AcceptTLMessageThreadPool implements Runnable{
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    Logger logger = Logger.getLogger(AcceptTLMessageThreadPool.class);
    private String message;
    private PmsLogRecordService pmsLogRecordService;
    private FcsConnect fcsConnect;
    private static ThreadPoolExecutor executor = new ThreadPool(4,8,60,TimeUnit.SECONDS);

    public AcceptTLMessageThreadPool(PmsLogRecordService pmsLogRecordService, String message,  FcsConnect fcsConnect){
        this.pmsLogRecordService = pmsLogRecordService;
        this.message = message;
        this.fcsConnect = fcsConnect;
    }

    public void run() {
        if(!StringUtil.isNull(message)){
          loggerMessageQueue.info(LogRemark.RX_TRANSFORM_FROM_UCS+message);
          logger.info(LogRemark.RX_TRANSFORM_FROM_UCS+message);
          if(message.contains("ST BI")){
              PhoneCharge phoneCharge = PhoneCharge.getByUscCharge(message);
              String sendMessage = CallCharges.getCallChargesByPhoneCharge(phoneCharge).toFcs();
              if(!fcsConnect.sendMessage(sendMessage)){
                  pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,"信息发送失败" ,"UCS话单信息发送到Fcs失败",message));
              }
          }

        }
    }

    public static ThreadPoolExecutor getExecutor(){
        return executor;
   }


    public static void main(String[] args) {
        String abc = "abcde";
        if(abc.length()>4){
            String c = abc.substring(0,4);
            System.out.println("c:"+c);
            System.out.println("abc:"+abc);
        }
    }
}
