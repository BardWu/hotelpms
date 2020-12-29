package com.networks.pms.bean.condition.usc;

import com.networks.pms.service.ucs.UcsCommon;

/**
 * @program: hotelpms
 * @description: 免打扰
 * @author: wh
 * @create: 2020-04-16 15:36
 */
public class Dnd {

    public static String DND_ON="ON";
    public static String DND_OFF="OF";
    private String begin = "SE ST";
    private String phone;//电话号码
    private String dn = "DN";
    private String dndType ;

    public Dnd(String phone,  String dndType) {
        this.phone = phone;
        this.dndType = dndType;
    }

    public String toUsc(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.begin);
        sb.append(" ");
        sb.append(UcsCommon.getUCSFormatMessage(phone,8,0));
        sb.append(" ");
        sb.append(dn);
        sb.append(" ");
        sb.append(this.dndType);
        return sb.toString();
    }

    public static void main(String[] args) {
        Dnd dnd = new Dnd("1001",Dnd.DND_OFF);
        System.out.println(dnd.toUsc());
    }
}
