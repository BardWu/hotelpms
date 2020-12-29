package com.networks.pms.bean.model;

/**
 * @program: hotelpms
 * @description: 酒店operaPmsName信息截取
 * @author: Bardwu
 * @create: 2019-03-28 10:05
 **/
public class OperaNameInter {

    private int id;

    private String intercept;

    private String remark;

    public OperaNameInter() {
    }

    public OperaNameInter(int id, String intercept, String remark) {
        this.id = id;
        this.intercept = intercept;
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntercept() {
        return intercept;
    }

    public void setIntercept(String intercept) {
        this.intercept = intercept;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "OperaNameInter{" +
                "id=" + id +
                ", intercept='" + intercept + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
