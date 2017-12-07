package com.example.danish.baseproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.BookDetailPagerAdapter;
import com.example.danish.baseproject.bean.BookListBean;
import com.example.danish.baseproject.fragment.BookAuthorFragment;
import com.example.danish.baseproject.fragment.BookCatalogFragment;
import com.example.danish.baseproject.fragment.BookContentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends AppCompatActivity {

    @BindView(R.id.bookDetail_img)
    ImageView bookDetailImg;
    @BindView(R.id.bookDetail_Tab)
    TabLayout bookDetailTab;
    @BindView(R.id.book_viewPager)
    ViewPager bookViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        BookListBean.BooksBean booksBean = (BookListBean.BooksBean)intent.getSerializableExtra("book");

        Log.d("BookDetailActivity","booksBean---->"+booksBean);

        bookDetailTab.addTab(bookDetailTab.newTab().setText("内容简介"));
        bookDetailTab.addTab(bookDetailTab.newTab().setText("作者简介"));
        bookDetailTab.addTab(bookDetailTab.newTab().setText("目录"));

        bookDetailTab.setupWithViewPager(bookViewPager);



        List<Fragment> list = new ArrayList();
        BookContentFragment contentFragment = new BookContentFragment();
        list.add(contentFragment);
        BookAuthorFragment authorFragment = new BookAuthorFragment();
        list.add(authorFragment);
        BookCatalogFragment catalogFragment = new BookCatalogFragment();
        list.add(catalogFragment);

        Bundle bundle = new Bundle();
        bundle.putSerializable("bookBean",booksBean);
        contentFragment.setArguments(bundle);
        authorFragment.setArguments(bundle);
        catalogFragment.setArguments(bundle);

        BookDetailPagerAdapter bookDetailPagerAdapter = new BookDetailPagerAdapter(getSupportFragmentManager(),list);
        bookViewPager.setAdapter(bookDetailPagerAdapter);

        bookDetailTab.getTabAt(0).setText("内容简介");
        bookDetailTab.getTabAt(1).setText("作者简介");
        bookDetailTab.getTabAt(2).setText("目录");

    }
}
