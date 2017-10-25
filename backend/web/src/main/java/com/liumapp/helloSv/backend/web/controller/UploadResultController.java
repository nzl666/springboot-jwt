package com.liumapp.helloSv.backend.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liumapp.helloSv.backend.web.model.UploadResult;
import com.liumapp.helloSv.backend.web.model.enums.UploadType;
import com.liumapp.helloSv.backend.web.service.UploadResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.liumapp.helloSv.backend.web.utils.Consts.STATUS_SUCCESS;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/uploadResult")
public class UploadResultController extends GlobalController {

    @Autowired
    private UploadResultService uploadResultService;

    @RequestMapping("/all")
    public List<UploadResult> getAll(UploadResult uploadResult) {
        return uploadResultService.getAll(uploadResult);
    }

    @RequestMapping(value = "/view/{uploadType}")
    public Object view(@PathVariable UploadType uploadType, UploadResult uploadResultView) {
        List<JSONObject> list = new ArrayList<>();
        uploadResultView.setUploadType(uploadType);
        List<UploadResult> uploadResults = uploadResultService.getByType(uploadResultView);
        if (uploadResults.size() > 0) {
            for (UploadResult uploadResult : uploadResults) {
                Date date = uploadResult.getCreateDate();
                JSON.parseArray(uploadResult.getDetail()).stream().forEach(o -> {
                    JSONObject per = (JSONObject) o;
                    per.put("createDate", date);
                    list.add(per);
                });

            }
        }
        return list.stream().filter(o -> !o.getString("status").equals(STATUS_SUCCESS)).collect(Collectors.toList());
    }


}

