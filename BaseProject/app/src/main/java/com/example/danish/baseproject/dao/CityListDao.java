package com.example.danish.baseproject.dao;

import com.example.danish.baseproject.net.AsyncRequestRunner;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.base.BaseDao;

/**
 * Created by danish on 2017/11/29.
 */

public class CityListDao extends BaseDao {
    public void cityData(RequestListener listener) {
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST,null,"cityData",listener);
    }
}
