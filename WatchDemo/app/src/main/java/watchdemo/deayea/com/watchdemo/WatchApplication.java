package watchdemo.deayea.com.watchdemo;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by danish on 2018/4/20.
 */

public class WatchApplication extends Application {
    private static WatchApplication watchApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        watchApplication = WatchApplication.this;

        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(new ComponentName(getApplicationContext().getPackageName(), MainActivity.class.getName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);


    }

    public static WatchApplication getContext() {
        return watchApplication;
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

}
