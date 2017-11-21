package com.example.danish.demotest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.demotest.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by danish on 2017/11/21.
 */

public class NewsListAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;


    public interface onRecyclerViewListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private onRecyclerViewListener onRecyclerViewListener;


    public void setOnRecyclerViewListener(NewsListAdaper.onRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    public NewsListAdaper(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (onRecyclerViewListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onRecyclerViewListener.onItemClick(holder.itemView,pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onRecyclerViewListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list,null);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView newsImg;
        public TextView newsTitle,newsContent;

        public MyViewHolder(View itemView) {
            super(itemView);

            newsImg = itemView.findViewById(R.id.news_img);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsContent = itemView.findViewById(R.id.news_content);
        }
    }


}
