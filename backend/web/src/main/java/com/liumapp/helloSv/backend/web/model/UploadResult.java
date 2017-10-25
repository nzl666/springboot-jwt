package com.liumapp.helloSv.backend.web.model;


import com.liumapp.helloSv.backend.web.model.enums.UploadType;

import java.util.Date;
/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class UploadResult extends BaseEntity {
    private Date createDate;
    private String detail;
    private UploadType uploadType;

    public UploadResult() {
    }

    public UploadResult(Date createDate, String detail, UploadType uploadType) {
        this.createDate = createDate;
        this.detail = detail;
        this.uploadType = uploadType;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public UploadType getUploadType() {
        return uploadType;
    }

    public void setUploadType(UploadType uploadType) {
        this.uploadType = uploadType;
    }
}
