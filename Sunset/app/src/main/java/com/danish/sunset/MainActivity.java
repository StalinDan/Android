package com.danish.sunset;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.qingmei2.library.view.QRCodeScannerView;
import com.qingmei2.library.view.QRCoverView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
//    @BindView(R.id.scanner_view)
//    com.mei_husky.library.view.QRCodeScannerView scannerView;
//    @BindView(R.id.cover_view)
//    com.mei_husky.library.view.QRCoverView coverView;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

//    @BindView(R.id.scanCode)
//    Button scanCode;

    QRCodeScannerView scannerView;
    QRCoverView coverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        scanCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                IntentIntegrator integrator=new         IntentIntegrator(MainActivity.this);
////                integrator.initiateScan();
//
////                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
////
////
////                ZxingConfig config = new ZxingConfig();
////                config.setPlayBeep(true);//是否播放扫描声音 默认为true
////                config.setShake(true);//是否震动  默认为true
////                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
////                config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为淡蓝色
////                config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
////                config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
////                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
////
////                startActivityForResult(intent, 123);
//            }
//        });


        scannerView = findViewById(R.id.scanner_view);
        coverView = findViewById(R.id.cover_view);

        //自动聚焦间隔2s
        scannerView.setAutofocusInterval(2000L);
//闪光灯
        scannerView.setTorchEnabled(true);
//扫描结果监听处理
        scannerView.setOnQRCodeReadListener(new QRCodeScannerView.OnQRCodeScannerListener() {
            @Override
            public void onDecodeFinish(String text, PointF[] points) {
                Log.d("tag", "扫描结果 ： " + text);
            }
        });
//相机权限监听
        scannerView.setOnCheckCameraPermissionListener(new QRCodeScannerView.OnCheckCameraPermissionListener() {
            @Override
            public boolean onCheckCameraPermission() {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                    return false;
                }
            }
        });
//开启后置摄像头
        scannerView.setBackCamera();

    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        IntentResult scanResult =IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
//        if (scanResult !=null){
//            String result=scanResult.getContents();
//            Toast.makeText(this,result,Toast.LENGTH_LONG).show();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // 扫描二维码/条码回传
//        if (requestCode == 123 && resultCode == RESULT_OK) {
//            if (data != null) {
//
//                String content = data.getStringExtra(Constant.CODED_CONTENT);
////                result.setText("扫描结果为：" + content);
//                Toast.makeText(this,content,Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
