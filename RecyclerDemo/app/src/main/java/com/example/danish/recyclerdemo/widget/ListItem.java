package com.example.danish.recyclerdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.danish.recyclerdemo.R;

import butterknife.BindView;

/**
 * Created by danish on 2017/12/7.
 */

public class ListItem extends LinearLayout {


    @BindView(R.id.list_title)
    TextView listTitle;
    @BindView(R.id.list_content)
    TextView listContent;
    public ListItem(Context context) {
        super(context);

    }

    public ListItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, null);
        addView(view);

    }
}
