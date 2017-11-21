package com.example.danish.baseproject.net.base;

import java.io.Serializable;

/**
 * Created by LHG on 2017/4/18.
 */

public class CommonBean<T> implements Serializable {

    String type = "";

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public class Result {
        String msg;

        public T getContent() {
            return content;
        }

        public void setContent(T content) {
            this.content = content;
        }

        T content;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    @Override
    public String toString() {
        return type + getResult().getMsg() + getResult().getContent();
    }
}
