package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/8.
 */

public class QueryTrainNoDao extends BaseDao {
    public void queryTrainNo(String plateNo, RequestListener listener){

        ArrayMap params = new ArrayMap();
        params.put("plateNo",plateNo);
        runner.request(AsyncRequestRunner.RequestGET,"/new-attendance/query-like-plant-no",params,"QueryTrainNoDao",listener);
    }
}
