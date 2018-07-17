package com.danish.attendance.net;

import java.io.Serializable;


public class BaseBean implements Serializable {

    String type = "";
    String msg = "";

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

}
