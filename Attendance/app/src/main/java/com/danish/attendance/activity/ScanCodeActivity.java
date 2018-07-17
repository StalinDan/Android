package com.danish.attendance.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.danish.attendance.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.camera.CameraManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanCodeActivity extends AppCompatActivity {

    @BindView(R.id.cancelBtn)
    Button cancelBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);

        CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scan_code_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_scanCode_container, captureFragment).commit();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            ScanCodeActivity.this.setResult(RESULT_OK, resultIntent);
            ScanCodeActivity.this.finish();


        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScanCodeActivity.this.setResult(RESULT_OK, resultIntent);
            ScanCodeActivity.this.finish();
        }
    };
}
