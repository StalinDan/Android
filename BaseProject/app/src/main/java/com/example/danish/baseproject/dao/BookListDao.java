package com.example.danish.baseproject.dao;

import android.support.v4.util.ArrayMap;

import com.example.danish.baseproject.net.AsyncRequestRunner;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.base.BaseDao;

/**
 * Created by danish on 2017/12/5.
 */

public class BookListDao extends BaseDao {
    public void bookListData(String bookName,RequestListener requestListener) {

        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("q",bookName);

        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST+"book/search",arrayMap,"bookListData",requestListener);
    }
}
