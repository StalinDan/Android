package com.danish.attendance.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danish.attendance.R;
import com.danish.attendance.adapter.AttendanceAdapter;
import com.danish.attendance.bean.BindDeviceInfoBean;
import com.danish.attendance.bean.ScanDeviceCodeBean;
import com.danish.attendance.dao.AttendanceDao;
import com.danish.attendance.dao.BindDeviceDao;
import com.danish.attendance.dao.ScanDeviceCodeDao;
import com.danish.attendance.net.BaseBean;
import com.danish.attendance.net.RequestListenerImpl;
import com.danish.attendance.utils.MyLogger;
import com.danish.attendance.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceActivity extends BaseActivity {

    @BindView(R.id.deviceNO)
    EditText deviceNO;
    @BindView(R.id.scanCodeBtn)
    Button scanCodeBtn;
    @BindView(R.id.queryBtn)
    Button queryBtn;
    @BindView(R.id.trainNo)
    EditText trainNo;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.lastStepBtn)
    Button lastStepBtn;
    @BindView(R.id.attendanceBtn)
    Button attendanceBtn;
    @BindView(R.id.deviceRecyclerView)
    RecyclerView deviceRecyclerView;
    @BindView(R.id.attendanceRL)
    RelativeLayout attendanceRL;
    @BindView(R.id.errorMsg)
    TextView errorMsg;

    private List deviceList = new ArrayList();

    private static final int REQUEST_CODE = 10010;

    AttendanceAdapter attendanceAdapter;
    AttendanceDao attendanceDao;
    String plateNo; //车牌号
    String workNo; //工号
    String scanCodeStr = ""; //扫描到的设备号

    ScanDeviceCodeDao scanDeviceCodeDao;
    private Timer timer = new Timer();
    private int countTime = 5;
    private TextView timeTextView ;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);

        scanDeviceCodeDao = new ScanDeviceCodeDao();

        Intent intent = getIntent();
        plateNo = intent.getStringExtra("plateNo");
        workNo = intent.getStringExtra("workNo");
        MyLogger.i("plateNo-->" + plateNo + "workNo-->" + workNo);
        if (plateNo != null && plateNo.length() > 0) {
            getBindDeviceInfo(plateNo);
            trainNo.setText(plateNo);
        }

        //退出
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //上一步
        lastStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        attendanceAdapter = new AttendanceAdapter(this, deviceList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        deviceRecyclerView.setAdapter(attendanceAdapter);
        deviceRecyclerView.setLayoutManager(layoutManager);


        deviceNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showSoftInput(v);
            }
        });

        attendanceRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftInput(v);
            }
        });

        attendanceRL.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom - oldBottom < -1) {
                    //软键盘弹上去了
                    exitBtn.setVisibility(View.INVISIBLE);
                    lastStepBtn.setVisibility(View.INVISIBLE);
                    attendanceBtn.setVisibility(View.INVISIBLE);
                } else if (bottom - oldBottom > 1) {
                    //软键盘弹下去了
                    exitBtn.setVisibility(View.VISIBLE);
                    lastStepBtn.setVisibility(View.VISIBLE);
                    attendanceBtn.setVisibility(View.VISIBLE);
                }
            }
        });

