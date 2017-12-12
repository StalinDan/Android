package com.example.danish.paddemotest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.paddemotest.R;
import com.example.danish.paddemotest.bean.GridItemBean;

import java.util.List;

/**
 * Created by danish on 2017/12/12.
 */

public class GridAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GridItemBean> mList;

    public GridAdapter(Context context,List list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder)holder;
        GridItemBean itemBean = mList.get(position);
        myViewHolder.name.setText(itemBean.getName());
        myViewHolder.trainNo.setText(itemBean.getTrainNo());
        myViewHolder.bed.setText(itemBean.getBed());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView bed,trainNo,name;
        public MyViewHolder(View itemView) {
            super(itemView);
            bed = itemView.findViewById(R.id.bed);
            trainNo = itemView.findViewById(R.id.trainNo);
            name = itemView.findViewById(R.id.name);
        }
    }
}
