package com.danish.attendance.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danish.attendance.R;
import com.danish.attendance.bean.RetireInfoBean;
import com.danish.attendance.dao.RetireDao;
import com.danish.attendance.dao.RetireInfoDao;
import com.danish.attendance.net.RequestListener;
import com.danish.attendance.net.RequestListenerImpl;
import com.danish.attendance.utils.MyLogger;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RetireActivity extends BaseActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.workNo)
    TextView workNo;
    @BindView(R.id.trainNo)
    TextView trainNo;
//    @BindView(R.id.attendanceNo)
//    TextView attendanceNo;
    @BindView(R.id.attendanceTime)
    TextView attendanceTime;
    @BindView(R.id.wearTime)
    TextView wearTime;
    @BindView(R.id.statusAvg)
    TextView statusAvg;
    @BindView(R.id.blueWarn)
    TextView blueWarn;
    @BindView(R.id.redWarn)
    TextView redWarn;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.lastBtn)
    Button lastBtn;
    @BindView(R.id.retireBtn)
    Button retireBtn;

    RetireInfoDao retireInfoDao;
    RetireDao retireDao;
    RetireInfoBean.ResultBean  infoBean;

    Dialog mDialog;
    private TextView timeTextView ;
//    private Handler mHandler;
    Timer timer = new Timer();
    int countTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retire);
        ButterKnife.bind(this);

        retireInfoDao = new RetireInfoDao();
        retireDao = new RetireDao();

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RetireActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        retireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infoBean != null && infoBean.getWorkNo().length() > 0) {
                    //测试 要解注释
                    goRetire(infoBean.getWorkNo());
//                    showDialog();
                }
            }
        });

        Intent intent = getIntent();
        String identifyCode = intent.getStringExtra("identifyCode");
        if (identifyCode != null && identifyCode.length() > 0) {

            getRetireInfo(identifyCode);

        }



    }


    /**
     * 获取退勤信息
     * @param identifyCode
     */
    private void getRetireInfo (String identifyCode) {
        retireInfoDao.retireInfo(identifyCode, new RequestListenerImpl<RetireInfoBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<RetireInfoBean>(){}.getType();
            }

            @Override
            public void onSuccess(RetireInfoBean response) {
                if (response.getType().equals("1")) {
                    infoBean = response.getResult();
                    updateInfo(infoBean);
                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
            }
        });
    }

    /**
     * 退勤
     * @param workNo
     */
    private void goRetire (String workNo) {
        retireDao.retire(workNo, new RequestListener() {
            @Override
            public void onFinish(String response) {

                MyLogger.i("退勤成功");
                showDialog();
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
                showNetErrToast();
            }
        });
    }

    private void updateInfo (RetireInfoBean.ResultBean bean) {
        name.setText(bean.getName());
        workNo.setText(bean.getWorkNo());
        trainNo.setText(bean.getPlateNo());
        wearTime.setText(bean.getWearingTime()+"分钟");
        statusAvg.setText(bean.getAvgFatigue()+"");
        blueWarn.setText(bean.getBlueCnt()+"次");
        redWarn.setText(bean.getRedCnt()+"次");
        attendanceTime.setText(bean.getTime());
    }

    private void showDialog(){

        timeTextView = new TextView(this);
        timeTextView.setGravity(Gravity.CENTER);
        timeTextView.setTextSize(20);
//        timeTextView.setTextColor(0x333333);

        mDialog = new AlertDialog.Builder(this)
                .setTitle("退勤成功")
                .setCancelable(false)
                .setView(timeTextView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timer.cancel();
                        Intent intent = new Intent(RetireActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .create();
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        timer.schedule(timerTask,0,1000);

    }

    Handler mHandler = new Handler(){
        public void handleMessage(Message message) {
            super.handleMessage(message);

            if (message.what > 0){
                timeTextView.setText(message.what+"秒后自动关闭");
            } else {
                MyLogger.i("倒计时自动关闭");
                if (mDialog != null) {
                    mDialog.dismiss();
                    Intent intent = new Intent(RetireActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                timer.cancel();
            }
        }
    };

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            mHandler.sendEmptyMessage(countTime);

            if (countTime > 0) {
                countTime--;
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
