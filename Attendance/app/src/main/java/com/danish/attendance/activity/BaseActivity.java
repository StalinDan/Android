package com.danish.attendance.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 消息提示
     *
     * @param showMessage
     */
    public void showToast(String showMessage) {
        Toast.makeText(this, showMessage, Toast.LENGTH_LONG).show();
    }

    public void showNetErrToast() {
        Toast.makeText(this, "网络错误，请稍后重试", Toast.LENGTH_LONG).show();
    }

    /**
     * 生成加载dialog
     *
     * @param message
     * @param canCancel
     */
    public ProgressDialog generateProgressDialog(@NonNull String message, boolean canCancel) {
        ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage(message);
        waitingDialog.setCancelable(canCancel);
        return waitingDialog;
    }

    public ProgressDialog generateProgressDialog(boolean canCancel) {
        ProgressDialog waitingDialog = new ProgressDialog(this);
        waitingDialog.setMessage("正在加载");
        waitingDialog.setCancelable(canCancel);
        return waitingDialog;
    }

    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();
    }

    /**
     * 这是兼容的 AlertDialog
     */
    public void showErrDialog(String message, String negativeText, String positiveText, boolean isForceUpgrade, DialogInterface.OnClickListener negativeListener, DialogInterface.OnClickListener positiveListener) {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("错误提示");
        builder.setMessage(message);
        if (!isForceUpgrade) {
            builder.setNegativeButton(negativeText, negativeListener);
        } else {
            builder.setCancelable(false);
        }
        builder.setPositiveButton(positiveText, positiveListener);
        builder.show();
    }

    /**
     *
     */
    public void showInfoDialog(String title, String message) {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton("确定", null);
        builder.show();
    }
}
