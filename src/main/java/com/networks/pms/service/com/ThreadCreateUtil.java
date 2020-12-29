package com.networks.pms.service.com;

/**
 * @program: hotelpms
 * @description: 创建线程
 * @author: Bardwu
 * @create: 2018-12-05 14:33
 **/
public abstract class ThreadCreateUtil {
    protected Thread thread;//线程
    protected Object sync;//线程的锁

    /**
     * 初始化线程
     */
    public void createThread(){
            //初始化存放消息队列的线程
            if(thread==null || !thread.isAlive()){
                synchronized (sync){
                    if(thread==null || !thread.isAlive()){
                        thread = new Thread(new Runnable() {
                           // @Override
                            public void run() {
                                threadRun();
                            }
                        });
                       // thread.setDaemon(true);
                        thread.start();
                    }
                }
            }

        }

        public abstract void threadRun();

}
