package com.example.danish.baseproject.bean;

import com.example.danish.baseproject.net.base.BaseBean;

import java.util.List;

/**
 * Created by danish on 2017/12/18.
 */

public class QBVideoListBean extends BaseBean {
    /**
     * number : 1
     * size : 10
     * totalElements : 12
     * totalPages : 2
     * content : [{"id":12,"name":"121212","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502606540659_343391.mp4","favorited":false},{"id":11,"name":"11111111","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502606613319_098773.mp4","favorited":false},{"id":10,"name":"0000","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502606766079_356965.mp4","favorited":false},{"id":9,"name":"999","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502608878499_335919.mp4","favorited":false},{"id":8,"name":"888","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502606838709_356925.mp4","favorited":false},{"id":7,"name":"777","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502607106589_086966.mp4","favorited":false},{"id":6,"name":"666","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502609451109_70786.mp4","favorited":false},{"id":5,"name":"5555","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502607306439_356773.mp4","favorited":false},{"id":4,"name":"4444","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":60,"video":"http://139.129.98.157/video/2017/8/9/1502607429539_356485.mp4","favorited":false},{"id":3,"name":"333","thumbnail":"https://static.youku.com/user/img/avatar/310/56.jpg","duration":0,"video":"http://139.129.98.157/video/2017/8/9/1502607601859_356438.mp4","favorited":false}]
     * numberOfElements : 10
     */

    private int number;
    private int size;
    private int totalElements;
    private int totalPages;
    private int numberOfElements;
    private List<ContentBean> content;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 12
         * name : 121212
         * thumbnail : https://static.youku.com/user/img/avatar/310/56.jpg
         * duration : 60
         * video : http://139.129.98.157/video/2017/8/9/1502606540659_343391.mp4
         * favorited : false
         */

        private int id;
        private String name;
        private String thumbnail;
        private int duration;
        private String video;
        private boolean favorited;

        //添加属性 区别是否播放
        private boolean isPlayed;

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

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public boolean isPlayed() {
            return isPlayed;
        }

        public void setPlayed(boolean isPlayed) {
            this.isPlayed = isPlayed;
        }
    }
}
