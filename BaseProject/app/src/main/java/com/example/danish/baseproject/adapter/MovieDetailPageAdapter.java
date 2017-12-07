package com.example.danish.baseproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by danish on 2017/12/6.
 */

public class MovieDetailPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    public MovieDetailPageAdapter(FragmentManager fm,List<Fragment>list) {
        super(fm);

        fragmentList = list;
        fragmentManager = fm;
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
