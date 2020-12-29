package com.networks.pms.service.fcs;


import com.networks.pms.common.util.Msg;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 *存放 fcs 请求的队列
 */
public class FcsRequestMessageQueue {
    Logger logger = Logger.getLogger(FcsRequestMessageQueue.class);
    LoggerMessageQueue loggerQueue = LoggerMessageQueue.getInstance();
    private Node head = null;
    private volatile Node rear = null;
    private static FcsRequestMessageQueue singleMessageQueue= new FcsRequestMessageQueue();
  //  private static HashSet<String> hashSet = new HashSet<String>();
    public static volatile int time = 0;
    public static class Node{
        private String t;
        private String acceptTime ;
        private Node next;

        public Node(){
            this.acceptTime = String.valueOf(new Date().getTime());
        }
        public String getAcceptTime() {
            return acceptTime;
        }

        public Node(String t) {
            this.t = t;
            this.acceptTime = String.valueOf(new Date().getTime());
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

    private FcsRequestMessageQueue() {
    }

    public void   add(String command){
        Node newNode = new Node(command);
        synchronized (FcsRequestMessageQueue.class) {
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
                FcsRequestMessageQueue.class.notify();
       //         System.out.println("hashSet add :"+hashSet.toString());
        }
    }

    public  Node get(){
        Node result = null;
        synchronized (FcsRequestMessageQueue.class) {
            if(rear ==null) {
                try {
                    FcsRequestMessageQueue.class.wait();
                } catch (InterruptedException e) {
                    loggerQueue.error("请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                    logger.error("请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                }
            }
            result = rear;
            rear = rear.getNext();
            time--;
        }
        return  result;
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

    /**
     * 获取队列中的信息(分页)
     */
   /* public List<QueueMessage> queueToList(int offset,int limit){
        QueueMessage queueMessage = null;
        List<QueueMessage> list =  list = new ArrayList<QueueMessage>();
        Node current = rear;
        int num = 0;
        int minTime = Math.min(time,(limit+offset));
        String context = null;
        //分页
        if(offset<=time){
            while(current !=null && num<minTime){
                context = current.getT().toString();
                current = current.getNext();
                if(num>=offset){
                    queueMessage = new QueueMessage((num+1),context,cloudCloud);
                    list.add(queueMessage);
                }
                num++;
            }
        }
        return list;
    }*/

    public static FcsRequestMessageQueue getInstance(){
        return singleMessageQueue;
    }
}


