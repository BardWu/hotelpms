package com.networks.pms.controller.com;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.networks.pms.service.com.SysConf;

@Controller
@RequestMapping("/menu")
public class MenuController {

  /*  @RequestMapping("/logRecord")
    public String LogRecordMenu(HttpServletRequest request, HttpServletResponse response) {
        return "hotelpms/operation/logRecord";
    }*/
    //登陆
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String index() {
        return "login";
    }
    //主页面
    @RequestMapping("/main")
    public String main() {
        return "main";
    }
    @RequestMapping("/densityFree")
    public String densityFree() {
        return "main";
    }
    @RequestMapping("/Test")
    public String Test() {
        return "UcsCommon/Test";
    }

    //跳转到控制台
    @RequestMapping("/console")
    public String console() {
        return "middleware/pms_control_panel";
    }
    //跳转到发送fcs请求
    @RequestMapping("/command")
    public String cloudCommend(){
        return "middleware/pms_cloud_command";
    }
    @RequestMapping("/resend")
    public String resend(){
        return "middleware/pms_resend";
    }
    @RequestMapping("/fcsMessage")
    public String fcsMessage(){
        return "middleware/pms_hotel_request";
    }
    //跳转到后台日志
    @RequestMapping("/logRecord")
    public String logRecord(){
        return "middleware/pms_log";
    }
    @RequestMapping(value="/login",method = RequestMethod.POST)
    @ResponseBody
    public String entrence(HttpServletRequest request, HttpServletResponse response) {
        String result = "false";
        JSONObject data = JSONObject.fromObject(request.getParameter("json"));
        String username = data.getString("username");
        String password = data.getString("password");
        String pmsLoginname = SysConf.PMS_LOGINNAME;
        String pmsPassword= SysConf.PMS_PASSWORD;
        System.out.println(pmsLoginname+","+pmsPassword);
        System.out.println(username + "," + password);
        if(pmsLoginname.equals(username) && pmsPassword.equals(password)){
            result = "true";
        }
        request.getSession().setAttribute("sys_user", "true");
        /*if (MessagePoint.START_THREAD) {
            request.getSession().setAttribute("connectStatus", "断开");
        }*/
        return result;
    }
}
