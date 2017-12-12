package com.example.danish.baseproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.baseproject.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by danish on 2017/12/11.
 */

public class QBHomeAdapter extends RecyclerView.Adapter {

    private List mList;
    private Context mContext;
    public QBHomeAdapter(Context context, List list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.qb_home_newsitem,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView news;
        public MyViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.qbhome_img);
            news = itemView.findViewById(R.id.qbhome_news);
        }
    }
}
