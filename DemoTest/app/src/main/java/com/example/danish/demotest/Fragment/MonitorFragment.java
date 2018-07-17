package com.example.danish.demotest.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.demotest.Adapter.WaterFlowAdapter;
import com.example.danish.demotest.R;
import com.example.danish.demotest.Utilils.MyLogger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/11/17.
 */

public class MonitorFragment extends BaseFragment {

//    @BindView(R.id.monitor)
//    TextView monitor;
    Unbinder unbinder;

    private ChangeTextReceivr changeTextReceivr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, null);
        unbinder = ButterKnife.bind(this, view);

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.danish.demotest.ChangeMonitorTitle");
//        changeTextReceivr = new ChangeTextReceivr();
//        getActivity().registerReceiver(changeTextReceivr,intentFilter);


        RecyclerView recyclerView = view.findViewById(R.id.waterFlow_recyclerView);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);

        WaterFlowAdapter adapter = new WaterFlowAdapter(getContext());
        recyclerView.setAdapter(adapter);

        MyLogger.i("onCreateView");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        MyLogger.i("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getActivity().unregisterReceiver(changeTextReceivr);
        MyLogger.i("onDestroy");
    }

    class ChangeTextReceivr extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            monitor.setText("字体被修改了");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLogger.i("onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MyLogger.i("onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyLogger.i("onActivityCreated");
    }


    @Override
    public void onStart() {
        super.onStart();
        MyLogger.i("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogger.i("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MyLogger.i("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        MyLogger.i("onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MyLogger.i("onDetach");
    }
}
