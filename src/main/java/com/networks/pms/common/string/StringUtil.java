package com.networks.pms.common.string;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author liuwx
 *
 */
public class StringUtil {
	
	private StringUtil() {
		// TODO Auto-generated constructor stub
	}
	
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
	 * 验证字符串长度是否操作设置长度
	 * @param str
	 * @param number
	 * @return
	 */
	public static boolean strLengthVer(String str,int number){
		
		if(str != null && !"".equals(str)){
			
			if(str.length() > number){
				
				return true;
			}else{
				
				return false;
			}
		}else{
			return false;
		}
		
		
		
	}
	
	/**
	 * 判断一个字符串是否为Int
	 * @param str
	 * @return
	 */
	public static boolean isInt(String str){
		try{
			Integer.parseInt(str);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	/**
	 * 根据字符 截取字符串
	 * @param charStr 从哪个字符开始截取
	 * @param str     原字符串
	 * @return
	 */
	public static String subStrByLastChar(String charStr,String str){
		
		String subStr = "";
		if(str!= null && !str.equals("")){
			
			subStr = str.substring(str.lastIndexOf(charStr)+1, str.length());
		}
		return subStr;
	}
	/**
	 * String 类型转换成Int 型 
	 * @param str        要转换的字符串
	 * @param defaultInt 给字符串的默认值 如果该字符串不是数字就使用这个默认值
	 * @return
	 */
	public static int strIsParseInt(String str,int defaultInt){
		
		int strIsInt = defaultInt;
		
		if(str != null && !str.equals("") && isInt(str)){
			strIsInt = Integer.parseInt(str);
		}
		
		return strIsInt;
	} 
	
	/**
	 * 把 InputStream 转成String 字符串
	 * @param is
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String convertStreamToString(InputStream is){  
		
		  StringBuilder sb = new StringBuilder(); 
		  try { 
			  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));   

	        String line = null;    

	            while ((line = reader.readLine()) != null) {    

	                sb.append(line + "\n");    

	            }    

	        } catch (IOException e) {    
	        	
	        	e.printStackTrace();

	        } finally {    

	            try {    

	                is.close();    

	            } catch (IOException e) {    

	            	e.printStackTrace();
	            }    

	        }    

	        return sb.toString();    

	  } 
	
	/**
	 * 手机号码校验
	 *@projectName:hsms
	 *@return:boolean 
	 *@createTime:2015-1-4
	 *@author:huaxk
	 *@param str 
	 */
	public static boolean checkPhoneNumber(String str){
		
		boolean flag = false;
		
		if(str != null && !"".equals(str)){
			
//			Pattern pattern = Pattern.compile("^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$");
			
			Pattern pattern = Pattern.compile("^1\\d{10}$");
			
			
			flag = pattern.matcher(str).matches();
		}
		
		return flag;
	}
	
	/**
	 * 数字校验
	 *@projectName:hsms
	 *@return:boolean 
	 *@createTime:2015-1-4
	 *@author:huaxk
	 */
	public static boolean checkIsNumber(String str){
		
		boolean flag = false;
		
		if(str != null && !"".equals(str)){
			
			Pattern pattern = Pattern.compile("[0-9]*");
			
			flag = pattern.matcher(str).matches();
		}
		
		return flag;
		
	}
	
	/**
	 * 大于0的正整数校验
	 * @projectName hsms
	 * @return boolean 
	 */
	public static boolean checkPositiveInteger(String str){
		
		boolean flag = false;
		
		String reg = "^\\+?[1-9]\\d*$";
		
		if(str != null && !"".equals(str)){
			
			Pattern pattern = Pattern.compile(reg);
			
			flag = pattern.matcher(str).matches();
		}
		
		return flag;
	}
	
	/**
	 * 邮箱校验
	 *@projectName:hsms
	 *@return:boolean 
	 *@createTime:2015-1-4
	 *@author:huaxk
	 *@param str
	 */
	public static boolean checkEmail(String str){
		
		boolean flag = false;
		
		if(str != null && !"".equals(str)){
			
			Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
			
			flag = pattern.matcher(str).matches();
		}
		
		return flag;
	}
	
	/**
	 * 中文校验
	 *@projectName:hsms
	 *@return:boolean 
	 *@createTime:2015-1-27
	 *@author:huaxk
	 */
	public static boolean checkChina(String str){
		
		boolean flag = false;
		
		if(str != null && !"".equals(str)){
			String reg = "[\u4e00-\u9fa5]+";
			Pattern pattern = Pattern.compile(reg);
			Matcher result=pattern.matcher(str);
			flag = result.find();
		}
		
		return flag;
	}
	
	/**
	 * 获取Ip地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	
	 /**
	    * 判断字符串是否是double类型	
	    * @param str
	    * @return
	    */
	   public static boolean strIsDouble(String str){
		   
		      boolean isDouble = false;
		   try{
			   
			   Double.parseDouble(str);
			   isDouble = true;
			   
			 }catch(Exception e){
				 isDouble = false;
			 }
		     
		   return isDouble;
		   
	   }

	/**
	 * 将String是否包含的信息删除
	 * @param msg	String
	 * @param contains	 包含的信息
	 * @return
	 */
	   public static String containsAndRemove(String msg,String contains){
	   		if(StringUtil.isNull(msg)){
	   			return null;
			}
			String newMsg =null;
		    newMsg = msg.substring(0,2);
			if(contains.equals(newMsg)){
				newMsg = msg.substring(2);
			}else{
				newMsg = null;
			}
			return newMsg;
	   }
	   
	public static void main(String[] args) {
		
		/*boolean falg = checkChina("zhangsan");
		
		System.out.println(falg);*/
		String msg = "BS20110";
		System.out.println(StringUtil.containsAndRemove(msg,"BS"));
	}
	
	
	
}
