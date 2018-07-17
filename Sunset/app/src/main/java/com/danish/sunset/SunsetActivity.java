package com.danish.sunset;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.danish.sunset.fragment.SingleFragmentActivity;
import com.danish.sunset.fragment.SunsetFragment;

public class SunsetActivity extends AppCompatActivity {

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
    }


//    @Override
//    protected Fragment createFragment() {
//        return new SunsetFragment();
//    }
}
