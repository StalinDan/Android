package com.example.danish.baseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.BookAdapter;
import com.example.danish.baseproject.bean.BookListBean;
import com.example.danish.baseproject.dao.BookListDao;
import com.example.danish.baseproject.dao.MovieListDao;
import com.example.danish.baseproject.net.RequestListener;
import com.example.danish.baseproject.net.RequestListenerImpl;
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

public class BookFragment extends BaseFragment {
//    @BindView(R.id.book_recyclerView)
    RecyclerView bookRecyclerView;

    private static String TAG = "BookFragment";
    private List<BookListBean.BooksBean> bookList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, null);
//        unbinder = ButterKnife.bind(this, view);

        configBookData(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

    private void configBookData(final View view) {
        String bookName = "黑客与画家";
        new BookListDao().bookListData(bookName, new RequestListenerImpl<BookListBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<BookListBean>(){}.getType();
            }

            @Override
            public void onSuccess(BookListBean response) {

                bookList = response.getBooks();
                configAdapter(view);
            }

            @Override
            public void onFail(String errMsg) {
                Log.d(TAG,"请求失败---"+errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                Log.d(TAG,"请求失败---"+e.toString());
            }
        });
    }

    private void configAdapter(View view){
        bookRecyclerView = view.findViewById(R.id.book_recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL,false);
        bookRecyclerView.setLayoutManager(manager);

        BookAdapter adapter = new BookAdapter(getContext(),bookList);
        bookRecyclerView.setAdapter(adapter);
    }
}
