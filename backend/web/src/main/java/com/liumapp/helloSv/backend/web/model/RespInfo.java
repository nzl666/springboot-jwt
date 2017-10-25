package com.liumapp.helloSv.backend.web.model;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class RespInfo {
    private Integer status;

    private Object content;

    private String message;

    private String token;

    public RespInfo() {
    }

    public RespInfo(Integer status, Object content, String message,String token) {
        this.status = status;
        this.content = content;
        this.message = message;
        this.token = token;
    }
    public RespInfo(Integer status, Object content, String message) {
        this.status = status;
        this.content = content;
        this.message = message;
    }

    public RespInfo(Integer status, Object content) {
        this.status = status;
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
