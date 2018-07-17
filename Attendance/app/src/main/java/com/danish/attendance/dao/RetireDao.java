package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/14.
 */

public class RetireDao extends BaseDao {
    public void retire(String workNo, RequestListener listener) {
        ArrayMap params = new ArrayMap();
        params.put("workNo",workNo);
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST + "/attendance/confirmBackGround",params,"RetireDao",listener);
    }
}
