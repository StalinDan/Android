package com.danish.sunset.fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.danish.sunset.R;

/**
 * Created by danish on 2018/5/31.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {


    protected abstract Fragment createFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            Log.i("bb","ragment == null");
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        } else {
            Log.i("bb","bbb");
        }
    }

}
