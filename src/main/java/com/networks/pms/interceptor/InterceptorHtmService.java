package com.networks.pms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.networks.pms.common.util.CommandReponse;
import com.networks.pms.common.util.Print;
import com.networks.pms.service.com.CloudRequestInterceptor;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;


public class InterceptorHtmService implements HandlerInterceptor {
    Logger logger = Logger.getLogger(InterceptorHtmService.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String url = request.getRequestURI() + "";
        if(url.contains("densityFree")|| url.contains("command")){//免密码
            request.getSession().setAttribute("sys_user","true");
            return  true;
        }

        //处理云端的请求
       /* if(CloudRequestInterceptor.isCloudRequest(url)){
            boolean interceptorResult  = CloudRequestInterceptor.interceptorReponse(request,response);
            if(interceptorResult){
                interceptorResult =  CloudRequestInterceptor.unopenedCommand(url,response);
            }
            return interceptorResult;
        }*/
       if (url.contains("index")|| url.contains("login")) {//登陆认证或登陆
            return true;
        }

        Object isLogin = request.getSession().getAttribute("sys_user");
        if (null == isLogin) {
            String localUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/menu/login";
            PrintWriter out = response.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('"+localUrl+"','_parent')"); //作为父窗口打开
            out.println("</script>");
            out.println("</html>");
            return false;
        }
        // 设置请求的字符编码
        request.setCharacterEncoding("UTF-8");
        // 设置返回请求的字符编码      
        response.setCharacterEncoding("UTF-8");
        // 获取当前请求的URI
       // System.out.println("拦截器url:" + url);
        return true;
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //System.out.println("postHandle");

    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //System.out.println("afterCompletion");

    }

}
