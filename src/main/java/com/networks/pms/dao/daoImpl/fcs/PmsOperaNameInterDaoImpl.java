package com.networks.pms.dao.daoImpl.fcs;

import com.networks.pms.bean.model.OperaNameInter;
import com.networks.pms.bean.model.PmsLanguage;
import com.networks.pms.dao.MybatisDao;

import java.util.List;

public class PmsOperaNameInterDaoImpl extends MybatisDao<OperaNameInter> {

    protected String getInterceptItem =".getInterceptItem";
    public PmsOperaNameInterDaoImpl(){
        getInterceptItem = entityClass.getSimpleName()+getInterceptItem;
    }
    /**
     * 查询需要截取的内容
     * @return
     */
    public List<OperaNameInter> getInterceptItem(){
        return getSqlSession().selectList(getInterceptItem);
    }

}
