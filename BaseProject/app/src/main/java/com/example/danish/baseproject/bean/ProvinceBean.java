package com.example.danish.baseproject.bean;

import com.example.danish.baseproject.net.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by danish on 2017/11/29.
 */

public class ProvinceBean extends BaseBean {

    private int id;
    private String name;

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

//    public List<ProvinceData> getList() {
//        return list;
//    }
//
//
//    List<ProvinceData> list;
//
//
//
//    public class ProvinceData implements Serializable {
//        private int id;
//        private String name;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }


    @Override
    public String toString() {


        return getName();
    }
}
