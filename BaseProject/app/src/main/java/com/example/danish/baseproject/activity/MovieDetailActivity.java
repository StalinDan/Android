package com.example.danish.baseproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.MovieDetailPageAdapter;
import com.example.danish.baseproject.bean.MovieListBean;
import com.example.danish.baseproject.fragment.MovieDetaiInfoFragment;
import com.example.danish.baseproject.fragment.MovieDetailLinkFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.movieDetail_img)
    ImageView movieDetailImg;
    @BindView(R.id.movieDetail_viewPager)
    ViewPager movieDetailViewPager;
    @BindView(R.id.movieDetail_Tab)
    TabLayout movieDetailTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        MovieListBean.MovieItemBean movieItemBean = (MovieListBean.MovieItemBean) intent.getSerializableExtra("Movie");



        movieDetailTab.addTab(movieDetailTab.newTab().setText("影片信息"));
        movieDetailTab.addTab(movieDetailTab.newTab().setText("简介"));

        movieDetailTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        movieDetailTab.setupWithViewPager(movieDetailViewPager);

        List<Fragment> fragmentList = new ArrayList<>();
        MovieDetaiInfoFragment infoFragment = new MovieDetaiInfoFragment();
        MovieDetailLinkFragment linkFragment = new MovieDetailLinkFragment();

        fragmentList.add(infoFragment);
        fragmentList.add(linkFragment);

        Bundle movieBundle = new Bundle();
        movieBundle.putSerializable("movie",movieItemBean);

        infoFragment.setArguments(movieBundle);
        linkFragment.setArguments(movieBundle);

        MovieDetailPageAdapter pageAdapter = new MovieDetailPageAdapter(getSupportFragmentManager(),fragmentList);
        movieDetailViewPager.setAdapter(pageAdapter);
        movieDetailViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        movieDetailTab.getTabAt(0).setText("影片信息");
        movieDetailTab.getTabAt(1).setText("简介");
    }
}
