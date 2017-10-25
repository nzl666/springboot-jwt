package com.liumapp.helloSv.backend.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.OrderInfo;
import com.liumapp.helloSv.backend.web.model.SendInfo;
import com.liumapp.helloSv.backend.web.service.SendInfoService;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.backend.web.utils.UploadExcelRules;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.model.UploadResult;
import com.liumapp.helloSv.backend.web.model.enums.UploadType;
import com.liumapp.helloSv.backend.web.service.OrderService;
import com.liumapp.helloSv.backend.web.service.UploadResultService;
import com.liumapp.helloSv.common.model.SheetBean;
import com.liumapp.helloSv.common.model.UploadFile;
import com.liumapp.helloSv.common.utils.DownUploadUtil;
import com.liumapp.helloSv.common.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/orderInfo")
public class OrderController extends GlobalController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SendInfoService sendInfoService;

    @Autowired
    private UploadResultService uploadResultService;

    @RequestMapping("/all")
    public List<OrderInfo> getAll(OrderInfo orderInfo) {

        return orderService.getAll(orderInfo);

    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        OrderInfo orderInfo = orderService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, orderInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        orderService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody OrderInfo orderInfo) {
        String msg = orderInfo.getId() == null ? "添加成功" : "修改成功";
        orderService.save(orderInfo);
        return new RespInfo(Consts.SUCCESS_CODE, orderInfo, msg);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RespInfo upload(HttpServletRequest request) throws IOException {
        List<UploadFile> files = DownUploadUtil.upload(request);
        RespInfo respInfo = new RespInfo(Consts.SUCCESS_CODE, null, "上传成功");
        boolean isPartSuccess = false, isPartFailed = false;
        JSONArray jsonArray = new JSONArray();
        for (UploadFile file : files) {
            String originalName = file.getOriginalFilename();
            List<SheetBean> sheetBeans = ExcelUtil.readExcel(file.getInputStream(), originalName.substring(originalName.lastIndexOf(".") + 1));
            List<OrderInfo> orders = UploadExcelRules.parseOrders(sheetBeans);
            if (orders.size() > 0) {
                for (OrderInfo orderInfo : orders) {
                    String status, errorReason = null;
                    String code = orderInfo.getCustomerCode(), name = orderInfo.getSender();
                    try {
                        if (sendInfoService.get(new SendInfo(code, name)) != null) {
                            orderService.save(orderInfo);
                            status = Consts.STATUS_SUCCESS;
                            isPartSuccess = true;
                        } else {
                            throw new BizException(String.format("客户信息中不存在该客户编号 %s 和客户名称 %s ", code, name));
                        }

                    } catch (Exception ex) {
                        if (!isPartFailed) {
                            isPartFailed = true;
                        }
                        errorReason = "该记录已存在,不可重复上传";
                        if (ex instanceof BizException) {
                            errorReason = ex.getMessage();
                        }
                        ex.printStackTrace();
                        status = Consts.STATUS_FAILURE;
                    }
                    saveUploadResult(jsonArray, orderInfo, status, errorReason);
                }
                if (isPartSuccess && isPartFailed) {
                    respInfo.setMessage("部分上传成功");
                }
                if (!isPartSuccess && isPartFailed) {
                    respInfo.setMessage("上传失败");
                    respInfo.setStatus(Consts.ERROR_CODE);
                }
            } else {
                respInfo.setStatus(Consts.ERROR_CODE);
                respInfo.setMessage("上传失败");
            }

        }
        if (jsonArray.size() > 0) {
            uploadResultService.save(new UploadResult(new Date(), jsonArray.toJSONString(), UploadType.ORDER));
        }
        return respInfo;
    }

}
