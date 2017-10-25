package com.liumapp.helloSv.common.exception;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class AppBizException extends RuntimeException {
    private String exceptionCode;

    public AppBizException(String exceptionMsg, String exceptionCode) {
        super(exceptionMsg);
        this.exceptionCode = exceptionCode;
    }

    public String getCode() {
        return exceptionCode;
    }
}
