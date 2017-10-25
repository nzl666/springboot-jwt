package com.liumapp.helloSv.backend.web.model.view;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class DispatchView {
    private Long id;

    private Long[] subIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getSubIds() {
        return subIds;
    }

    public void setSubIds(Long[] subIds) {
        this.subIds = subIds;
    }
}
