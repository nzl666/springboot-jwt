package com.liumapp.helloSv.common.model;

import java.util.List;

/**
 * Created by liumapp on 10/19/17.
 * E-mail:liumapp.com@gmail.com
 * home-page:http://www.liumapp.com
 */
public class SheetBean {
    private Integer sheetNo;//sheet序号

    private String sheetName;//sheet名称

    private List<CellBean> cellBeanList;//单元格数据

    public Integer getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(Integer sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<CellBean> getCellBeanList() {
        return cellBeanList;
    }

    public void setCellBeanList(List<CellBean> cellBeanList) {
        this.cellBeanList = cellBeanList;
    }
}
