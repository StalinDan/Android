package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.activity.MainActivity;
import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

import java.util.List;

/**
 * Created by danish on 2018/3/23.
 */

public class DownLoadFaceDao extends BaseDao {
    public void downLoadFace(List workNoList, MainActivity.DownLoadCallBack callBack) {
        ArrayMap paramsMap = new ArrayMap();
        paramsMap.put("workNos",workNoList);
//        runner.request(AsyncRequestRunner.RequestPOST,AsyncRequestRunner.HOST + "/face-feature/download",paramsMap,"DownLoadFaceDao",listener);
        runner.request(AsyncRequestRunner.RequestPOST,AsyncRequestRunner.HOST + "/face-feature/download",paramsMap,"DownLoadFaceDao",callBack);
    }
}
