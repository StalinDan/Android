package com.danish.msdemo;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mcube.ms.sdk.MSSDK;
import com.mcube.ms.sdk.definitions.MSDefinition;
import com.mcube.ms.sdk.interfaces.MSCallbacks;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MSCallbacks {

    private static final String TAG = "MainActivity";
    MSSDK ms;
    @BindView(R.id.startScan)
    Button startScan;
    @BindView(R.id.stopScan)
    Button stopScan;
    @BindView(R.id.vibrate)
    Button vibrate;
    @BindView(R.id.heartRate)
    Button heartRate;
    @BindView(R.id.distance)
    Button distance;
    @BindView(R.id.blood)
    Button blood;

    @BindView(R.id.closBlood)
    Button closBlood;
//    @BindView(R.id.startVibrate)
//    Button startVibrate;
//    @BindView(R.id.stopVibrate)
//    Button stopVibrate;


    boolean isVirbateOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initMSSDK();


        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ms.getBLEModule().scan(12);

                ms.getBLEModule().connect("A4:C1:3C:F7:01:AF");


//                ms.getFirmwareModule().readBatteryLevel();
//
//                ms.Device_Vibrate = MSDefinition.VIBRATE_ENABLE;
//                ms.Profile_Age = 170;
//                ms.Display_Step = MSDefinition.STEPS_ENABLE;
//                ms.Display_Distance = MSDefinition.DISTANCE_ENABLE;
//                ms.Display_Calorie = MSDefinition.CALORIE_ENABLE;
//                ms.Display_Battery = MSDefinition.BATTERY_ENABLE;


            }
        });

        stopScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ms.getBLEModule().stopScan();
            }
        });

        vibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //發送尋找裝置的命令，裝置收到此命令後會振動，此命令在連線狀態下才有效
                ms.getUserModule().findWristband();
//                ms.getUserModule().setSMSNotify();

//                ms.getUserModule().setVibrate(MSDefinition.VIBRATE_ENABLE);
//                isVirbateOpen = true;
            }
        });

        heartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!isVirbateOpen) {
//                    ms.getUserModule().setVibrate(MSDefinition.VIBRATE_ENABLE);
//                    isVirbateOpen = true;
//                }
                ms.getUserModule().setHeartRateTest(MSDefinition.HEART_RATE_TEST_START);
            }
        });

        distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String str = ms.getSportModule().getDistance(1000, MSDefinition.UNIT_KILOMETERS);
//                distance.setText("距离:" + str);

//                if (!isVirbateOpen) {
//                    ms.getUserModule().setVibrate(MSDefinition.VIBRATE_ENABLE);
//                    isVirbateOpen = true;
//                }

                ms.getBLEModule().requestSync();

            }
        });

        blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (!isVirbateOpen) {
//                    ms.getUserModule().setVibrate(MSDefinition.VIBRATE_ENABLE);
//                    isVirbateOpen = true;
//                }

                ms.getUserModule().setBloodPressureTest(MSDefinition.BLOOD_PRESSURE_TEST_START);
            }
        });

        closBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms.getUserModule().setBloodPressureTest(MSDefinition.BLOOD_PRESSURE_TEST_STOP);
            }
        });

//        startVibrate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//                vibrator.vibrate(1000);
//            }
//        });
//
//        stopVibrate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vibrator.cancel();
//            }
//        });

        //獲取當前 BLE 的連接狀態。
        Log.i(TAG, "state = " + ms.getBLEModule().getConnectionState());

        //取得當前連線中的 BluetoothDevice。可透過該 Device 獲取 Device name、Device address 等資訊，若未 連線，則回傳 null 值。
//        Log.i(TAG, "device = " + ms.getBLEModule().getConnectedDevice().getAddress());

        //取得裝置於手機中的配對狀態。
        Log.i(TAG, "state = " + ms.getBLEModule().getBondState());

        //取得連線中裝置的訊號強度，並從 [onRSSIRead] 得到一個 int 值。
        Log.i(TAG, " RSSI = " + ms.getBLEModule().getDeviceRSSI());


