package com.networks.pms.service.ucs;

import com.networks.pms.common.util.StrUtil;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;

/**
 * @program: hotelpms
 * @description: ucs 公用方法
 * @author: wh
 * @create: 2020-04-16 14:37
 */
public class UcsCommon {
    private static Logger logger = Logger.getLogger(UcsCommon.class);
    private static  LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    /**
     * 转换ucs信息
     * @param message 原始信息
     * @param length 转换后的长度
     * @param type  0：右对齐 1：左对齐
     * @return 转换后的信息（将message 根据 type 转换成特定的length,长度不足用空格表示）
     */
    public static String getUCSFormatMessage(String message ,int length,int type){

        if(StrUtil.isNull(message)){
            message = "";
        }
        if(message.length() >length){
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String split=" ";
        int remainLength = length - message.length();//剩余的长度
        if(type == 0){//右对齐
            for(int i = 0; i<remainLength; i++){
                sb.append(split);
            }
            sb.append(message);
        }else{
            sb.append(message);
            for(int i = 0; i<remainLength; i++){
                sb.append(split);
            }
        }

        String newMessage = sb.toString();
        String info = "原始信息:"+message+",转换的长度："+length+", 对齐方式："+(type==0?"右对齐":"左对齐")+",转换后的信息："+newMessage;
        logger.debug(info);
        System.out.println(info);
        return newMessage;
    }
}
