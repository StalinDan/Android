package com.example.danish.servicedemo;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startService,stopService,bindService,unBindService;
    private MyService.MyBinder myBinder;
    private MyAidlService myAidlService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder)service;
            myBinder.startDownLoad();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = findViewById(R.id.start);
        stopService = findViewById(R.id.stop);
        bindService = findViewById(R.id.bind_service);
        unBindService = findViewById(R.id.unbind_service);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unBindService.setOnClickListener(this);

        Log.d("MainActivity","MainActivity thread id is " + Thread.currentThread().getId());
        Log.d("MainActivity", "process id is " + android.os.Process.myPid());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);
                break;

            case R.id.stop:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;

            case R.id.bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                break;

            case R.id.unbind_service:
                unbindService(connection);
                break;

            default:
                break;
        }
    }
}
