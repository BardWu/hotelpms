package com.networks.pms.service.com;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.util.DateUtil;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @program: hotelpms
 * @description: 定时任务
 * @author: Bardwu
 * @create: 2019-07-11 15:55
 **/
@Component
public class TimingTasks {

    @Autowired
   private PmsLogRecordService pmsLogRecordService;

    /**
     * 检测当天该系统发生的异常信息，异常次数超过5个即，发送邮件。
     *
     *   @Scheduled(cron="0/20 * * * * ?" )
     *    @Scheduled(cron="0 0 0/1 * * ?" )
     */
    @Scheduled(cron="0 0 0/1 * * ?" )
    public void inspectSystemError(){
       Map<String,String> map = new HashMap<String,String>();
        String  cloudEndTime= String.valueOf(new Date().getTime());
        String cloudBegTime  = String.valueOf(Long.parseLong(cloudEndTime)-(1*60*60*1000));
        map.put("cloudBegTime",cloudBegTime);
        map.put("cloudEndTime",cloudEndTime);
        map.put("status","error");
        int total = pmsLogRecordService.pagCount(map);
        int errorNumber = Integer.parseInt(SysConf.ERROR_NUMBER_FOR_HOUR_MAIL);
        if(total>0){
            LoggerMessageQueue.getInstance().error("系统前一个小时内发生"+total+"个异常");
        }
        if(total>=errorNumber){
            MailUtil.defaultSend("系统前一个小时内发生"+total+"个异常,请查看具体原因",SysConf.PMS_HOTELNAME);
        }

    }


}
