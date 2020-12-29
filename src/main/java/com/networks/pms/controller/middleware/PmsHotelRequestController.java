package com.networks.pms.controller.middleware;

import com.networks.pms.bean.model.HotelRequest;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.*;
import com.networks.pms.service.fcs.FcsConnect;
import com.networks.pms.service.middleware.HotelRequestService;
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
@RequestMapping("/hotelRequest")
public class PmsHotelRequestController {
    @Autowired
    private HotelRequestService hotelRequestService;

    @Autowired
    private FcsConnect fcsConnect;

    /**
     * 分页查询
     * @param response
     * @param limit
     * @param offset
     */
    @ResponseBody
    @RequestMapping("/requestMessage")
    public void  commandResponse(HttpServletResponse response, int limit, int offset,String uuid ,String status,String msgType,String portType,String cloudBegTime,String cloudEndTime){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit",limit);
        map.put("offset",offset);
        map.put("uuid",uuid);
        map.put("status",status);
        map.put("msgType",msgType);
        map.put("portType",portType);
        if(!StringUtil.isNull(cloudBegTime)){
            cloudBegTime = DateUtil.DataStringToTimeStampString(cloudBegTime,"yyyy-MM-dd");
        }
        if(!StringUtil.isNull(cloudEndTime)){
            cloudEndTime = DateUtil.DataStringToTimeStampString(cloudEndTime,"yyyy-MM-dd");
        }
        map.put("cloudBegTime",cloudBegTime);
        map.put("cloudEndTime",cloudEndTime);
        List<HotelRequest> list = new ArrayList<HotelRequest>();
        list = hotelRequestService.findByPaging(map);
        int total = hotelRequestService.pagCount(map);
        MyPager paper = new MyPager(list,total);
        System.out.println("2->"+JSONObject.fromObject(paper).toString());
        Print.printJSON(response, JSONObject.fromObject(paper));
    }




}
