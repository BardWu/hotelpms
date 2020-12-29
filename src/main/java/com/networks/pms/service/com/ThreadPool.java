package com.networks.pms.service.com;

import java.util.concurrent.*;

/**
 * @program: hotelpms
 * @description:
 * @author: Bardwu
 * @create: 2019-06-27 11:30
 **/
public class ThreadPool extends ThreadPoolExecutor {


      //默认的ThreadFactory 而创建出来的线程就是非守护的。而相应的程序就永远不会退出
    static class DefaultThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread s = Executors.defaultThreadFactory().newThread(r);
            s.setDaemon(true);//守护线程
            if (s.getPriority() != Thread.NORM_PRIORITY){
                s.setPriority(Thread.NORM_PRIORITY);
            }
            return s;
        }
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,  new LinkedBlockingQueue<Runnable>(),new DefaultThreadFactory());
    }


    public static  ExecutorService getSingleThreadExecutor(){
        return Executors.newSingleThreadExecutor(new DefaultThreadFactory());
    }

    public static ExecutorService  getFixedThreadPool(int number){
        return Executors.newFixedThreadPool(number,new DefaultThreadFactory());
    }

    public static ExecutorService  getCachedThreadPool(int number){
        return Executors.newFixedThreadPool(number,new DefaultThreadFactory());
    }
}
