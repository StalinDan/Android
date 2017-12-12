package com.example.danish.baseproject.bean;

import com.example.danish.baseproject.net.base.BaseBean;

import java.util.List;

/**
 * Created by danish on 2017/12/11.
 */

public class QBNewsListBean extends BaseBean {


    private List<NewslettersBean> newsletters;

    public List<NewslettersBean> getNewsletters() {
        return newsletters;
    }

    public void setNewsletters(List<NewslettersBean> newsletters) {
        this.newsletters = newsletters;
    }

    public static class NewslettersBean {
        /**
         * createdAt : 2017-12-11T07:27:03.131Z
         * id : 0
         * name : string
         * thumbnail : string
         */

        private String createdAt;
        private int id;
        private String name;
        private String thumbnail;

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
