package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/13.
 */

public class DriverListDao extends BaseDao {
    public void getDriverListData(int pageNo, int pageSize, String param, RequestListener listener) {
        ArrayMap params = new ArrayMap();
//        params.put("pageNo",pageNo);
//        params.put("pageSize",pageSize);
//        params.put("param",param);
        runner.request(AsyncRequestRunner.RequestPOST,AsyncRequestRunner.HOST + "/face-feature/driver-list?pageNo=" + pageNo + "&pageSize=10" + "&param=" + param,params,"DriverListDao",listener);
    }
}
