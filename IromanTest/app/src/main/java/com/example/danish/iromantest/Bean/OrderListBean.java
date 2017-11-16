package com.example.danish.iromantest.Bean;

import java.util.List;

/**
 * Created by danish on 2017/11/16.
 */

public class OrderListBean {
    Result result;

    public class Result {

        List<Data> list;

        public class Data {
            private Number sid;
            private String no;
            private String createdDt;
            private String image;
            private String title;
            private String status;
            private Number points;

            public Number getSid() {
                return sid;
            }

            public String getNo() {
                return no;
            }

            public String getCreatedDt() {
                return createdDt;
            }

            public String getImage() {
                return image;
            }

            public String getTitle() {
                return title;
            }

            public String getStatus() {
                return status;
            }

            public Number getPoints() {
                return points;
            }

            public void setSid(Number sid) {
                this.sid = sid;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public void setCreatedDt(String createdDt) {
                this.createdDt = createdDt;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setPoints(Number points) {
                this.points = points;
            }
        }

        public List<Data> getList() {
            return list;
        }
    }

    public Result getResult() {
        return result;
    }
}

