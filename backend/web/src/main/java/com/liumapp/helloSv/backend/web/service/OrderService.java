package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.model.OrderInfo;
import com.liumapp.helloSv.backend.web.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class OrderService extends BaseService {
    @Autowired
    private OrderMapper orderBeanMapper;

    public List<OrderInfo> getAll(OrderInfo orderBean) {
        String sort = orderBean.getSort(), order = orderBean.getOrder(), orderNum = orderBean.getOrderNum(), orderNumCondition = "";
        Integer dateRange = orderBean.getDateRange();
        if (!StringUtils.isEmpty(orderNum)) {
            orderNumCondition = String.format("order_num='%s'", orderNum);
        }
        return orderBeanMapper.selectByExample(createDateRangeExample(OrderInfo.class, order, sort, dateRange, orderNumCondition));
    }

    public OrderInfo getById(Integer id) {
        return orderBeanMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        orderBeanMapper.deleteByPrimaryKey(id);
    }

    public void save(OrderInfo orderBean) {
        Date date = new Date();
        orderBean.setUpdateDate(date);
        if (orderBean.getId() != null) {
            orderBeanMapper.updateByPrimaryKey(orderBean);
        } else {
            orderBean.setCreateDate(date);
            orderBeanMapper.insert(orderBean);
        }
    }

    public int getCount(OrderInfo orderBean) {
        return orderBeanMapper.selectCount(orderBean);
    }

    public OrderInfo getOrderInfoByNum(String orderNum) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNum(orderNum);
        return orderBeanMapper.selectOne(orderInfo);
    }
}