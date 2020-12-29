package com.networks.pms.service.fcs;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.common.util.KV;
import com.networks.pms.service.com.HotelPortStatusAbstractMQ;
import com.networks.pms.service.com.SysConf;
import com.networks.pms.service.middleware.PmsConnectService;
import com.networks.pms.service.middleware.PmsLogRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: hotelpms
 * @description: Fcs port change
 * @author: Bardwu
 * @create: 2019-02-19 14:38
 **/
@Service
public  class FcsPortStatusServiceMQ extends HotelPortStatusAbstractMQ {


    @Autowired
    private PmsLogRecordService pmsLogRecordService;
    @Autowired
    private PmsConnectService pmsConnectService;

   public FcsPortStatusServiceMQ(){
       init();
   }
   private void init(){
       super.setPortType(Integer.parseInt(KV.FcsInterface.getMessage()));
       super.setPortName("FCS");
   }

    @Override
    public void dealListenerError(String errorReason) {
        PmsLogRecord pmsLogRecord = PmsLogRecord.errorLog(SysConf.PMS_HOTELNAME,errorReason,"同步Fcs状态到云端失败");
        pmsLogRecordService.addLogRecord(pmsLogRecord);
    }

    @Override
    public void setConnectingByCloud() {
        pmsConnectService.fcsConnect();
    }

    @Override
    public void setDisConnectByCloud() {
        pmsConnectService.FcsDisConnect();
    }

    @Override
    public Object getSynchForStatus() {
        return this;
    }
}
