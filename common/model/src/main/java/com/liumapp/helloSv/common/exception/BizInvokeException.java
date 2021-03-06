package com.liumapp.helloSv.common.exception;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class BizInvokeException extends RuntimeException {
    private String exceptionCode;

    public BizInvokeException(String exceptionMsg, String exceptionCode) {
        super(exceptionMsg);
        this.exceptionCode = exceptionCode;
    }

    public String getCode() {
        return exceptionCode;
    }
}
