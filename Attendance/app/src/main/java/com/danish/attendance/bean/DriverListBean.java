package com.danish.attendance.bean;

import android.graphics.Bitmap;

import com.danish.attendance.net.BaseBean;

import java.util.List;

/**
 * Created by danish on 2018/6/13.
 */

public class DriverListBean extends BaseBean {

    /**
     * type : 1
     * result : {"pageNo":1,"pageSize":10,"totalRecs":1,"resultList":[{"sid":1,"workNo":"CS001","name":"测试一","plateNo":"沪A001","faceFeatureExist":false}],"totalPages":1}
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
         * pageNo : 1
         * pageSize : 10
         * totalRecs : 1
         * resultList : [{"sid":1,"workNo":"CS001","name":"测试一","plateNo":"沪A001","faceFeatureExist":false}]
         * totalPages : 1
         */

        private int pageNo;
        private int pageSize;
        private int totalRecs;
        private int totalPages;
        private List<ResultListBean> resultList;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalRecs() {
            return totalRecs;
        }

        public void setTotalRecs(int totalRecs) {
            this.totalRecs = totalRecs;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * sid : 1
             * workNo : CS001
             * name : 测试一
             * plateNo : 沪A001
             * faceFeatureExist : false
             */

            private int sid;
            private String workNo;
            private String name;
            private String plateNo;
            private boolean faceFeatureExist;

            private Bitmap faceBitmap;

            public Bitmap getFaceBitmap() {
                return faceBitmap;
            }

            public void setFaceBitmap(Bitmap faceBitmap) {
                this.faceBitmap = faceBitmap;
            }


            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
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

            public boolean isFaceFeatureExist() {
                return faceFeatureExist;
            }

            public void setFaceFeatureExist(boolean faceFeatureExist) {
                this.faceFeatureExist = faceFeatureExist;
            }
        }
    }
}
