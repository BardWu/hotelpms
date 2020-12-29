package com.networks.pms.service.fcs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.*;
import com.networks.pms.service.com.MailUtil;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class SocketService {
    Logger logger = Logger.getLogger(SocketService.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();

    private String host;
    private int port;
    private  Socket socket;
    private  OutputStream os = null;
    private  InputStream is = null;
    private  BufferedReader bufferedReader = null;
    private MailUtil mailUtil = new MailUtil();

    private volatile boolean isSendSuccess = false;//信息是否发送成功

    @Autowired
    private MessageDeal messageDeal;
    @Autowired
    private PmsLogRecordService pmsLogRecordService;

    @Autowired
    private FcsPortStatusServiceMQ fcsPortStatusServiceMQ;


    public boolean isSendSuccess() {
        return isSendSuccess;
    }

    public void setSendSuccess(boolean heartbeatSuccess) {
        this.isSendSuccess = heartbeatSuccess;
    }


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }





    private void connect() {
        String connectInfo = null;
        if (!fcsPortStatusServiceMQ.isConnected()) {
            try {
                socket = new Socket(host, port);
                is = socket.getInputStream();
                os = socket.getOutputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                fcsPortStatusServiceMQ.setConnected();

                connectInfo="连接Fcs成功";
                pmsLogRecordService.addLogRecord(PmsLogRecord.successLog(SysConf.PMS_HOTELNAME, connectInfo));
            } catch(Exception e){
                e.printStackTrace();
                connectInfo = "Fcs链接失败，原因:" + Msg.getExceptionDetail(e);
                fcsPortStatusServiceMQ.setConnecting();
                logger.error(connectInfo);
                loggerMessageQueue.error(connectInfo);
                pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(e, "Fcs链接失败", SysConf.PMS_HOTELNAME));
            }
        }
        mailUtil.defaultSendForConnect(fcsPortStatusServiceMQ.isConnected(),connectInfo,SysConf.PMS_HOTELNAME);
    }


    /**
     * 发送消息
     * @param message
     * @return 发送的结果
     * 只有链接成功情况下发送
     */
    public boolean sendMessageWithACK(String message)  {
        boolean sendResult = false;

        String info = null;
        if(!message.equals(String.valueOf(MessagePoint.ACK))){
            message = MessagePoint.STX + message + MessagePoint.ETX;
        }
        synchronized (this){
            if (fcsPortStatusServiceMQ.isConnected()) {
                int i = 0;
                while (i<3){
                    if((sendResult =send(message))){
                        String sleepTime = SysConf.FCS_RESPONSE_WAITING_TIME;
                        if(!StringUtil.isNull(sleepTime)){
                            try {
                                Thread.sleep(Integer.parseInt(sleepTime));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(!isSendSuccess()){
                            sendResult = false;
                        }
                        setSendSuccess(false);
                        break;
                    }else {
                        i++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(!sendResult){
                    info = "中间件已连接Fcs,但信息发送失败";
                }

            }else{
                info = "中间件未连接Fcs,发送失败";
            }

            if(!sendResult){
                loggerMessageQueue.error(info);
                logger.error(info);
            }
            return sendResult;
        }
    }

     protected synchronized   boolean send(String message){
        boolean sendResult = false;
        try {
            os.write(message.getBytes("utf-8"));
            os.flush();
            sendResult = true;
            logger.info(LogRemark.TX_TRANSFORM_TO_FCS + message);
            loggerMessageQueue.info(LogRemark.TX_TRANSFORM_TO_FCS + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return sendResult;
    }

    /**
     * 获取云端发送的信息
     * 中间件保持每隔2秒发送一条数据
     */
   public void getMessage() {
       if (!fcsPortStatusServiceMQ.isConnected()) {
           connect();
       }
       ThreadPoolExecutor fcsMsgExecutor = DealFCSMsgThreadPool.getExecutor();//处理Pms信息
       ThreadPoolExecutor fcsACKMsgExecutor = DealFCSMsgThreadPool.getExecutor();//处理Fcs ACK 和响应成功信息
       int len = -1;
       String strData = null;
       while (fcsPortStatusServiceMQ.isConnected()) {
           try {
               char[] buf = new char[1024 * 15];
               len = bufferedReader.read(buf);
               if (len != -1) {
                    strData = new String(buf, 0, len);
                    FcsTimer.setLastAcceptFcsMessageTime(new Date().getTime());
                    //接收到数据后
                    if(!StringUtil.isNull(strData)){
                        //单独的线程池回应Ack，为了解决Fcs数据堵住无限发送数据后没有及时响应的问题.
                        fcsACKMsgExecutor.execute(new DealFCSACKMsgThreadPool(this,strData,pmsLogRecordService));
                        //单独的线程池回应Ack
                        if(!strData.equals(String.valueOf(MessagePoint.ACK))) {//如果信息不只是ACK
                            fcsMsgExecutor.execute(new DealFCSMsgThreadPool(strData,messageDeal,pmsLogRecordService));
                        }
                    }
               } else {//等于-1 的原因是假链接成功
                   if(!fcsPortStatusServiceMQ.isDisConnected()){//如果连接已经关闭，但io还没有彻底关闭，还能接收到数据。
                       fcsPortStatusServiceMQ.setConnecting();
                   }
                   mailUtil.defaultSendForConnect(fcsPortStatusServiceMQ.isConnected(),"连接Fcs失败(接收-1)",SysConf.PMS_HOTELNAME);
               }
           } catch (IOException e) {//关闭socket
               //发生异常的原因又两种，一:socket本身发生异常。二：系统主动发生异常
               if(!fcsPortStatusServiceMQ.isDisConnected()){//如果连接已经关闭，但io还没有彻底关闭，还能接收到数据。
                   fcsPortStatusServiceMQ.setConnecting();
                   logger.error("中间件和fcs异常断开,原因:"+Msg.getExceptionDetail(e));
                   loggerMessageQueue.error("中间件和fcs异常断开,原因:"+Msg.getExceptionDetail(e));
                   pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,"中间件和FCS异常断开,可能Fcs系统被关闭，或者网络出现问题。代码报错信息:"+Msg.getExceptionDetail(e), "中间件和fcs异常断开"));
                   MailUtil.defaultSend("中间件和fcs异常断开,原因:"+Msg.getExceptionDetail(e),SysConf.PMS_HOTELNAME);
               }else {//主动关闭
                   logger.info("中间件已主动关闭和FCS的连接");
                   loggerMessageQueue.info("中间件已主动关闭和FCS的连接");
               }
           }
       }
   }

    public  void ioClose(){
       //看依赖关系，如果流a依赖流b，应该先关闭流a，再关闭流b
      /*  if(bufferedReader != null){
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        if( is!= null){
            try {
              is .close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(os !=null){
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket !=null){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}

