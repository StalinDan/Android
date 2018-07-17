package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/20.
 */

public class ScanDeviceCodeDao extends BaseDao {
    public void scanDeviceCode (String identifyCode, RequestListener listener) {
        ArrayMap params = new ArrayMap();
        params.put("identifyCode",identifyCode);
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST + "/new-attendance/driver-bind-info-by-identify-code",params,"ScanDeviceCodeDao",listener);
    }
}
