package com.example.danish.baseproject.dao;

import com.example.danish.baseproject.net.AsyncRequestRunner;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.base.BaseDao;

/**
 * Created by danish on 2017/12/4.
 */

public class MovieListDao extends BaseDao {
    public void movieListData(RequestListener requestListener) {
        runner.request(AsyncRequestRunner.RequestGET,AsyncRequestRunner.HOST+"movie/in_theaters",null,"movieListData",requestListener);

    }
}
