package com.example.danish.demotest.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/27.
 */

public class WaterFlowAdapter extends RecyclerView.Adapter<WaterFlowAdapter.WaterflowViewHolder> {

    private Context mContext;

    public WaterFlowAdapter(Context context) {
        mContext = context;
    }

    @Override
    public WaterflowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
           view = LayoutInflater.from(mContext).inflate(R.layout.staggerd_warterflow_item_one,null);
        } else  {
            view = LayoutInflater.from(mContext).inflate(R.layout.staggerd_waterflow_item_two,null);
        }

        WaterflowViewHolder holder = new WaterflowViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(WaterflowViewHolder holder, int position) {

        holder.itemText.setText("item-"+position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int getItemViewType(int position) {
        return position%2;
    }

    public class WaterflowViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImg;
        public TextView itemText;


        public WaterflowViewHolder(View itemView) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.waterFlow_img);
            itemText = itemView.findViewById(R.id.waterFlow_text);
        }
    }
}
