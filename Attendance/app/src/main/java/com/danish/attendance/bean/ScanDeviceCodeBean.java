package com.danish.attendance.bean;

import com.danish.attendance.net.BaseBean;

import java.util.List;

/**
 * Created by danish on 2018/6/20.
 */

public class ScanDeviceCodeBean extends BaseBean {

    /**
     * type : 4105
     * result : {"plateNo":"沪A001","deviceVOS":[{"sid":1,"identifyCode":"V102176C0011","devType":1,"devUsableStatus":1,"bindTime":""},{"sid":2,"identifyCode":"H203175P0008","devType":2,"devUsableStatus":1,"bindTime":""},{"sid":3,"identifyCode":"W10217470103","devType":3,"devUsableStatus":1,"bindTime":""}],"vehicleDriverVOS":[{"sid":1,"workNo":"CS001","name":"测试一","plateNo":"沪A001"},{"sid":33,"workNo":"21321312","name":"112312","plateNo":"沪A001"}]}
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
         * vehicleDriverVOS : [{"sid":1,"workNo":"CS001","name":"测试一","plateNo":"沪A001"},{"sid":33,"workNo":"21321312","name":"112312","plateNo":"沪A001"}]
         */

        private String plateNo;
        private int status; //车辆状态   0-表示车辆正常  1-车辆维修保养 2-车辆报废
        private List<DeviceVOSBean> deviceVOS;
        private List<VehicleDriverVOSBean> vehicleDriverVOS;

        public AttendanceInfoVOBean getAttendanceInfoVO() {
            return attendanceInfoVO;
        }

        public void setAttendanceInfoVO(AttendanceInfoVOBean attendanceInfoVO) {
            this.attendanceInfoVO = attendanceInfoVO;
        }

        private AttendanceInfoVOBean attendanceInfoVO;

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<DeviceVOSBean> getDeviceVOS() {
            return deviceVOS;
        }

        public void setDeviceVOS(List<DeviceVOSBean> deviceVOS) {
            this.deviceVOS = deviceVOS;
        }

        public List<VehicleDriverVOSBean> getVehicleDriverVOS() {
            return vehicleDriverVOS;
        }

        public void setVehicleDriverVOS(List<VehicleDriverVOSBean> vehicleDriverVOS) {
            this.vehicleDriverVOS = vehicleDriverVOS;
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


        public static class VehicleDriverVOSBean {
            /**
             * sid : 1
             * workNo : CS001
             * name : 测试一
             * plateNo : 沪A001
             */

            private int sid;
            private String workNo;
            private String name;
            private String plateNo;

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
        }

        public static class AttendanceInfoVOBean {

            private int sid;
            private String workNo;
            private String time;
            private String name;

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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
