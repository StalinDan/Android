package com.example.danish.demotest.Widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/21.
 */

public class NewsContent extends LinearLayout{

    public ImageView newsImg;
    public TextView newsContent;
    private Drawable img;
    private String content;
    private Context mContext;
    private View view;

    public NewsContent(Context context) {
        super(context);
        init(context);
    }


    public NewsContent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.news_content,null);
        newsImg = view.findViewById(R.id.newsDetail_img);
        newsContent = view.findViewById(R.id.newsDetail_content);
        if (content != null) {
            newsContent.setText(content);
        }
        if (img != null) {
            newsImg.setImageDrawable(img);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        addView(view);
    }
}
