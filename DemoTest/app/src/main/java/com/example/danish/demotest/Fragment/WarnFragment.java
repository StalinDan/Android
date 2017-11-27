package com.example.danish.demotest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.danish.demotest.Activity.SectorViewPager;
import com.example.danish.demotest.Adapter.AnimalPagerAdapter;
import com.example.danish.demotest.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danish on 2017/11/17.
 */

public class WarnFragment extends BaseFragment {

    private ArrayList<Integer> mGoodsList;
    private ArrayList<View>mivGoodsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_warn,null);

        initView();

        AnimalPagerAdapter adapter = new AnimalPagerAdapter(getContext(),mivGoodsList);
        final ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        //设置切换动画
        viewPager.setPageTransformer(true,new SectorViewPager());

        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    return;
                }

                if (currentPosition == 0) {
                    viewPager.setCurrentItem(mivGoodsList.size()-2,false);
                } else if (currentPosition == mivGoodsList.size()-1) {
                    viewPager.setCurrentItem(1,false);
                }
            }
        });

        return view;
    }

    public void initView(){

        mGoodsList = new ArrayList<>();
        mGoodsList.add(R.drawable.sample_7);
        mGoodsList.add(R.drawable.sample_0);
        mGoodsList.add(R.drawable.sample_1);
        mGoodsList.add(R.drawable.sample_2);
        mGoodsList.add(R.drawable.sample_3);
        mGoodsList.add(R.drawable.sample_4);
        mGoodsList.add(R.drawable.sample_5);
        mGoodsList.add(R.drawable.sample_6);
        mGoodsList.add(R.drawable.sample_7);
        mGoodsList.add(R.drawable.sample_0);

        mivGoodsList = new ArrayList<>();
        for (int i = 0;i<mGoodsList.size();i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(mGoodsList.get(i));
            mivGoodsList.add(imageView);
        }

    }
}
