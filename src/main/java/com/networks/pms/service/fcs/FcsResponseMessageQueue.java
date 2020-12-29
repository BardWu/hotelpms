package com.networks.pms.service.fcs;


import com.networks.pms.common.util.Msg;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

/**
 *处理接收fcs信息的队列
 */
public class FcsResponseMessageQueue {
    Logger logger = Logger.getLogger(FcsResponseMessageQueue.class);
    LoggerMessageQueue loggerQueue = LoggerMessageQueue.getInstance();
    private Node head = null;
    private volatile Node rear = null;
    private static FcsResponseMessageQueue singleMessageQueue= new FcsResponseMessageQueue();
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

    private FcsResponseMessageQueue() {
    }

    public void   add(String command){
        Node newNode = new Node(command);
        synchronized (FcsResponseMessageQueue.class) {
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
                FcsResponseMessageQueue.class.notify();
       //         System.out.println("hashSet add :"+hashSet.toString());
        }
    }

    public  String get(){
        Node result = null;
        synchronized (FcsResponseMessageQueue.class) {
            if(rear ==null) {
                // return null;
                try {
                    System.out.println("wait睡觉了");
                    FcsResponseMessageQueue.class.wait();
                    System.out.println("wait醒了");
                } catch (InterruptedException e) {
                    loggerQueue.error("请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                    logger.error("请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                }
            }
            result = rear;
            rear = rear.getNext();
            time--;
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
    public static FcsResponseMessageQueue getInstance(){
        return singleMessageQueue;
    }
}


