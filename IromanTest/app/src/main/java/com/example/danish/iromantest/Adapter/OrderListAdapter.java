package com.example.danish.iromantest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.iromantest.Bean.OrderListBean;
import com.example.danish.iromantest.R;

import java.util.List;

/**
 * Created by danish on 2017/11/16.
 */

public class OrderListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private OrderListBean orderListBean;


    public OrderListAdapter(Context context) {
        mContext = context;
       // orderListBean = listBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
    }



    @Override
    public int getItemCount() {
        return 3;
    }

    private  class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, score, date, status;
        public ImageView productImg;

        public MyViewHolder(View view) {
            super(view);

            productName = view.findViewById(R.id.productName_text);
            score = view.findViewById(R.id.score_text);
            date = view.findViewById(R.id.date_text);
            status = view.findViewById(R.id.status_text);
            productImg = view.findViewById(R.id.productImg);
        }
    }
}
