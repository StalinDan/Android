package com.example.danish.baseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.bean.MovieListBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/12/6.
 */

public class MovieDetailLinkFragment extends Fragment {

    @BindView(R.id.movieDetail_link)
    TextView movieDetailLink;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_link, null);
        Log.d("MovieDetailLinkFragment", "MovieDetailLinkFragment");


        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle!=null){
            MovieListBean.MovieItemBean movieItemBean = (MovieListBean.MovieItemBean) bundle.getSerializable("movie");
            movieDetailLink.setText("简介："+movieItemBean.getAlt());
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
