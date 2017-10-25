package com.liumapp.helloSv.backend.web.service;

import com.liumapp.helloSv.backend.web.mapper.UploadResultMapper;
import com.liumapp.helloSv.backend.web.model.UploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@Service
public class UploadResultService extends BaseService {
    @Autowired
    private UploadResultMapper uploadResultMapper;

    public List<UploadResult> getAll(UploadResult uploadResult) {

        return uploadResultMapper.selectAll();
    }

    public UploadResult getById(Integer id) {
        return uploadResultMapper.selectByPrimaryKey(id);
    }

    public List<UploadResult> getByType(UploadResult uploadResult) {
        String sort = uploadResult.getSort(), order = uploadResult.getOrder();
        Integer dateRange = uploadResult.getDateRange();
        Example example = createDateRangeExample(UploadResult.class, order, sort, dateRange, "upload_type=" + uploadResult.getUploadType().ordinal());
        return uploadResultMapper.selectByExample(example);
    }

    public void deleteById(Integer id) {
        uploadResultMapper.deleteByPrimaryKey(id);
    }

    public void save(UploadResult uploadResult) {
        if (uploadResult.getId() != null) {
            uploadResultMapper.updateByPrimaryKey(uploadResult);
        } else {
            uploadResultMapper.insert(uploadResult);
        }
    }

    public void saveList(List<UploadResult> uploadResults) {
        uploadResultMapper.insertList(uploadResults);
    }


}
