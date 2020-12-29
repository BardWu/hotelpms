package com.networks.pms.service.fcs;

import com.networks.pms.bean.condition.usc.Dnd;
import com.networks.pms.bean.condition.usc.GuestMessage;
import com.networks.pms.bean.condition.usc.MessageLamp;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.*;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.middleware.*;
import com.networks.pms.service.ucs.PmsSendUCSService;
import com.networks.pms.service.ucs.UCSSocketService;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.networks.pms.bean.model.PmsLogRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 解析fcs发送的信息
 */
@Component
public class MessageDeal {
   // HotelpmsLogger logger = HotelpmsLogger.getLogger(MessageDeal.class);
    Logger logger = Logger.getLogger(MessageDeal.class);
    LoggerMessageQueue loggerQueue = LoggerMessageQueue.getInstance();
    @Autowired
    private PmsLogRecordService pmsLogRecordService;
    @Autowired
    private PmsLanguageService pmsLanguageService;
    @Autowired
    private PmsSendUCSService pmsSendUCSService;
    @Autowired
    private UCSSocketService ucsSocketService;
    //中间件处理信息类型
    private static  List<String> acceptList =null;
    static{
        acceptList = new ArrayList<String>();
        acceptList.add("<CheckOut>");
        acceptList.add("<CheckIn>");
     //   acceptList.add("<InfoChange>");
        acceptList.add("<DoNotDisturb>");//客房免打扰
        acceptList.add("<MesgLamp>");// 留言灯状态   Message Waiting
        //acceptList.add("<RoomChange>");
       // acceptList.add("<CreateJob>");//创建工单
       // acceptList.add("JobEnquiry");//工单状态
        /*if(KV.FcsAndTLInterface.getMessage().equals(SysConf.PMS_METHOD)){
          //  acceptList.add("<GuestFolioDetail>");//客房账单
            acceptList.add("<ClassOfService>");//客房等级
            acceptList.add("<DoNotDisturb>");//客房免打扰
            acceptList.add("<MesgLamp>");// 留言灯状态   Message Waiting
        }*/
     //   acceptList.add("<RoomStatus>");
    }
    /**
     * 解析Message -> List<bean>
     * @param message 处理一次接收的信息
     */
    public void dealMessage(String message){
        String xmlStr = null;
        if(StringUtil.isNull(message)){
            return;
        }
        //解析指定的信息
        message = message.replaceAll(MessagePoint.ACK+"","");
        //按照指定的规则截取信息
        String[] strs = message.split("" + MessagePoint.ETX);
        for (int i = 0; i < strs.length; i++) {
            try {
                xmlStr = strs[i].split("" + MessagePoint.STX)[1];
                if (xmlStr.contains("CheckOut")) {
                   XmlToCheckOut(xmlStr);

                }else if(xmlStr.contains("CheckIn")){
                   XmlToCheckIn(xmlStr);
                }else if(xmlStr.contains("DoNotDisturb")){ //MesgLamp
                   XmlToDoNotDisturb(xmlStr);
                }else if(xmlStr.contains("MesgLamp")){ //
                   XmlToMessageWaiting(xmlStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("解析fcs请求出错,原因:"+e);
                loggerQueue.error("解析fcs请求出错,原因:"+e);
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(e,"解析内容失败",SysConf.PMS_HOTELNAME);
                pmsLogRecord.setContent(xmlStr);
                pmsLogRecordService.addLogRecord(pmsLogRecord);
            }
        }
    }

    public void XmlToCheckIn(String message){
        try {
            Document  dom=DocumentHelper.parseText(message);
            Element root = dom.getRootElement();

            String phone =xmlTest(root,"Ex");
            String name = xmlTest(root,"GName");
            String type = GuestMessage.CheckIn_Type;

            GuestMessage guestMessage = new GuestMessage(phone,name,type);
            if(!ucsSocketService.sendMessage(guestMessage.toUcs())){
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,"信息发送失败","解析Fcs的checkIn发送到UCS失败");
                pmsLogRecord.setContent(guestMessage.toUcs());
                pmsLogRecordService.addLogRecord(pmsLogRecord);
            }
            /*try {
                String swapFlag = null;
                //1.SwapFlag存在与CheckIn和CheckOut指令中。2.SwapFlag在同步的信息中会存在
               swapFlag = xmlTest(root,"SwapFlag");
               pmsHotelGuest.setSwapFlag(swapFlag);
            } catch (NullPointerException e) {

            }
            String allName = xmlTest(root,"GName");
            String firstName = xmlTest(root,"GFName");//firstName
            String lastName = xmlTest(root,"GLName");//LastName*/
        } catch (Exception e) {
            loggerQueue.error("解析Fcs发送的checkin/InfoChange失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            logger.error("解析Fcs发送的checkin/InfoChange失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(e,"解析Fcs发送的checkin/InfoChange失败",SysConf.PMS_HOTELNAME);
            pmsLogRecord.setContent(message);
            pmsLogRecordService.addLogRecord(pmsLogRecord);
        }
    }

    public void XmlToCheckOut(String message){
        try {

            Document dom=DocumentHelper.parseText(message);
            Element root = dom.getRootElement();
            String phone =xmlTest(root,"Ex");
            String name = null;
            String type = GuestMessage.CheckOut_Type;
            GuestMessage guestMessage = new GuestMessage(phone,name,type);

            if(!ucsSocketService.sendMessage(guestMessage.toUcs())){
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,"信息发送失败","解析Fcs的checkOut发送到UCS失败");
                pmsLogRecord.setContent(guestMessage.toUcs());
                pmsLogRecordService.addLogRecord(pmsLogRecord);
            }
           // String swapFlag = null;
            /*try {
                //1.SwapFlag存在与CheckIn和CheckOut指令中。2.SwapFlag在同步的信息中会存在
               swapFlag = xmlTest(root,"SwapFlag");
               pmsHotelGuest.setSwapFlag(swapFlag);
            } catch (NullPointerException e) {
            }*/
        } catch (Exception e) {
            loggerQueue.error("解析Fcs发送的checkout失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            logger.error("解析Fcs发送的checkout失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(e,"解析Fcs发送的checkout失败",SysConf.PMS_HOTELNAME);
            pmsLogRecord.setContent(message);
            pmsLogRecordService.addLogRecord(pmsLogRecord);
        }
    }

    /**
     *   判断是否是中间件处理信息类型
     * @param message
     * @return
     */
    public boolean messageAcceptType(String message){
        boolean acceptResult = false;
        if(message== null || "".equals(message)) {
            acceptResult = false;
        }
        for(String type :acceptList){
            if(message.contains(type)){
                acceptResult = true;
                break;
            }
        }
        return acceptResult;

    }



    /**
     * 检测解析的信息中是否存在对应标签
     * @param root
     * @param key
     * @return
     * @throws Exception
     */
    public String xmlTest(Element root,String key)throws NullPointerException{
        String value = null;
        try {
             value =  root.element(key).getText();
        } catch (NullPointerException e) {
           throw  new NullPointerException("接收的信息中没有"+key+"标签");
        }
        return value;
    }



    public void XmlToDoNotDisturb(String message){
        try {
            Document dom=DocumentHelper.parseText(message);
            Element root = dom.getRootElement();
            String phone = xmlTest(root,"Ex");
            String dndType = xmlTest(root,"DND");
            if("On".equals(dndType)){
                dndType = Dnd.DND_ON;
            }else if("Off".equals(dndType)){
                dndType = Dnd.DND_OFF;
            }
            Dnd dnd = new Dnd(phone,dndType);
            if(! ucsSocketService.sendMessage(dnd.toUsc())){
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,"信息发送失败","解析Fcs的免打扰发送到UCS失败");
                pmsLogRecord.setContent(dnd.toUsc());
                pmsLogRecordService.addLogRecord(pmsLogRecord);
            }

        } catch (Exception e) {
            loggerQueue.error("解析Fcs发送的DND失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            logger.error("解析Fcs发送的DND失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(e,"解析fcs发送的DND失败",SysConf.PMS_HOTELNAME);
            pmsLogRecord.setContent(message);
            pmsLogRecordService.addLogRecord(pmsLogRecord);
        }
    }
    public void XmlToMessageWaiting(String message){
        try {
            Document dom=DocumentHelper.parseText(message);
            Element root = dom.getRootElement();

            String phone = xmlTest(root,"Ex");
            String messageLampType = xmlTest(root,"MWL");
            if("On".equals(messageLampType)){
                messageLampType = MessageLamp.MessageLamp_ON;
            }else if("Off".equals(messageLampType)){
                messageLampType = MessageLamp.MessageLamp_OFF;
            }
            MessageLamp messageLamp = new MessageLamp(phone,messageLampType);

            if(!ucsSocketService.sendMessage(messageLamp.toUsc())){
                PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,"信息发送失败","解析Fcs的留言灯发送到UCS失败");
                pmsLogRecord.setContent(messageLamp.toUsc());
                pmsLogRecordService.addLogRecord(pmsLogRecord);
            }
        } catch (Exception e) {
            loggerQueue.error("解析Fcs发送的留言灯失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            logger.error("解析Fcs发送的留言灯信息失败，解析的信息:"+message+",原因:"+Msg.getExceptionDetail(e));
            PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(e,"解析fcs发送的留言灯信息失败",SysConf.PMS_HOTELNAME);
            pmsLogRecord.setContent(message);
            pmsLogRecordService.addLogRecord(pmsLogRecord);
        }
    }


    public static void main(String[] args) {
        String message = "29/10/2018 15:38:36";
        Date date = DateUtil.StringToDate(message,"dd/MM/yyy HH:mm:ss");
        System.out.println(DateUtil.DateToString(date));
        //    CloudMessage cloudMessage = new MessageDeal().xmlToCloudMessage(message);
        //    System.out.println(cloudMessage.getUuid());
    }
}
