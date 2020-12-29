package com.networks.pms.bean.condition.usc;

import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.Msg;
import com.networks.pms.service.com.SysConf;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: hotelpms
 * @description: 话单
 * @author: Bardwu
 * @create: 2019-05-30 09:23
 **/
public class CallCharges {

    private static String outGoingCall = "CC";//内线打外线
    private static String internalCall = "IC";//内线打内线

    private String ex;
    private String rm;
    private String pid;
    private String postType;
    private String tel;
    private String dur;
    private String trk;
    private String auth;
    private String carrier;
    private String transDati;
    private String sysDati;
    private String guid;


    public CallCharges() {
        this.guid = DateUtil.getUUID();
        sysDati = DateUtil.DateToString(new Date(),"yyyy/MM/dd HH:mm:ss");
        pid = SysConf.FCS_PID;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        //‘CC’ – outgoing call, ‘IC’ – internal call ‘IE’ – incoming call
        // Out  Lan in
      /*  if("Out".equals(postType)){
            postType = "CC";
        }else if("Lan".equals(postType)){
            postType="IC";
        }else if("In".equals(postType)){
            postType="IE";
        }*/
        this.postType = postType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getDur() {
        return dur;
    }

    public void setDur(String dur) {
        /*String[] durs = dur.split(":");
        int h = Integer.parseInt(durs[0]);
        int m = Integer.parseInt(durs[1]);
        int s = Integer.parseInt(durs[2]);
        this.dur =String.valueOf(h*60*60+m*60+s) ;*/
        this.dur = dur;
    }

    public String getTrk() {
        return trk;
    }

    public void setTrk(String trk) {
        this.trk = trk;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTransDati() {
        return transDati;
    }

    public void setTransDati(String transDati) {
        this.transDati = transDati;
    }

    public String getSysDati() {
        return sysDati;
    }

    public void setSysDati(String sysDati) {
        this.sysDati = sysDati;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return "CallCharges{" +
                "ex='" + ex + '\'' +
                ", rm='" + rm + '\'' +
                ", pid='" + pid + '\'' +
                ", postType='" + postType + '\'' +
                ", tel='" + tel + '\'' +
                ", dur='" + dur + '\'' +
                ", trk='" + trk + '\'' +
                ", auth='" + auth + '\'' +
                ", carrier='" + carrier + '\'' +
                ", transDati='" + transDati + '\'' +
                ", sysDati='" + sysDati + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }
    public String toFcs(){
        return "<CallCharge>" +
                Msg.StringToXml("Ex",ex)+//主叫
                Msg.StringToXml("PId",pid)+
                Msg.StringToXml("PostType",postType)+
                Msg.StringToXml("Tel",tel)+
                Msg.StringToXml("Dur",dur)+
                Msg.StringToXml("TransDaTi",transDati)+
                Msg.StringToXml("SysDaTi",sysDati)+
                Msg.StringToXml("GUID",guid)+
                "</CallCharge>";
    }

    public static CallCharges getCallChargesByPhoneCharge(PhoneCharge phoneCharge){
        CallCharges callCharges = new CallCharges();
        callCharges.setEx(phoneCharge.getDn());
        String called = phoneCharge.getCalled();
        //被叫的号码长度 大于 酒店话机号码最大的个数 表示是外线
        if(SysConf.PMS_HOTEL_MAX_NUMBER_OF_PHONE_NUMBERS.length()>called.length()){
            callCharges.setPostType(CallCharges.outGoingCall);
        }else{
            callCharges.setPostType(CallCharges.internalCall);
        }
        callCharges.setTel(called);
        callCharges.setDur(phoneCharge.getInterval());
        callCharges.setTransDati(phoneCharge.getEndTime());
        return callCharges;
    }

    public static void main(String[] args) {
        SimpleDateFormat s = new SimpleDateFormat("mm:");
    }
}
