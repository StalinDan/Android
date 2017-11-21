package com.example.danish.demotest.Widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.demotest.R;

import java.util.zip.Inflater;

/**
 * Created by danish on 2017/11/20.
 */

public class My_iconItem extends FrameLayout {

    private Context mContext;
    private View view;
    private ImageView itemImg;
    public TextView itemText;

    public String imgTitle = "";
    public Drawable imgIcon;

    public My_iconItem(@NonNull Context context) {
        super(context);
        init(context);
    }

    public My_iconItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.my_imgItem);

        imgTitle = a.getString(R.styleable.my_imgItem_itemTitle);
        imgIcon = a.getDrawable(R.styleable.my_imgItem_itemImg);
        a.recycle();
        init(context);
    }

    private void init(Context context) {

        mContext = context;
        view = LayoutInflater.from(mContext).inflate(R.layout.my_imgicon,null);
        itemImg = (ImageView) view.findViewById(R.id.my_imgIcon);
        itemText = view.findViewById(R.id.my_imgText);

        if (imgTitle != null) {
            itemText.setText("1111");
        }
        if (imgIcon != null) {
            itemImg.setImageDrawable(imgIcon);
        }
        addView(view);
    }
}
