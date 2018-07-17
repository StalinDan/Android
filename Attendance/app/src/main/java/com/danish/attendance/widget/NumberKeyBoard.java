package com.danish.attendance.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.danish.attendance.R;


/**
 * Created by danish on 2018/2/28.
 */

public class NumberKeyBoard extends LinearLayout implements View.OnClickListener{

    private Context mContext;
    private View view;
    private Button numBtn0;
    private Button numBtn1;
    private Button numBtn2;
    private Button numBtn3;
    private Button numBtn4;
    private Button numBtn5;
    private Button numBtn6;
    private Button numBtn7;
    private Button numBtn8;
    private Button numBtn9;
    private Button confirmBtn;
    private Button deleteBtn;

    public interface NumberKeyBoardCallBack {
        public void numberBoardClick(int num);
    }

    private NumberKeyBoardCallBack numberKeyBoardCallBack;

    public void setNumberKeyBoardCallBack(NumberKeyBoardCallBack numberKeyBoardCallBack) {
        this.numberKeyBoardCallBack = numberKeyBoardCallBack;
    }

    public NumberKeyBoard(Context context) {
        super(context);

        initContext(context);
    }

    public NumberKeyBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initContext(context);
    }

    private void initContext(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.number_keyboard,null);
        numBtn0 = view.findViewById(R.id.num0);
        numBtn1 = view.findViewById(R.id.num1);
        numBtn2 = view.findViewById(R.id.num2);
        numBtn3 = view.findViewById(R.id.num3);
        numBtn4 = view.findViewById(R.id.num4);
        numBtn5 = view.findViewById(R.id.num5);
        numBtn6 = view.findViewById(R.id.num6);
        numBtn7 = view.findViewById(R.id.num7);
        numBtn8 = view.findViewById(R.id.num8);
        numBtn9 = view.findViewById(R.id.num9);
        confirmBtn = view.findViewById(R.id.confirm);
        deleteBtn = view.findViewById(R.id.delete);
        addView(view);

        numBtn0.setOnClickListener(this);
        numBtn1.setOnClickListener(this);
        numBtn2.setOnClickListener(this);
        numBtn3.setOnClickListener(this);
        numBtn4.setOnClickListener(this);
        numBtn5.setOnClickListener(this);
        numBtn6.setOnClickListener(this);
        numBtn7.setOnClickListener(this);
        numBtn8.setOnClickListener(this);
        numBtn9.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.num0:
                numberKeyBoardCallBack.numberBoardClick(0);
                break;
            case R.id.num1:
                numberKeyBoardCallBack.numberBoardClick(1);
                break;
            case R.id.num2:
                numberKeyBoardCallBack.numberBoardClick(2);
                break;
            case R.id.num3:
                numberKeyBoardCallBack.numberBoardClick(3);
                break;
            case R.id.num4:
                numberKeyBoardCallBack.numberBoardClick(4);
                break;
            case R.id.num5:
                numberKeyBoardCallBack.numberBoardClick(5);
                break;
            case R.id.num6:
                numberKeyBoardCallBack.numberBoardClick(6);
                break;
            case R.id.num7:
                numberKeyBoardCallBack.numberBoardClick(7);
                break;
            case R.id.num8:
                numberKeyBoardCallBack.numberBoardClick(8);
                break;
            case R.id.num9:
                numberKeyBoardCallBack.numberBoardClick(9);
                break;
            case R.id.confirm:
                numberKeyBoardCallBack.numberBoardClick(10);
                break;
            case R.id.delete:
                numberKeyBoardCallBack.numberBoardClick(-1);
                break;
        }
    }
}
