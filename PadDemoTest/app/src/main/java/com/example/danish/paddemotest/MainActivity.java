package com.example.danish.paddemotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.danish.paddemotest.adapter.GridAdapter;
import com.example.danish.paddemotest.bean.GridItemBean;
import com.example.danish.paddemotest.widget.GridSpacingItemDecoration;
import com.example.danish.paddemotest.widget.TopBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<GridItemBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        recyclerView = findViewById(R.id.main_recycler);
        GridLayoutManager manager = new GridLayoutManager(this,6);
        recyclerView.setLayoutManager(manager);

        GridAdapter adapter = new GridAdapter(this,mList);
        recyclerView.setAdapter(adapter);

        int spanCount = 6;
//        int spacing = 50;
        int spacing = 0;
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));

        TopBar topBar = findViewById(R.id.topBar);
        topBar.setLeftImg(R.drawable.ic_launcher_background);
        topBar.setRightImg(R.drawable.ic_launcher_foreground);
        topBar.setTitle("Hello");

        topBar.setLeftListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"左侧按钮",Toast.LENGTH_SHORT).show();
                Log.d("MainActivity","左侧按钮");
            }
        });

        topBar.setRightListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"右侧按钮",Toast.LENGTH_SHORT).show();
                Log.d("MainActivity","右侧按钮");
            }
        });

    }

    private void initData() {
        for (int i = 0;i<40;i++) {
            GridItemBean bean = new GridItemBean();
            bean.setBed("床位"+i);
            bean.setName("张三"+i);
            if (i%4 == 0) {
                bean.setStatus(4);
            } else if (i%4 == 1){
                bean.setStatus(1);
            } else if (i%4 == 2){
                bean.setStatus(2);
            } else if (i%4 == 3){
                bean.setStatus(3);
            } else {
                bean.setStatus(0);
            }
            bean.setTrainNo("G"+i);

            mList.add(bean);
        }
    }
}
