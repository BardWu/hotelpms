package com.networks.pms.controller.middleware;

import com.networks.pms.bean.model.ResendMessage;
import com.networks.pms.common.util.MyPager;
import com.networks.pms.common.util.Print;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resend")
public class PmsResendMessageController {
   // @Autowired
   // private ResendMessageService resendMessageService;
   /* *//**
     * 分页查询
     * @param response
     * @param limit
     * @param offset
     *//*
    @ResponseBody
    @RequestMapping("/resendMessage")
    public void  commandResponse(HttpServletResponse response, int limit, int offset,String status,String requestType){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit",limit);
        map.put("offset",offset);
        map.put("status",status);
        map.put("requestType",requestType);
        List<ResendMessage> list = new ArrayList<ResendMessage>();
        list = resendMessageService.findByPaging(map);
        int total = resendMessageService.pagCount(map);
        MyPager paper = new MyPager(list,total);
        System.out.println("2->"+JSONObject.fromObject(paper).toString());
        Print.printJSON(response, JSONObject.fromObject(paper));
    }*/
}
