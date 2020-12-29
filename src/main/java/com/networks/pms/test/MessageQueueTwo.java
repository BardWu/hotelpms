package com.networks.pms.test;

import java.util.HashSet;

public class MessageQueueTwo<T> {
    private Node head = null;
    private Node rear = null;
    private static MessageQueueTwo singleMessageQueue= new MessageQueueTwo();
    private static HashSet<String> hashSet = new HashSet<String>();
    private static int time = 0;
    private static class Node<T>{
        private T t;
        private Node next;

        public Node(T t) {
            this.t = t;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }


        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private MessageQueueTwo() {
    }

    public void   add(T t){
        Node newNode = new Node(t);
        synchronized (MessageQueueTwo.class) {
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
            System.out.println("add："+toString());
            MessageQueueTwo.class.notify();
        }
    }

    public synchronized T get(){
        Node result = null;
        synchronized (MessageQueueTwo.class) {
            if(rear ==null) {
               // return null;
                try {
                    System.out.println("睡觉了");
                    MessageQueueTwo.class.wait();
                    System.out.println("醒了");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            result = rear;
            rear = rear.getNext();
         //   System.out.println("remove:"+toString());
        }
        return  (T)result.t;
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

    public static MessageQueueTwo getInstance(){
        return singleMessageQueue;
    }
}


