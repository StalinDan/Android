package com.example.danish.baseproject;

import android.app.Application;

import com.example.danish.baseproject.utils.CrashHandler;
import com.facebook.stetho.Stetho;

/**
 * Created by LHG on 2017/4/13.
 */

public class AsimsApplication extends Application {


    private static AsimsApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Stetho.initializeWithDefaults(this);
        CrashHandler.getInstance().init(this);

    }

    public static AsimsApplication getContext()
    {
        return mApplication;
    }

}
