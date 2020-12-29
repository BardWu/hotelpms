package com.networks.pms.dao.daoImpl.fcs;

import com.networks.pms.bean.model.HotelRequest;
import com.networks.pms.dao.MybatisDao;

import java.util.List;
import java.util.Map;

public class HotelRequestDaoImpl extends MybatisDao<HotelRequest> {
    protected String findByPaging =".findByPaging";
    protected String pagCount = ".pagCount";
    public HotelRequestDaoImpl(){
        findByPaging = entityClass.getSimpleName()+findByPaging;
        pagCount = entityClass.getSimpleName()+pagCount;
    }

    public List<HotelRequest> findByPaging(Map<String,Object> map){
        return this.getSqlSession().selectList(findByPaging,map);
    }
    public int pagCount(Map<String,Object> map){
        return getSqlSession().selectOne(pagCount,map);
    }
}
