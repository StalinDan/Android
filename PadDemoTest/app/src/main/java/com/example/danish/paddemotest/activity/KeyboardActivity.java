package com.example.danish.paddemotest.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.danish.paddemotest.R;
import com.example.danish.paddemotest.widget.ClearEditText;

import cn.baymax.android.keyboard.BaseKeyboard;
import cn.baymax.android.keyboard.KeyboardManager;
import cn.baymax.android.keyboard.NumberKeyboard;

public class KeyboardActivity extends AppCompatActivity {

    ClearEditText editText;
    private Context mContext;
    private KeyboardManager keyboardManagerNumber;
    private NumberKeyboard numberKeyboard;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        mContext = this;
        linearLayout = findViewById(R.id.ll);
        editText = findViewById(R.id.edit_text);

//        LinearLayout linearLayout = findViewById(R.id.ll);
//        editText = new ClearEditText(this);
//        linearLayout.addView(editText);

        keyboardManagerNumber = new KeyboardManager(this);
        initNumberKeyboard();
        keyboardManagerNumber.bindToEditor(editText,numberKeyboard);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linearLayout.setFocusable(true);
                linearLayout.setFocusableInTouchMode(true);
                linearLayout.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(),0);
                return false;
            }
        });
    }

    private void initNumberKeyboard() {
        numberKeyboard = new NumberKeyboard(mContext,NumberKeyboard.DEFAULT_NUMBER_XML_LAYOUT);
        numberKeyboard.setActionDoneClickListener(new NumberKeyboard.ActionDoneClickListener() {
            @Override
            public void onActionDone(CharSequence charSequence) {
                if(TextUtils.isEmpty(charSequence) || charSequence.toString().equals("0") || charSequence.toString().equals("0.0")) {
                    Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
//                    onNumberkeyActionDone();
                    editText.clearFocus();
                    keyboardManagerNumber.hideKeyboard();

                }
            }
        });

        numberKeyboard.setKeyStyle(new BaseKeyboard.KeyStyle() {
            @Override
            public Drawable getKeyBackound(Keyboard.Key key) {
                if(key.iconPreview != null) {
                    return key.iconPreview;
                } else {
                    return ContextCompat.getDrawable(mContext,R.drawable.key_number_bg);
                }
            }

            @Override
            public Float getKeyTextSize(Keyboard.Key key) {
                if(key.codes[0] == mContext.getResources().getInteger(R.integer.action_done)) {
                    return convertSpToPixels(mContext, 20f);
                }
                return convertSpToPixels(mContext, 24f);
            }

            @Override
            public Integer getKeyTextColor(Keyboard.Key key) {
                if(key.codes[0] == mContext.getResources().getInteger(R.integer.action_done)) {
                    return Color.WHITE;
                }
                return null;
            }

            @Override
            public CharSequence getKeyLabel(Keyboard.Key key) {
                return null;
            }
        });
    }

    public float convertSpToPixels(Context context, float sp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

}
