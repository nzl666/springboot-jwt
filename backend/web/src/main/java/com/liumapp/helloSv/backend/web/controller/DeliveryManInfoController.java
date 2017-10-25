package com.liumapp.helloSv.backend.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.DeliveryManInfo;
import com.liumapp.helloSv.backend.web.utils.UploadExcelRules;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.model.UploadResult;
import com.liumapp.helloSv.backend.web.model.enums.UploadType;
import com.liumapp.helloSv.backend.web.service.DeliveryManInfoService;
import com.liumapp.helloSv.backend.web.service.UploadResultService;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.common.model.SheetBean;
import com.liumapp.helloSv.common.model.UploadFile;
import com.liumapp.helloSv.common.utils.DownUploadUtil;
import com.liumapp.helloSv.common.utils.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/deliveryManInfo")
public class DeliveryManInfoController extends GlobalController {

    @Autowired
    private DeliveryManInfoService deliveryManInfoService;

    @Autowired
    private UploadResultService uploadResultService;

    @RequestMapping("/all")
    public List<DeliveryManInfo> getAll(DeliveryManInfo deliveryManInfo) {
        return deliveryManInfoService.getAll(deliveryManInfo);
    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        DeliveryManInfo deliveryManInfo = deliveryManInfoService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, deliveryManInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        deliveryManInfoService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody DeliveryManInfo deliveryManInfo) {
        String msg = deliveryManInfo.getId() == null ? "添加成功" : "修改成功";
        deliveryManInfoService.save(deliveryManInfo);
        return new RespInfo(Consts.SUCCESS_CODE, deliveryManInfo, msg);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RespInfo upload(HttpServletRequest request) throws Exception {
        List<UploadFile> files = DownUploadUtil.upload(request);
        RespInfo respInfo = new RespInfo(Consts.SUCCESS_CODE, null, "上传成功");
        JSONArray jsonArray = new JSONArray();
        boolean isPartFailed = false, isPartSuccess = false;
        for (UploadFile file : files) {
            String originalName = file.getOriginalFilename();
            List<SheetBean> sheetBeans = ExcelUtil.readExcel(file.getInputStream(), originalName.substring(originalName.lastIndexOf(".") + 1));
            List<DeliveryManInfo> deliveryManInfos = UploadExcelRules.parseDeliveryManInfos(sheetBeans);
            if (deliveryManInfos.size() > 0) {
                for (DeliveryManInfo deliveryManInfo : deliveryManInfos) {
                    String errorReason = null, status;
                    try {
                        doValid(deliveryManInfo);
                        deliveryManInfoService.save(deliveryManInfo);
                        isPartSuccess = true;
                        status =Consts.STATUS_SUCCESS;
                    } catch (Exception ex) {
                        if (!isPartFailed) {
                            isPartFailed = true;
                        }
                        errorReason = Consts.DUPLICATED_MESSAGE;
                        ex.printStackTrace();
                        if (ex instanceof BizException) {
                            errorReason = ex.getMessage();
                        }
                        status = Consts.STATUS_FAILURE;
                    }
                    saveUploadResult(jsonArray, deliveryManInfo, status, errorReason);
                }
                if (isPartSuccess && isPartFailed) {
                    respInfo.setMessage("部分上传成功");
                }
                if (!isPartSuccess && isPartFailed) {
                    respInfo.setMessage("上传失败");
                    respInfo.setStatus(Consts.ERROR_CODE);
                }
            } else {
                throw new Exception("上传失败");
            }
        }
        if (jsonArray.size() > 0) {
            uploadResultService.save(new UploadResult(new Date(), jsonArray.toJSONString(), UploadType.DELIVERYMANINFO));
        }
        return respInfo;
    }

    private void doValid(DeliveryManInfo deliveryManInfo) throws BizException {
        if (StringUtils.isEmpty(deliveryManInfo.getCode()) || StringUtils.isEmpty(deliveryManInfo.getName()))
            throw new BizException("承运商代码或承运商名称不能为空");
    }

}
