package com.example.danish.demotest.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.demotest.Activity.NewsDetailActivity;
import com.example.danish.demotest.Fragment.NewsContentFragment;
import com.example.danish.demotest.R;
import com.example.danish.demotest.Widgets.NewsContent;
import com.example.danish.demotest.bean.NewsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by danish on 2017/11/21.
 */

public class NewsListAdaper extends RecyclerView.Adapter<NewsListAdaper.MyViewHolder> {

    @BindView(R.id.news_img)
    ImageView newsImg;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_content)
    TextView newsContent;
    @BindView(R.id.news_Btn)
    Button newsBtn;

    private Context mContext;

    private List<NewsBean> mNewsBeanList;

    private FragmentManager mFragManager;

//    public interface onRecyclerViewListener {
//        void onItemClick(View view, int position);
//
//        void onItemLongClick(View view, int position);
//
//        void onBtnClick(View view, int position);
//    }
//
//    private onRecyclerViewListener onRecyclerViewListener;
//
//
//    public void setOnRecyclerViewListener(NewsListAdaper.onRecyclerViewListener onRecyclerViewListener) {
//        this.onRecyclerViewListener = onRecyclerViewListener;
//    }

    public NewsListAdaper(Context context, List<NewsBean> newsBeanList,FragmentManager fragmentManager) {
        mContext = context;
        mNewsBeanList = newsBeanList;

        mFragManager = fragmentManager;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("NewsListAdaper","onItemClick---"+position);

//                NewsBean news = new NewsBean();
//                news.setTitle("添加标题"+(position+1));
//                news.setContent("添加内容="+(position+1));
//                news.setSelected(false);
//
//                insert(news,position);


//                Intent intent = new Intent(mContext,NewsDetailActivity.class);
//                mContext.startActivity(intent);


                FragmentTransaction transaction = mFragManager.beginTransaction();
                NewsContentFragment contentFragment = new NewsContentFragment();
                Bundle bd = new Bundle();
                bd.putString("content",mNewsBeanList.get(position).getContent());
                contentFragment.setArguments(bd);

//                transaction.setCustomAnimations(R.anim.)
                transaction.replace(R.id.layout_content,contentFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d("NewsListAdaper","onItemLongClick---"+position);
                remove(position);
                return false;
            }
        });

        holder.newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewsListAdaper","newsBtnClick---"+position);

                NewsBean newsBean = mNewsBeanList.get(position);

                newsBean.isSelected = !newsBean.isSelected;

                if (newsBean.isSelected) {
                    holder.newsBtn.setBackgroundColor(Color.RED);
                } else  {
                    holder.newsBtn.setBackgroundColor(Color.BLACK);
                }

                mNewsBeanList.set(position,newsBean);

            }
        });


        NewsBean newsBean = mNewsBeanList.get(position);
        holder.newsTitle.setText(newsBean.title);
        holder.newsContent.setText(newsBean.content);


        if (newsBean.isSelected) {
            holder.newsBtn.setBackgroundColor(Color.RED);
        } else  {
            holder.newsBtn.setBackgroundColor(Color.BLACK);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list, null);
        final MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @Override
    public int getItemCount() {
        return mNewsBeanList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView newsImg;
        public TextView newsTitle, newsContent;
        public Button newsBtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            newsImg = itemView.findViewById(R.id.news_img);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsContent = itemView.findViewById(R.id.news_content);
            newsBtn = itemView.findViewById(R.id.news_Btn);
        }
    }

    public void insert(NewsBean newsBean, int position) {
        mNewsBeanList.add(position, newsBean);
//        notifyItemInserted(position);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mNewsBeanList.remove(position);
//        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void updateBtnColor(int color,MyViewHolder holder) {
        holder.newsBtn.setBackgroundColor(color);
        notifyDataSetChanged();
    }

}
