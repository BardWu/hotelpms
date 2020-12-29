package com.networks.pms.service.ucs;

import com.networks.pms.bean.model.PmsFcsTL;
import com.networks.pms.dao.daoImpl.fcs.PmsSendTLDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: hotelpms
 * @description: fcs send TL service
 * @author: Bardwu
 * @create: 2019-05-27 14:32
 **/
@Service
public class PmsSendUCSService {

    @Autowired
    private PmsSendTLDaoImpl pmsSendTLDao;

    public int insert(PmsFcsTL pmsFcsTL){

        return  pmsSendTLDao.insert(pmsFcsTL);
    }

    public int update(PmsFcsTL pmsFcsTL){
        return pmsSendTLDao.update(pmsFcsTL);
    }

    /**
     * 查询发送次数小于2次，且未发送成功的数据
     * @param pmsFcsTL
     * @return
     */
    public List<PmsFcsTL> getList(PmsFcsTL pmsFcsTL){
        return pmsSendTLDao.getList(pmsFcsTL);
    }

    public int insertList(List<PmsFcsTL> pmsFcsTLList){
        return pmsSendTLDao.insertList(pmsFcsTLList);
    }
}
