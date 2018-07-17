package com.danish.attendance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danish.attendance.R;
import com.danish.attendance.bean.BindDeviceInfoBean;
import com.danish.attendance.utils.MyLogger;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by danish on 2018/6/12.
 */

public class AttendanceAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List mDeviceList;

    public AttendanceAdapter(Context mContext, List deviceList) {
        this.mContext = mContext;
        this.mDeviceList = deviceList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_device,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder)holder;
        BindDeviceInfoBean.ResultBean.DeviceVOSBean deviceVOSBean = (BindDeviceInfoBean.ResultBean.DeviceVOSBean) mDeviceList.get(position);
        myViewHolder.orderNum.setText(deviceVOSBean.getSid()+"");
        if (deviceVOSBean.getDevType() == 1){
            myViewHolder.deviceType.setText("车载台");
        } else if (deviceVOSBean.getDevType() == 2) {
            myViewHolder.deviceType.setText("安全宝");
        } else if (deviceVOSBean.getDevType() == 3) {
            myViewHolder.deviceType.setText("手环");
        }

        myViewHolder.bindTime.setText(deviceVOSBean.getBindTime());
        myViewHolder.deviceNo.setText(deviceVOSBean.getIdentifyCode());
        if (deviceVOSBean.getDevUsableStatus() == 0) {
            myViewHolder.deviceStatus.setText("故障");
        }else {
            myViewHolder.deviceStatus.setText("正常");
        }

    }

    @Override
    public int getItemCount() {
//        MyLogger.i("mDeviceList.size()-->"+mDeviceList.size());
        return mDeviceList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView orderNum,deviceType,bindTime,deviceNo,deviceStatus;
        public MyViewHolder(View itemView) {
            super(itemView);

            orderNum = itemView.findViewById(R.id.num);
            deviceType = itemView.findViewById(R.id.deviceType);
            bindTime = itemView.findViewById(R.id.bindTime);
            deviceNo = itemView.findViewById(R.id.deviceNo);
            deviceStatus = itemView.findViewById(R.id.deviceStatus);
        }
    }

    public void updateDeviceData(List list) {
        mDeviceList = list;
        notifyDataSetChanged();
    }
}
