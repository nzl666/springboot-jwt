package com.liumapp.helloSv.backend.web.controller;

import com.liumapp.helloSv.backend.web.model.CostInfo;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/costMaintainInfo")
public class CostController extends GlobalController {

    @Autowired
    private com.liumapp.helloSv.backend.web.service.CostInfoService CostInfoService;

    @RequestMapping("/all")
    public RespInfo getAll(CostInfo CostInfo) throws Exception {
        RespInfo res = new RespInfo(Consts.SUCCESS_CODE, CostInfoService.getAll(CostInfo));

        return res;
    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        CostInfo CostInfo = CostInfoService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, CostInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        CostInfoService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody CostInfo CostInfo) {
        String msg = CostInfo.getId() == null ? "添加成功" : "修改成功";
        CostInfoService.save(CostInfo);
        return new RespInfo(Consts.SUCCESS_CODE, CostInfo, msg);
    }

}