package com.danish.attendance.popWindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.danish.attendance.R;
import com.danish.attendance.activity.DeleteFaceInfoActivity;
import com.danish.attendance.activity.MainActivity;
import com.danish.attendance.activity.ModifyHostActivity;


/**
 * Created by danish on 2018/3/16.
 */

public class PopConfigWidow extends PopupWindow {

    private View view;
    private Context mContext;
    private ImageView closeImg;
    private Button modifyHostBtn,deleteFaceBtn;

    public PopConfigWidow(View contentView, int width, int height, boolean focusable, Context mContext) {
        super(contentView, width, height, focusable);
        this.mContext = mContext;

        view = contentView;

        initPopWindow();
    }

    private void initPopWindow () {
        closeImg = view.findViewById(R.id.close_btn);
        modifyHostBtn = view.findViewById(R.id.modifyHostBtn);
        deleteFaceBtn = view.findViewById(R.id.deleteFaceBtn);

        modifyHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)mContext;
                Intent intent = new Intent(mainActivity, ModifyHostActivity.class);
                mContext.startActivity(intent);
            }
        });

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopupWindow();
            }
        });

        deleteFaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)mContext;
                Intent intent = new Intent(mainActivity, DeleteFaceInfoActivity.class);
                mContext.startActivity(intent);
            }
        });

        //1.构造一个PopupWindow，参数依次是加载的View，宽高
//        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,true);
        //这些为了点击非PopupWindow区域，PopupWindow会消失的
        this.setTouchable(true);
        this.setFocusable(true);

//        this.setFocusable(false);
//        this.setOutsideTouchable(true);

        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
//                return true;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        this.setAnimationStyle(R.style.ContextPopupAnim);

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    public void showPopupWindow () {
        this.showAtLocation(view, Gravity.CENTER,0,0);
    }

    public void dismissPopupWindow () {
        this.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha( float bgAlpha)
    {
        Activity context = (Activity)mContext;
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
