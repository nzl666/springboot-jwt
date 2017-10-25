package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.SendInfoMapper;
import com.liumapp.helloSv.backend.web.model.SendInfo;
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
public class SendInfoService extends BaseService {
    @Autowired
    private SendInfoMapper sendInfoMapper;

    public List<SendInfo> getAll(SendInfo sendInfo) {
        String sort = sendInfo.getSort(), order = sendInfo.getOrder();
        Integer dateRange = sendInfo.getDateRange();
        return sendInfoMapper.selectByExample(createDateRangeExample(SendInfo.class, order, sort, dateRange));
    }

    public int getCount(SendInfo sendInfo) {
        return sendInfoMapper.selectCount(sendInfo);
    }

    public SendInfo getById(Integer id) {
        return sendInfoMapper.selectByPrimaryKey(id);
    }

    public void deleteById(Integer id) {
        sendInfoMapper.deleteByPrimaryKey(id);
    }

    public void save(SendInfo sendInfo) {
        if (sendInfo.getId() != null) {
            sendInfo.setUpdateDate(new Date());
            sendInfoMapper.updateByPrimaryKey(sendInfo);
        } else {
            Date date = new Date();
            sendInfo.setCreateDate(date);
            sendInfo.setUpdateDate(date);
            sendInfoMapper.insert(sendInfo);
        }
    }

    public SendInfo get(SendInfo sendInfo) {
        return sendInfoMapper.selectOne(sendInfo);
    }
}