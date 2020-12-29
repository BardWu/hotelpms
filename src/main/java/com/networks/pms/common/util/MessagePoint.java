package com.networks.pms.common.util;

import java.util.concurrent.ExecutorService;

public class MessagePoint {
    public static final char STX = 2;//信息的开始点
    public static final char ETX = 3;//信息的结束点
    public static  char ZSTX = 02;//信息的开始点
    public static  char ZETX = 03;//信息的结束点
    public static  char ZACK = 06;//信息的结束点
    public static final char ACK = 6;//响应信息

    /*fcs配置信息*/
  //  public static volatile boolean FCS_IS_START = false;//FCS线程是否启动
   // public static volatile boolean FCS_STOP_THREAD = false;//是否主动关闭fcs链接
    public static volatile  boolean CONNECT_WEBSOCKET = false;//创建websocket链接
    public static volatile  boolean EXIST_WEBSOCKET_SESSION = false;//是否存在websocketsession
    public static volatile Thread  CONNECT_FCS_THREAD = null;//链接fcs的线程
    //0:链接中(包括从新连接) 1：链接成功  2：链接失败
    public static volatile int FCS_CONNECT_STATUS = -1;//链接fsc的状态

  //  public static  volatile  long LAST_ACCEPT_FCS_MESSAGE_TIME=-1;//最新一次接收Fcs信息的时间
  /*  public static synchronized  void SET_FCS_CONNECT_STATUS(int type){
        FCS_CONNECT_STATUS = type;
    }
    public static int GET_FCS_CONNECT_STATUS(){
        return FCS_CONNECT_STATUS;
    }*/
    //存放jobEnquiry消息的队列
    public static volatile  Thread MSG_QUEUE_THREAD = null;
    public static volatile boolean FCS_IS_RELINK = true;//socket是否从新连接
    //处理createJob的线程
    public static volatile Thread CREATE_JOB_THREAD= null;
    //处理createJob线程的锁
    public static  Object CREATE_JOB_LOCK = new Object();
    //存放fcsRequest消息的队列的线程
    public static volatile  Thread FCS_REQUEST_THREAD = null;
    public static volatile boolean IS_USE_FCS;//是否使用fcs
    //定时发送jobEnquiry的线程
    public static volatile  ExecutorService FCS_JOB_ENQUIRY_TIMER = null;

