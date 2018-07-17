package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/14.
 */

public class RetireInfoDao extends BaseDao {
    public void retireInfo(String identifyCode, RequestListener listener) {
        ArrayMap params = new ArrayMap();
        params.put("identifyCode",identifyCode);
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST + "/new-attendance/off-duty-info",params,"RetireInfoDao",listener);
    }
}
