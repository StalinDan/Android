package com.danish.attendance.bean;


import com.danish.attendance.net.BaseBean;

/**
 * Created by danish on 2018/3/8.
 */

public class FaceInfoBean extends BaseBean {

    private String workNo;
    private String name;
    private String ldName;

    public String getLdName() {
        return ldName;
    }

    public void setLdName(String ldName) {
        this.ldName = ldName;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
