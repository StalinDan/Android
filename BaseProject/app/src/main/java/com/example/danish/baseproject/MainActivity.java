package com.example.danish.baseproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.danish.baseproject.bean.ProvinceBean;
import com.example.danish.baseproject.dao.CityListDao;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.RequestListenerImpl;
import com.example.danish.baseproject.net.base.BaseBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import com.google.gson.JsonArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CityListDao().cityData(new RequestListener() {
            @Override
            public void onFinish(String response) {
                Log.d("MainActivity","response---->"+ String.valueOf(response));


                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(response).getAsJsonArray();

                Gson gson = new Gson();
                List<ProvinceBean> list = new ArrayList<>();
                for (JsonElement element:jsonArray) {
                    ProvinceBean provinceBean = gson.fromJson(element,ProvinceBean.class);
                    list.add(provinceBean);

                    Log.d("MainActivity","provinceBean--->"+provinceBean);
                }


            }

            @Override
            public void onFail(String errMsg) {
                Log.d("MainActivity","请求失败"+errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                Log.d("MainActivity","请求失败" +e.toString());
            }
        });


//        new CityListDao().cityData(new RequestListenerImpl<ProvinceBean>() {
//            @Override
//            public Type getTypeReference() {
//                return new TypeToken<ProvinceBean>(){}.getType();
//            }
//
//            @Override
//            public void onSuccess(ProvinceBean response) {
//
//                List<ProvinceBean> list = new ArrayList<>();
//
//                Log.d("MainActivity","response---->"+ String.valueOf(response));
//            }
//
//            @Override
//            public void onFail(String errMsg) {
//                Log.d("MainActivity","请求失败"+errMsg);
//            }
//
//            @Override
//            public void onNetError(Throwable e) {
//
//                Log.d("MainActivity","请求失败" +e.toString());
//            }
//        });

    }
}
