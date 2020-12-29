package com.networks.pms.service.middleware;

import com.networks.pms.bean.model.PmsLanguage;
import com.networks.pms.dao.daoImpl.fcs.PmsLanguageDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PmsLanguageService {
    @Autowired
    private PmsLanguageDaoImpl pmsLanguageDao;


    public int getLIdByLName(String guestLanguage){
        PmsLanguage pmsLanguage = pmsLanguageDao.findById(guestLanguage);
        if(pmsLanguage == null){
            pmsLanguage = pmsLanguageDao.getDefaultLanguage();
        }
        return pmsLanguage.getLangId();
    }

}
