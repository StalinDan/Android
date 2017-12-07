package com.example.danish.baseproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by danish on 2017/12/6.
 */

public class BookDetailPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment>fragmentList;

    public BookDetailPagerAdapter(FragmentManager fm,List<Fragment>list) {
        super(fm);
        fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
