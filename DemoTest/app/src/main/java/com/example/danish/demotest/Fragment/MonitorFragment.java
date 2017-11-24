package com.example.danish.demotest.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.demotest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/11/17.
 */

public class MonitorFragment extends BaseFragment {

    @BindView(R.id.monitor)
    TextView monitor;
    Unbinder unbinder;

    private ChangeTextReceivr changeTextReceivr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, null);
        unbinder = ButterKnife.bind(this, view);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.danish.demotest.ChangeMonitorTitle");
        changeTextReceivr = new ChangeTextReceivr();
        getActivity().registerReceiver(changeTextReceivr,intentFilter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(changeTextReceivr);
    }

    class ChangeTextReceivr extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            monitor.setText("字体被修改了");
        }
    }
}
