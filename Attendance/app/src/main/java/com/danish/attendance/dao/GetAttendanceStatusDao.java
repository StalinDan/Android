package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/12.
 */

public class GetAttendanceStatusDao extends BaseDao {
    public void getAttendanceStatus (String workNo, RequestListener listener) {
        ArrayMap params = new ArrayMap();
        params.put("workNo",workNo);
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST + "/new-attendance/driver-info",params,"GetAttendanceStatusDao",listener);
    }
}
