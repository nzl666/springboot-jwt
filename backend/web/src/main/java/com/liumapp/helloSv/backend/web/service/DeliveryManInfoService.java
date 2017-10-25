package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.model.DeliveryManInfo;
import com.liumapp.helloSv.backend.web.mapper.DeliveryManInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class DeliveryManInfoService extends BaseService {
    @Autowired
    private DeliveryManInfoMapper deliveryManInfoMapper;

    public List<DeliveryManInfo> getAll(DeliveryManInfo deliveryManInfo) {
        String sort = deliveryManInfo.getSort(), order = deliveryManInfo.getOrder();
        Integer dateRange = deliveryManInfo.getDateRange();
        return deliveryManInfoMapper.selectByExample(createDateRangeExample(DeliveryManInfo.class, order, sort, dateRange));
    }

    public DeliveryManInfo getById(Integer id) {
        return deliveryManInfoMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        deliveryManInfoMapper.deleteByPrimaryKey(id);
    }

    public void save(DeliveryManInfo deliveryManInfo) {
        Date date = new Date();
        if (deliveryManInfo.getId() != null) {
            deliveryManInfo.setUpdateDate(date);
            deliveryManInfoMapper.updateByPrimaryKey(deliveryManInfo);
        } else {
            deliveryManInfo.setCreateDate(date);
            deliveryManInfo.setUpdateDate(date);
            deliveryManInfoMapper.insert(deliveryManInfo);
        }
    }

    public int getCount(DeliveryManInfo deliveryManInfo) {
        return deliveryManInfoMapper.selectCount(deliveryManInfo);
    }
}