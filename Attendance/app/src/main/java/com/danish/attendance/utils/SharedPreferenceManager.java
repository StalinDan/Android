package com.danish.attendance.utils;


import com.danish.attendance.AttendanceApplication;

/**
 * Created by LHG on 2017/4/17.
 */

public class SharedPreferenceManager {

    private static final String BIND_DEVICE = "BIND_DEVICE";
    private static final String BIND_NUMBER = "BIND_NUMBER";
    private static final String HOST = "HOST";
    private static final String INTERVAL = "INTERVAL";
    private static final String TIMEOUT = "TIMEOUT";
    private static final String NAME = "NAME";

    private static final String SID = "SID";
    private static final String TRAINNO = "TRAINNO";

    private static final String UUIDSTRING = "UUIDSTRING";

    private static AttendanceApplication getContext() {
        return AttendanceApplication.getContext();
    }

    public static String getBindDevice() {
        return PreferencesUtil.getSharedPreferences(getContext(), BIND_DEVICE, "");
    }

    public static void setBindDevice(String macAdd) {
        PreferencesUtil.setEditor(getContext(), BIND_DEVICE, macAdd);
    }

    public static String getBindNumber() {
        return PreferencesUtil.getSharedPreferences(getContext(), BIND_NUMBER, "");
    }

    public static void setBindNumber(String number) {
        PreferencesUtil.setEditor(getContext(), BIND_NUMBER, number);
    }


    public static String getHost() {
        return PreferencesUtil.getSharedPreferences(getContext(), HOST, "");
    }

    public static void setHost(String host) {
        PreferencesUtil.setEditor(getContext(), HOST, host);
    }

    public static String getInterval() {
        return PreferencesUtil.getSharedPreferences(getContext(), INTERVAL, "");
    }

    public static void setInterval(String interval) {
        PreferencesUtil.setEditor(getContext(), INTERVAL, interval);
    }

    public static String getTimeout() {
        return PreferencesUtil.getSharedPreferences(getContext(), TIMEOUT, "");
    }

    public static void setTimeout(String timeout) {
        PreferencesUtil.setEditor(getContext(), TIMEOUT, timeout);
    }

    public static String getName() {
        return PreferencesUtil.getSharedPreferences(getContext(), NAME, "");
    }

    public static void setName(String name) {
        PreferencesUtil.setEditor(getContext(), NAME, name);
    }

    public static void setSID(String sid) {
        PreferencesUtil.setEditor(getContext(), SID, sid);
    }

    public static String getSID() {
        return PreferencesUtil.getSharedPreferences(getContext(), SID, "");
    }

    public static String getTRAINNO() {
        return PreferencesUtil.getSharedPreferences(getContext(), TRAINNO, "");
    }

    public static void setTRAINNO(String trainno) {
        PreferencesUtil.setEditor(getContext(), TRAINNO, trainno);
    }

    public static String getUUID() {
        return PreferencesUtil.getSharedPreferences(getContext(), UUIDSTRING, "");
    }

    public static void setUUID(String uuid) {
        PreferencesUtil.setEditor(getContext(), UUIDSTRING, uuid);
    }
}
