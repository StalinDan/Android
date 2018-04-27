package watchdemo.deayea.com.watchdemo;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import android.app.Service;

/**
 * Created by danish on 2018/4/24.
 */

public class AlarmReceiver extends BroadcastReceiver {

//    private KeyguardManager km;
//    private KeyguardManager.KeyguardLock kl;
//    private PowerManager pm;
//    private PowerManager.WakeLock wl;

    Vibrator vibrator;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        mContext = context;
        vibrator = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
        WatchApplication watchApplication = WatchApplication.getContext();

//        //获取电源管理器对象
//        pm=(PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
//        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
//        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
//
//        //得到键盘锁管理器对象
//        km= (KeyguardManager)mContext.getSystemService(Context.KEYGUARD_SERVICE);
//        kl = km.newKeyguardLock("unLock");


        if(intent.getAction().equals("startAlarm")){
//            Toast.makeText(context, "short alarm", Toast.LENGTH_LONG).show();
            Log.i("AlarmReceiver","startAlarm");
//            wakeAndUnlock(true);

            watchApplication.wakeAndUnlock(true);
            long[] pattern = { 10, 10000 };
            vibrator.vibrate(pattern,0);
        }else if (intent.getAction().equals("cancelAlarm")){
            vibrator.cancel();
            watchApplication.wakeAndUnlock(false);
//            wakeAndUnlock(false);
//            Toast.makeText(context, "repeating alarm",Toast.LENGTH_LONG).show();
            Log.i("AlarmReceiver","cancelAlarm");
        }
    }

//    private void wakeAndUnlock(boolean b) {
//        if(b) {
//            //点亮屏幕
//            wl.setReferenceCounted(false);
//            wl.acquire();
//            //解锁
//            if (km.inKeyguardRestrictedInputMode()) {
//                // 解锁键盘
//                kl.disableKeyguard();
//            }
//
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        } else {
//            //锁屏
//            if (kl != null) {
//                Log.i("AlarmReceiver","kl不为空");
//                if (!km.inKeyguardRestrictedInputMode()) {
//                    Log.i("AlarmReceiver","锁屏");
//                    kl.reenableKeyguard();
//                }
//            }
//
//            //释放wakeLock，关灯
//            if (wl != null) {
//                Log.i("AlarmReceiver","wl不为空");
////                if (wl.isHeld()) {
////                    Log.i("AlarmReceiver","wl没有释放");
////                    wl.release();
////                }
//                wl.release();
//                wl = null;
//
//            }
//        }
//    }
}

