package com.networks.pms.common.util;

import java.io.BufferedReader;

public class StrUtil {

    /**
     * 判断一个字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNull(String str){

        if(str == null || str.equals("")){

            return true;
        }else{

            return false;
        }

    }
    
    /**
     * 把object对象转换成string串
     * @param str
     * @return
     */
    public static String objToStrValue(Object str){

        if(str == null || str.equals("")){

            return "";
        }else{

            return str.toString();
        }

    }
    
    /**
     * 验证字符串长度是否操作设置长度
     * @param str
     * @param number
     * @return
     */
    public static boolean strLengthVer(String str,int number){

        if(str!=null && !str.equals("")){
            if(str.length() > number){

                return true;
            }else{

                return false;
            }
        }

        return false;

    }
    
    /**
     * 去掉字符串中所有的空格
     * @param str
     * @return
     */
    public static String strRemoveEmpty(String str){
    	
    	String str1 = str.replaceAll(" ", ""); 
    
    	return str1;
    }
   /**
    *  截取证件的后4位
    * @param str
    * @return
    */
   public static String getCardLast4(String str){
    	
    	if(str == null || str.equals("")){
    		
    		return str;
    	}
    	
    	if(str.length() <= 4){
    		
    		return str;
    	}
    	
    	String str1 = str.substring(str.length()-4, str.length());
    	
    	return str1;
    }
   
   public static String getBodyString(BufferedReader br){

    	String inputLine;
       String str = "";
	    try {
	      while ((inputLine = br.readLine()) != null) {
	         str += inputLine;
	      }
	      br.close();
	    } catch (Exception e) {
	      System.out.println("IOException: " + e);
	    }
	    
	    return str;
    }
   /**
    * 当前时间+4位随机数+用户Id
    * @param userId
    * @return
    */
   public static String getOrderNumber(int userId){
		
		String nowDate = DateUtil.getPresentTime("yyyyMMddhhmmss");
		int random = (int)((Math.random()*9+1)*1000);
		
		String orderNumber = nowDate+random+userId;
		
		return orderNumber;
   }
    

}
