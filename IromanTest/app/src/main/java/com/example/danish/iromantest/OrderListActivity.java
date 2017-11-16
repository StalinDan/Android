package com.example.danish.iromantest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.danish.iromantest.Adapter.OrderListAdapter;

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);



        RecyclerView recyclerView = findViewById(R.id.orderList_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        OrderListAdapter orderListAdapter = new OrderListAdapter(this);
        recyclerView.setAdapter(orderListAdapter);
    }
}
