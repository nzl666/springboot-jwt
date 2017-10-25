package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.CityMapper;
import com.liumapp.helloSv.backend.web.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class CityService {

    @Autowired
    private CityMapper cityMapper;

    public List<City> getAll(City city) {
//        if (city.getPage() != null && city.getRows() != null) {
//            PageHelper.startPage(city.getPage(), city.getRows());
//        }
        Example example = new Example(City.class);
        example.setOrderByClause("");
        return cityMapper.selectAll();
    }

    public City getById(Integer id) {
        return cityMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        cityMapper.deleteByPrimaryKey(id);
    }

    public void save(City country) {
        if (country.getId() != null) {
            cityMapper.updateByPrimaryKey(country);
        } else {
            cityMapper.insert(country);
        }
    }
}
