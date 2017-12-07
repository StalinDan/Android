package com.example.danish.baseproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.bean.BookListBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by danish on 2017/12/6.
 */

public class BookCatalogFragment extends Fragment {
    TextView bookCatalog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_catalog, null);

        Bundle bundle = getArguments();
        BookListBean.BooksBean booksBean = (BookListBean.BooksBean)bundle.getSerializable("bookBean");
        bookCatalog = view.findViewById(R.id.book_catalog);
        bookCatalog.setText(booksBean.getCatalog());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
