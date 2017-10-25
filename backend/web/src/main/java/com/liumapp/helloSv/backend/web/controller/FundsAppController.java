package com.liumapp.helloSv.backend.web.controller;

import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.CostInfo;
import com.liumapp.helloSv.backend.web.model.FundsStatisticsInfo;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.service.CostInfoService;
import com.liumapp.helloSv.backend.web.service.FundsStatisticsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/fundsApp")
public class FundsAppController{

    @Autowired
    private CostInfoService costInfoService;

    @Autowired
    private FundsStatisticsInfoService fundsStatisticsInfoService;

    @RequestMapping(value = "/applyCostStatus", method = RequestMethod.POST)
    public RespInfo applyCostStatus(@RequestBody CostInfo costInfo) throws BizException {
        costInfoService.applyCostStatus(costInfo);
        return new RespInfo(Consts.SUCCESS_CODE, costInfo, "申请成功");
    }

    @RequestMapping(value = "/checkCostStatus", method = RequestMethod.POST)
    public RespInfo checkCostStatus(@RequestBody CostInfo costInfo) throws BizException {
        FundsStatisticsInfo fsi = new FundsStatisticsInfo();
        fsi.setOrderNum(costInfo.getOrderNum());
        fundsStatisticsInfoService.save(fsi);
        costInfoService.checkCostStatus(costInfo);

        return new RespInfo(Consts.SUCCESS_CODE, costInfo, "审批成功");
    }

    @RequestMapping(value = "/submitCostStatus", method = RequestMethod.POST)
    public RespInfo submitCostStatus(@RequestBody CostInfo costInfo) throws BizException {
        costInfoService.submitCostStatus(costInfo);
        return new RespInfo(Consts.SUCCESS_CODE, costInfo, "提交成功");
    }

    @RequestMapping(value = "/rejectCostStatus", method = RequestMethod.POST)
    public RespInfo rejectCostStatus(@RequestBody CostInfo costInfo) throws BizException {
        costInfoService.rejectCostStatus(costInfo);
        return new RespInfo(Consts.SUCCESS_CODE, costInfo, "拒绝成功");
    }

}
