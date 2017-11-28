package com.example.danish.fragmentbottombar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private View mContentView;
    private View mLoadingView;
    private int mAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentView = findViewById(R.id.content);
        mLoadingView = findViewById(R.id.loading);

        mContentView.setVisibility(View.GONE);

        mAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        Button button = findViewById(R.id.animationBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crossfade();
            }
        });


    }

    private void crossfade() {
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        mContentView.animate().alpha(1f).setDuration(mAnimationDuration).setListener(null);

        mLoadingView.animate().alpha(0f).setDuration(mAnimationDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }
}
