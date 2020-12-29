package com.networks.pms.common.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class Print {
	
	/**
	 * 向前台打印JSON对象
	 * @param respone
	 * @param jsonStr
	 */
	public static void printJSON(HttpServletResponse respone,String jsonStr){
		printJSON(respone,jsonStr);
	}
	
	/**
	 * 向前台打印JSON对象
	 * @param response
	 * @param jsonObject
	 */
	public static void printJSON(HttpServletResponse response,Object jsonObject){
		PrintWriter writer = null;
		try {
			response.setContentType("application/json; charset=UTF-8");
			writer = response.getWriter();
			writer.print(jsonObject);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}
	
	/**
	 * 向前台打印文本信息
	 * @param response
	 * @param textStr
	 */
	public static void printText(HttpServletResponse response,String textStr){
		PrintWriter writer = null;
		try {
			response.setContentType("text/html; charset=UTF-8");
			writer = response.getWriter();
			writer.print(textStr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

}
