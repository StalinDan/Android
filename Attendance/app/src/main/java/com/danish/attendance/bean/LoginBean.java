package com.danish.attendance.bean;

import com.danish.attendance.net.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by danish on 2018/6/26.
 */

public class LoginBean extends BaseBean {

    /**
     * type : 1
     * result : {"msg":"","content":{"token":"661cb65a-1755-4938-a95c-08f9e69bf485","rank":0,"userInfo":{"sid":2,"identifier":"zwk","realName":"张文凯","isPwdModified":"Y","coachTeamSidList":[3,4,5,6,14],"coachTeamCodeList":["HFCP_0002","HFCP_003","HFCP_004","HFCP_005","HFCP_011"],"rbSid":1,"rbCode":"RAIL_SH0001","rbName":"上海铁路局","ldSid":1,"ldCode":"SHJWD_0001","ldName":"合肥机务段","admin":true,"userType":0,"initStationList":["合肥南","合肥","合肥南所"]},"bmConf":{"havingDriverRanking":true}}}
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
         * msg :
         * content : {"token":"661cb65a-1755-4938-a95c-08f9e69bf485","rank":0,"userInfo":{"sid":2,"identifier":"zwk","realName":"张文凯","isPwdModified":"Y","coachTeamSidList":[3,4,5,6,14],"coachTeamCodeList":["HFCP_0002","HFCP_003","HFCP_004","HFCP_005","HFCP_011"],"rbSid":1,"rbCode":"RAIL_SH0001","rbName":"上海铁路局","ldSid":1,"ldCode":"SHJWD_0001","ldName":"合肥机务段","admin":true,"userType":0,"initStationList":["合肥南","合肥","合肥南所"]},"bmConf":{"havingDriverRanking":true}}
         */

        @SerializedName("msg")
        private String msgX;
        private ContentBean content;

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }

        public ContentBean getContent() {
            return content;
        }

        public void setContent(ContentBean content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * token : 661cb65a-1755-4938-a95c-08f9e69bf485
             * rank : 0
             * userInfo : {"sid":2,"identifier":"zwk","realName":"张文凯","isPwdModified":"Y","coachTeamSidList":[3,4,5,6,14],"coachTeamCodeList":["HFCP_0002","HFCP_003","HFCP_004","HFCP_005","HFCP_011"],"rbSid":1,"rbCode":"RAIL_SH0001","rbName":"上海铁路局","ldSid":1,"ldCode":"SHJWD_0001","ldName":"合肥机务段","admin":true,"userType":0,"initStationList":["合肥南","合肥","合肥南所"]}
             * bmConf : {"havingDriverRanking":true}
             */

            private String token;
            private int rank;
            private UserInfoBean userInfo;
            private BmConfBean bmConf;

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public UserInfoBean getUserInfo() {
                return userInfo;
            }

            public void setUserInfo(UserInfoBean userInfo) {
                this.userInfo = userInfo;
            }

            public BmConfBean getBmConf() {
                return bmConf;
            }

            public void setBmConf(BmConfBean bmConf) {
                this.bmConf = bmConf;
            }

            public static class UserInfoBean {
                /**
                 * sid : 2
                 * identifier : zwk
                 * realName : 张文凯
                 * isPwdModified : Y
                 * coachTeamSidList : [3,4,5,6,14]
                 * coachTeamCodeList : ["HFCP_0002","HFCP_003","HFCP_004","HFCP_005","HFCP_011"]
                 * rbSid : 1
                 * rbCode : RAIL_SH0001
                 * rbName : 上海铁路局
                 * ldSid : 1
                 * ldCode : SHJWD_0001
                 * ldName : 合肥机务段
                 * admin : true
                 * userType : 0
                 * initStationList : ["合肥南","合肥","合肥南所"]
                 */

                private int sid;
                private String identifier;
                private String realName;
                private String isPwdModified;
                private int rbSid;
                private String rbCode;
                private String rbName;
                private int ldSid;
                private String ldCode;
                private String ldName;
                private boolean admin;
                private int userType;
                private List<Integer> coachTeamSidList;
                private List<String> coachTeamCodeList;
                private List<String> initStationList;

                public int getSid() {
                    return sid;
                }

                public void setSid(int sid) {
                    this.sid = sid;
                }

                public String getIdentifier() {
                    return identifier;
                }

                public void setIdentifier(String identifier) {
                    this.identifier = identifier;
                }

                public String getRealName() {
                    return realName;
                }

                public void setRealName(String realName) {
                    this.realName = realName;
                }

                public String getIsPwdModified() {
                    return isPwdModified;
                }

                public void setIsPwdModified(String isPwdModified) {
                    this.isPwdModified = isPwdModified;
                }

                public int getRbSid() {
                    return rbSid;
                }

                public void setRbSid(int rbSid) {
                    this.rbSid = rbSid;
                }

                public String getRbCode() {
                    return rbCode;
                }

                public void setRbCode(String rbCode) {
                    this.rbCode = rbCode;
                }

                public String getRbName() {
                    return rbName;
                }

                public void setRbName(String rbName) {
                    this.rbName = rbName;
                }

                public int getLdSid() {
                    return ldSid;
                }

                public void setLdSid(int ldSid) {
                    this.ldSid = ldSid;
                }

                public String getLdCode() {
                    return ldCode;
                }

                public void setLdCode(String ldCode) {
                    this.ldCode = ldCode;
                }

                public String getLdName() {
                    return ldName;
                }

                public void setLdName(String ldName) {
                    this.ldName = ldName;
                }

                public boolean isAdmin() {
                    return admin;
                }

                public void setAdmin(boolean admin) {
                    this.admin = admin;
                }

                public int getUserType() {
                    return userType;
                }

                public void setUserType(int userType) {
                    this.userType = userType;
                }

                public List<Integer> getCoachTeamSidList() {
                    return coachTeamSidList;
                }

                public void setCoachTeamSidList(List<Integer> coachTeamSidList) {
                    this.coachTeamSidList = coachTeamSidList;
                }

                public List<String> getCoachTeamCodeList() {
                    return coachTeamCodeList;
                }

                public void setCoachTeamCodeList(List<String> coachTeamCodeList) {
                    this.coachTeamCodeList = coachTeamCodeList;
                }

                public List<String> getInitStationList() {
                    return initStationList;
                }

                public void setInitStationList(List<String> initStationList) {
                    this.initStationList = initStationList;
                }
            }

            public static class BmConfBean {
                /**
                 * havingDriverRanking : true
                 */

                private boolean havingDriverRanking;

                public boolean isHavingDriverRanking() {
                    return havingDriverRanking;
                }

                public void setHavingDriverRanking(boolean havingDriverRanking) {
                    this.havingDriverRanking = havingDriverRanking;
                }
            }
        }
    }
}
