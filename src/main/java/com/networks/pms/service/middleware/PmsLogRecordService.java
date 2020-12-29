package com.networks.pms.service.middleware;

import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.dao.daoImpl.fcs.PmsLogRecordDaoImpl;
import com.networks.pms.service.webSocket.LoggerMessageQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PmsLogRecordService {
    @Autowired
    private PmsLogRecordDaoImpl pmsLogRecordDao;
    private Logger logger = Logger.getLogger(PmsLogRecordService.class);
    private LoggerMessageQueue loggerMessageQueue = LoggerMessageQueue.getInstance();
    /**
     * 添加错误信息
     * @param pmsLogRecord 错误信息
     */
    public void addLogRecord(PmsLogRecord pmsLogRecord){
        try{
            pmsLogRecordDao.insert(pmsLogRecord);
        }catch (Exception e){
            logger.error("添加错误日志失败,失败原因:"+e.getMessage() );
            loggerMessageQueue.error("添加错误日志失败,失败原因:"+ e.getMessage());
        }

    }
    /**
     *  添加提示信息
     * @param pmsLogRecord  错误信息
     * @param clazz
     * @param infoMessage 日志信息
     */
    public void addLogRecordWithInfoLog(PmsLogRecord pmsLogRecord,Class clazz,String infoMessage){
        logger.info(infoMessage);
        loggerMessageQueue.info(infoMessage);
        addLogRecord(pmsLogRecord);
    }
    /**
     *  添加错误信息
     * @param pmsLogRecord  错误信息
     * @param clazz
     * @param infoMessage 日志信息
     */
    public void addLogRecordWithErrorLog(PmsLogRecord pmsLogRecord,Class clazz,String infoMessage){
        logger.error(infoMessage);
        loggerMessageQueue.error(infoMessage);
        addLogRecord(pmsLogRecord);
    }
    public int pagCount(Map map){
        return pmsLogRecordDao.pagCount( map);
    }

    public List<PmsLogRecord> findByPaging(Map map){
        return pmsLogRecordDao.findByPaging(map);
    }

}
