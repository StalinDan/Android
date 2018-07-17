package watchdemo.deayea.com.watchdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmClockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        Button startBtn = findViewById(R.id.startBtn);
        Button stopBtn = findViewById(R.id.stopBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AlarmClockActivity.this, AlarmReceiver.class);
                intent.setAction("startAlarm");
                PendingIntent sender=
                        PendingIntent.getBroadcast(AlarmClockActivity.this, 0, intent, 0);

                //设定一个五秒后的时间
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 3);

                AlarmManager alarm=(AlarmManager)getSystemService(ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                //或者以下面方式简化
                //alarm.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+5*1000, sender);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent =new Intent(AlarmClockActivity.this, AlarmReceiver.class);
                intent.setAction("cancelAlarm");
                sendBroadcast(intent);

                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

    }

}
