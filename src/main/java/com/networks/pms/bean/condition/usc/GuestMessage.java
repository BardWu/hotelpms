package com.networks.pms.bean.condition.usc;

import com.networks.pms.service.ucs.UcsCommon;

/**
 * @program: hotelpms
 * @description: 瑞斯康达 UCS checkIn and checkout
 * @author: wh
 * @create: 2020-04-16 14:27
 */
public class GuestMessage {

    public static String CheckIn_Type = "CH IN";
    public static String CheckOut_Type = "CH OU";
    private String begin = "SE ST";
    private String phone;//电话号码
    private String name;//客人姓名
    private String type ;

    public GuestMessage(String phone,String name ,String  type){
        this.phone = phone;
        this.name = name;
        this.type = type;
    }

    public String toUcs() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.begin);
        sb.append(" ");
        sb.append(UcsCommon.getUCSFormatMessage(phone,8,0));
        sb.append(" ");
        sb.append(type);
        sb.append(" ");
        sb.append(UcsCommon.getUCSFormatMessage("\""+name+"\"",30,1));
       return sb.toString();
    }


    @Override
    public String toString() {
        return "GuestMessage{" +
                "begin='" + begin + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static void main(String[] args) {
        GuestMessage guestMessage = new GuestMessage("2023","zhangsan",GuestMessage.CheckIn_Type);
        System.out.println(guestMessage.toUcs());
    }


}
