package com.example.danish.paddemotest.popWindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by danish on 2018/2/12.
 */

public class KeyBoardPopWindow extends PopupWindow {

    private Context mContext;
    View view;


    public KeyBoardPopWindow(View contentView, int width, int height, boolean focusable, Context mContext) {
        super(contentView, width, height, focusable);
        this.mContext = mContext;

        view = contentView;
    }

    private void initPopWindow(Context context) {

    }
}
