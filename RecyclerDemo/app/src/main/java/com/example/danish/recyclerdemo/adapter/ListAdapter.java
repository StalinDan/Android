package com.example.danish.recyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.recyclerdemo.R;
import com.example.danish.recyclerdemo.bean.ListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by danish on 2017/12/7.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {



    public List<ListBean> mList;
    private Context mContext;

    public ListAdapter(Context context, List list) {
        mList = list;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MyViewHolder viewHolder = (MyViewHolder) holder;
        ListBean listBean = mList.get(position);
        Log.d("ListAdapter", "getTitle===" + listBean.getTitle());
        viewHolder.listTitle.setText(listBean.getTitle());
        viewHolder.listContent.setText(listBean.getContent());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_title)
        TextView listTitle;
        @BindView(R.id.list_content)
        TextView listContent;

//        @BindView(R.id.list_delete)
//        TextView listDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
