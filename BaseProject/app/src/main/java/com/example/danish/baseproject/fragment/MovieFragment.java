package com.example.danish.baseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.MovieAdapter;
import com.example.danish.baseproject.bean.MovieListBean;
import com.example.danish.baseproject.bean.ProvinceBean;
import com.example.danish.baseproject.dao.MovieListDao;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.RequestListenerImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/11/30.
 */

public class MovieFragment extends BaseFragment {

    static final String TAG = "MovieFragment";

    RecyclerView movieRecyclevier;
    private List<MovieListBean.MovieItemBean> mMoviesList = new ArrayList();
    private MovieListBean movieListBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.movie_fragment, null);

//        new MovieListDao().movieListData(new RequestListener() {
//            @Override
//            public void onFinish(String response) {
//                Log.d(TAG,"response"+response);
//
//                JsonParser parser = new JsonParser();
//                JsonArray jsonArray = parser.parse(response).getAsJsonArray();
//
//                Gson gson = new Gson();
//                List<MovieListBean.MovieItemBean> list = new ArrayList<>();
//                for (JsonElement element:jsonArray) {
//                    MovieListBean.MovieItemBean movieItemBean = gson.fromJson(element,MovieListBean.MovieItemBean.class);
//                    list.add(movieItemBean);
//
//                    Log.d("TAG","movieItemBean--->"+movieItemBean);
//                }
//            }
//
//            @Override
//            public void onFail(String errMsg) {
//                Log.d(TAG,"请求失败"+errMsg);
//            }
//
//            @Override
//            public void onNetError(Throwable e) {
//                Log.d(TAG,"请求失败"+e.toString());
//            }
//        });

        new MovieListDao().movieListData(new RequestListenerImpl<MovieListBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<MovieListBean>(){

                }.getType();
            }

            @Override
            public void onSuccess(MovieListBean response) {

                Log.d(TAG,"response==>"+response);
                movieListBean = response;
                mMoviesList = movieListBean.getSubjects();
                Log.d(TAG,"getSubjects==>"+mMoviesList.size());

                configAdapter(view);
            }

            @Override
            public void onFail(String errMsg) {
                Log.d(TAG,"请求失败---"+errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                Log.d(TAG,"请求失败==="+e.toString());
            }
        });


        return view;
    }

    public void configAdapter (View view){
        movieRecyclevier = view.findViewById(R.id.movie_recyclevier);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, OrientationHelper.VERTICAL, false);

        movieRecyclevier.setLayoutManager(manager);

        MovieAdapter adapter = new MovieAdapter(getContext(),mMoviesList);
        movieRecyclevier.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
}
