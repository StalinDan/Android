package com.example.danish.baseproject.net;

import android.content.Intent;

import com.example.danish.baseproject.AsimsApplication;
import com.example.danish.baseproject.net.base.BaseBean;
import com.example.danish.baseproject.utils.MyLogger;
import com.google.gson.Gson;

import java.lang.reflect.Type;


public abstract class RequestListenerImpl<T extends BaseBean> implements RequestListener {

    @Override
    public void onFinish(String response) {
        try {

            MyLogger.i(response);
            Gson gson = new Gson();

            T responseBody = gson.fromJson(response, getTypeReference());

            if (responseBody == null || responseBody.getType() == null) {
                MyLogger.i("responseBody null -->  pageIN and pageOUT");
            } else if (responseBody.getType().equals("1")) {
                onSuccess(responseBody);
            } else if (responseBody.getType().equals("401")) {
                Intent intent = new Intent();
                intent.setAction("com.deayea.asims.Broadcast.401");
                AsimsApplication.getContext().sendBroadcast(intent);
            } else if (responseBody.getType().equals("4101") || responseBody.getType().equals("4102")) {
                type(responseBody.getType());
            } else {
                onFail(responseBody.getMsg());
                MyLogger.i(responseBody.getMsg());
            }
        } catch (Exception e) {
            onFail("解析错误" + e.toString());
            MyLogger.i(e.toString());
            e.printStackTrace();
        }

    }

    public void type(String type) {

    }

    public abstract Type getTypeReference();

    public abstract void onSuccess(T response);
}
