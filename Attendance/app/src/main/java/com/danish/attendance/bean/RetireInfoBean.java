package com.danish.attendance.bean;

import com.danish.attendance.net.BaseBean;

/**
 * Created by danish on 2018/6/14.
 */

public class RetireInfoBean extends BaseBean {

    /**
     * type : 1
     * result : {"workNo":"CS001","name":"测试一","plateNo":"沪A001","wearingTime":0,"avgFatigue":0,"blueCnt":0,"redCnt":0}
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
         * wearingTime : 0
         * avgFatigue : 0
         * blueCnt : 0
         * redCnt : 0
         */

        private String workNo;
        private String name;
        private String plateNo;
        private float wearingTime;
        private double avgFatigue;
        private int blueCnt;
        private int redCnt;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

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

        public float getWearingTime() {
            return wearingTime;
        }

        public void setWearingTime(float wearingTime) {
            this.wearingTime = wearingTime;
        }

        public double getAvgFatigue() {
            return avgFatigue;
        }

        public void setAvgFatigue(double avgFatigue) {
            this.avgFatigue = avgFatigue;
        }

        public int getBlueCnt() {
            return blueCnt;
        }

        public void setBlueCnt(int blueCnt) {
            this.blueCnt = blueCnt;
        }

        public int getRedCnt() {
            return redCnt;
        }

        public void setRedCnt(int redCnt) {
            this.redCnt = redCnt;
        }
    }
}
