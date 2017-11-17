package com.example.danish.demotest.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/17.
 */

public class MainMenuLayout extends FrameLayout {
    private Context mContext;
    private View view;
    private ImageView tabIv;
    private TextView titleTv;
    private TextView cornerTv;

    private String title = "";

    private int selectImageID ;
    private int noSelectImageID ;

    private int selectTextColor ;
    private int noSelectTextColor ;

    //是否被选中
    private boolean mIsSelect = false;

    public MainMenuLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MainMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.MainMenu);

        title = a.getString(R.styleable.MainMenu_menutext);

        selectImageID = a.getResourceId(R.styleable.MainMenu_selectedImage,0);
        noSelectImageID = a.getResourceId(R.styleable.MainMenu_noselectedImage,0);
        selectTextColor = Color.parseColor("#5fa7d7");//a.getColor(R.styleable.MainMenu_selectedtextcolor,0);
        noSelectTextColor = Color.parseColor("#929292"); //a.getColor(R.styleable.MainMenu_noselectedtextcolor,0);
        a.recycle();

        init(context);
    }

    public MainMenuLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context)
    {
        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.layout_mainmenu,null);
        tabIv =(ImageView) view.findViewById(R.id.menu_iamge);
        titleTv = (TextView)view.findViewById(R.id.menu_text);
        cornerTv = (TextView)view.findViewById(R.id.menu_cornertext);

        titleTv.setText(title);
        if(noSelectImageID != 0)
        {
            tabIv.setBackgroundResource(noSelectImageID);
        }

        this.addView(view);
        selectMenu(false);
    }

    /**
     * 是否选中
     * @param isSelect
     */
    public void selectMenu(boolean isSelect)
    {
        mIsSelect = isSelect;

        if(isSelect)
        {
            tabIv.setBackgroundResource(selectImageID);
            titleTv.setTextColor(selectTextColor);
        }
        else
        {
            tabIv.setBackgroundResource(noSelectImageID);
            titleTv.setTextColor(noSelectTextColor);
        }
    }

    public void  setCornertext(int count)
    {
        if(count == 0)
        {
            cornerTv.setVisibility(INVISIBLE);
        }
        else
        {
            cornerTv.setVisibility(VISIBLE);
            if(count > 99)
            {
                cornerTv.setText("99");
            }
            else
            {
                cornerTv.setText(count+"");
            }

        }
    }
}
