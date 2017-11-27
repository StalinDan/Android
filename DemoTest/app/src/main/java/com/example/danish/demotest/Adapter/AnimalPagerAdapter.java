package com.example.danish.demotest.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by danish on 2017/11/27.
 */

public class AnimalPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mViewList;

    @Override
    public int getCount() {
//        return mViewList.size();
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== object;
    }

    public AnimalPagerAdapter(Context context,List<View> viewList) {
        mContext = context;
        mViewList = viewList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("AnimalPagerAdapter","position--->"+position);
        ImageView imageView = (ImageView) mViewList.get(position);
        container.addView(imageView);
        return imageView;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ImageView imageView = (ImageView)object;
        container.removeView(imageView);
    }
}
