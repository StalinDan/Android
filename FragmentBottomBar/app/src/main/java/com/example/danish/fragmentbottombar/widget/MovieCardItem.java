package com.example.danish.fragmentbottombar.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.danish.fragmentbottombar.R;

/**
 * Created by danish on 2017/11/30.
 */

public class MovieCardItem extends LinearLayout {
    private ImageView movieImg;
    private TextView movieName;
    private RatingBar ratingBar;
    private TextView movieScore;

    public MovieCardItem(Context context) {
        super(context);
    }

    public MovieCardItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

       View view = LayoutInflater.from(context).inflate(R.layout.movie_card_item,null);
        init(context);
        addView(view);
    }

    public void init(Context context) {
        movieImg = findViewById(R.id.movieItem_img);
        movieName = findViewById(R.id.movieItem_name);
        ratingBar = findViewById(R.id.movieItem_rateBar);
        movieScore = findViewById(R.id.movieItem_score);

    }

    public void setData(){

    }
}
