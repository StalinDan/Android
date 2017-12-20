package com.example.danish.baseproject.dao;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;

import com.example.danish.baseproject.net.AsyncRequestRunner;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.base.BaseDao;

/**
 * Created by danish on 2017/12/18.
 */

public class VideoListDao extends BaseDao {

    public void videoListData(int categoryId, int page, int pageSize, RequestListener listener) {

        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("categoryId",categoryId+"");
        arrayMap.put("page",page+"");
        arrayMap.put("size",pageSize+"");

        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST,arrayMap,"videoListData",listener);
    }
}
