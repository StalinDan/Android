package com.example.danish.baseproject.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.QBHomeAdapter;
import com.example.danish.baseproject.adapter.QBHomePagerAdapter;

import java.util.ArrayList;

public class QBHomeActivity extends AppCompatActivity {

    private ViewPager pager;
    private ArrayList<ImageView> mList = new ArrayList<>();
    private ArrayList<ImageView> mDataList = new ArrayList<>();
    private int[] mColors = {0xFF34e21,0xFF7231ab,0xFF56bcaa};

    private RecyclerView homeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbhome);

        initData();

        pager = findViewById(R.id.qb_homePager);


        QBHomePagerAdapter adapter = new QBHomePagerAdapter(mList);
        pager.setAdapter(adapter);

        homeRecyclerView = findViewById(R.id.qb_recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        homeRecyclerView.setLayoutManager(manager);

        QBHomeAdapter homeAdapter = new QBHomeAdapter(this,mList);
        homeRecyclerView.setAdapter(homeAdapter);

    }

    private void initData(){
        for (int i = 0;i<3;i++) {
            ImageView img = new ImageView(this);
            img.setBackgroundColor(mColors[i]);
            mList.add(img);
        }

        for (int i=0;i<5;i++) {
            
        }
    }
}
