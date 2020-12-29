package com.networks.pms.service.ucs;

import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.com.SysConf;
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
public class UCSTimer {

    Logger logger = Logger.getLogger(UCSTimer.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();

    private int connectTime;

    private Thread connectThread;


    @Autowired
    private UCSConnect UCSConnect;
    @Autowired
    private UCSPortStatusServiceMQ UCSPortStatusServiceMQ;

    private static volatile long lastAcceptTLMessageTime = -1;//最近一次接收Fcs信息的时间

    public static long getLastAcceptTLMessageTime() {
        return lastAcceptTLMessageTime;
    }

    public static void setLastAcceptTLMessageTime(long lastAcceptFcsMessageTime) {
        lastAcceptTLMessageTime = lastAcceptFcsMessageTime;
    }

    public void connectClose(){
        UCSConnect.close();
    }

    //获取心跳的内容
    private String getHeartMessage(){
        return "Communication";
    }

    /**
     * 设置心跳
     */
    public void setIsHeartBeat(boolean heartBeat){
        MessagePoint.IS_UCS_HEART_BEAT_SUCCESS = heartBeat;
    }

    /**
     * 获取心跳
     * @return
     */
    public  boolean getHeartBeat(){
        return  MessagePoint.IS_UCS_HEART_BEAT_SUCCESS;
    }

    private void init(){
        connectThread = new Thread(new Runnable() {
            @Override
            public void run() {
                UCSConnect.run(connectTime++);
            }
        });
        connectThread.start();
    }

    private boolean sendHeartMessage(String heartMsg){
      return   UCSConnect.sendMessage(heartMsg, SysConf.PMS_HOTELNAME);
    }
    private void setIsInit(boolean init){
        MessagePoint.UCS_IS_INIT = init;
    }
    private boolean getIsInit(){
        return   MessagePoint.UCS_IS_INIT;
    }
    /**
     * 心跳步骤
     * 一：判断是否需要心跳
     * 二：若需要，判断是否进行初始化
     * 三：初始化后，发送心跳
     * 四：心跳发送失败，重新初始化
     *
     */
    public void Timer(){
        setIsHeartBeat(true);
        try {
            while (getHeartBeat()) {//是否需要心跳
                try {
                    if (!getIsInit()) {
                        init();
                        setIsInit(true);
                    } else {
                        Thread.currentThread().sleep(Integer.parseInt(SysConf.INTERFACE_TIMER_TIME));
                       // sendTimerMessage(getHeartMessage());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!getHeartBeat()) {//停止心跳
                UCSPortStatusServiceMQ.setDisConnect();
                connectClose();
                logger.info("UC 心跳已取消");
                loggerMessageQueue.info("UC 心跳已取消");
            }
        }finally {
            UCSPortStatusServiceMQ.setDisConnect();
            connectClose();
            logger.info("close connect UCTimer");
            loggerMessageQueue.info("close connect UCTimer");
        }
    }

    private void sendTimerMessage(String timerMessage){
        boolean result = false;
        //连接程序知道自己断开了，此时不需要心跳去做链接。心跳是为了程序不知道自己断开了而所做的措施。
        if(UCSPortStatusServiceMQ.isConnected()){//如果连接成功,发送心跳
            Long currentTime = new Date().getTime();
            long lastAcceptTime = getLastAcceptTLMessageTime();
            if( lastAcceptTLMessageTime==-1 || currentTime - lastAcceptTime>=Integer.parseInt(SysConf.INTERFACE_TIMER_TIME)){
                result=  sendHeartMessage(timerMessage);
                if(!result) {
                    loggerMessageQueue.error("tl心跳发送失败");
                    logger.error("tl心跳发送失败");
                }
            }else{
                result = true;
            }
        }
        if(!result){
            setIsInit(false);//发送失败，需要重新初始化
            UCSConnect.close();
        }

    }
}
