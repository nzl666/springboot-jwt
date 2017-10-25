package com.liumapp.helloSv.backend.web.controller;


import com.alibaba.fastjson.JSONArray;
import com.liumapp.helloSv.backend.web.exceptions.BizException;
import com.liumapp.helloSv.backend.web.model.PersonalInfo;
import com.liumapp.helloSv.backend.web.service.PersonalInfoService;
import com.liumapp.helloSv.backend.web.utils.UploadExcelRules;
import com.liumapp.helloSv.backend.web.model.RespInfo;
import com.liumapp.helloSv.backend.web.model.UploadResult;
import com.liumapp.helloSv.backend.web.model.enums.UploadType;
import com.liumapp.helloSv.backend.web.service.UploadResultService;
import com.liumapp.helloSv.backend.web.utils.Consts;
import com.liumapp.helloSv.common.model.SheetBean;
import com.liumapp.helloSv.common.model.UploadFile;
import com.liumapp.helloSv.common.utils.DownUploadUtil;
import com.liumapp.helloSv.common.utils.ExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.liumapp.helloSv.backend.web.utils.Consts;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
@RestController
@RequestMapping("/personalInfo")
public class PersonalInfoController extends GlobalController {

    @Autowired
    private PersonalInfoService personalInfoService;

    @Autowired
    private UploadResultService uploadResultService;

    @RequestMapping("/all")
    public List<PersonalInfo> getAll(PersonalInfo personalInfo) {
        return personalInfoService.getAll(personalInfo);
    }

    @RequestMapping(value = "/view/{id}")
    public RespInfo view(@PathVariable Integer id) {
        PersonalInfo personalInfo = personalInfoService.getById(id);
        return new RespInfo(Consts.SUCCESS_CODE, personalInfo);
    }

    @RequestMapping(value = "/delete/{id}")
    public RespInfo delete(@PathVariable Integer id) {
        personalInfoService.deleteById(id);
        return new RespInfo(Consts.SUCCESS_CODE, null, "删除成功");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RespInfo save(@RequestBody PersonalInfo personalInfo) {
        String msg = personalInfo.getId() == null ? "添加成功" : "修改成功";
        personalInfoService.save(personalInfo);
        return new RespInfo(Consts.SUCCESS_CODE, personalInfo, msg);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public RespInfo upload(HttpServletRequest request) throws Exception {
        List<UploadFile> files = DownUploadUtil.upload(request);
        RespInfo respInfo = new RespInfo(Consts.SUCCESS_CODE, null, "上传成功");
        JSONArray jsonArray = new JSONArray();
        boolean isPartSuccess = false, isPartFailed = false;
        for (UploadFile file : files) {
            String originalName = file.getOriginalFilename();
            List<SheetBean> sheetBeans = ExcelUtil.readExcel(file.getInputStream(), originalName.substring(originalName.lastIndexOf(".") + 1));
            List<PersonalInfo> personalInfos = UploadExcelRules.parsePersonalInfos(sheetBeans);
            if (personalInfos.size() > 0) {
                for (PersonalInfo personalInfo : personalInfos) {
                    String status;
                    String errorReason = null;
                    try {
                        doValid(personalInfo);
                        personalInfoService.save(personalInfo);
                        status = Consts.STATUS_SUCCESS;
                        isPartSuccess = true;
                    } catch (Exception ex) {
                        status = Consts.STATUS_FAILURE;
                        if (!isPartFailed) {
                            isPartFailed = true;
                        }
                        errorReason = Consts.DUPLICATED_MESSAGE;
                        ex.printStackTrace();
                        if (ex instanceof BizException) {
                            errorReason = ex.getMessage();
                        }
                    }
                    saveUploadResult(jsonArray, personalInfo, status, errorReason);
                }
            } else {
                respInfo.setStatus(Consts.ERROR_CODE);
                respInfo.setMessage("上传失败");
            }
        }
        if (jsonArray.size() > 0) {
            uploadResultService.save(new UploadResult(new Date(), jsonArray.toJSONString(), UploadType.PERSONALINFO));
        }
        if (isPartSuccess && isPartFailed) {
            respInfo.setMessage("部分上传成功");
        }
        if (!isPartSuccess && isPartFailed) {
            respInfo.setMessage("上传失败");
            respInfo.setStatus(Consts.ERROR_CODE);
        }
        return respInfo;
    }

    private void doValid(PersonalInfo personalInfo) throws BizException {
        if (StringUtils.isEmpty(personalInfo.getDriverName())) throw new BizException("司机名不能为空");
    }

}
