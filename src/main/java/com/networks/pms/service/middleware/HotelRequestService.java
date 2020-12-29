package com.networks.pms.service.middleware;

import com.networks.pms.bean.model.HotelRequest;
import com.networks.pms.dao.daoImpl.fcs.HotelRequestDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HotelRequestService {
    @Autowired
    private HotelRequestDaoImpl hotelRequestDao;

    /**
     * 添加一个信息请求请求
     * @return
     */
    public int inset(HotelRequest hotelRequest){

        //中间件存储fcs请求发送的结果
        return hotelRequestDao.insert(hotelRequest);
    }

    public HotelRequest findByUUID(String uuid){
        return hotelRequestDao.findById(uuid);
    }

    public List<HotelRequest> findByPaging(Map<String,Object> map){
        return hotelRequestDao.findByPaging(map);
    }
    public int pagCount(Map<String,Object> map){
        return hotelRequestDao.pagCount(map);
    }
}
