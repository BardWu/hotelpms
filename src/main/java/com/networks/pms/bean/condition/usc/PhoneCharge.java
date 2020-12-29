package com.networks.pms.bean.condition.usc;

import com.networks.pms.common.string.StringUtil;

/**
 * @program: hotelpms
 * @description: 话单
 * @author: wh
 * @create: 2020-04-16 16:00
 */
public class PhoneCharge {

    private String begin;
    private String dn;//主叫号码
    private String called;//被叫号码
    private String endTime;//通话结束时间
    private String interval;//耗时
    private String money;//费用

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getCalled() {
        return called;
    }

    public void setCalled(String called) {
        this.called = called;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 通过ucs charge 获取 PhoneCharge对象
     * @param UscCharge
     * @return
     * ST BI     2023                     2024 2020-04-24,12:26:40    10
     */
    public static PhoneCharge getByUscCharge(String UscCharge){
        if(StringUtil.isNull(UscCharge)|| UscCharge.length() != 72){
            return null;
        }

        PhoneCharge phoneCharge = new PhoneCharge();
        phoneCharge.setBegin(UscCharge.substring(0,5).trim());
        phoneCharge.setDn(UscCharge.substring(6,14).trim());
        phoneCharge.setCalled(UscCharge.substring(15,39).trim());
        String endTime = UscCharge.substring(40,59).trim();
        if (!StringUtil.isNull(endTime)){
            endTime = endTime.replace(","," ");
        }
        phoneCharge.setEndTime(endTime);
        phoneCharge.setInterval(UscCharge.substring(60,65).trim());
        phoneCharge.setMoney(UscCharge.substring(66,72).trim());
        return phoneCharge;
    }



    @Override
    public String toString() {
        return "PhoneCharge{" +
                "begin='" + begin + '\'' +
                ", dn='" + dn + '\'' +
                ", called='" + called + '\'' +
                ", endTime='" + endTime + '\'' +
                ", interval='" + interval + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String message ="ST BI 12345678 123456781234567812345678 0123456789123456789 12345 123456";
        message ="ST BI     2110              01063901000 2015-01-15,12:30:00  6000  12000";
        message="ST BI     2023                     2024 2020-04-24,12:26:40    10       ";
        System.out.println(message.length());
        PhoneCharge phoneCharge = PhoneCharge.getByUscCharge(message);
        System.out.println(phoneCharge);
        System.out.println(CallCharges.getCallChargesByPhoneCharge(phoneCharge).toFcs());;
    }
}
