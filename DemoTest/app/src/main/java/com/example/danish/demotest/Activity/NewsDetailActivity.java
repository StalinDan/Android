package com.example.danish.demotest.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.view.WindowManager;

import com.example.danish.demotest.R;
import com.example.danish.demotest.Widgets.NewsContent;

import android.content.Context;

import java.util.Random;



public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        WindowManager wm = (WindowManager) NewsDetailActivity.this.getSystemService(Context.WINDOW_SERVICE);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.newsDetail_content);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wm.getDefaultDisplay().getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i< 4; i++) {
            NewsContent newsContent = new NewsContent(NewsDetailActivity.this);
            newsContent.setLayoutParams(layoutParams);
            newsContent.newsContent.setText(getRandomContent("新闻内容"));
            linearLayout.addView(newsContent);
        }
    }

    private String getRandomContent(String content) {
        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for (int i= 0;i<length;i++){
            builder.append(content);
        }
        return builder.toString();
    }
}
