package com.danish.sunset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.danish.sunset.fragment.SingleFragmentActivity;
import com.danish.sunset.fragment.SunsetFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class SunsetActivity extends AppCompatActivity {


    public static final String TAG = "SunsetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            Log.i("bb","ragment == null");
            fragment = new SunsetFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        } else {
            Log.i("bb","bbb");
        }





        int m =  44;
        int [] count = new int[]{10,12,14,16,18,20,22,24,26,28};
        List list = new ArrayList();


        for (int a:count) {
            if (list.contains(m-a)) {
                Log.i(TAG,"包含--》"+a+"");
                Log.i(TAG,"第一个数的序号---》" + Arrays.binarySearch(count,a) + "第二个数的序号--》" + Arrays.binarySearch(count,(m-a)));

            } else {
                Log.i(TAG,"不包含--》"+a+"");
                list.add(a);
            }
        }


        String targetStr = "Hello world 你好";

        String [] strArr = targetStr.split(" ");
        StringBuilder builder0 = new StringBuilder();
        for (int i = strArr.length-1; i< strArr.length; i--) {
            if (i < 0) {
                break;
            }
            builder0.append(strArr[i] + " ");
        }

        Log.i(TAG,builder0.toString());

        String tempStr ;
        StringBuilder builder = new StringBuilder(targetStr);
        Log.i(TAG,targetStr.length()+"===");
//        for (int i = targetStr.length()-1;i < targetStr.length();i--) {
//            if (i < 0) {
//                break;
//            }
//            builder.append(targetStr.charAt(i));
//            Log.i(TAG,"---》"+builder.toString());
//        }
        tempStr = builder.reverse().toString();
        Log.i(TAG,"转换后的字符串---》"+tempStr);

    }


//    @Override
//    protected Fragment createFragment() {
//        return new SunsetFragment();
//    }
}
