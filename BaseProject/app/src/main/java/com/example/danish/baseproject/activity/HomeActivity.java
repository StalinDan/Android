package com.example.danish.baseproject.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.HomePagerAdapter;
import com.example.danish.baseproject.fragment.BookFragment;
import com.example.danish.baseproject.fragment.MovieFragment;


import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.home_pager);

        tabLayout.addTab(tabLayout.newTab().setText("最热新闻"));
        tabLayout.addTab(tabLayout.newTab().setText("搞笑视频"));
//        tabLayout.addTab(tabLayout.newTab().setText("短视频"));
//        tabLayout.addTab(tabLayout.newTab().setText("新闻"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Log.i(TAG,"onTabSelected:"+tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new MovieFragment());
        fragmentList.add(new BookFragment());

        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(homePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.i(TAG,"select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.getTabAt(0).setText("电影");
        tabLayout.getTabAt(1).setText("读书");
    }
}
