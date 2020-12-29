package com.networks.pms.common.util;

/**
 * @program: hotelpms
 * @description: Fcs发送给TL相关点
 * @author: Bardwu
 * @create: 2019-05-27 16:50
 **/
public class FcsSendTLPoint {


    private static boolean isSendToTL  ;//是否发送给通利
    private static boolean isSendToFcs ;//是否发送给Fcs
    private static int time =1;

    private static Object isSendToTLSyc = new Object();
    private static Object isSendToFcsSyc = new Object();



    public static boolean getIsSendToTL() {
        synchronized (isSendToTLSyc) {
            return isSendToTL;
        }
    }

    public static void sendToTL() {
        synchronized (isSendToTLSyc){
            isSendToTL = true;
        }
    }
    public static void notSendToTL() {
        synchronized (isSendToTLSyc){
            isSendToTL = false;
        }
    }

    public static boolean getIsSendToFcs() {
        synchronized (isSendToFcsSyc) {
            return isSendToFcs;
        }
    }

    public static void sendToFcs() {
        synchronized (isSendToFcsSyc){
            isSendToFcs = true;
        }
    }
    public static void notSendToFcs() {
        synchronized (isSendToFcsSyc){
            isSendToFcs = false;
        }
    }
}
