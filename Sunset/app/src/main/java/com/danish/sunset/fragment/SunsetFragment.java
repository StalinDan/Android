package com.danish.sunset.fragment;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.danish.sunset.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2018/6/4.
 */

public class SunsetFragment extends Fragment {
    @BindView(R.id.sun)
    ImageView sun;
    @BindView(R.id.sky)
    FrameLayout sky;
    Unbinder unbinder;

    private View mScreenView;
    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

//    public static SunsetFragment newInstance() {
//
////        Bundle args = new Bundle();
////
////        SunsetFragment fragment = new SunsetFragment();
////        fragment.setArguments(args);
//        return new SunsetFragment();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("aa", "aaa");
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);
        unbinder = ButterKnife.bind(this, view);

        mScreenView = view;

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);


        mScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });



        return view;
    }

    private void startAnimation (){
        float sunYStart = sun.getTop();
        float sunYEnd = sky.getHeight();

        ObjectAnimator heightAnimator = ObjectAnimator.ofFloat(sun,"y",sunYStart,sunYEnd).setDuration(3200);
        heightAnimator.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator sunsetSkyAnimator = ObjectAnimator.ofInt(sky,"backgroundColor",mBlueSkyColor,mSunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());


        ObjectAnimator nightSkyAnimator = ObjectAnimator.ofInt(sky,"backgroundColor",mSunsetSkyColor,mNightSkyColor).setDuration(3000);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet set = new AnimatorSet();
        set.play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator);
        set.start();

//        heightAnimator.start();
//        sunsetSkyAnimator.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
