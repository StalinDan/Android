package watchdemo.deayea.com.watchdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Vibrator vibrator;
    PowerManager.WakeLock wl;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.flags |= (WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(0);


        Button startBtn = findViewById(R.id.startBtn);
        Button stopBtn = findViewById(R.id.stopBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        vibrator.vibrate(1000*60*60*24);
                        wakeUpAndUnlock();
                        long[] pattern = { 10, 10000 };
                        vibrator.vibrate(pattern,0);
                    }
                },30000);

//                vibrator.vibrate(1000*60*60*24);


            }
        });


        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.cancel();
            }
        });

    }


    /**
     * 唤醒手机屏幕并解锁
     */
    public void wakeUpAndUnlock() {
        Log.i("aa","wakeUpAndUnlock");
//        openApplicationFromBackground(this);

        WatchApplication application = WatchApplication.getContext();
        application.openAPP("watchdemo.deayea.com.watchdemo");

        // 获取电源管理器对象
        PowerManager pm = (PowerManager) this.getSystemService(POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {

            Log.i("aa","点亮屏幕");
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(10000); // 点亮屏幕
            wl.release(); // 释放

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) this.getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
//        // 屏幕锁定
//        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }


//    /**
//     * 打开应用. 应用在前台不处理,在后台就直接在前台展示当前界面, 未开启则重新启动
//     */
//    public void openApplicationFromBackground(Context context) {
//        Intent intent;
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
//        if (!list.isEmpty() && list.get(0).topActivity.getPackageName().equals(context.getPackageName())) {
//            //此时应用正在前台, 不作处理
//            return;
//        }
//        for (ActivityManager.RunningTaskInfo info : list) {
//            if (info.topActivity.getPackageName().equals(context.getPackageName())) {
//                intent = new Intent();
//                intent.setComponent(info.topActivity);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                if (! (context instanceof Activity)) {
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                }
//                context.startActivity(intent);
//                return;
//            }
//        }
//        intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//        context.startActivity(intent);
//
//    }
}
