package com.danish.sunset;

import android.graphics.drawable.LevelListDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawableActivity extends AppCompatActivity {

    @BindView(R.id.view)
    View view;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.scaleView)
    View scaleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        ButterKnife.bind(this);

        LevelListDrawable levelListDrawable = (LevelListDrawable) view.getBackground();
        levelListDrawable.setLevel(0);


        TransitionDrawable transitionDrawable = (TransitionDrawable) textView.getBackground();
        transitionDrawable.startTransition(1000);


        ScaleDrawable scaleDrawable = (ScaleDrawable) scaleView.getBackground();
        scaleDrawable.setLevel(10);




    }
}
