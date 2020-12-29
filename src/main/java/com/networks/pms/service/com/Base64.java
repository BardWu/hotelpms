package com.networks.pms.service.com;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @program: hotelpms
 * @description: 邮件账号 加密、解密
 * @author: Bardwu
 * @create: 2019-07-15 14:34
 **/
public class Base64 {

    /**   BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64(String key) throws Exception {
        return new String((new BASE64Decoder()).decodeBuffer(key));
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static void main(String[] args) throws Exception{
        String msg = encryptBASE64("Bw12345678".getBytes());
        System.out.println("加密后:"+msg);

      String s = decryptBASE64(msg);
        System.out.println("解密后:"+s);
    }

}
