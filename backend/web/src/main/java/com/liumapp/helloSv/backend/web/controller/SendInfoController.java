package com.liumapp.helloSv.backend.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.model.SendInfo;
import com.liumapp.helloSv.backend.web.model.UploadResult;
import com.liumapp.helloSv.backend.web.model.enums.UploadType;
import com.liumapp.helloSv.backend.web.service.SendInfoService;
import com.liumapp.helloSv.backend.web.service.UploadResultService;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.backend.web.utils.UploadExcelRules;
import com.liumapp.helloSv.common.model.SheetBean;
import com.liumapp.helloSv.common.model.UploadFile;
import com.liumapp.helloSv.common.utils.DownUploadUtil;
import com.liumapp.helloSv.common.utils.ExcelUtil;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("/sendInfo")
public class SendInfoController extends GlobalController {

    @Autowired
    private SendInfoService sendInfoService;

    @Autowired
    private UploadResultService uploadResultService;

    @RequestMapping("/all")
    public List<SendInfo> getAll(SendInfo sendInfo) {

        return sendInfoService.getAll(sendInfo);

    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        SendInfo sendInfo = sendInfoService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, sendInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        sendInfoService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody SendInfo sendInfo) {
        String msg = sendInfo.getId() == null ? "添加成功" : "修改成功";
        sendInfoService.save(sendInfo);
        return new RespInfo(Consts.SUCCESS_CODE, sendInfo, msg);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RespInfo upload(HttpServletRequest request) throws IOException {
        List<UploadFile> files = DownUploadUtil.upload(request);
        RespInfo respInfo = new RespInfo(Consts.SUCCESS_CODE, null, "上传成功");
        boolean isPartFailed = false, isPartSuccess = false;
        JSONArray jsonArray = new JSONArray();
        for (UploadFile file : files) {
            String originalName = file.getOriginalFilename();
            List<SheetBean> sheetBeans = ExcelUtil.readExcel(file.getInputStream(), originalName.substring(originalName.lastIndexOf(".") + 1));
            List<SendInfo> sendInfos = UploadExcelRules.parseSendInfos(sheetBeans);
            if (sendInfos.size() > 0) {
                for (SendInfo sendInfo : sendInfos) {
                    String status, errorReason = null;
                    try {
                        doValid(sendInfo);
                        sendInfoService.save(sendInfo);
                        status = Consts.STATUS_SUCCESS;
                        isPartSuccess = true;
                    } catch (Exception ex) {
                        if (!isPartFailed) {
                            isPartFailed = true;
                        }
                        errorReason = Consts.DUPLICATED_MESSAGE;
                        if (ex instanceof BizException) {
                            errorReason = ex.getMessage();
                        }
                        ex.printStackTrace();
                        status = Consts.STATUS_FAILURE;
                    }
                    saveUploadResult(jsonArray, sendInfo, status, errorReason);
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
            uploadResultService.save(new UploadResult(new Date(), jsonArray.toJSONString(), UploadType.SENDINFO));
        }
        return respInfo;
    }

    private void doValid(SendInfo sendInfo) throws BizException {
        if (StringUtils.isEmpty(sendInfo.getCode()) || StringUtils.isEmpty(sendInfo.getName()))
            throw new BizException("客户编码或者客户名称不能为空");

    }

}
