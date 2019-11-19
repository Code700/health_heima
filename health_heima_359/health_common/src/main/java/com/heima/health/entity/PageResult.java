package com.heima.health.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王立腾
 * @created 2019/11/18 16:02.
 * @description
 */
public class PageResult implements Serializable {
    //总页数
    private Long total;
    //当前页数据
    private List rows;

    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
