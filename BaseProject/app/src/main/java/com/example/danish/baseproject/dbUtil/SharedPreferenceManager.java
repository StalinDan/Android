package com.example.danish.baseproject.dbUtil;

import com.example.danish.baseproject.AsimsApplication;

/**
 * Created by LHG on 2017/4/17.
 */

public class SharedPreferenceManager {

    private static final String USERTOKEN = "USERTOKEN";
    private static final String UUIDSTRING = "UUIDSTRING";
    private static final String ACCOUNT = "ACCOUNT";
    private static final String USERTPYE = "USERTPYE";
    private static final String PASSWORD = "PASSWORD";
    private static final String DEPTS = "DEPTS";
    private static final String ALLDEPT = "ALLDEPT";
    private static final String MONITORDEPT = "MONITORDEPT";
    private static final String WARNDEPT = "WARNDEPT";
    private static final String RANKINGDEPT = "RANKINGDEPT";
    private static final String NAME = "name";
    private static final String SID = "sid";

    private static final String SHOW_DRIVER_RANKING = "SHOW DRIVER RANKING";

    private static final String CAN_SEE_DAY = "CANSEEDAY";


    private static AsimsApplication getContext() {
        return AsimsApplication.getContext();
    }

    public static String getUUID() {
        return PreferencesUtil.getSharedPreferences(getContext(), UUIDSTRING, "");
    }

    public static void setUUID(String uuid) {
        PreferencesUtil.setEditor(getContext(), UUIDSTRING, uuid);
    }

    public static String getUsertoken() {
        return PreferencesUtil.getSharedPreferences(getContext(), USERTOKEN, "");
    }

    public static void setUsertoken(String usertoken) {
        PreferencesUtil.setEditor(getContext(), USERTOKEN, usertoken);
    }

    public static void setAccount(String account) {
        PreferencesUtil.setEditor(getContext(), ACCOUNT, account);
    }

    public static String getAccount() {
        return PreferencesUtil.getSharedPreferences(getContext(), ACCOUNT, "");
    }

    public static void setPASSWORD(String password) {
        PreferencesUtil.setEditor(getContext(), PASSWORD, password);
    }

    public static String getPASSWORD() {
        return PreferencesUtil.getSharedPreferences(getContext(), PASSWORD, "");
    }

    public static String getDEPTS() {
        return PreferencesUtil.getSharedPreferences(getContext(), DEPTS, "");
    }

    public static void setDEPTS(String depts) {
        PreferencesUtil.setEditor(getContext(), DEPTS, depts);
    }

    public static String getUSERTPYE() {
        return PreferencesUtil.getSharedPreferences(getContext(), USERTPYE, "");
    }

    public static void setUserType(String userType)
    {
        PreferencesUtil.setEditor(getContext(), USERTPYE, userType);
    }

    public static String getAlldept() {
        return PreferencesUtil.getSharedPreferences(getContext(), ALLDEPT, "");
    }

    public static void setAlldept(String alldept)
    {
        PreferencesUtil.setEditor(getContext(), ALLDEPT, alldept);
    }

    public static String getMonitordept() {
        return PreferencesUtil.getSharedPreferences(getContext(), MONITORDEPT, "");
    }

    public static void setMonitordept(String monitordept)
    {
        PreferencesUtil.setEditor(getContext(), MONITORDEPT, monitordept);
    }


    public static String getWarndept() {
        return PreferencesUtil.getSharedPreferences(getContext(), WARNDEPT, "");
    }

    public static void setWarndept(String warnDept)
    {
        PreferencesUtil.setEditor(getContext(), WARNDEPT, warnDept);
    }

    public static String getName() {
        return PreferencesUtil.getSharedPreferences(getContext(), NAME, "");
    }

    public static void setName(String name)
    {
        PreferencesUtil.setEditor(getContext(), NAME, name);
    }

    public static String getSID() {
        return PreferencesUtil.getSharedPreferences(getContext(), SID, "");
    }

    public static void setSID(String sid)
    {
        PreferencesUtil.setEditor(getContext(), SID, sid);
    }

    public static String getRankingdept() {
        return PreferencesUtil.getSharedPreferences(getContext(), RANKINGDEPT, "");
    }

    public static void setRankingdept(String sid) {
        PreferencesUtil.setEditor(getContext(), RANKINGDEPT, sid);
    }
    public static boolean isShowDriverRanking() {
        return PreferencesUtil.getSharedPreferences(getContext(), SHOW_DRIVER_RANKING, false);
    }

    public static void setShowDriverRanking(boolean showDriverRanking) {
        PreferencesUtil.setEditor(getContext(), SHOW_DRIVER_RANKING, showDriverRanking);
    }

    public static int getCanSeeDay() {
        return PreferencesUtil.getSharedPreferences(getContext(), CAN_SEE_DAY, 0);
    }

    public static void setCanSeeDay(int canSeeDay) {

        PreferencesUtil.setEditor(getContext(), CAN_SEE_DAY, canSeeDay);
    }
}
