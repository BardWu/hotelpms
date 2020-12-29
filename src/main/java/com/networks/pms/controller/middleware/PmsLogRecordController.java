package com.networks.pms.controller.middleware;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.string.StringUtil;
import com.networks.pms.common.util.DateUtil;
import com.networks.pms.common.util.MyPager;
import com.networks.pms.common.util.Print;
import com.networks.pms.service.middleware.PmsLogRecordService;
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
@RequestMapping("/pmsLog")
public class PmsLogRecordController {
    @Autowired
    private PmsLogRecordService pmsLogRecordService;

    /**
     * 分页查询
     * @param response
     * @param limit
     * @param offset
     */
    @ResponseBody
    @RequestMapping("/content")
    public void  commandResponse(HttpServletResponse response, int limit, int offset,String cloudBegTime,String cloudEndTime,String status){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("limit",limit);
        map.put("offset",offset);
        if(!StringUtil.isNull(cloudBegTime)){
            cloudBegTime = DateUtil.DataStringToTimeStampString(cloudBegTime,"yyyy-MM-dd");
        }
        if(!StringUtil.isNull(cloudEndTime)){
            cloudEndTime = DateUtil.DataStringToTimeStampString(cloudEndTime,"yyyy-MM-dd");
        }
        map.put("cloudBegTime",cloudBegTime);
        map.put("cloudEndTime",cloudEndTime);
        map.put("status",status);
        List<PmsLogRecord> list = new ArrayList<PmsLogRecord>();
        list = pmsLogRecordService.findByPaging(map);
        int total = pmsLogRecordService.pagCount(map);
        MyPager paper = new MyPager(list,total);
        System.out.println("2->"+JSONObject.fromObject(paper).toString());
        //return  JSONObject.fromObject(paper).toString();void//
        Print.printJSON(response, JSONObject.fromObject(paper));
    }

}
