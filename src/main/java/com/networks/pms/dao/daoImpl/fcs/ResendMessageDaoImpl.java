package com.networks.pms.dao.daoImpl.fcs;

import com.networks.pms.bean.model.CloudMessage;
import com.networks.pms.bean.model.ResendMessage;
import com.networks.pms.dao.MybatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public class ResendMessageDaoImpl extends MybatisDao<ResendMessage> {
    protected String findByPaging =".findByPaging";
    protected String pagCount = ".pagCount";
    protected String getResendMsg = ".getResendMsg";

    public ResendMessageDaoImpl(){
        findByPaging = entityClass.getSimpleName()+findByPaging;
        pagCount = entityClass.getSimpleName()+pagCount;
        getResendMsg = entityClass.getSimpleName()+getResendMsg;
    }

    public  List<ResendMessage> findByPaging(Map<String,Object> map){
        return this.getSqlSession().selectList(findByPaging,map);
    }
    public int pagCount(Map<String,Object> map){
        return getSqlSession().selectOne(pagCount,map);
    }



    /**
     * 发送不成功且发送次数小于2次(0,1)
     * resendTotalTime < 2 and resendResult !='1
     * @param resendType
     * @return
     */
    public List<ResendMessage> getResendMsg(@Param("list") List<String> resendType) {
        return this.getSqlSession().selectList(getResendMsg,resendType);
    }

}
