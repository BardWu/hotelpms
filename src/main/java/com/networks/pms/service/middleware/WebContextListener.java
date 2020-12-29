package com.networks.pms.service.middleware;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.fcs.FcsConnect;
import com.networks.pms.service.fcs.FcsTimer;
import com.networks.pms.service.ucs.UCSConnect;
import com.networks.pms.service.ucs.UCSTimer;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 程序启动时，执行该方法，用来连接FCS进行通信
 */
@Component
public class WebContextListener implements InitializingBean{




    @Autowired
    private FcsTimer fcsTimer;
    @Autowired
    private FcsConnect fcsConnect;
    @Autowired
    private UCSTimer UCSTimer;
    @Autowired
    private PmsConnectService pmsConnectService;

    @Autowired
    private UCSConnect UCSConnect;
    Logger logger = Logger.getLogger(WebContextListener.class);
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();

    public void afterPropertiesSet() throws Exception {
          String method = SysConf.PMS_METHOD;//0:fcs 1:opera 2:hotSOS

          if ("0".equals(method)) {
              setFcsConfig();
              setUCSConfig();
          } else{
              logger.error("请在配置文件中设置中间件连接的端口信息");
              loggerMessageQueue.error("请在配置文件中设置中间件连接的端口信息");
          }



    }




    private void setFcsConfig(){
        String fcsIp=SysConf.PMS_FCSSERVICEIP;
        String fcsPort=SysConf.PMS_FCSPORT;
        if(!StringUtil.isNull(fcsIp) && !StringUtil.isNull(fcsPort)){
            pmsConnectService.fcsConnect();
            MessagePoint.IS_USE_FCS = true;
        }
    }
    private void setUCSConfig(){
        String tlIp=SysConf.UCS_SERVICE_IP;
        String tlPort=SysConf.UCS_SERVICE_PORT;
        //线程功能:连接UCS交换机，开启发送和接收功能
        if(!StringUtil.isNull(tlIp) && !StringUtil.isNull(tlPort)){
            pmsConnectService.tlConnect();
            MessagePoint.IS_USE_UCS = true;
        }
    }
}
