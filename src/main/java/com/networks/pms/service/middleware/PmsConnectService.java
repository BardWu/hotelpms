package com.networks.pms.service.middleware;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.fcs.FcsTimer;
import com.networks.pms.service.ucs.UCSTimer;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hotelpms
 * @description:
 * @author: Bardwu
 * @create: 2019-07-22 15:39
 **/
@Service
public class PmsConnectService {
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    Logger logger = Logger.getLogger(PmsConnectService.class);

    private int fcsRepeatTime = 5;
    private int operaRepeatTime = 5;
    private int tlRepeatTime = 5;
    private int operaFMFRepeatTime = 5;
    @Autowired
    private FcsTimer fcsTimer;
    @Autowired
    private PmsLogRecordService pmsLogRecordService;
    @Autowired
    private UCSTimer UCSTimer;

    /**
     * 母线程和子线程
     * 1.定时母线程A异常结束时，可以重新开启新的母线程A执行以前的功能，但重新开启的时间时有限制的(最多repeat创建5次)
     * 2.定时母线程A中会定时创建新的子线程B，每一个定时母线程A异常结束时，会关闭创建的子线程B
     * 3.定时创建子线程B时，如果以前的子线程B未结束，会首先关闭以前的子线程B。
     *
     * 5次结束后，页面交互，从而再次执行母线程。
     *
     * 通过递归的方式，还是不太好
     */
    public void fcsConnect(){
        if(fcsRepeatTime<=0){//执行5次connectFcs后，connectFcs彻底不执行了，fcsRepeatTime=0。通过页面交互，重新去连接
            fcsRepeatTime = 5;
        }
        //当fcsRepeatTime =5时，表明是第一次进入，如果 MessagePoint.CONNECT_FCS_THREAD发生UncaughtException,fcsRepeatTime--，这时其它线程就运行不了该代码。
        if(fcsRepeatTime ==5){
            connectFcs();
        }else {
            logger.info("上次FcsTimer 还未结束，请稍后再试 fcsRepeatTime:"+fcsRepeatTime);
            loggerMessageQueue.info("上次FcsTimer 还未结束，请稍后再试 fcsRepeatTime:"+fcsRepeatTime);
        }
    }
    private void connectFcs(){
        logger.info("FcsTimer 还有"+fcsRepeatTime+"次执行机会");
        loggerMessageQueue.info("FcsTimer 还有"+fcsRepeatTime+"次执行机会");
        MessagePoint.CONNECT_FCS_THREAD = new Thread(new Runnable() {
            @Override
            public void run() {
                fcsTimer.Timer();
            }
        });
        MessagePoint.CONNECT_FCS_THREAD.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                String error = Msg.getExceptionDetail(e);
                logger.error("Fcs Timer 发生异常，异常信息:"+t.getName() + ": " + error);
                loggerMessageQueue.error("Fcs Timer 发生异常，在重新启动，异常信息:"+t.getName() + ": " + error);
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,error,"FcsTimer 发生异常");
                pmsLogRecordService.addLogRecord(pmsLogRecord);
                //异常线程
                if(fcsRepeatTime>0){
                    fcsRepeatTime--;
                    if(fcsTimer.getHeartBeat()){
                        connectFcs();  //本次FcsTimer发生unCatchExcepting 取消心跳
                    }else{
                        fcsRepeatTime=0;
                    }
                }else{
                    logger.error("这是一个很严重的错误，FcsTimer 彻底结束，异常信息:"+t.getName() + ": " + error);
                    loggerMessageQueue.error("这是一个很严重的错误，FcsTimer 彻底结束，异常信息:"+t.getName() + ": " + error);
                    pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,error,"这是一个很严重的错误，FcsTimer 彻底结束");
                    pmsLogRecordService.addLogRecord(pmsLogRecord);
                }

            }
        });
        MessagePoint.CONNECT_FCS_THREAD.start();
    }
    public void FcsDisConnect(){
        //断掉连接，先要关闭线程池
        loggerMessageQueue.info("中间件发送主动停止FCS心跳的请求");
        logger.info("中间件发送主动停止FCS心跳的请求");
        fcsTimer.setIsHeartBeat(false);//停止心跳
    }









    public void tlConnect(){
        if(tlRepeatTime<=0){//执行5次connectFcs后，connectFcs彻底不执行了，fcsRepeatTime=0。通过页面交互，重新去连接
            tlRepeatTime = 5;
        }
        //当fcsRepeatTime =5时，表明是第一次进入，如果 MessagePoint.CONNECT_FCS_THREAD发生UncaughtException,fcsRepeatTime--，这时其它线程就运行不了该代码。
        if(tlRepeatTime ==5){
            connectTl();
        }else {
            logger.info("上次TLTimer 还未结束，请稍后再试 tlRepeatTime:"+tlRepeatTime);
            loggerMessageQueue.info("上次TLTimer 还未结束，请稍后再试 tlRepeatTime:"+tlRepeatTime);
        }
    }

    private void connectTl(){
        logger.info("UCSTimer 还有"+tlRepeatTime+"次执行机会");
        loggerMessageQueue.info("UCSTimer 还有"+tlRepeatTime+"次执行机会");
        MessagePoint.CONNECT_UCS_THREAD = new Thread(new Runnable() {
            @Override
            public void run() {
                UCSTimer.Timer();
            }
        });
        MessagePoint.CONNECT_UCS_THREAD.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                String error = Msg.getExceptionDetail(e);
                logger.error("UCSTimer 发生异常，异常信息:"+t.getName() + ": " + error);
                loggerMessageQueue.error("UCSTimer 发生异常，异常信息:"+t.getName() + ": " + error);
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,error,"UCSTimer 发生异常");
                pmsLogRecordService.addLogRecord(pmsLogRecord);
                //异常线程
                if(tlRepeatTime>0){
                    tlRepeatTime--;
                    if(UCSTimer.getHeartBeat()){
                        connectTl();  //本次tlRepeatTime发生unCatchExcepting 取消心跳
                    }else{
                        tlRepeatTime=0;
                    }
                }else{
                    logger.error("这是一个很严重的错误，tlRepeatTime 彻底结束，异常信息:"+t.getName() + ": " + error);
                    loggerMessageQueue.error("这是一个很严重的错误，tlRepeatTime 彻底结束，异常信息:"+t.getName() + ": " + error);
                    pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,error,"这是一个很严重的错误，UCSTimer 彻底结束");
                    pmsLogRecordService.addLogRecord(pmsLogRecord);
                }
            }
        });
        MessagePoint.CONNECT_UCS_THREAD.start();
    }

    public void tlDisConnect(){
        loggerMessageQueue.info("中间件发送主动停止TL心跳的请求");
        logger.info("中间件发送主动停止TL心跳的请求");
        if(!MessagePoint.UCS_STOP_THREAD){
            MessagePoint.UCS_STOP_THREAD = true;//主动断开
        }
        UCSTimer.setIsHeartBeat(false);//停止心跳

    }
}
