package com.danish.attendance.net;


/**
 * Created by LHG on 2017/4/6.
 */

public class BaseDao {

    public AsyncRequestRunner runner = new AsyncRequestRunner();
    private String tag = "";

    public void cancelRequest(String tag)
    {
        runner.cancel(tag);
    }
}
