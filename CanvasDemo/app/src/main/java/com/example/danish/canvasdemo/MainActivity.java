package com.example.danish.canvasdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.danish.canvasdemo.widget.DrawView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    private void init(){
        LinearLayout layout = (LinearLayout)findViewById(R.id.main_layout);
        final DrawView drawView = new DrawView(this);
        drawView.setBackgroundColor(0xFFE4B5);
        drawView.setMinimumHeight(500);
        drawView.setMinimumWidth(300);
        layout.addView(drawView);

    }


}
