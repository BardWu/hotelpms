package com.networks.pms.service.middleware;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.LogRemark;
import com.networks.pms.service.fcs.FcsRequestMessageQueue;
import com.networks.pms.service.fcs.MessageDeal;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @program: hotelpms
 * @description:
 * @author: Bardwu
 * @create: 2019-03-21 14:15
 **/
public class FcsSocketServerThread implements Runnable{
    Logger logger = Logger.getLogger(FcsSocketServerPools.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();

    FcsRequestMessageQueue messageQueue = FcsRequestMessageQueue.getInstance();

    private Socket socket;

    private MessageDeal messageDeal;



    public FcsSocketServerThread(Socket socket, MessageDeal messageDeal) {
        this.socket = socket;
        this.messageDeal = messageDeal;
    }

    @Override
    public void run() {
        BufferedReader bis = null;
        BufferedWriter bw = null ;
        try {
            Thread currentThread = Thread.currentThread();
             bis = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            boolean flag = true;
            char []chars = new char[1024*2];
            int length = -1;
            String message = null;
            while(flag){
                length =  bis.read(chars);
                message = new String(chars,0,length);
                bw.write("ok");
                bw.flush();
                int number = FcsSocketServerPools.addMessageNumber();
                logger.info(currentThread.getName()+" receive a "+number+" message:"+message);
                loggerMessageQueue.info(currentThread.getName()+" receive a "+number+" message:"+message);

                if(messageDeal.messageAcceptType(message)) {
             //       logger.info(LogRemark.RX_ORIGINAL_FROM_FCS+message);
            //        loggerMessageQueue.info(LogRemark.RX_ORIGINAL_FROM_FCS+message);
                    messageQueue.add(message);
                }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
