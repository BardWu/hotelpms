package com.networks.pms.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieUtil {

    private static int time = 3600*24;

    /**
     * 写入cookie
     * @param key
     * @param value
     * @param response
     */
    public static void setCookie(String key,String value,HttpServletResponse response){

        Cookie cookie = new Cookie(key,value);
        cookie.setMaxAge(time);//设置其生命周期
        cookie.setPath("/");
        //cookie.setDomain("127.0.0.1");
        response.addCookie(cookie);

    }

    /**
     * 根据key获取cookie信息
     * @param key
     * @param request
     * @return
     */
    public static String getCookie(String key,HttpServletRequest request){

        String value = null;
        Cookie[] cookies = request.getCookies(); //获取cookie数组
        HttpSession session = request.getSession();
        if(cookies == null || cookies.length == 0){

            return value;
        }
        for(Cookie cookie:cookies){//遍历cookie数组

            if(cookie.getName().equals(key)){
                value = cookie.getValue();
                break;
            }
        }
        return value;
    }

    public static void deleteCookie(String key,HttpServletRequest request,HttpServletResponse response){

        Cookie cookie = new Cookie(key,null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        //cookie.setDomain("127.0.0.1");
        response.addCookie(cookie);

    }



}