//        ms.getUserModule().setDisplay(
//                MSDefinition.FORMAT_ONLY_TIME,
//                MSDefinition.STEPS_ENABLE,
//                MSDefinition.DISTANCE_ENABLE,
//                MSDefinition.CALORIE_ENABLE,
//                MSDefinition.HEART_RATE_ENABLE,
//                MSDefinition.BATTERY_ENABLE,
//                MSDefinition.LIFT_ENABLE,
//                MSDefinition.ROTATE_ENABLE,
//                MSDefinition.LANGUAGE_CHINESE,
//                MSDefinition.BLOOD_PRESSURE_ENABLE
//                );

    }

    private void initMSSDK() {
        ms = new MSSDK(getApplicationContext());
        ms.setMSCallbacks(MainActivity.this);
        ms.setDebugEnable(true);

        String sdkVersion = ms.getSDKVersion();
//        if(DEBUG)
        Log.i(TAG, "MS SDK - version : " + sdkVersion);

        ms.Device_Vibrate = MSDefinition.VIBRATE_ENABLE;
//        ms.Profile_Age = 170;
        ms.Display_Step = MSDefinition.STEPS_ENABLE;
        ms.Display_Distance = MSDefinition.DISTANCE_ENABLE;
        ms.Display_Calorie = MSDefinition.CALORIE_ENABLE;
        ms.Display_Battery = MSDefinition.BATTERY_ENABLE;
        ms.Display_Blood_Pressure = MSDefinition.BLOOD_PRESSURE_ENABLE;
        ms.Display_Heart_Rate = MSDefinition.HEART_RATE_ENABLE;
//
        ms.getFirmwareModule().readBatteryLevel();

        ms.getUserModule().setVibrate(MSDefinition.VIBRATE_ENABLE);
        ms.getUserModule().setHeartRateTest(MSDefinition.HEART_RATE_TEST_START);
        ms.getUserModule().setBloodPressureTest(MSDefinition.BLOOD_PRESSURE_TEST_START);
    }

    @Override
    public void onConnectionStateChanged(int i) {

        // 当装置的连线状态改变时回传。
        //0 : BluetoothProfile.STATE_DISCONNECTED
        // 2 : BluetoothProfile.STATE_CONNECTED

        Log.i(TAG, "onConnectionStateChanged() - state = " + i);

        if (i == 0) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    heartRate.setText("测量心率");
                    distance.setText("测量距离");
                    blood.setText("测量血压");
                }
            });
        }

    }

    @Override
    public void onServicesDiscovered(int i) {

        //在 Debug mode 之下可以看到 SDK 将找到的 Service 打印出来。确保 State == 0 的情况下再继续连线流 程。

        Log.i(TAG, "onServicesDiscovered() - state = " + i);

        //取得裝置於手機中的配對狀態。
        Log.i(TAG, "state = " + ms.getBLEModule().getBondState());
        if (i == BluetoothGatt.GATT_SUCCESS) {

            ms.getUserModule().setVibrate(MSDefinition.VIBRATE_ENABLE);
            if (ms.getBLEModule().getBondState()) {
                Log.i(TAG, "++++++++");
                ms.getBLEModule().startConnectionFlow();
            }
        }
    }

    @Override
    public void onRSSIRead(int i) {
        //在 [getDeviceRSSI] 函式之後回傳，數值越小代表訊號強度越強。
        Log.i(TAG, "onRSSIRead() - rssi : " + i);
    }

    @Override
    public void onDeviceScanned(BluetoothDevice bluetoothDevice) {
        Log.i(TAG, "onDeviceScanned===>name:" + bluetoothDevice.getName() + "\n"
                + "address==" + bluetoothDevice.getAddress());
        ms.getBLEModule().connect(bluetoothDevice);
    }

    @Override
    public void onSedentaryChanged() {
        //當久坐狀態發生時回傳。
        Log.i(TAG, "onSedentaryChanged()");
    }

    @Override
    public void onHeartRateChanged(final int i) {
        //在开启心率量测后，若测试成功便会返回数值。
        Log.i(TAG, "onHeartRateChanged() - count = " + i);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                heartRate.setText("心率为：" + i);
            }
        });

    }

    @Override
    public void onBloodPressureChanged(final int i, final int i1) {
        //在开启血压量测后，若测试成功便会返回收缩压与舒张压。
        Log.i(TAG, "onBloodPressureChanged() - systolic = " + i + ", diastolic = " + i1);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                blood.setText("收缩压：" + i + "   舒张压" + i1);
            }
        });

        // Update UI.

    }

    @Override
    public void onSelfieChanged() {
        //在开始自拍侦测后，若装置侦测到晃动，便会回传
        Log.i(TAG, "onSelfieChanged()");
    }

    @Override
    public void onOTAStart() {
        //當 OTA 過程中的 Start 命令傳送成功時回傳。
        Log.i(TAG, "onOTAStart()");
    }

    @Override
    public void onOTAProcess() {

    }

    @Override
    public void onOTAEnd() {
        Log.i(TAG, "onOTAEnd()");
    }

    @Override
    public void onFirmwareVersionRead(String s) {
        Log.i(TAG, "onFirmwareVersionRead() - version : " + s);
    }

    @Override
    public void onBatteryRead(int i, int i1) {
        //在 [readBatteryLevel] 之後回傳，一般的連線流程中也會傳回一次。
        Log.i(TAG, "onBatteryRead() - percentage = " + i + ", state = " + i1);
        // Update UI.

    }

    @Override
    public void onStateAndStepsChanged(int i, int i1) {
        Log.i(TAG, "onStateAndStepsChanged() - state = " + i + ", steps = " + i1);
        // Save data.

    }

    @Override
    public void onSyncHistories(String s, int i, int i1, long l) {
        //在 [requestSync] 后发生，回传该笔历史记录的 Address、状态、步数、发生时间。一般的连线流程中 也有可能会传回。
        Log.i(TAG, "onSyncHistories() - address : " + s + ", state = " + i + ", steps = " + i1 + ", time = " + l);
        // Save data.


//        final String str = ms.getSportModule().getDistance(i1, MSDefinition.UNIT_KILOMETERS);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                distance.setText("距离:" + str);
//            }
//        });


    }

    @Override
    public void onSyncCurrentState(String s, int i, final int i1, long l, int i2) {
        //Sync 完畢後，回傳裝置的當前狀態，以及該狀態的持續時間，一般的連線流程中會傳回一次。
        Log.i(TAG, "onSyncCurrentState() - address : " + s + ", state = " + i
                + ", steps = " + i1 + ", time = " + l + ", far = " + i2);

        final String str = ms.getSportModule().getDistance(i1, MSDefinition.UNIT_KILOMETERS);
        final int step = i1;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                distance.setText("距离:" + str + " 步数：" + i1);
            }
        });
    }

    @Override
    public void onSyncEnd() {
        //Sync 結束後回傳，一般的連線流程中會傳回一次。
        Log.i(TAG, "onSyncEnd()");
    }


}
