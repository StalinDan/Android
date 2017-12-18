package com.example.danish.paddemotest.bean;

/**
 * Created by danish on 2017/12/12.
 */

public class GridItemBean {
    private String bed;
    private String trainNo;
    private String name;
    private int status; //1 红 2黄 3绿 4蓝

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
