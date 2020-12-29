package com.networks.pms.service.webSocket;


import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 存放中间件产生的消息，用于推送到页面
 */
public class LoggerMessageQueue {
    Logger logger = Logger.getLogger(LoggerMessageQueue.class);

    private Node head = null;
    private volatile Node rear = null;
    private static LoggerMessageQueue singleMessageQueue= new LoggerMessageQueue();
  //  private static HashSet<String> hashSet = new HashSet<String>();
    public static volatile int time = 0;
    private static class Node{
        private String t;
        private Node next;

        public Node(String t) {
            this.t = t;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }


        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }


    }

    private LoggerMessageQueue() {
    }
    public void info(String command){
        //没有webSocket session 就不需要添加log
        if(MessagePoint.EXIST_WEBSOCKET_SESSION && time<700){
            add("[INFO]: "+"->"+command);
        }

    }
    public void error(String command){
       // 没有webSocket session 就不需要添加log
        if(MessagePoint.EXIST_WEBSOCKET_SESSION && time<700 ){

            add("[ERROR]:"+command);
        }
    }

    private void   add(String command){

        synchronized (LoggerMessageQueue.class) {
            command = DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss")+command;
        //    Node newNode = new Node(command+"->test:MessagePoint.FCS_CONNTCT_TYPE="+MessagePoint.GET_FCS_CONNECT_STATUS()+",MessagePoint.OPERA_CONNECT_TYPE="+MessagePoint.GET_OPERA_CONNECT_STATUS()+",logNum:"+LoggerMessageQueue.time);
            Node newNode = new Node(command);
            if (head == null && rear == null) {
                head = newNode;
                rear = newNode;
            } else {
                head.setNext(newNode);
                head = newNode;
                if (rear == null) {
                    rear = head;
                }
            }
            time++;

         //       System.out.println("add："+toString());
                LoggerMessageQueue.class.notify();
       //         System.out.println("hashSet add :"+hashSet.toString());
        }
    }

    public  String get(){
        Node result = null;
        synchronized (LoggerMessageQueue.class) {
            if(rear ==null) {
                // return null;
                try {
                    System.out.println("wait睡觉了");
                    LoggerMessageQueue.class.wait();
                    System.out.println("wait醒了");
                } catch (InterruptedException e) {
                    logger.error("请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                }
            }
            result = rear;
            if(result==null){
                System.out.println(result+""+rear);
            }
            rear = rear.getNext();
            //     System.out.println("hashSet remove :"+hashSet.toString());
            time--;
            //  System.out.println("remove:"+toString());
        }
        return  result.t;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node current = rear;
        while(current !=null){
            sb.append(current.getT().toString());
            current = current.getNext();
        }

        return sb.toString();
    }
    //
    public  void clear(){
        rear = rear = null;
        time = 0;
    }
    public static LoggerMessageQueue getInstance(){
        return singleMessageQueue;
    }


}


