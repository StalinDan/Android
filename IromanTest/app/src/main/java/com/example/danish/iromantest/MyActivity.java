package com.example.danish.iromantest;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.WindowManager;
import android.view.Window;

public class MyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        if (Build.VERSION.SDK_INT >=21) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        setContentView(R.layout.activity_my);


        View giftLayout = (View) findViewById(R.id.gift);
        ImageView giftImg = giftLayout.findViewById(R.id.item_img);
        giftImg.setImageResource(R.mipmap.gift);
        TextView giftText = giftLayout.findViewById(R.id.item_text);
        giftText.setText("我的礼品");

        View addressLayout = (View) findViewById(R.id.address);
        ImageView addressImg = addressLayout.findViewById(R.id.item_img);
        addressImg.setImageResource(R.mipmap.address);
        TextView addressText = addressLayout.findViewById(R.id.item_text);
        addressText.setText("收货地址");

        View feedbackLayout = (View) findViewById(R.id.feedBack);
        ImageView feedbackImg = feedbackLayout.findViewById(R.id.item_img);
        feedbackImg.setImageResource(R.mipmap.feedback);
        TextView feedbackText = feedbackLayout.findViewById(R.id.item_text);
        feedbackText.setText("意见反馈");

        View aboutLayout = (View) findViewById(R.id.about);
        ImageView aboutImg = aboutLayout.findViewById(R.id.item_img);
        aboutImg.setImageResource(R.mipmap.about);
        TextView aboutText = aboutLayout.findViewById(R.id.item_text);
        aboutText.setText("关于");

    }
}
