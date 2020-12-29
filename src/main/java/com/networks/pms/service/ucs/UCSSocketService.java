package com.networks.pms.service.ucs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.util.LogRemark;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.com.MailUtil;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.fcs.FcsConnect;
import com.networks.pms.service.middleware.PmsLogRecordService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class UCSSocketService {
    int time1 = 0;
    int time2 = 0;
    Logger logger = Logger.getLogger(UCSSocketService.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    private String host;
    private int port;
    private volatile Socket socket;
    private volatile OutputStream os = null;
    private volatile boolean isConnect = false;
    private  volatile InputStream is = null;
    BufferedReader bufferedReader = null;
    private   Boolean sendSuccess = new Boolean(false);//是否发送成功

    private MailUtil mailUtil = new MailUtil();
    @Autowired
    private FcsConnect fcsConnect;
    @Autowired
    private PmsLogRecordService pmsLogRecordService;
    @Autowired
    private PmsSendUCSService pmsSendUCSService;
    @Autowired
    private UCSConnectStatusService UCSConnectStatusService;
    @Autowired
    private UCSPortStatusServiceMQ connectStatus;

 /*   public void setSendSuccess(boolean sendResult){
        synchronized (sendSuccess){
            this.sendSuccess = sendResult;
        }

    }

    public boolean getSendSuccess(){
        synchronized (sendSuccess) {
            return sendSuccess;
        }
    }*/

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public boolean isConnect() {
        return isConnect;
    }


    public void connect() {
        String connectInfo;
        if (!connectStatus.isConnected()) {
            try {
                socket = new Socket(host, port);
                is = socket.getInputStream();
                os = socket.getOutputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                connectStatus.setConnected();
                connectInfo = "UCS 交换机已成功连接";
                logger.info(connectInfo);
                loggerMessageQueue.info(connectInfo);

            } catch (Exception e) {
                e.printStackTrace();
                connectInfo = "UCS 交换机连接失败";
                logger.error(connectInfo);
                loggerMessageQueue.error(connectInfo);
                connectStatus.setConnecting();
            }
            mailUtil.defaultSendForConnect(connectStatus.isConnected(),connectInfo,SysConf.PMS_HOTELNAME);

        }
    }
    //心跳和发送给
    public boolean sendMessage(String message)  {
        String hotelName = SysConf.PMS_HOTELNAME;
        String msg = null;
        boolean sendResult = false;

        synchronized (UCSSocketService.class) {
            int i = 0;
            while (i<3){
                if(send(message)){
                    sendResult = true;
                    break;
                }else{
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if(!sendResult){//发送失败，
            msg= "中间件已连接UCS,但信息发送失败。相关信息:";
        }

        if(msg != null){//发送失败，
            loggerMessageQueue.error(msg+message);
            logger.error(msg+message);
            MailUtil.defaultSend(msg+message,hotelName);
        }
        return sendResult;
    }



    public boolean send(String message){
        if (!connectStatus.isConnected()) {
            connect();
        }
        try {
            os.write(message.getBytes("utf-8"));
        }catch (IOException e){
            logger.error("因发生I/0异常,中间件发送信息给UCS失败,原因："+Msg.getExceptionDetail(e));
            loggerMessageQueue.error("因发生I/0异常,中间件发送信息给UCS失败,原因："+Msg.getExceptionDetail(e));
            return false;
        }
        return true;
    }

    /**
     * 获取云端发送的信息
     */
   public void getMessage() {
       //如果当前没有连接并且连接状态不是 “连接失败”
       if (!connectStatus.isConnected()) {
           connect();
       }
       int len = -1;
       int a = 1;
       ThreadPoolExecutor executor = AcceptTLMessageThreadPool.getExecutor();
       String strData = null;
       while (connectStatus.isConnected()) {
           try {
               char[] buf = new char[1024 * 15];
               len = bufferedReader.read(buf);
               if (len != -1) {
                    strData = new String(buf, 0, len);
                    executor.execute(new AcceptTLMessageThreadPool(pmsLogRecordService, strData,  fcsConnect));
               } else {//等于-1 的原因是假链接成功
                   connectStatus.setConnecting();
                   connect();
                //   UCSConnectStatusService.changeStatus(Integer.parseInt(KV.TLConnecting.getMessage()));
               }
           } catch (IOException e) {//关闭socket
               e.printStackTrace();
               String error = "";
               if(!connectStatus.isDisConnected()){
                   if (!MessagePoint.UCS_STOP_THREAD) {//不是主动关闭时，需要重连
                       error = Msg.getExceptionDetail(e);
                       pmsLogRecordService.addLogRecord(PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,error, "中间件和fcs异常断开"));
                       logger.error("中间件和fcs异常断开,原因:"+error);
                       loggerMessageQueue.error("中间件和fcs异常断开,原因:"+error);
                       connectStatus.setConnecting();
                   } else {//主动关闭
                       error = "中间件已主动关闭UCS";
                       logger.info(error);
                       loggerMessageQueue.info(error);
                   }
               }
           }
       }
   }

    public  void ioClose(){
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

    /**
     * 1. 1s后 检测是否收到响应
     * 2. 若未收到，在等2s.
     * @return
     */
  /*  public boolean isSendSuccess(){
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(getSendSuccess()){
            return true;
        }else{
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(getSendSuccess()){
                return true;
            }else{
                return false;
            }
        }
    }*/

}

