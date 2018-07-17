package com.danish.attendance.bean;

import com.danish.attendance.net.BaseBean;

import java.util.List;

/**
 * Created by danish on 2018/6/7.
 */

public class BindDeviceInfoBean extends BaseBean {


    /**
     * type : 4104
     * result : {"plateNo":"沪A001","deviceVOS":[{"sid":1,"identifyCode":"V102176C0011","devType":1,"devUsableStatus":1,"bindTime":""},{"sid":2,"identifyCode":"H203175P0008","devType":2,"devUsableStatus":1,"bindTime":""},{"sid":3,"identifyCode":"W10217470103","devType":3,"devUsableStatus":1,"bindTime":""}]}
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
         * plateNo : 沪A001
         * deviceVOS : [{"sid":1,"identifyCode":"V102176C0011","devType":1,"devUsableStatus":1,"bindTime":""},{"sid":2,"identifyCode":"H203175P0008","devType":2,"devUsableStatus":1,"bindTime":""},{"sid":3,"identifyCode":"W10217470103","devType":3,"devUsableStatus":1,"bindTime":""}]
         */

        private String plateNo;
        private List<DeviceVOSBean> deviceVOS;

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public List<DeviceVOSBean> getDeviceVOS() {
            return deviceVOS;
        }

        public void setDeviceVOS(List<DeviceVOSBean> deviceVOS) {
            this.deviceVOS = deviceVOS;
        }

        public static class DeviceVOSBean {
            /**
             * sid : 1
             * identifyCode : V102176C0011
             * devType : 1
             * devUsableStatus : 1
             * bindTime :
             */

            private int sid;
            private String identifyCode;
            private int devType;
            private int devUsableStatus;
            private String bindTime;

            public int getSid() {
                return sid;
            }

            public void setSid(int sid) {
                this.sid = sid;
            }

            public String getIdentifyCode() {
                return identifyCode;
            }

            public void setIdentifyCode(String identifyCode) {
                this.identifyCode = identifyCode;
            }

            public int getDevType() {
                return devType;
            }

            public void setDevType(int devType) {
                this.devType = devType;
            }

            public int getDevUsableStatus() {
                return devUsableStatus;
            }

            public void setDevUsableStatus(int devUsableStatus) {
                this.devUsableStatus = devUsableStatus;
            }

            public String getBindTime() {
                return bindTime;
            }

            public void setBindTime(String bindTime) {
                this.bindTime = bindTime;
            }
        }
    }
}
