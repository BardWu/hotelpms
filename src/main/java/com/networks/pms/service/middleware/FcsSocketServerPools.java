package com.networks.pms.service.middleware;

import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.fcs.FcsRequestMessageQueue;
import com.networks.pms.service.fcs.MessageDeal;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @program: hotelpms
 * @description:
 * @author: Bardwu
 * @create: 2019-03-21 14:07
 **/

public class FcsSocketServerPools {

       Logger logger = Logger.getLogger(FcsSocketServerPools.class);
       LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();

        @Autowired
        private MessageDeal messageDeal;

        private InputStream inputStream = null;

        private OutputStream outputStream = null;

        private ServerSocket server  = null;

        public static  int acceptNumber;

         private static Object objPort = new Object();

         private static int portNumber = 9000;

        public void init(){
                initFcsResMessageQueue();
                    new Thread(new Runnable() {
                       // @Override
                        public void run() {
                            try {

                                for(int i= 0; i<100;i++){

                                    new Thread(new Runnable() {
                                       // @Override
                                        public void run() {
                                            int port =objPort();
                                            try {
                                                server  = new ServerSocket(port);
                                                loggerMessageQueue.info("Listening port........:"+port);
                                                int i = 0;
                                                while(i++<=1){
                                                    new Thread(new Runnable() {
                                                     //   @Override
                                                        public void run() {
                                                            try {
                                                                Socket client = server.accept();
                                                                new FcsSocketServerThread(client,messageDeal).run();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }).start();
                                                }

                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    Thread.sleep(1000);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();






        }

        public  static int addMessageNumber(){
                synchronized (FcsSocketServerPools.class){
                        acceptNumber++;
                }
                return acceptNumber;
        }

        //初始化处理fcs请求队列的信息 checkin/checkout...
        public void initFcsResMessageQueue(){
                //初始化存放消息队列的线程
                if(MessagePoint.FCS_REQUEST_THREAD ==null || !MessagePoint.FCS_REQUEST_THREAD.isAlive()){
                        synchronized (MessagePoint.FCS_REQUEST_SYNC){
                                if(MessagePoint.FCS_REQUEST_THREAD ==null || !MessagePoint.FCS_REQUEST_THREAD.isAlive()){
                                        MessagePoint.FCS_REQUEST_THREAD = new Thread(new Runnable() {
                                              //  @Override
                                                public void run() {
                                                        FcsRequestMessageQueue messageQueue = FcsRequestMessageQueue.getInstance();
                                                        String command = null;
                                                        while (true) {
                                                                try {
                                                                        FcsRequestMessageQueue.Node node = messageQueue.get();
                                                                        command = node.getT();
                                                                        logger.info("队列处理一个Fcs请求,目前未处理请求个数"+FcsRequestMessageQueue.time);
                                                                        loggerMessageQueue.info("队列处理一个Fcs请求,目前未处理请求个数"+FcsRequestMessageQueue.time);
                                                                        messageDeal.dealMessage(command);
                                                                }catch (Exception e){
                                                                        logger.error("初始化存放Fcs请求队列的线程发生异常,原因:"+Msg.getExceptionDetail(e));
                                                                        loggerMessageQueue.error("初始化存放Fcs请求队列的线程发生异常,原因:"+Msg.getExceptionDetail(e));
                                                                }
                                                        }
                                                }
                                        });
                                        MessagePoint.FCS_REQUEST_THREAD.start();
                                }
                        }
                }

        }

    public static int objPort(){
        synchronized (objPort){
            portNumber++;
        }
        return portNumber;
    }


}
