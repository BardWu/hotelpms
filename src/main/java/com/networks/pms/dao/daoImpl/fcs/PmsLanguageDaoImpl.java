package com.networks.pms.dao.daoImpl.fcs;

import com.networks.pms.bean.model.PmsLanguage;
import com.networks.pms.dao.MybatisDao;

public class PmsLanguageDaoImpl extends MybatisDao<PmsLanguage> {
    protected  String defaultLanguage=".defaultLanguage";
    public PmsLanguageDaoImpl(){
        defaultLanguage=entityClass.getSimpleName()+defaultLanguage;
    }

    public PmsLanguage getDefaultLanguage()throws  RuntimeException{
        return this.getSqlSession().selectOne(defaultLanguage);
    }

}
