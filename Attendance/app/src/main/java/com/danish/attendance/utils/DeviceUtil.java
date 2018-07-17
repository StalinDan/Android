package com.danish.attendance.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;


import com.danish.attendance.AttendanceApplication;

import java.util.UUID;

/**
 * Created by LHG on 2017/4/17.
 */

public class DeviceUtil {

    public static String getDeviceId() {

        Context context = AttendanceApplication.getContext();

        StringBuilder deviceId = new StringBuilder();
        try {

//            //IMEI（imei）
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            String imei = tm.getDeviceId();
//            if(!isEmpty(imei)){
//                deviceId.append(imei);
//                return deviceId.toString();
//            }
//            //序列号（sn）
//            String sn = tm.getSimSerialNumber();
//            if(!isEmpty(sn)){
//                deviceId.append(sn);
//                return deviceId.toString();
//            }
//
//            //wifi mac地址
//            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            String wifiMac = info.getMacAddress();
//            if(!isEmpty(wifiMac)){
//                deviceId.append(wifiMac);
//                return deviceId.toString();
//            }

            //如果上面都没有， 则生成一个id：随机码
            String uuid = getUUID(context);
            if(!isEmpty(uuid)){
                deviceId.append("id");
                deviceId.append(uuid);
                return deviceId.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append("id").append(getUUID(context));
        }
        return deviceId.toString();
    }
    /**
     * 得到全局唯一UUID
     */
    private static String getUUID(Context context){

        String uuid ="";//= SharedPreferenceManager.getUUID();

        if(isEmpty(uuid)){
            uuid = UUID.randomUUID().toString();
            //SharedPreferenceManager.setUUID(uuid);
        }
        return uuid;
    }

    private static boolean isEmpty(String str)
    {
        if(str == null || str.equals(""))
        {
            return true;
        }
        return false;
    }
}
