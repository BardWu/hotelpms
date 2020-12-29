package com.networks.pms.service.com;

import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.CommandReponse;
import com.networks.pms.common.util.ErrorControl;
import com.networks.pms.common.util.Print;
import net.sf.json.JSONObject;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CloudRequestInterceptor {
    private static List<String> requestType;
    static{
        requestType = new ArrayList<String>();
        requestType.add("create_order");
        requestType.add("job_enquiry");
        requestType.add("room_status");
        requestType.add("posting");
        requestType.add("hs_createJob");
        requestType.add("hs_JobEnquiry");
        requestType.add("guest_is_valid");
        requestType.add("initPortStatus");
        requestType.add("updatePortStatus");
    }

    public static boolean isCloudRequest(String url){
        for(String type:requestType) {
            if (url.contains(type)) {//判断是否是云端的请求
                return true;
            }
        }
        return false;
    }

    /**
     * 处理云端的请求,是否存在正确的apikey
     * @param response
     * @return
     */
  /*  public static boolean interceptorReponse(HttpServletRequest request, HttpServletResponse response){
        boolean result = true;
        String apikey = request.getParameter("apikey");
        CommandReponse commandReponse = null;
        if(StringUtil.isNull(apikey)){
            commandReponse =  new CommandReponse("",ErrorControl.REQUEST_ERROR,CommandReponse.ERROR);
            result = false;
            Print.printJSON(response, JSONObject.fromObject(commandReponse));
        }else {
            //判断apiKey是否正确
            if (apikey.equals(SysConf.PMS_APIKEY1) || apikey.equals(SysConf.PMS_APIKEY2) || apikey.equals(SysConf.PMS_APIKEY3)) {//不存在apikey 请求格式错误
                request.setAttribute("sysConf", SysConf.getConfByApik(apikey));
                result = true;
            } else {
                commandReponse =  new CommandReponse("",ErrorControl.REQUEST_ERROR,CommandReponse.ERROR);
                result = false;
                Print.printJSON(response, JSONObject.fromObject(commandReponse));
            }
        }

        return result;
    }*/
    /*//检测请求的命令是否是外开启的
    public static   boolean unopenedCommand(String url, HttpServletResponse response){
        boolean result = true;
        CommandReponse commandReponse =  new CommandReponse("",ErrorControl.REQUEST_ERROR,CommandReponse.ERROR);
        if(url.contains("room_status")){
            if(!"true".equals(SysConf.OPERA_ACCEPT_ROOM_STATUS_REQUEST)){
                result = false;

            }
        }
        if(url.contains("posting")){
            if(!"true".equals(SysConf.OPERA_ACCEPT_POSTING_REQUEST)){
                result = false;
            }
        }
       if(!result){
           Print.printJSON(response, JSONObject.fromObject(commandReponse));
       }
       return result;
    }*/
}
