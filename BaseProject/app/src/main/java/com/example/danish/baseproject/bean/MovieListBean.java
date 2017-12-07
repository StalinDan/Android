package com.example.danish.baseproject.bean;


import com.example.danish.baseproject.net.base.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danish on 2017/12/4.
 */

public class MovieListBean extends BaseBean {

    private int count;
    private int start;
    private int total;
    private List<MovieItemBean> subjects;
    private String title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MovieItemBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MovieItemBean> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public class MovieItemBean implements Serializable{
        private Rateing rating;
        private List<String> genres;
        private String title;
        private List<CastsItem> casts;
        private int collect_count;
        private String original_title;
        private String subtype;
        private List<CastsItem> directors;
        private String year;
        private String alt;
        private String id;
        private Avatars images;

        public Rateing getRating() {
            return rating;
        }

        public void setRating(Rateing rating) {
            this.rating = rating;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<CastsItem> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsItem> casts) {
            this.casts = casts;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public List<CastsItem> getDirectors() {
            return directors;
        }

        public void setDirectors(List<CastsItem> directors) {
            this.directors = directors;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Avatars getImages() {
            return images;
        }

        public void setImages(Avatars images) {
            this.images = images;
        }
    }

     public class Rateing implements Serializable{
        private int max;
        private int min;
        private float average;
        private String stars;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }
    }

    public class CastsItem implements Serializable {
        private String alt;
        private Avatars avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public Avatars getAvatars() {
            return avatars;
        }

        public void setAvatars(Avatars avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class Avatars implements Serializable {
        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }




}