   //*************preFcs***********
   public static volatile boolean IS_USE_PRE_FCS;//是否使用previous fcs
    // 1：链接成功  2：链接失败 3：断开（中间件不发送信息给fcs webService）
    public static int PRE_FCS_PORT_STATUS = 1;//hotSOS连接状态
    //*************preFcs***********
    //fcsRequest消息的队列线程的锁
    public static  Object FCS_REQUEST_SYNC = new Object();
    //存放fcsResponse消息的队列的线程
    public static volatile  Thread FCS_RESPONSE_THREAD = null;
    //fcsResponse消息的队列线程的锁
    public static  Object FCS_RESPONSE_SYNC = new Object();
    public static String HOTELPMS_LOG = "HOTELPMS_LOG";
 //   public static volatile boolean IS_FCS_HEART_BEAT_SUCCESS= true;//心跳是否成功
    public static volatile boolean IS_FCS_HEART_BEAT = true;//是否需要心跳功能
   // public static volatile boolean FCS_IS_INIT ;//是否初始化（是否连接）
    //********opera 信息******
    public static Thread OPERA_ACCEPT_THREAD = null;//接收opera线程的类
    public static Thread OPERA_SEND_THREAD = null;//发送opera线程的类
    public static Object OPERA_ACCEPT_SYNC = new Object();//接收opera线程类的锁
    public static Object OPERA_SEND_SYNC = new Object();//发送opera线程类的锁
    //public static volatile OperaMessageQueue OPERA_ACCEPT_QUEUE = null;//接收消息的队列
    //public static volatile OperaMessageQueue OPERA_SEND_QUEUE = null;//发送消息的队列
    public static volatile boolean OPERA_STOP= false;//是否主动关闭Opera链接
    public static volatile Thread OPERA_HEART_BEAT_THREAD;//Opera心跳Thread
    public static volatile boolean IS_OPERA_HEART_BEAT_SUCCESS= true;//心跳是否成功
  //  public static volatile boolean IS_OPERA_HEART_BEAT = true;//是否需要心跳功能
  //  public static volatile boolean OPERA_IS_INIT ;//是否初始化（是否连接）
    //0:链接中 1：链接成功  2：链接失败
    public static volatile int OPERA_CONNECT_STATUS = -1;//链接OPERA的状态
   /* public static synchronized  void SET_OPERA_CONNECT_STATUS(int type){
        OPERA_CONNECT_STATUS = type;
    }
    public static int GET_OPERA_CONNECT_STATUS(){
            return OPERA_CONNECT_STATUS;
    }*/
    public static volatile boolean OPERA_IS_RELINK = true;//socket是否从新连接
    public static volatile Thread CONNECT_OPERA_THREAD = null;//链接OPERA的线程
    public static volatile Thread CONNECT_OPERA_FMF_THREAD = null;//连接Opera(FMF)的线程
    public static volatile boolean OPERA_IS_START = false;//FCS线程是否启动
    public static volatile boolean IS_USE_OPERA;//是否使用OPERA,如果在使用，有新的连接时，需要在页面提示
    public static volatile boolean IS_USE_OPERA_FMF;
    public static volatile boolean OPERA_IS_SYNC = false;//OPERA是否同步


    public static volatile boolean OPERA_FMF_STOP= false;//是否主动关闭Opera FMF链接
    public static volatile boolean OPERA_FMF_SYNC = false;//OPERA是否同步
    //********opera 信息*****
    //********hotSOS信息*****
    public static volatile boolean IS_USE_HOTSOS;//是否使用fcs
    // 1：链接成功  2：链接失败 3：断开（中间件不发送信息给hotSOS）
    public static int HOTSOS_PORT_STATUS = 1;//hotSOS连接状态
    //********hotSOS信息*****
    //********preOpera*******
    public static Thread PRE_OPERA_THREAD;
    //0:服务关闭  1：已连接 2:断开（服务断开，可自动重启）  3:启动
    // 0:服务关闭  1：已连接 2:断开（服务断开，可自动重启）  3:启动
    public static  int PRE_OPERA_STATUS = 2;
    public static volatile boolean PRE_OPERA_STOP=false;//是否主动停止服务
    public static volatile boolean IS_USE_PRE_OPERA;//是否使用PRE OPERA
    //********preOpera*******
    //***********tongli***********
    //0:链接中 1：链接成功  2：链接失败
    public static  int UCS_CONNECT_STATUS= -1;//连接通利的状态
    public static volatile Thread CONNECT_TONGLI_THREAD = null;//链接fcs的线程
    //public static volatile boolean TL_IS_START = false;//FCS线程是否启动
    public static volatile boolean UCS_STOP_THREAD = false;//是否主动关闭fcs链接
    public static volatile boolean IS_USE_UCS;//是否使用OPERA,如果在使用，有新的连接时，需要在页面提示
    public static volatile boolean IS_UCS_HEART_BEAT_SUCCESS= true;//心跳是否成功
    public static volatile boolean UCS_IS_INIT ;//是否初始化（是否连接）
    public static volatile Thread CONNECT_UCS_THREAD = null;//链接tl的线程
    //***********tongli***********

    //********重发线程
    public static ExecutorService RESEND_PMS_THREAD = null;
    public static ExecutorService RESEND_JOB_THREAD  = null;
    public static ExecutorService RESEND_FCS_JOB_ENQUIRY_THREAD  = null;
    public static ExecutorService RESEND_OPERA_ROOM_STATUS = null;
}
