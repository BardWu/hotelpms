package com.networks.pms.service.webSocket;

import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.com.SysConf;
import org.apache.log4j.Logger;

import javax.websocket.Session;
import java.io.*;
public class Work extends Thread {
    Logger logger = Logger.getLogger(Work.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
   // @Override
    public void run() {
        try{
            loggerMessageQueue.info("读取日志的任务开始");
            this.read();
        }catch (Exception e){
            logger.error("读取log日志的任务断开");
        }finally {
            MessagePoint.CONNECT_WEBSOCKET = false;
            logger.error("系统结束读取log日志");
        }
    }
    public void read()throws Exception{
        WebSocket webSocket = new WebSocket();
        String message;
        //没有session时就没必要读取信息
        while (MessagePoint.EXIST_WEBSOCKET_SESSION){
            message = loggerMessageQueue.get();
            webSocket.sendMessage(message);
         //  Thread.sleep(1000);
         }
    }
   /* public void read1()throws Exception{
        LineNumberReader reader = null;
        File file = new File(SysConf.PMS_LOGADDRESS);
        int currentLines = 0;
        reader = new LineNumberReader(new InputStreamReader(new FileInputStream(file)));
        //循环取出数据
        Session session = null;
        String message = "";
        int totalLines;
        WebSocket webSocket = new WebSocket();
        totalLines = getTotalLines(file);//500
        int lines = totalLines;//500
        int time =1;
        logger.info("系统开始读取log日志");
        try {
            while (true) {
                totalLines = getTotalLines(file);//501
                while (currentLines < totalLines) {
                  //  totalLines = getTotalLines(file);
                    currentLines++;
                    message = reader.readLine();
                    //        System.out.println("message"+message);
                    //message ="当前行数:"+currentLines+message;
                    if(time==1){
                        if(currentLines>lines){
                            webSocket.sendMessage(message);
                            time = 2;
                        }
                    }else{
                        webSocket.sendMessage(message);
                    }
                }
                //  System.out.println("读完一次 currentLines："+currentLines+",totalLines:"+totalLines);
                Thread.sleep(3000);
            }
        }finally {
            if(reader !=null){
                reader.close();
            }
        }

        //关闭流
    }*/
    private int getTotalLines(File file)throws FileNotFoundException,IOException{
        FileReader in = null;
        LineNumberReader reader = null;
        int lines = 0;
        try {
            in = new FileReader(file);
            reader = new LineNumberReader(in);
             String line = reader.readLine();
             while (line != null) {
                lines++;
                line = reader.readLine();
             }
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
           if(in != null){
               try {
                   in.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

        }

        return lines;
    }

    public static void main(String[] args) {
   /*     PropertyConfigurator.configure("D:\\wh\\work\\workspace\\hotelpms\\hotelpms\\src\\main\\resources\\config/log4j.properties");
        PropertyConfigurator.
        FileAppender appender= (FileAppender) Logger.getRootLogger().getAppender("FILE");
        appender.getFile();*/
    }
}
