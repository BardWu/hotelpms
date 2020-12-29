package com.networks.pms.service.com;

import com.networks.pms.common.string.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysConf {

    //酒店名称
    public static String PMS_HOTELNAME;
    public static String PMS_METHOD;
    //hotelpms loginName
    public static String PMS_LOGINNAME;
    //hotelpms passworld
    public static String PMS_PASSWORD;
    public static String INTERFACE_TIMER_TIME;
    //pms.hotelMaxNumberOfPhoneNumbers
    public static String PMS_HOTEL_MAX_NUMBER_OF_PHONE_NUMBERS;

    //***************邮件配置信息
    //pms.mailSenderAccount
    public static String MAIL_SENDER_ACCOUNT;
    // pms.mailSenderPassword
    public static String MAIL_SENDER_PASSWORD;
    // pms.mailAccepterAccounts
    public static String MAIL_ACCEPTER_ACCOUNTS;
    //pms.mailNumberForDay
    public static String MAIL_NUMBER_FOR_DAY;
    //pms.errorNumberForHourMail
    public static String ERROR_NUMBER_FOR_HOUR_MAIL;

    //******************fcs 连接的信息
    //fcs service ip
    public static String PMS_FCSSERVICEIP;
    //fcs service port
    public static String PMS_FCSPORT;
    //fcs.pid
    public static String FCS_PID;
    //
    public static String FCS_RESPONSE_WAITING_TIME;
    //**************UCS 连接的信息
    public static String UCS_SERVICE_IP ;
    public static String UCS_SERVICE_PORT;




    @Value("${pms.method}")
    public void setPmsMethod(String method){
        PMS_METHOD = method;
    }

    //@Value("${pms.hotelId}")
    @Value("${pms.hotelName}")
    public void setPmsHotelName(String hotelName) {
        PMS_HOTELNAME = hotelName;
    }

    @Value("${pms.fcsServiceIP}")
    public void setPmsFcsServiceIP(String pmsFcsServiceIP) {
        SysConf.PMS_FCSSERVICEIP = pmsFcsServiceIP;
    }

    @Value("${pms.fcsPort}")
    public void setPmsFcsPort(String pmsFcsPort) {
        SysConf.PMS_FCSPORT = pmsFcsPort;
    }
    @Value("${pms.loginName}")
    public void setPmsLoginName(String pmsLoginName){
        SysConf.PMS_LOGINNAME = pmsLoginName;
    }
    @Value("${pms.password}")
    public  void setPmsPassword(String pmsPassword) {
        SysConf.PMS_PASSWORD = pmsPassword;
    }

    @Value("${pms.hotelMaxNumberOfPhoneNumbers}")
    public  void setPmsHotelMaxNumberOfPhoneNumbers(String hotelMaxNumberOfPhoneNumbers) {
        SysConf.PMS_HOTEL_MAX_NUMBER_OF_PHONE_NUMBERS = hotelMaxNumberOfPhoneNumbers;
    }


    @Value("${fcs.Pid}")
    public  void setFcsPid(String fcsPid) {
        FCS_PID = fcsPid;
    }
    @Value("${fcs.responseWaitingTime}")
    public  void setFcsResponseWaitingTime(String responseWaitingTime) {
        FCS_RESPONSE_WAITING_TIME = responseWaitingTime;
    }

   @Value("${UCS.serviceIp}")
    public  void setUCSServiceIp(String UCSServiceIp) {
        UCS_SERVICE_IP = UCSServiceIp;
    }
    @Value("${UCS.servicePort}")
    public  void setTlServicePort(String servicePort) {
        UCS_SERVICE_PORT = servicePort;
    }

    @Value("${pms.interfaceTimerTime}")
    public void setInterfaceFaceTimerTime(String interfaceFaceTimerTime){
        INTERFACE_TIMER_TIME = interfaceFaceTimerTime;
    }

    @Value("${pms.mailSenderAccount}")
    public void setMailSenderAccount(String mailSenderAccount){
        MAIL_SENDER_ACCOUNT = mailSenderAccount;
    }
    @Value("${pms.mailSenderPassword}")
    public void setMailSenderPassword(String mailSenderPassword){
        MAIL_SENDER_PASSWORD = mailSenderPassword;
    }
    @Value("${pms.mailAccepterAccounts}")
    public void setMailAccepterAccounts(String mailAccepterAccounts){
        MAIL_ACCEPTER_ACCOUNTS = mailAccepterAccounts;
    }

    @Value("${pms.mailNumberForDay}")
    public void setMailNumberForDay(String mailNumberForDay){
        MAIL_NUMBER_FOR_DAY = mailNumberForDay;
    }

    @Value("${pms.errorNumberForHourMail}")
    public void setErrorNumberForHourMail(String errorNumberForHourMail){
        ERROR_NUMBER_FOR_HOUR_MAIL = errorNumberForHourMail;
    }
}
