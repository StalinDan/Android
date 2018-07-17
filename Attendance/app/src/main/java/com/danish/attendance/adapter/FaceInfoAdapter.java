package com.danish.attendance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.danish.attendance.R;
import com.danish.attendance.bean.FaceInfoBean;

import java.util.List;

/**
 * Created by danish on 2018/3/8.
 */

public class FaceInfoAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<FaceInfoBean> mList;

    public interface FaceInfoCallBack{
        public void faceInfoData(List<FaceInfoBean> list);
    };

    private FaceInfoCallBack faceInfoCallBack;

    public void setFaceInfoCallBack(FaceInfoCallBack faceInfoCallBack) {
        this.faceInfoCallBack = faceInfoCallBack;
    }

    public FaceInfoAdapter(Context mContext, List<FaceInfoBean> list) {
        this.mContext = mContext;
        this.mList = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_face_info,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder myViewHolder = (MyViewHolder)holder;
        final FaceInfoBean faceInfoBean = mList.get(position);
        myViewHolder.name.setText(faceInfoBean.getName());
        myViewHolder.workNo.setText(faceInfoBean.getWorkNo());
//        myViewHolder.ldName.setText(faceInfoBean.getLdName());

        if (faceInfoBean.isSelect()) {
            myViewHolder.tickImg.setBackgroundResource(R.mipmap.tick_selected);
        } else {
            myViewHolder.tickImg.setBackgroundResource(R.mipmap.tick_unselected);
        }

        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceInfoBean.setSelect(!faceInfoBean.isSelect());
                if (faceInfoBean.isSelect()) {
                    myViewHolder.tickImg.setBackgroundResource(R.mipmap.tick_selected);
                } else {
                    myViewHolder.tickImg.setBackgroundResource(R.mipmap.tick_unselected);
                }
                faceInfoCallBack.faceInfoData(mList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView workNo,name;
        private ImageView tickImg;
        View view;
        public MyViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            workNo = itemView.findViewById(R.id.workNo);
            name = itemView.findViewById(R.id.name);
            tickImg = itemView.findViewById(R.id.tickImg);
//            ldName = itemView.findViewById(R.id.ldName);
        }
    }

    public void updateData(List list) {
        mList = list;
        notifyDataSetChanged();
    }
}
