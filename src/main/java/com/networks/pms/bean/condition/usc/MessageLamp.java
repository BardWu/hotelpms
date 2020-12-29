package com.networks.pms.bean.condition.usc;

import com.networks.pms.service.ucs.UcsCommon;

/**
 * @program: hotelpms
 * @description: 留言灯
 * @author: wh
 * @create: 2020-04-16 15:36
 */
public class MessageLamp {

    public static String MessageLamp_ON="ON";
    public static String MessageLamp_OFF="OF";
    private String begin = "SE ST";
    private String phone;//电话号码
    private String mw = "MW";
    private String messageLampType ;

    public MessageLamp(String phone, String messageLampType) {
        this.phone = phone;
        this.messageLampType = messageLampType;
    }

    public String toUsc(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.begin);
        sb.append(" ");
        sb.append(UcsCommon.getUCSFormatMessage(phone,8,0));
        sb.append(" ");
        sb.append(mw);
        sb.append(" ");
        sb.append(this.messageLampType);
        return sb.toString();
    }

    public static void main(String[] args) {
        MessageLamp dnd = new MessageLamp("1001",MessageLamp.MessageLamp_OFF);
        System.out.println(dnd.toUsc());
    }
}
