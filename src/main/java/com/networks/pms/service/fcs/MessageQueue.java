package com.networks.pms.service.fcs;


import com.networks.pms.common.util.Msg;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 存放云端发送JobEnquiry的信息
 */
public class MessageQueue {
    Logger logger = Logger.getLogger(MessageQueue.class);
    LoggerMessageQueue loggerQueue = LoggerMessageQueue.getInstance();
    private Node head = null;
    private volatile Node rear = null;
    private static MessageQueue singleMessageQueue= new MessageQueue();
    private static HashSet<Object> hashSet = new HashSet<Object>();
    public static volatile int time = 0;
    private static class Node{
        private Object t;
        private Node next;
        private String command;

        public Node(Object t) {
            this.t = t;
        }

        public Object getT() {
            return t;
        }

        public void setT(Object t) {
            this.t = t;
        }


        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }
    }

    private MessageQueue() {
    }

    public void   add(Object t,String command){
        Node newNode = new Node(t);
        newNode.command = command;
        synchronized (MessageQueue.class) {
            if(hashSet.add(command)){
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
                MessageQueue.class.notify();
       //         System.out.println("hashSet add :"+hashSet.toString());
            }
        }
    }

    public  Object get(){
        Node result = null;
        synchronized (MessageQueue.class) {
            if(rear ==null) {
                // return null;
                try {
                    logger.info("处理FcsJobEnquiry的线程 wait");
                    MessageQueue.class.wait();
                    logger.info("处理FcsJobEnquiry的线程 notify");
                } catch (InterruptedException e) {
                    logger.error("处理FcsJobEnquiry的线程 ,请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                    loggerQueue.error("处理FcsJobEnquiry的线程,请求队列发生异常,原因:"+ Msg.getExceptionDetail(e));
                }
            }
            result = rear;
            rear = rear.getNext();
            hashSet.remove(result.command);
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


    public static MessageQueue getInstance(){
        return singleMessageQueue;
    }
}


