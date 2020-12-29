package com.networks.pms.service.com;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.MessagePoint;
import com.networks.pms.common.util.Msg;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.*;

import static com.networks.pms.service.com.Base64.decryptBASE64;

/**
 * @program: hotelpms
 * @description: 发送邮件工具类
 * @author: Bardwu
 * @create: 2018-12-12 16:50
 **/
public class MailUtil {
    private static Logger logger = Logger.getLogger(MailUtil.class);
    private static  Properties props;
    private boolean isSend = true;//是否需要发送
    private static String mailSenderAccount;//发送者账号
    private static String mailSenderPassword;//发送者密码
    private static List<String> internetAccepter;//接收者账号
    private static Map<String,Integer> mailSendNumber; //key 日期  value:已发送邮件的个数
    private static int sendNumber = 0;//每天发送邮件的个数
    static{
        props = new Properties();
        // 开启debug调试
        //    props.setProperty("mail.debug", "true");  //false
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.office365.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        mailSendNumber = new HashMap<String,Integer>();

        mailSenderAccount = SysConf.MAIL_SENDER_ACCOUNT;
        mailSenderPassword = SysConf.MAIL_SENDER_PASSWORD;
        String accepter = SysConf.MAIL_ACCEPTER_ACCOUNTS;

        if(!StringUtil.isNull(accepter)){
            String []accepters =  accepter.split(",");
            internetAccepter = Arrays.asList(accepters);
        }
        logger.info("mailSenderAccount:"+mailSenderAccount+",mailSenderPassword:"+mailSenderPassword+"mailAccepter:"+accepter);
    }



    /**
     * 只有链接状态改变的情况下才发送
     * @param connectStatus
     * @param sendMessage
     * @param hotelName
     * @return
     */
    public  boolean defaultSendForConnect(boolean connectStatus,String sendMessage,String hotelName){
        if(isSend != connectStatus){
            isSend = connectStatus;
            return  defaultSend(sendMessage,hotelName);
        }else {
            return false;
        }
    }

    public static boolean defaultSend(String sendMessage,String hotelName){
        boolean result = false;
        if(isSendMail( hotelName)){
            result =  send(sendMessage,hotelName);
        }
       return result;
    }

    public static boolean send(String sendMessage,String hotelName){
        if(StringUtil.isNull(mailSenderAccount) || StringUtil.isNull(mailSenderPassword) ){
            logger.info("未设置发件人信息，邮件发送失败");
            return true;
        }
        try {
            String password = Base64.decryptBASE64(mailSenderPassword);
            return sendMessage(hotelName+"酒店中间件信息",sendMessage,mailSenderAccount, password,internetAccepter,hotelName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("邮件密码解密失败,原因:"+Msg.getExceptionDetail(e));
        }
        return false;
    }
    //判断当天是否能发送邮件
    public static boolean isSendMail(String hotelName){
        String dataTime = DateUtil.getPresentTime("yyyy-MM-dd");
        boolean result = false;
        synchronized (MailUtil.class){
            if(!mailSendNumber.containsKey(dataTime)){
                mailSendNumber.clear();
                sendNumber = 0;
            }
            mailSendNumber.put(dataTime,++sendNumber);

        }
        if(sendNumber<Integer.parseInt(SysConf.MAIL_NUMBER_FOR_DAY)){
            result = true;
        }else if(sendNumber == Integer.parseInt(SysConf.MAIL_NUMBER_FOR_DAY)){
            send("因今天系统发送邮件频率较高，该邮件是系统今天发送的最后一条邮件。",hotelName);
        }else{
            result = false;
        }

        return result;
    }


    /**
     * 发送邮件
     * @param subject 邮件主题
     * @param context 邮件内容
     * @param userName 发送邮件的账号
     * @param password 发送邮件的密码
     * @param accepter 接受邮件的账号
     * @return
     */
    public static boolean sendMessage(String subject,String context,String userName,String password,List<String> accepter,String hotelName){
        boolean isSend = false;
        try{
            Session session =  Session.getInstance(props);
            // 创建邮件对象
            Message msg = new MimeMessage(session);
            msg.setSubject(MimeUtility.encodeText(subject,"utf-8","B"));
            // 设置邮件内容
            msg.setContent(initError(context,hotelName), "text/html;charset=utf-8");

            //       msg.setText(context);
            // 设置发件人
            msg.setFrom(new InternetAddress(userName));

            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect(userName, password);
            // 发送邮件
            transport.sendMessage(msg, initAddress(accepter));
            // 关闭连接
            transport.close();
            isSend = true;
        }catch( Exception e ){
            logger.error("邮件发送失败,原因:"+ Msg.getExceptionDetail(e));
        }

        return isSend;
    }
    private static Address[] initAddress(List<String> internetAccepter){
        InternetAddress internetAddress = null;
        List<InternetAddress> internetAddresses = new ArrayList<InternetAddress>();
        for(String accepter:internetAccepter){
            try {
                internetAddress = new InternetAddress(accepter);
            } catch (AddressException e) {
            }
            internetAddresses.add(internetAddress);
        }
        return internetAddresses.toArray(new Address[internetAddresses.size()]);
    }

    public static String initError(String error,String hotelName){
        String init = "<h2>您好!</h2></br> <h2> &nbsp&nbsp&nbsp 名称:"+hotelName+"。邮件产生原因 <xmp> "+error+"</xmp></h2>" +
                "</br> <h3> &nbsp&nbsp&nbsp 请注意:已上内容为中间件系统发出请勿回复，谢谢!</h3>";
        return init;
    }
    public static void main(String[] args) {
        //  MailUtil.defaultSend("xxx");
    }

    /*发送公司邮箱模板*/
  /*  private  void test() {
        try{
            props = new Properties();
            // 开启debug调试
            //    props.setProperty("mail.debug", "true");  //false
            // 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");
            // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.office365.com");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");

            Session session =  Session.getInstance(props);
            // 创建邮件对象
            Message msg = new MimeMessage(session);
            msg.setSubject("subject");
            // 设置邮件内容
            msg.setText("text");
            // 设置发件人
            msg.setFrom(new InternetAddress("bard.wu@planetone-asia.com"));

            Transport transport = session.getTransport();
            // 连接邮件服务器
            transport.connect("bard.wu@planetone-asia.com", "Bw12345678");
            // 发送邮件
            transport.sendMessage(msg, new Address[]{new InternetAddress("bard.wu@planetone-asia.com")});
            // 关闭连接
            transport.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }*/

}
