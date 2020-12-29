package com.networks.pms.service.com;

import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

import java.util.HashMap;

public  abstract class HotelPortStatusAbstractMQ implements HotelPortStatusInterfaceMQ {

   private static Logger logger = Logger.getLogger(HotelPortStatusAbstractMQ.class);
    private static LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    private int hotelId;
    private int portType;
    private String portName;
    private int currentPortStatus=-1;
    private int connecting = 0;//0:链接中(包括从新连接)
    private int connected =1; //1：链接成功
    private int disConnected =2;//  2：链接失败
    private static HashMap<Integer, String>    portStatusDetail = new HashMap<Integer, String>();

    static {
        portStatusDetail.put(0,"链接中");
        portStatusDetail.put(1,"链接成功");
        portStatusDetail.put(2,"断开");
        portStatusDetail.put(-1,"未初始化");
        portStatusDetail.put(-2,"error");
    }
    //获取连接的说明 : 链接中  链接成功 链接失败
    public String getStatusDetail(){
        return portStatusDetail.get(getPortStatus());
    }
    @Override
    public void setPortStatus(int portStatus) {
        boolean needChange = false;
        synchronized (getSynchForStatus()){
              if(portStatus != getPortStatus() ){
                  needChange = true;
                  currentPortStatus = portStatus;
                  if(needChange){
                      currentPortDetailBeforeUpdate();
                      synchPortStatusToCloud();

                  }
              }
        }

    }
    @Override
    public void setPortStatusByCloud(int status){
        if(isEffectivePortStatus(status)){
            String log ="当前接口状态:"+getStatusDetail();
            if(status == getPortStatus()){
                log+=",和云端要修改的状态一致，系统不做任何处理";
            }else{
                log+=",云端将要修改为:"+portStatusDetail.get(status);
            }
            logger.info(log);
            loggerMessageQueue.info(log);
            //云端要连接
            if(status ==connected && status != getPortStatus()){
                if(this.isDisConnected()) {//如果接口为断开状态，可以连接
                    setConnectingByCloud();
                }else{//否则接口的状态和云端不一致，需要同步接口的状态给云端
                    this.synchPortStatusToCloud();
                }
            }
            //云断要断开
            if(status == disConnected && disConnected != getPortStatus()){
                if(!this.isDisConnected()){//如果接口不是断开状态，可以断开
                    setDisConnectByCloud();
                }else{//否则接口的状态和云端不一致，需要同步接口的状态给云端
                    this.synchPortStatusToCloud();
                }
            }
        }else {
            logger.error("云端要修改的接口状态无效");
            loggerMessageQueue.error("云端要修改的接口状态无效");
        }
    }
    @Override
    public int getPortStatus() {
        synchronized (getSynchForStatus()){
            return currentPortStatus;
        }
    }

    /**
     * hotelpms 修改
     *  setPortStatus(修改接口) ->portChangeListener(发送到云端)
     */
    @Override
    public void setConnected() {
        setPortStatus(this.connected);
    }

    @Override
    public void setConnecting() {
        setPortStatus(this.connecting);
    }

    @Override
    public void setDisConnect() {
        setPortStatus(this.disConnected);
    }

    @Override
    public void synchPortStatusToCloud() {
      /*  HotelPortStatusDetail hotelPortStatusInter = new HotelPortStatusDetail(hotelId,portType,getPortStatus());
        currentPortDetailAfterUpdate();
        JSONObject jsonObject = JSONObject.fromObject(hotelPortStatusInter);
        HttpRequest httpRequest = HttpRequest.synchPortStatusToCloud(jsonObject,"interStatus",apiKey);
        String response =  httpRequest.httpClientPost();
        logger.info("云端响应"+portName+"接口状态的结果为:"+response);
        loggerMessageQueue.info("云端响应"+portName+"接口状态的结果为:"+response);
        String msg = "云端响应接口状态成功";
        //获取请求的响应
        if(!StringUtil.isNull(response)){
            try {
                jsonObject = JSONObject.fromObject(response);
                if (jsonObject.get("status") != null) {
                    msg = jsonObject.get("status").toString();
                    if (!"success".equals(msg)) {
                        if (jsonObject.get("message") != null) {
                            msg = jsonObject.get("message").toString();
                        }else {
                            msg="云端响应接口状态失败";
                        }
                    }
                }
            }catch (JSONException e){
                msg = response;
            }
        }
        if("云端响应接口状态成功".equals(msg)){
            logger.error("云端响应" + portName + "接口状态失败,原因:"+msg);
            loggerMessageQueue.error("云端响应" + portName + "接口状态失败,原因:"+msg);
            dealListenerError(msg);
        }*/
    }
    @Override
    public boolean isConnected(){
        return connected==this.getPortStatus()?true:false;
    }
    @Override
    public boolean isDisConnected(){
        return disConnected==this.getPortStatus()?true:false;
    }

    @Override
    public boolean isConnecting() {
        return connecting == this.getPortStatus()?true:false;
    }

    private void currentPortDetailBeforeUpdate(){
        logger.info(this.portName+"接口状态将要被修改,当前连接状态:"+getStatusDetail());
        loggerMessageQueue.info(this.portName+"接口状态将要被修改,当前连接状态:"+getStatusDetail());
    }
    private void currentPortDetailAfterUpdate(){
        logger.info(this.portName+"接口已被修改为:"+getStatusDetail()+",稍后同步云端");
        loggerMessageQueue.info(this.portName+"接口已被修改为:"+getStatusDetail()+",稍后同步云端");
    }

    /**
     * 判断是否是存在的接口
     * @param status
     * @return
     */
    public boolean isEffectivePortStatus(int status){
        return portStatusDetail.containsKey(status);
    }

    /**
     * 处理listenerError 情况
     */
    public abstract  void dealListenerError(String errorReason);




    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getPortType() {
        return portType;
    }

    public void setPortType(int portType) {
        this.portType = portType;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }
}
