package watchdemo.deayea.com.watchdemo;

import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by danish on 2018/4/20.
 */

public class WatchApplication extends Application {
    private static WatchApplication watchApplication;

    private KeyguardManager km;
    private KeyguardManager.KeyguardLock kl;
    private PowerManager pm;
    private PowerManager.WakeLock wl;

    Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        watchApplication = WatchApplication.this;



    }

    public static WatchApplication getContext() {
        return watchApplication;
    }

    public void goToForeground () {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(getApplicationContext().getPackageName(), MainActivity.class.getName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);

    }

    public void openAPP(String appPackageName){
        try{
            //如果有，直接打开
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(this, "没有安装", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
                Log.i(context.getPackageName(), "此appimportace ="
                        + appProcess.importance
                        + ",context.getClass().getName()="
                        + context.getClass().getName());
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i(context.getPackageName(), "处于后台"
                            + appProcess.processName);
                    return true;
                } else {
                    Log.i(context.getPackageName(), "处于前台"
                            + appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }


    public void wakeAndUnlock(boolean b) {
        if(b) {
            if (isBackground(this)) {
                Log.i("aa","在后台");
                openAPP("watchdemo.deayea.com.watchdemo");
            }

            //获取电源管理器对象
            pm=(PowerManager) getSystemService(Context.POWER_SERVICE);
            //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
            wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

            //点亮屏幕
            wl.setReferenceCounted(false);
            wl.acquire();

            //得到键盘锁管理器对象
            km= (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
            kl = km.newKeyguardLock("unLock");

            //解锁
            if (km.inKeyguardRestrictedInputMode()) {
                // 解锁键盘
                kl.disableKeyguard();
            }

//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        } else {
            //锁屏
            kl.reenableKeyguard();

            if (wl != null) {
                //释放wakeLock，关灯
                wl.release();
                wl = null;
            }

        }

    }


}
