package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.model.PersonalInfo;
import com.liumapp.helloSv.backend.web.mapper.PersonalInfoMapper;
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
public class PersonalInfoService extends BaseService {
    @Autowired
    private PersonalInfoMapper personalInfoMapper;

    public List<PersonalInfo> getAll(PersonalInfo personalInfo) {
        String sort = personalInfo.getSort(), order = personalInfo.getOrder();
        Integer dateRange = personalInfo.getDateRange();
        return personalInfoMapper.selectByExample(createDateRangeExample(PersonalInfo.class, order, sort, dateRange));
    }

    public PersonalInfo getById(Integer id) {
        return personalInfoMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        personalInfoMapper.deleteByPrimaryKey(id);
    }

    public void save(PersonalInfo personalInfo) {
        Date date = new Date();
        personalInfo.setUpdateDate(date);
        if (personalInfo.getId() != null) {
            personalInfoMapper.updateByPrimaryKey(personalInfo);
        } else {
            personalInfo.setCreateDate(date);
            personalInfoMapper.insert(personalInfo);
        }
    }

    public int getCount(PersonalInfo personalInfo) {
        return personalInfoMapper.selectCount(personalInfo);
    }
}
