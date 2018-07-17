package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/7.
 */

public class BindDeviceDao extends BaseDao {
    public void getBindInfo(String plateNo, RequestListener listener) {
        ArrayMap params = new ArrayMap();
        params.put("plateNo",plateNo);
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST + "/new-attendance/driver-bind-info-by-plate-no",params,"BindDeviceDao",listener);
    }
}
