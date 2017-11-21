package com.example.danish.demotest.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.danish.demotest.Activity.NewsDetailActivity;
import com.example.danish.demotest.Adapter.NewsListAdaper;
import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/17.
 */

public class DataBoardFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.fragmet_databoard,null);

        RecyclerView recyclerView = view.findViewById(R.id.news_list_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        NewsListAdaper adaper = new NewsListAdaper(getContext());
        adaper.setOnRecyclerViewListener(new NewsListAdaper.onRecyclerViewListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("DataBoardFragment","onItemClick---"+position);

                Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("DataBoardFragment","onItemLongClick---"+position);
            }
        });
        recyclerView.setAdapter(adaper);

       return view;


    }
}
