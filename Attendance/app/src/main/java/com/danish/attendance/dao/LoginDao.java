package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

/**
 * Created by danish on 2018/6/26.
 */

public class LoginDao extends BaseDao {
    public void login(String username, String password, RequestListener listener) {
        ArrayMap params = new ArrayMap();
        params.put("username",username);
        params.put("password",password);
        runner.request(AsyncRequestRunner.RequestPOST,AsyncRequestRunner.HOST + "/app/login",params,"LoginDao",listener);
    }
}
