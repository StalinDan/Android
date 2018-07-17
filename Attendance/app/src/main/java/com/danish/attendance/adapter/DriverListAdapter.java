package com.danish.attendance.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danish.attendance.R;
import com.danish.attendance.activity.DriverLIstActivity;
import com.danish.attendance.activity.RetireActivity;
import com.danish.attendance.bean.DriverListBean;
import com.danish.attendance.popWindow.RegisterFacePopWindow;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danish on 2018/6/11.
 */

public class DriverListAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<DriverListBean.ResultBean.ResultListBean> listBeans = new ArrayList<>();

    public interface DriverListAdapterSelectItem {
        public void selectIndex(int index, List<DriverListBean.ResultBean.ResultListBean> listBeans);
        public void deleteFaceAtIndex(int index, List<DriverListBean.ResultBean.ResultListBean> listBeans);
    }

    private DriverListAdapterSelectItem driverListAdapterSelectItem;

    public void setDriverListAdapterSelectItem(DriverListAdapterSelectItem driverListAdapterSelectItem) {
        this.driverListAdapterSelectItem = driverListAdapterSelectItem;
    }

    public DriverListAdapter(Context mContext, DriverLIstActivity driverLIstActivity) {
        this.mContext = mContext;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_driver_list,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyViewHolder myViewHolder = (MyViewHolder)holder;
        DriverListBean.ResultBean.ResultListBean bean = listBeans.get(position);
        myViewHolder.num.setText(bean.getSid()+"");
        myViewHolder.workNo.setText(bean.getWorkNo());
        myViewHolder.name.setText(bean.getName());
        myViewHolder.trainNo.setText(bean.getPlateNo());
        if (bean.isFaceFeatureExist()) {
            myViewHolder.faceInfo.setText("已录入");
            myViewHolder.operate.setText("删除");
            myViewHolder.operate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    driverListAdapterSelectItem.selectIndex(position,listBeans);
                    driverListAdapterSelectItem.deleteFaceAtIndex(position,listBeans);

                }
            });
        } else {
            myViewHolder.faceInfo.setText("未录入");
            myViewHolder.operate.setText("录入");

            myViewHolder.operate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    driverListAdapterSelectItem.selectIndex(position,listBeans);


                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView num,workNo,name,trainNo,faceInfo,operate;

        public MyViewHolder(View itemView) {
            super(itemView);

            num = itemView.findViewById(R.id.num);
            workNo = itemView.findViewById(R.id.workNo);
            name = itemView.findViewById(R.id.name);
            trainNo = itemView.findViewById(R.id.trainNo);
//            status = itemView.findViewById(R.id.status);
            faceInfo = itemView.findViewById(R.id.faceInfo);
            operate = itemView.findViewById(R.id.operate);
        }
    }

    public void removeData () {
        listBeans.clear();
        notifyDataSetChanged();
    }

    public void addData (DriverListBean driverListBean) {
        listBeans.addAll(driverListBean.getResult().getResultList());
        notifyDataSetChanged();
    }

    public void updateFaceData (int index, DriverListBean.ResultBean.ResultListBean listBean) {
        listBeans.set(index,listBean);
        notifyDataSetChanged();
    }
}
