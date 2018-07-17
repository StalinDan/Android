package com.example.danish.demotest.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.danish.demotest.Activity.NewsDetailActivity;
import com.example.danish.demotest.Adapter.NewsListAdaper;
import com.example.danish.demotest.R;
import com.example.danish.demotest.Utilils.MyLogger;
import com.example.danish.demotest.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danish on 2017/11/17.
 */

public class DataBoardFragment extends BaseFragment {

    private boolean isSelected;
    private List <NewsBean> newsBeanList = new ArrayList<>();
    private FragmentManager fManager = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragmet_databoard,null);

        fManager = getFragmentManager();

       initData();

        final RecyclerView recyclerView = view.findViewById(R.id.news_list_recyclerView);

        //1. 以垂直或者水平列表方式展示Item
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        //2. 以网格方式展示Item
        GridLayoutManager manager1 = new GridLayoutManager(getContext(),4, OrientationHelper.VERTICAL,false);

        //3. 以瀑布流方式展示Item
        StaggeredGridLayoutManager manager2 = new StaggeredGridLayoutManager(2,OrientationHelper.VERTICAL);

        recyclerView.setLayoutManager(manager);

        final NewsListAdaper adaper = new NewsListAdaper(getContext(),newsBeanList,fManager);

        recyclerView.setAdapter(adaper);



       return view;

    }

    private void initData(){
        for (int i=0;i<3;i++) {
            NewsBean newsBean = new NewsBean();
            newsBean.setTitle("新闻标题 "+ i);
            newsBean.setContent("新闻内容 "+i );
            newsBean.setSelected(false);
            newsBeanList.add(newsBean);
        }
    }


}
