package com.networks.pms.dao.daoImpl.fcs;

import com.networks.pms.bean.model.CloudRequest;
import com.networks.pms.dao.MybatisDao;

import java.util.List;
import java.util.Map;

public class CloudRequestDaoImpl extends MybatisDao<CloudRequest> {

    protected String findByPaging =".findByPaging";
    protected String pagCount = ".pagCount";
    private String getSendMessage = ".getSendMessage";
    private String find = ".find";
    public CloudRequestDaoImpl(){
        findByPaging = entityClass.getSimpleName()+findByPaging;
        pagCount = entityClass.getSimpleName()+pagCount;
        getSendMessage=entityClass.getSimpleName()+getSendMessage;
        find=entityClass.getSimpleName()+find;
    }
    //获取发送给fcs的信息
    public List<CloudRequest> getSendMessage(){
        return  this.getSqlSession().selectList(getSendMessage);
    }
    //分页查询
    public List<CloudRequest> findByPaging(Map<String,Object> map){
        return  this.getSqlSession().selectList(findByPaging,map);
    }

    public int pagCount(Map<String,Object> map){
        return getSqlSession().selectOne(pagCount,map);
    }

    public List<CloudRequest> find(CloudRequest cloudRequest){
        return this.getSqlSession().selectList(find,cloudRequest);
    }
}
