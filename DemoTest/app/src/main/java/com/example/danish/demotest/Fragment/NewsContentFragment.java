package com.example.danish.demotest.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.demotest.R;

/**
 * Created by danish on 2017/11/28.
 */

public class NewsContentFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_content_frag,null);
        TextView textView = view.findViewById(R.id.newsContent_text);
        textView.setText(getArguments().getString("content"));
        return view;
    }
}
