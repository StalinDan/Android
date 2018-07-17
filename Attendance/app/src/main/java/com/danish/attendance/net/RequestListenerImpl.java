package com.danish.attendance.net;

import android.util.Log;

import com.danish.attendance.utils.MyLogger;
import com.google.gson.Gson;

import java.lang.reflect.Type;


public abstract class RequestListenerImpl<T extends BaseBean> implements RequestListener {

    @Override
    public void onFinish(String response) {
        try {
            MyLogger.i(response);
            Gson gson = new Gson();

            T responseBody = gson.fromJson(response ,getTypeReference());

            if(responseBody.getType().equals("1"))
            {
                Log.d("RequestListenerImpl","success----======");
                onSuccess(responseBody);
            }
            else {
                onFail(responseBody.getType(),responseBody.getMsg());
                MyLogger.i(responseBody.getMsg());
            }
        } catch (Exception e) {
            onFail("", "解析错误" + e.toString());
            MyLogger.i(e.toString());
            e.printStackTrace();
        }
    }
    public abstract Type getTypeReference();

    public abstract void onSuccess(T response);
}
