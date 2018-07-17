package com.example.danish.demotest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.danish.demotest.R;
import com.example.danish.demotest.Utilils.MyLogger;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.xiaweizi.marquee.MarqueeTextView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LifeCircleActivity extends AppCompatActivity {

//    @BindView(R.id.skipBtn)
//    Button skipBtn;
    @BindView(R.id.marqueeText)
    MarqueeTextView marqueeText;
    @BindView(R.id.marqueeText1)
    TextView marqueeText1;
    @BindView(R.id.simpleMarqueeView)
    SimpleMarqueeView simpleMarqueeView;

    Button skipBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_circle);
        ButterKnife.bind(this);

        skipBtn = findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LifeCircleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        marqueeText.startScroll();

//        final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
//        SimpleMF <String> marqueeFactory = new SimpleMF<>(this);
//        marqueeFactory.setData(datas);
//        simpleMarqueeView.setMarqueeFactory(marqueeFactory);
//        simpleMarqueeView.startFlipping();


        MyLogger.i("onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
        MyLogger.i("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLogger.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLogger.i("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyLogger.i("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyLogger.i("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLogger.i("onDestroy");
    }
}
