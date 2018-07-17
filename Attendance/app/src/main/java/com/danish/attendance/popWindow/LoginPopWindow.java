package com.danish.attendance.popWindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.danish.attendance.R;
import com.danish.attendance.activity.DriverLIstActivity;
import com.danish.attendance.activity.MainActivity;

/**
 * Created by danish on 2018/6/8.
 */

public class LoginPopWindow extends PopupWindow {

    private View view;
    private Context mContext;
    private EditText accountEditText,pwdEditText;
    private Button closeBtn,loginBtn;

    private LoginCallback loginCallback;

    public interface LoginCallback {
        public void loginClicked(String name,String password);
    }

    public void setLoginCallback(LoginCallback loginCallback) {
        this.loginCallback = loginCallback;
    }

    public LoginPopWindow(View contentView, int width, int height, boolean focusable, Context context) {
        super(contentView, width, height, focusable);
        view = contentView;
        mContext = context;

        initPopWindow();
    }

    private void initPopWindow() {
        accountEditText = view.findViewById(R.id.accountEditText);
        pwdEditText = view.findViewById(R.id.pwdEditText);
        closeBtn = view.findViewById(R.id.closeBtn);
        loginBtn = view.findViewById(R.id.loginBtn);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = accountEditText.getText().toString();
                String password = pwdEditText.getText().toString();
                if (!checkAccountAndPassword(name,password)) {
                    Toast.makeText(mContext,"请输入账号或密码",Toast.LENGTH_SHORT).show();
                    return;
                }

                loginCallback.loginClicked(name,password);

//                MainActivity activity = (MainActivity)mContext;
//                Intent intent = new Intent(activity,DriverLIstActivity.class);
//                mContext.startActivity(intent);
//                dismiss();
            }
        });


        //1.构造一个PopupWindow，参数依次是加载的View，宽高
//        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,true);
        //这些为了点击非PopupWindow区域，PopupWindow会消失的
        this.setFocusable(true);
        this.setTouchable(true);
        this.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
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

    /**
     * 检查用户名密码是否为空
     * @param account
     * @param password
     * @return
     */
    public boolean checkAccountAndPassword(String account, String password)
    {
        if(account == null || TextUtils.isEmpty(account) || password == null || TextUtils.isEmpty(password))
        {
            return  false;
        }
        return true;
    }

    public void clearLoginInfo() {
        accountEditText.setText("");
        pwdEditText.setText("");
    }
}
