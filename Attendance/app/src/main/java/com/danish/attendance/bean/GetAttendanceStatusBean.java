package com.danish.attendance.bean;

import com.danish.attendance.net.BaseBean;

/**
 * Created by danish on 2018/6/12.
 */

public class GetAttendanceStatusBean extends BaseBean {
    /**
     * type : 1
     * result : {"workNo":"CS001","name":"测试一","plateNo":"沪A001","status":true}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * workNo : CS001
         * name : 测试一
         * plateNo : 沪A001
         * status : true
         */

        private String workNo;
        private String name;
        private String plateNo;
        private boolean status;

        private String identifyCode; //司机出勤所用设备号(已经出勤的才有意义,未出勤的该值为空)

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

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getIdentifyCode() {
            return identifyCode;
        }

        public void setIdentifyCode(String identifyCode) {
            this.identifyCode = identifyCode;
        }
    }
}
