package com.danish.attendance.bean;

import com.danish.attendance.net.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by danish on 2018/6/8.
 */

public class QueryTrainNoBean extends BaseBean {

    /**
     * type : 1
     * result : [{"plateNo":"沪A001","model":"","type":"","brand":"","purpose":"","identifyCode":"V121331311","createdBy":"","createdDt":"2018-06-07 15:44:50"}]
     */

    private List<ResultBean> result;

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * plateNo : 沪A001
         * model :
         * type :
         * brand :
         * purpose :
         * identifyCode : V121331311
         * createdBy :
         * createdDt : 2018-06-07 15:44:50
         */

        private String plateNo;
        private String model;
        @SerializedName("type")
        private String typeX;
        private String brand;
        private String purpose;
        private String identifyCode;
        private String createdBy;
        private String createdDt;

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getTypeX() {
            return typeX;
        }

        public void setTypeX(String typeX) {
            this.typeX = typeX;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getIdentifyCode() {
            return identifyCode;
        }

        public void setIdentifyCode(String identifyCode) {
            this.identifyCode = identifyCode;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDt() {
            return createdDt;
        }

        public void setCreatedDt(String createdDt) {
            this.createdDt = createdDt;
        }
    }
}
