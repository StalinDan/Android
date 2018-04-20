package intentdemo.deayea.com.intentdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PendingIntent pi;
    private AlarmManager alarmManager;
    Button setAarmBtn;
    Button closeAarmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btn);




       final Intent intent = new Intent(MainActivity.this,FirstActivity.class);

//        //传递数组
//        Bundle bundle = new Bundle();
//        bundle.putStringArray("array",new String[]{"嘻嘻","哈哈"});
//        intent.putExtras(bundle);



        //传递集合
        List list = new ArrayList();
        list.add("apple");
        list.add("orange");
        list.add("pieapple");
        intent.putStringArrayListExtra("aa", (ArrayList<String>) list);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YhdSingleton.getInstance().put("ABC","abc");
                startActivity(intent);
            }
        });


        setAarmBtn = findViewById(R.id.setAlarm);
        closeAarmBtn = findViewById(R.id.closeAlarm);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent intent1 = new Intent(this,AlarmActivity.class);
//        pi = PendingIntent.getActivity(this,0,intent1,0);

       pi = PendingIntent.getService(this,0,new Intent(this, AlarmActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) //Android 6，针对省电优化
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000, pi);
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) //Android 4.4，针对set不准确
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, pi);
        else
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1000, pi);

        setAarmBtn.setOnClickListener(this);
        closeAarmBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setAlarm:
                Calendar currentTime = Calendar.getInstance();
                new TimePickerDialog(MainActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                //设置当前时间
                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());
                                // 根据用户选择的时间来设置Calendar对象
                                c.set(Calendar.HOUR, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                // ②设置AlarmManager在Calendar对应的时间启动Activity
                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                Log.i("HEHE",c.getTimeInMillis()+"");   //这里的时间是一个unix时间戳
                                // 提示闹钟设置完毕:
                                Toast.makeText(MainActivity.this, "闹钟设置完毕~"+ c.getTimeInMillis(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime
                        .get(Calendar.MINUTE), false).show();
                closeAarmBtn.setVisibility(View.VISIBLE);

//                PendingIntent pi = PendingIntent.getService(this,0,new Intent(this, AlarmActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

                break;
            case R.id.closeAlarm:
                alarmManager.cancel(pi);
                closeAarmBtn.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "闹钟已取消", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }
}
