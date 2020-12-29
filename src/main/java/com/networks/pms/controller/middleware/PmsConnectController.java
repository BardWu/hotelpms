package com.networks.pms.controller.middleware;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.KV;
import com.networks.pms.common.util.MyPager;
import com.networks.pms.common.util.Print;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.fcs.*;
import com.networks.pms.service.middleware.PmsConnectService;
import com.networks.pms.service.ucs.UCSTimer;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.service.middleware.PmsLogRecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制台连接接口的控制器
 */
@Controller
@RequestMapping("/portConnect")
public class PmsConnectController {
    LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    Logger logger = Logger.getLogger(PmsConnectController.class);
    @Autowired
    private SocketService socketService;
    @Autowired
    private PmsLogRecordService pmsLogRecordService;

    @Autowired
    private FcsTimer fcsTimer;
    @Autowired
    private FcsPortStatusServiceMQ fcsPortStatusService;
    @Autowired
    private PmsConnectService pmsConnectService;
    @Autowired
    private UCSTimer ucsTimer;


    @ResponseBody
    @RequestMapping("/connect")
    public void connect(String portType){
        if("fcs".equals(portType)){
            pmsConnectService.fcsConnect();
        }
    }
    @ResponseBody
    @RequestMapping("/disConnect")
    public void disConnect(String portType){
        if("fcs".equals(portType)){
            pmsConnectService.FcsDisConnect();
        }
    }
    //fcs链接的状态
    @ResponseBody
    @RequestMapping("/connectStatus")
    public String connectStatus(){
        String result = "0";
        if(MessagePoint.CONNECT_FCS_THREAD != null && MessagePoint.CONNECT_FCS_THREAD.isAlive()){
            result = "1";
        }
        return result;
    }

    //获取连接的状态
    @ResponseBody
    @RequestMapping("/getConnectStatus")
    public void getConnectStatus(HttpServletResponse response){
        Map<String,String> connMap = new HashMap<String,String>();
        int status = -2;
        String statusDetail="系统未初始化";
         if( MessagePoint.IS_USE_FCS){
            status = fcsPortStatusService.getPortStatus();
            if(KV.FcsConnected.getMessage().equals(String.valueOf(status))){
                statusDetail="Fcs连接成功";
            }else if(KV.FcsConnecting.getMessage().equals(String.valueOf(status))){
                statusDetail="Fcs正在连接";
            }if(KV.FcsDisConnected.getMessage().equals(String.valueOf(status))){
                statusDetail="Fcs连接失败";
            }
        }else{
             statusDetail ="系统只显示Fcs接口连接状态";
         }
        connMap.put("status",statusDetail);
        Print.printJSON(response, JSONObject.fromObject(connMap));
    }

    //使用接口且获取接口信息
    @ResponseBody
    @RequestMapping("/setPortConfig")
    public void setConnMsg(HttpServletResponse response, String portType, HttpServletRequest request){
        String fcsIp=SysConf.PMS_FCSSERVICEIP;
        String fcsPort=SysConf.PMS_FCSPORT;
        boolean isConflict=false;//是否冲突
        Map<String,String> connMap = new HashMap<String,String>();
        connMap.put("error","");
        if("fcs".equals(portType)) {
            if (StringUtil.isNull(fcsIp) || StringUtil.isNull(fcsPort)) {
                connMap.put("error", "请检查fcs相关的接口设置");
            }
        }
        Print.printJSON(response, JSONObject.fromObject(connMap));
    }



    /**
     * 获取接口的使用情况
     * @param response
     */
    @ResponseBody
    @RequestMapping("/getPortConfig")
    public void getConnMsg(HttpServletResponse response){

        Map<String,String> connMap = new HashMap<String,String>();
         if( MessagePoint.IS_USE_FCS){
            connMap.put("fcsIp",SysConf.PMS_FCSSERVICEIP);
            connMap.put("fcsPort",SysConf.PMS_FCSPORT);
            connMap.put("open_fcs","true");

        }
        if(MessagePoint.IS_USE_UCS){
            connMap.put("tlIp",SysConf.UCS_SERVICE_IP);
            connMap.put("tlPort",SysConf.UCS_SERVICE_PORT);
            connMap.put("open_UCS","true");
        }

        Print.printJSON(response, JSONObject.fromObject(connMap));
    }
    /**
     * 停止fcsSocket重连功能
     */
    @ResponseBody
    @RequestMapping("/stopHeartBeat")
    public void  stopHeartBeat(String portType){
        if("fcs".equals(portType)){
            fcsTimer.setIsHeartBeat(false);
           // MessagePoint.SET_FCS_CONNECT_STATUS(2); 添加修改该状态，响应云端的需求
        }
        if("ucs".equals(portType)){
            ucsTimer.setIsHeartBeat(false);
            // MessagePoint.SET_FCS_CONNECT_STATUS(2); 添加修改该状态，响应云端的需求
        }
    }
}
