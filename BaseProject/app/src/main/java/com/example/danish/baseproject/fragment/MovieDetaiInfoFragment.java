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

public class MovieDetaiInfoFragment extends Fragment {


    @BindView(R.id.movieDetail_Director)
    TextView movieDetailDirector;
    @BindView(R.id.movieDetail_actor)
    TextView movieDetailActor;
    @BindView(R.id.movieDetail_movieName)
    TextView movieDetailMovieName;
    @BindView(R.id.movieDetail_time)
    TextView movieDetailTime;
    @BindView(R.id.movieDetail_type)
    TextView movieDetailType;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_info, null);

        Log.d("MovieDetaiInfoFragment", "MovieDetaiInfoFragment");
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            MovieListBean.MovieItemBean movieItemBean = (MovieListBean.MovieItemBean) bundle.getSerializable("movie");
            configMovieDetailData(movieItemBean);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void configMovieDetailData (MovieListBean.MovieItemBean itemBean) {

        String directorStr = "导演：";
        for (int i = 0;i < itemBean.getDirectors().size();i++) {
            MovieListBean.CastsItem castsItem = itemBean.getDirectors().get(i);
            directorStr += " "+castsItem.getName();
        }
        movieDetailDirector.setText(directorStr);

        String actorStr = "主演：";
        for (int i = 0;i < itemBean.getCasts().size();i++) {
            MovieListBean.CastsItem castsItem = itemBean.getCasts().get(i);
            actorStr += " "+castsItem.getName();
        }
        movieDetailActor.setText(actorStr);

        movieDetailTime.setText("上映："+itemBean.getYear());

        String typeStr = "类型：";
        for (int i= 0;i<itemBean.getGenres().size();i++) {

            typeStr += "/"+itemBean.getGenres().get(i);
        }
        movieDetailType.setText(typeStr);

    }
}
