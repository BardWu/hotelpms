package com.networks.pms.common.util;

import org.apache.log4j.Logger;

public class HotelpmsLogger  {
    private Logger logger;
    private static  HotelpmsLogger hotelpmsLogger = new HotelpmsLogger();
    private HotelpmsLogger(){
    }
    public static HotelpmsLogger getLogger(Class clazz){
        hotelpmsLogger.logger = Logger.getLogger(clazz);
        return hotelpmsLogger;
    }
    public void info(Object message){
        logger.info("hotelpmsLog:"+message);
    }

    public void debug(Object message){
        logger.debug("hotelpmsLog:"+message);
    }
    public void warn(Object message){
        logger.warn("hotelpmsLog:"+message);

    }
    //错误日志单独创建
  /* public void error(Object message){
        logger.error("hotelpmsLog:"+message);

    }
    public void error(Object message,Exception e){
        logger.error("hotelpmsLog:"+message,e);

    }*/
}
