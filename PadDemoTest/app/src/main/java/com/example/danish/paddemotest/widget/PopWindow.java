package com.example.danish.paddemotest.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.danish.paddemotest.R;

import javax.security.auth.callback.Callback;

/**
 * Created by danish on 2017/12/14.
 */

public class PopWindow extends PopupWindow {

    private Context mContext;
    View view ;

//    public OnBtnClickListner getOnBtnClickListner() {
//        return onBtnClickListner;
//    }

    private OnBtnClickListner onBtnClickListner;

    public void setOnBtnClickListner(OnBtnClickListner onBtnClickListner) {
        this.onBtnClickListner = onBtnClickListner;
    }


    public interface OnBtnClickListner {
        void leftBtnPress();
        void rightBtnPress();
    }



    public PopWindow(Context context,View contentView, int width, int height, boolean focusable) {

        super(contentView,width,height);
        mContext = context;
        view = contentView;

        initPopWindow(context);

    }


    public void showPopWindow(View view) {
        this.showAtLocation(view, Gravity.CENTER,0,0);
    }

    public void dismissPopWindow() {
        this.dismiss();
    }

    private void initPopWindow(Context context) {


        Button xiBtn = view.findViewById(R.id.btn_xixi);
        Button haBtn = view.findViewById(R.id.btn_haha);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
//        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,true);
        //这些为了点击非PopupWindow区域，PopupWindow会消失的
        this.setTouchable(true);
        this.setFocusable(true);

        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        this.setAnimationStyle(R.style.ContexPopupAnim);

        //设置popupWindow里的按钮的事件
        xiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dismissPopWindow();
                if (onBtnClickListner != null) {
                    onBtnClickListner.leftBtnPress();
                }
            }
        });

        haBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBtnClickListner != null) {
                    onBtnClickListner.rightBtnPress();
                }
            }
        });

    }

}