//        final Intent scanCodeIntent = new Intent(this, CaptureActivity.class);
        final Intent scanCodeIntent = new Intent(this, ScanCodeActivity.class);
        scanCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftInput(v);
                startActivityForResult(scanCodeIntent, REQUEST_CODE);
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftInput(v);
                scanCodeStr = deviceNO.getText().toString();
                MyLogger.i("scanCodeStr--->"+scanCodeStr);

                if (scanCodeStr.length() > 0) {
                    getDeviceInfoByCode(scanCodeStr);
                } else {
                    if (scanCodeStr.length() > 0) {
                        getDeviceInfoByCode(scanCodeStr);
                    } else {
                        if (plateNo != null && plateNo.length() > 0) {
                            getBindDeviceInfo(plateNo);
                        } else {
                            showToast("请扫描设备号");
                        }
                    }

                }
            }
        });

        attendanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attendance();
            }
        });

    }

    /**
     * 根据车牌号获取车辆绑定的设备信息
     */
    private void getBindDeviceInfo(String plateNo) {
        errorMsg.setText("");
        BindDeviceDao bindDeviceDao = new BindDeviceDao();
        bindDeviceDao.getBindInfo(plateNo, new RequestListenerImpl<BindDeviceInfoBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<BindDeviceInfoBean>() {
                }.getType();
            }

            @Override
            public void onSuccess(BindDeviceInfoBean response) {

                if (response.getType().equals("1")) {
                    deviceList = response.getResult().getDeviceVOS();
                    MyLogger.i("绑定的设备--》" + deviceList.size());
                    attendanceAdapter.updateDeviceData(deviceList);

                    boolean isBroken = false;
                    for (int i = 0; i < deviceList.size(); i++) {
                        BindDeviceInfoBean.ResultBean.DeviceVOSBean deviceVOSBean = (BindDeviceInfoBean.ResultBean.DeviceVOSBean) deviceList.get(i);
                        if (deviceVOSBean.getDevUsableStatus() == 0) {
                            isBroken = true;
                            break;
                        }
                    }

                    if (!isBroken) {
                        blueBtn();
                    } else {
                        grayBtn();
                        errorMsg.setText("该车辆绑定的设备发生故障，不能办理出勤");
                    }
                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg+ "type--->"+type);
                grayBtn();
                if (type.equals("4101")) {
                    errorMsg.setText("未绑定任何设备");
                } else if (type.equals("4102")) {
                    errorMsg.setText("车辆绑定设备不全");
                } else if (type.equals("4103")) {
                    errorMsg.setText("车辆绑定设备异常");
                }  else if (type.equals("4104")) {
                    errorMsg.setText("车辆已出勤");
                }
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
                grayBtn();
            }
        });
    }

    /**
     * 出勤
     */
    private void attendance() {
        attendanceDao = new AttendanceDao();
        String identifyCodeStr = "";
        for (int i=0;i<deviceList.size();i++) {
            BindDeviceInfoBean.ResultBean.DeviceVOSBean deviceVOSBean = (BindDeviceInfoBean.ResultBean.DeviceVOSBean) deviceList.get(i);
            if (deviceVOSBean.getDevType() == 1) {
                identifyCodeStr = deviceVOSBean.getIdentifyCode();
            }
        }

        attendanceDao.attendance(workNo, (plateNo.length() == 0) ? "" : plateNo, identifyCodeStr, new RequestListenerImpl<BaseBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<BaseBean>() {
                }.getType();
            }

            @Override
            public void onSuccess(BaseBean response) {
                MyLogger.i("type===>"+ response.getType());
                if (response.getType().equals("1")) {
                    MyLogger.i("出勤成功");

                    showDialog();

                }  else {
                    MyLogger.i("出勤失败--》" + response.getMsg());
                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
                showToast(errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
            }
        });
    }


    /**
     * 扫码获取设备信息
     */
    private void getDeviceInfoByCode (String identifyCode) {
        deviceList.clear();
        errorMsg.setText("");

        scanDeviceCodeDao.scanDeviceCode(identifyCode, new RequestListenerImpl<ScanDeviceCodeBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<ScanDeviceCodeBean>(){}.getType();
            }

            @Override
            public void onSuccess(ScanDeviceCodeBean response) {
                String type = response.getType().toString();
                if (type.equals("1")) {
                    plateNo = response.getResult().getPlateNo();
                    if (plateNo.length() > 0) {
                        trainNo.setText(plateNo);
                    }

                    updateDeviceInfo(response);

                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
                if (type.equals("4101")) {
                    errorMsg.setText("设备没有绑定任何车辆");
                    grayBtn();
//                    showToast("设备没有绑定任何车辆");

                }
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
                showNetErrToast();
                grayBtn();
            }
        });
    }

    /**
     * 扫码后更新设备信息
     * @param response
     */
    private void updateDeviceInfo (ScanDeviceCodeBean response) {

        MyLogger.i("updateDeviceInfo");
        List arrayList = new ArrayList();
        arrayList = response.getResult().getDeviceVOS();

        for (int i = 0;i<arrayList.size();i++) {
            BindDeviceInfoBean.ResultBean.DeviceVOSBean bean = new BindDeviceInfoBean.ResultBean.DeviceVOSBean();
            ScanDeviceCodeBean.ResultBean.DeviceVOSBean deviceVOSBean = (ScanDeviceCodeBean.ResultBean.DeviceVOSBean) arrayList.get(i);
            bean.setSid(deviceVOSBean.getSid());
            bean.setDevType(deviceVOSBean.getDevType());
            bean.setIdentifyCode(deviceVOSBean.getIdentifyCode());
            bean.setBindTime(deviceVOSBean.getBindTime());
            bean.setDevUsableStatus(deviceVOSBean.getDevUsableStatus());
            deviceList.add(bean);
        }

        attendanceAdapter.updateDeviceData(deviceList);

        boolean isBroken = false;
        for (int i = 0; i < deviceList.size(); i++) {
            BindDeviceInfoBean.ResultBean.DeviceVOSBean deviceVOSBean = (BindDeviceInfoBean.ResultBean.DeviceVOSBean) deviceList.get(i);
            if (deviceVOSBean.getDevUsableStatus() == 0) {
                isBroken = true;
                break;
            }
        }

        if (!isBroken) {
            blueBtn();
        } else {
            grayBtn();
            errorMsg.setText("该车辆绑定的设备发生故障，不能办理出勤");
        }

        //车辆已出勤
        if (response.getResult().getAttendanceInfoVO() != null && !(response.getResult().getAttendanceInfoVO().getSid()+"").equals("") && response.getResult().getAttendanceInfoVO().getWorkNo() != null) {

            String workNoStr = response.getResult().getAttendanceInfoVO().getWorkNo().toString();
            String timeStr = response.getResult().getAttendanceInfoVO().getTime().toString();
            String nameStr = response.getResult().getAttendanceInfoVO().getName().toString();
            String msg = "该车辆已出勤，司机工号：" + workNoStr + "，姓名："+nameStr;
            errorMsg.setText(msg);

            MyLogger.i(workNoStr + " " +timeStr+ "msg-->"+msg);
            grayBtn();
        }

        MyLogger.i("status---->"+ response.getResult().getStatus());
        //判断车辆状态
        if (response.getResult().getStatus() == 0) {
            MyLogger.i("车辆正常");
            blueBtn();
        } else if (response.getResult().getStatus() == 1) {
            MyLogger.i("车辆维修保养");
            grayBtn();
        } else if (response.getResult().getStatus() == 2) {
            MyLogger.i("车辆报废");
            grayBtn();
        }
    }

    private void grayBtn() {
        attendanceBtn.setBackgroundResource(R.drawable.btn_corner_gray);
        attendanceBtn.setEnabled(false);
    }

    private void blueBtn() {
        attendanceBtn.setBackgroundResource(R.drawable.btn_corner_blue);
        attendanceBtn.setEnabled(true);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }

                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    scanCodeStr = result;
                    if (scanCodeStr.length() > 0) {
                        deviceNO.setText(scanCodeStr);
                        deviceNO.setSelection(scanCodeStr.length());
                    }
//                    Toast.makeText(AttendanceActivity.this, "解析结果：" + scanCodeStr, Toast.LENGTH_SHORT).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(AttendanceActivity.this, "解析二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void showDialog(){

        timeTextView = new TextView(this);
        timeTextView.setGravity(Gravity.CENTER);
        timeTextView.setTextSize(20);
//        timeTextView.setTextColor(0x333333);

        mDialog = new AlertDialog.Builder(this)
                .setTitle("出勤成功")
                .setCancelable(false)
                .setView(timeTextView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        timer.cancel();
                        Intent intent = new Intent(AttendanceActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .create();
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);
        timer.schedule(timerTask,0,1000);

    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            mHanlder.sendEmptyMessage(countTime);

            if (countTime > 0) {
                countTime--;
            }

        }
    };


    Handler mHanlder = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what > 0){
                timeTextView.setText(msg.what+"秒后自动关闭");
            } else {
                MyLogger.i("倒计时自动关闭");
                if (mDialog != null) {
                    mDialog.dismiss();
                    Intent intent = new Intent(AttendanceActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                timer.cancel();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
