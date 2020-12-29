package com.networks.pms.service.fcs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: hotelpms
 * @description: fcs心跳
 * @author: Bardwu
 * @create: 2019-01-03 14:12
 **/
@Service
public class FcsTimer {

    int time = 0;
    Logger logger = Logger.getLogger(FcsTimer.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();


    @Autowired
    private FcsPortStatusServiceMQ fcsPortStatusServiceMQ;

    @Autowired
    private FcsConnect fcsConnect;
    @Autowired
    private SocketService socketService;

    @Autowired
    private PmsLogRecordService pmsLogRecordService;

    private int initTime;//从新连接的次数

    private  boolean needHeartBeat =true;//是否需要心跳

    private Thread connFcsThread;

    private boolean needInit;//是否需要初始化

    private static volatile long lastAcceptFcsMessageTime = -1;//最近一次接收Fcs信息的时间

    private String heartbeatMessage = "<StayAlive><SysDaTi>"+DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+"</SysDaTi><GUID>"+DateUtil.getUUID()+"</GUID></StayAlive>";

    public static long getLastAcceptFcsMessageTime() {
            return lastAcceptFcsMessageTime;
    }

    public static void setLastAcceptFcsMessageTime(long acceptFcsMessageTime) {
        lastAcceptFcsMessageTime = acceptFcsMessageTime;
    }

    /**
     * 设置心跳
     */
    public  void setIsHeartBeat(boolean heartBeat){

        needHeartBeat = heartBeat;
    }

    /**
     * 获取心跳
     * @return
     */
    public  boolean getHeartBeat(){
        return  needHeartBeat;
    }

    private void init(){
        initTime++;
        connFcsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                fcsConnect.run(initTime);
            }
        });
        connFcsThread.start();
    }

    private void setIsInit(boolean init){
        needInit = init;
    }
    private boolean getIsInit(){
        return  needInit;
    }
    /**
     * 心跳步骤
     * 一：判断是否需要心跳
     * 二：若需要，判断是否进行初始化
     * 三：初始化后，发送心跳
     * 四：心跳发送失败，重新初始化
     *
     *
     */
    public void Timer(){
        logger.info("begin connect FcsTimer");
        loggerMessageQueue.info("begin connect FcsTimer");
        setIsHeartBeat(true);
        try {
            while (getHeartBeat()){//是否需要心跳
                try {
                    if(!getIsInit()){
                        init();
                        setIsInit(true);
                    }else{
                        Thread.currentThread().sleep(Integer.parseInt(SysConf.INTERFACE_TIMER_TIME));
                        sendTimerMessage();
                    }
                } catch (Exception e) {
                    logger.error("Fcs connect getHeartBeat() 发生异常原因:"+ Msg.getExceptionDetail(e));
                    loggerMessageQueue.error("Fcs connect getHeartBeat() 发生异常原因:"+ Msg.getExceptionDetail(e));
                    pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(e,"Fcs Timer发生异常",SysConf.PMS_HOTELNAME));
                }
            }
            if(!getHeartBeat()){//停止心跳
                fcsPortStatusServiceMQ.setDisConnect();
                fcsConnect.close();//I/o流的关闭，使线程抛出异常，从而使线程走完
                logger.info("fcs 心跳功能已取消");
                loggerMessageQueue.info("fcs 心跳功能已取消");

            }
        }  finally {
            fcsPortStatusServiceMQ.setDisConnect();
            fcsConnect.close();//母线程，即将结束，I/o流的关闭，使连接的子线程抛出异常，从而子使线程走完，从而使init()所对应的线程结束
            logger.info("close connect FcsTimer");
            loggerMessageQueue.info("close connect FcsTimer");
        }
    }

    private void sendTimerMessage(){
        boolean result = false;

        if(fcsPortStatusServiceMQ.isConnected()){//如果连接成功,发送心跳
            //当前时间和最近一次Fcs接收数据的时间相差30s(Fcs 30秒内处理心跳响应，没有发送数据了，在发送心跳)
            Long currentTime = new Date().getTime();
            long lastAcceptTime = getLastAcceptFcsMessageTime();
            if( lastAcceptTime==-1 || currentTime - lastAcceptTime>=Integer.parseInt(SysConf.INTERFACE_TIMER_TIME)){
                result=  sendHeartbeat();
                if(!result) {
                    loggerMessageQueue.error("心跳发送失败");
                    logger.error("心跳发送失败");
                }
            }else{
                result = true;
            }
        }
        setIsInit(result);
    }

    private boolean sendHeartbeat(){
        boolean sendResult  =  false ;
        //发送心跳数据失败
        if(!fcsConnect.sendMessage(heartbeatMessage)){


            //但是接口在此断期间接收了其它数据，则心跳也成功
            Long currentTime = new Date().getTime();
            long lastAcceptTime = getLastAcceptFcsMessageTime();
            if( currentTime - lastAcceptTime<=Integer.parseInt(SysConf.INTERFACE_TIMER_TIME)){
                sendResult = true;
                logger.info("****心跳发送失败,但接口接收了新的数据*****");
            }


        }else{
            sendResult = true;
        }
        return sendResult;

    }
}
