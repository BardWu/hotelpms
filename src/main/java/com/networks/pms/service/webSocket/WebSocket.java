package com.networks.pms.service.webSocket;

import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.fcs.FcsPortStatusServiceMQ;
import com.networks.pms.service.ucs.UCSPortStatusServiceMQ;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 思想
 * 只会开启一个线程去获取log信息。
 * 当没有连接时，不去获取和添加log信息，并且将log信息置空
 *
 */



@ServerEndpoint("/websocket")
public class WebSocket {
    public Session session;
    public Session getSession(){
        return this.session;
    }
    public static volatile boolean hasSession =false;
    private static int currentType = -2;
    private static int tlCurrentType = -2;

    Logger logger = Logger.getLogger(WebSocket.class);

    private FcsPortStatusServiceMQ fcsPortStatusServiceMQ= (FcsPortStatusServiceMQ)ContextLoader.getCurrentWebApplicationContext().getBean("fcsPortStatusServiceMQ");
    private UCSPortStatusServiceMQ UCSPortStatusServiceMQ = (UCSPortStatusServiceMQ)ContextLoader.getCurrentWebApplicationContext().getBean("UCSPortStatusServiceMQ");

    public  static CopyOnWriteArraySet<WebSocket> webSocketSet =
            new CopyOnWriteArraySet<WebSocket>();
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
         MessagePoint.EXIST_WEBSOCKET_SESSION = true;
        //第一个链接
        if(!MessagePoint.CONNECT_WEBSOCKET){
            MessagePoint.CONNECT_WEBSOCKET = true;
           new Work().start();
        }else {
        //添加一个新的链接需要发送当前链接状态
           String message = "";
             if( MessagePoint.IS_USE_FCS){
                    currentType=fcsPortStatusServiceMQ.getPortStatus();
                    message = message +  "->connectStatus:MessagePoint.FCS_CONNECT_TYPE="+currentType;
             }
             if(MessagePoint.IS_USE_UCS){
                tlCurrentType= UCSPortStatusServiceMQ.getPortStatus();
                message = message +  "->connectStatus:MessagePoint.UCS_CONNECT_TYPE="+tlCurrentType;
             }

            synchronized (session) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    Send Message
     */
    @OnMessage
    public void onMessage(String message) {
        if("ping".equals(message)){
            synchronized (session) {
                try {
                    session.getBasicRemote().sendText("pong");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /*
    Errot
     */
    @OnError
    public void onError(Throwable throwable, Session session) {
        logger.error("throwable = " + throwable.getMessage());
        throwable.printStackTrace();
    }

    /*
    Close Connection
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose:"+session);
        webSocketSet.remove(this);
       /* for(WebSocket webSocket:webSocketSet){
            System.out.println(webSocket.session);
        }*/
       //当没有session时，log队列中信息全部删除
        if(webSocketSet.size()==0){
            MessagePoint.EXIST_WEBSOCKET_SESSION = false;
            LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
            loggerMessageQueue.clear();
        }
      //  WebSocketList.onClose();
    }


    public   void sendMessage(String message){
        Session session = null;
      //  String date = DateUtil.DateToString(new Date(),"HH:mm:ss");
         if( MessagePoint.IS_USE_FCS){
            if(currentType !=fcsPortStatusServiceMQ.getPortStatus()){
                currentType =fcsPortStatusServiceMQ.getPortStatus();
                message = message +  "->connectStatus:MessagePoint.FCS_CONNECT_TYPE="+currentType;
            }
        }
        if(MessagePoint.IS_USE_UCS) {
            if (tlCurrentType != UCSPortStatusServiceMQ.getPortStatus()) {
                tlCurrentType = UCSPortStatusServiceMQ.getPortStatus();
                message = message + "->connectStatus:MessagePoint.UCS_CONNECT_TYPE=" + tlCurrentType;
            }
        }
        for(WebSocket webSocket:WebSocket.webSocketSet){
                session = webSocket.session;
                synchronized (session) {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

        }
    }



}

