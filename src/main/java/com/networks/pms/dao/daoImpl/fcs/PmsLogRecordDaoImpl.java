package com.networks.pms.dao.daoImpl.fcs;

import com.networks.pms.bean.model.CloudMessage;
import com.networks.pms.bean.model.PmsLogRecord;
import com.networks.pms.dao.MybatisDao;

import java.util.List;
import java.util.Map;

public class PmsLogRecordDaoImpl extends MybatisDao<PmsLogRecord> {
    protected String findByPaging =".findByPaging";
    protected String pagCount = ".pagCount";
    public PmsLogRecordDaoImpl(){
        findByPaging = entityClass.getSimpleName()+findByPaging;
        pagCount = entityClass.getSimpleName()+pagCount;
    }
    /**
     * 查询分页信息
     * @param map ：limit offset
     * @return
     */
    public List<PmsLogRecord> findByPaging(Map map){
        return getSqlSession().selectList(findByPaging,map);
    }

    public int pagCount(Map map){
        return getSqlSession().selectOne(pagCount,map);
    }
}
