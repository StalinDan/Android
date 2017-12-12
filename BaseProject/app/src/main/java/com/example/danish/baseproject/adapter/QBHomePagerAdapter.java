package com.example.danish.baseproject.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danish on 2017/12/11.
 */

public class QBHomePagerAdapter extends PagerAdapter {

    private ArrayList<ImageView> mList;

    public QBHomePagerAdapter(ArrayList<ImageView>list) {
        Log.d("QBHomePagerAdapter","====="+list.size());
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView imageView = (ImageView)object;
        container.removeView(imageView);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView)mList.get(position);
        container.addView(imageView);
        return imageView;
    }
}
