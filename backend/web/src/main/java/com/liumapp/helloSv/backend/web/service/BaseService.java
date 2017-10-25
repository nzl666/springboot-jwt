package com.liumapp.helloSv.backend.web.service;

import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class BaseService {
    public Example createDateRangeExample(Class clazz, String order, String sort, Integer dateRange, String... conditions) {
        Example example = new Example(clazz);
        if (!StringUtils.isEmpty(sort) && !"null".equals(sort) && !"undefined".equals(sort) && !StringUtils.isEmpty(order) && !"null".equals(order) && !"undefined".equals(order))
            example.setOrderByClause(String.format("%s %s", sort, order));

        if (dateRange == null) dateRange = 3;
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition(String.format("create_date>DATE_ADD(now(),INTERVAL -%d MONTH)", dateRange));
        for (String condition : conditions) {
            if (!StringUtils.isEmpty(condition)) criteria.andCondition(condition);
        }
        return example;
    }
}
