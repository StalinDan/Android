package com.example.danish.demotest.Widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/20.
 */

public class MyRowTitle extends LinearLayout {

    private Context mContext;
    public TextView rowTitle;
    public View view;

    public String title = "";

    public MyRowTitle(Context context) {
        super(context);
        init(context);
    }

    public MyRowTitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public  void init(Context context) {
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.my_row_title,null);
        rowTitle = view.findViewById(R.id.rowTitle);
        if (title != null) {
            rowTitle.setText(title);
        }
        addView(view);
    }
}
