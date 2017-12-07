package com.example.danish.baseproject.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.activity.MovieDetailActivity;
import com.example.danish.baseproject.bean.MovieListBean;
import com.example.danish.baseproject.bitmaputils.BitmapLoadUtil;
import com.example.danish.baseproject.bitmaputils.ImageLoadUtils;

import java.util.List;

import javax.security.auth.callback.Callback;


/**
 * Created by danish on 2017/11/30.
 */

public class MovieAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<MovieListBean.MovieItemBean> mMovieItemBeanList;

    public MovieAdapter(Context context, List<MovieListBean.MovieItemBean> movieItemBeanList) {
        Log.d("MovieAdapter","+++"+movieItemBeanList.size());
        mContext = context;
        mMovieItemBeanList = movieItemBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        holder = (MyViewHolder)holder;
        final MovieListBean.MovieItemBean itemBean = mMovieItemBeanList.get(position);
        ((MyViewHolder) holder).movieName.setText(itemBean.getTitle());

        ((MyViewHolder) holder).ratingBar.setRating(itemBean.getRating().getAverage()/2);
        ((MyViewHolder) holder).score.setText(itemBean.getRating().getAverage()+"");
//
//        ((MyViewHolder) holder).score.setText(itemBean.getRating().);

        ImageLoadUtils.loadImageView(mContext,itemBean.getImages().getSmall(),((MyViewHolder) holder).movieImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtra("Movie",itemBean);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("MovieAdapter","======"+mMovieItemBeanList.size());
        return mMovieItemBeanList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView movieImg;
        public TextView movieName,score;
        public RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.movieItem_img);
            movieName = itemView.findViewById(R.id.movieItem_name);
            ratingBar = itemView.findViewById(R.id.movieItem_rateBar);
            score = itemView.findViewById(R.id.movieItem_score);
        }
    }

}
