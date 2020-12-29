package com.networks.pms.common.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CommandReponse {
    private Object data;
    private int errCode;
    private String message;
    private String status;

    public static String SUCCESS="success";
    public static String ERROR= "error";

    public CommandReponse(Map<String, Object> data, int errCode,String status) {
        this.data = data;
        this.errCode = errCode;
        this.message =ErrorControl.getErrorMessage(errCode);
        this.status = status;
    }
    public CommandReponse(String data, int errCode,String status) {
        this.data = data;
        this.errCode = errCode;
        this.message =ErrorControl.getErrorMessage(errCode);
        this.status = status;
    }


    public CommandReponse() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getSUCCESS() {
        return SUCCESS;
    }

    public static void setSUCCESS(String SUCCESS) {
        CommandReponse.SUCCESS = SUCCESS;
    }

    @Override
    public String toString() {
        return "CommandReponse{" +
                "data=" + data +
                ", errCode='" + errCode + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static CommandReponse jsonToCommandReponse(String json)throws JSONException {
        CommandReponse commandReponse = new CommandReponse();
        JSONObject jsonObject = new JSONObject().fromObject(json);
        commandReponse = (CommandReponse)JSONObject.toBean(jsonObject,CommandReponse.class);
        return commandReponse;
    }

    public static void main(String[] args) {
        String s = "JobNo=\"000670\"";
        String s2 = "=\"\"";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("JobNo","000670");
        map.put("DeadlineDaTi","17-Oct-2018 16:21:59");
        CommandReponse commandReponse = new CommandReponse();
      //  commandReponse.setErrCode("123");
        commandReponse.setMessage("message");
        commandReponse.setStatus("1");
        commandReponse.setData(map);
        System.out.println(   JSONObject.fromObject(commandReponse));


    }
}
