package com.networks.pms.common.util;

import java.util.HashMap;
import java.util.Map;

//错误对照表(响应云端)
public class ErrorControl {
    private static Map<Integer,String> ERROR_CONTROL;
    public static int UNKNOWN_ERROR = -3;
    public static int SEND_FAILURE = 1001;
    public static int EXECUTE_SUCCESS = 0;
    public static int REQUEST_TIMEOUT = 1002;
    public static int SERVER_ERROR = -1;
    public static int REQUEST_ERROR=-2;
    public static int JOB_ENQUIRY_FAILURE=1003;
    public static int CREATE_FAILURE = 1004;
    public static int SYSTEM_BUSY = 1005;
    public static int  DUPLICATES_JOB= 1006;
    static{
        ERROR_CONTROL = new HashMap<Integer,String>();
        ERROR_CONTROL.put(SERVER_ERROR,"服务器错误");
        ERROR_CONTROL.put(EXECUTE_SUCCESS,"OK");
        ERROR_CONTROL.put(SEND_FAILURE,"发送失败");
        ERROR_CONTROL.put(REQUEST_TIMEOUT,"请求超时");
        ERROR_CONTROL.put(REQUEST_ERROR,"请求数据格式错误");
        ERROR_CONTROL.put(JOB_ENQUIRY_FAILURE,"修改工单状态信息失败");
        ERROR_CONTROL.put(CREATE_FAILURE,"创建失败");
        ERROR_CONTROL.put(SYSTEM_BUSY,"系统正忙");
        ERROR_CONTROL.put(DUPLICATES_JOB,"重复工单");
        ERROR_CONTROL.put(UNKNOWN_ERROR,"未知错误");
    }
    public static String getErrorMessage(int errorCode){
        if(ERROR_CONTROL.containsKey(errorCode)){
            return ERROR_CONTROL.get(errorCode);
        }else {
            return errorCode+"无效";
        }

    }

}
