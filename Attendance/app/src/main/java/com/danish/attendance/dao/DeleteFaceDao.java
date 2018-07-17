package com.danish.attendance.dao;

import android.support.v4.util.ArrayMap;

import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.BaseDao;
import com.danish.attendance.net.RequestListener;

import java.util.List;

/**
 * Created by danish on 2018/3/29.
 */

public class DeleteFaceDao extends BaseDao {

    public void deleteFaceData (String[] workNoList,RequestListener listener) {
        ArrayMap paramsMap = new ArrayMap();
        paramsMap.put("workNos",workNoList);
        runner.request(AsyncRequestRunner.RequestPOST,AsyncRequestRunner.HOST + "/face-feature/delete",paramsMap,"DeleteFaceDao",listener);
    }

}
