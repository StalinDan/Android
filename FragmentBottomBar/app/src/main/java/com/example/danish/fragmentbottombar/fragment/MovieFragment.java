package com.example.danish.fragmentbottombar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danish.fragmentbottombar.R;
import com.example.danish.fragmentbottombar.adapter.MovieAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/11/30.
 */

public class MovieFragment extends BaseFragment {

    @BindView(R.id.movie_recyclevier)
    RecyclerView movieRecyclevier;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_fragment, null);
        unbinder = ButterKnife.bind(this, view);

        GridLayoutManager manager = new GridLayoutManager(getContext(),3, OrientationHelper.VERTICAL,false);
        movieRecyclevier.setLayoutManager(manager);

        MovieAdapter adapter = new MovieAdapter(getContext());
        movieRecyclevier.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
