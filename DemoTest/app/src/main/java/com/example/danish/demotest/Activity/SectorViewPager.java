package com.example.danish.demotest.Activity;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.View;

/**
 * Created by danish on 2017/11/27.
 */

public class SectorViewPager implements ViewPager.PageTransformer {

    private static final float ROT_MAX = 20.0f;
    private float mRot;

    @Override
    public void transformPage(View page, float position) {

        Log.e("TAG", page + " , " + position + "");

        //界面不可见
        if (position < -1) {
            page.setRotation(0);
        }
        //界面可见
        else if (position <= 1) {
            mRot = ROT_MAX*position;
            page.setPivotX(page.getMeasuredWidth()*0.5f);
            page.setPivotY(page.getMeasuredHeight());
            page.setRotation(mRot);
        }
        //界面不可见
        else {
            page.setRotation(0);
        }
    }
}


