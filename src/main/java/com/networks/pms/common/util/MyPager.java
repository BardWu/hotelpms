package com.networks.pms.common.util;

import com.networks.pms.bean.model.CloudMessage;

import java.util.List;

public class MyPager<T> {
    List<T> rows;//查询的结果
    int total;//查询的总个数

    public MyPager(List<T> rows, int total) {
        this.rows = rows;
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
