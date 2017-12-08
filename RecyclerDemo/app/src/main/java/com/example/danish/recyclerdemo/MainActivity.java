package com.example.danish.recyclerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.example.danish.recyclerdemo.adapter.ListAdapter;
import com.example.danish.recyclerdemo.bean.ListBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ListBean> mList = new ArrayList<>();
    private DividerItemDecoration mDivider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        recyclerView = findViewById(R.id.main_recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        ListAdapter adapter = new ListAdapter(this,mList);


//        mDivider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(mDivider);

        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(new MyItemTouchCallback(adapter));
        helper.attachToRecyclerView(recyclerView);


    }

    private void initData(){
        for (int i=0; i<10;i++) {
            ListBean bean = new ListBean();
            bean.setTitle("标题 "+i);
            bean.setContent("内容"+i);
            mList.add(bean);
        }
    }

    public class MyItemTouchCallback extends ItemTouchHelper.Callback {
        private  final  ListAdapter adapter;
        public MyItemTouchCallback(ListAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlag;
            int swipeFlag;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

            dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
            swipeFlag =  ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;

            Log.d("MainActivity","MainActivityMainActivityMainActivity");
            return makeMovementFlags(dragFlag,swipeFlag);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i<toPosition;i++) {
                    Collections.swap(adapter.mList,i,i+1);
                }
            } else {
                for (int i = fromPosition;i>toPosition;i--) {
                    Collections.swap(adapter.mList,i,i-1);
                }
            }
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            if (direction ==ItemTouchHelper.END) {
                adapter.mList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        }
    }
}
