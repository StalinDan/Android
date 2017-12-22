package com.example.danish.servicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by danish on 2017/12/20.
 */

public class MyService extends Service {

    public static final String TAG = "MyService";
    private MyBinder myBinder = new MyBinder();

    @Override
    public void onCreate() {


        super.onCreate();
        Log.d(TAG, "onCreate() executed");
        Log.d(TAG, "process id is " + android.os.Process.myPid());

//        Log.d(TAG,"MyService thread id is " + Thread.currentThread().getId());

//        Notification notification = new Notification(R.drawable.ic_launcher_background,"有通知到来",System.currentTimeMillis());
//        Intent notificationIntent = new Intent(this,MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
//        startForeground(1,notification);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("标题")
                .setContentText("通知内容")
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_VIBRATE);
//
//        try {
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,mBuilder.build());



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    class MyBinder extends Binder {

        public void startDownLoad() {
            //
            Log.d(TAG,"startDownLoad");
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();
        }
    }

    MyAidlService.Stub mBinder = new MyAidlService.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        public String toUpperCase(String string) throws RemoteException {
            if (string != null) {
                return string;
            }else {
                return null;
            }
        }

        public int plus(int a,int b) throws RemoteException{
            return a+b;
        }

    };

}
