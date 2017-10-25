package com.liumapp.helloSv.backend.web.controller;

import com.liumapp.helloSv.backend.web.model.FundsStatisticsInfo;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/fundsStatistics")
public class FundsStatisticsController extends GlobalController {

    @Autowired
    private com.liumapp.helloSv.backend.web.service.FundsStatisticsInfoService FundsStatisticsInfoService;


    @RequestMapping("/all")
    public RespInfo getAll(FundsStatisticsInfo FundsStatisticsInfo) throws Exception {
        RespInfo res = new RespInfo(Consts.SUCCESS_CODE, FundsStatisticsInfoService.getAll(FundsStatisticsInfo));

        return res;

    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        FundsStatisticsInfo FundsStatisticsInfo = FundsStatisticsInfoService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, FundsStatisticsInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        FundsStatisticsInfoService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody FundsStatisticsInfo FundsStatisticsInfo) {
        String msg = FundsStatisticsInfo.getId() == null ? "添加成功" : "修改成功";
        FundsStatisticsInfoService.save(FundsStatisticsInfo);
        return new RespInfo(Consts.SUCCESS_CODE, FundsStatisticsInfo, msg);
    }

}
