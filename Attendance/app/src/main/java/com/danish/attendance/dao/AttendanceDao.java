package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/8.
 */

public class AttendanceDao extends BaseDao {
    public void attendance(String workNo, String plateNo, String identifyCode, RequestListener listener) {

        ArrayMap params = new ArrayMap();
        params.put("workNo",workNo);
        params.put("plateNo",plateNo);
        params.put("identifyCode",identifyCode);

        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST +"/new-attendance/on-duty",params,"AttendanceDao",listener);
    }
}
