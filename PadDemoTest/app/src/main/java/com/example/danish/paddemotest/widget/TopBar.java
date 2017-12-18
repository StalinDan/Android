package com.example.danish.paddemotest.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.danish.paddemotest.R;

import java.lang.reflect.Type;

/**
 * Created by danish on 2017/12/15.
 */

public class TopBar extends RelativeLayout {

    private View view;
    private ImageView leftImg,rightImg;
    private TextView titleText;

    private int leftImgId;
    private int rightImgId;
    private String title;

    public TopBar(Context context) {
        super(context);

        initContext(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.TopBar);
        leftImgId = array.getResourceId(R.styleable.TopBar_leftImg,0);
        rightImgId = array.getResourceId(R.styleable.TopBar_rightImg,0);
        title = array.getString(R.styleable.TopBar_title);

        array.recycle();

        initContext(context);
    }

    private void initContext(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_topbar,null);
        leftImg = view.findViewById(R.id.leftImg);
        titleText = view.findViewById(R.id.topBar_title);
        rightImg = view.findViewById(R.id.rightImg);
        addView(view);
    }

    public void setLeftImg(int img) {
        leftImg.setImageResource(img);
    }

    public void setRightImg(int img) {
        rightImg.setImageResource(img);
    }

    public void setTitle(String string){
        titleText.setText(string);
    }

    public void setLeftListner(OnClickListener leftListner){
        leftImg.setOnClickListener(leftListner);
    }

    public void setRightListner(OnClickListener rightListner) {
        rightImg.setOnClickListener(rightListner);
    }
}
