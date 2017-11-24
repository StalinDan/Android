package com.example.danish.demotest.Widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/21.
 */

public class TopBar extends RelativeLayout{

    private ImageView leftImg,righthImg;
    private TextView titleView;

    int  leftDrawable,rightDrawable;
    private String title;

    private Context mContext;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initContext(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.commont_topbar,null);
        leftImg = view.findViewById(R.id.topBar_leftImg);
        righthImg = view.findViewById(R.id.topBar_rightImg);
        titleView = view.findViewById(R.id.topBar_title);

        if (leftDrawable != 0) {
//            leftImg.setImageDrawable(leftDrawable);
            leftImg.setImageResource(leftDrawable);
        }

        if (rightDrawable != 0) {
//            righthImg.setImageDrawable(rightDrawable);
            righthImg.setImageResource(rightDrawable);

        }

        if (title != null){
            titleView.setText(title);
        }
        addView(view);
    }

}
