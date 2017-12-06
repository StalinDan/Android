package com.example.danish.baseproject.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.bean.BookListBean.BooksBean;
import com.example.danish.baseproject.bitmaputils.ImageLoadUtils;
import com.github.lzyzsd.randomcolor.RandomColor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by danish on 2017/12/5.
 */

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    private Context mContext;
    private List<BooksBean> mBookList;

    public BookAdapter(Context context, List<BooksBean> bookList) {

        mContext = context;
        mBookList = bookList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);


        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        final MyViewHolder viewHolder = (MyViewHolder) holder;

        RandomColor randomColor = new RandomColor();
        int color = randomColor.randomColor();

        Drawable drawable = ResourcesCompat.getDrawable(mContext.getResources(),R.drawable.bg_oval_corner,null);
        drawable.setTint(color);

        BooksBean booksBean = mBookList.get(position);
        ImageLoadUtils.loadImageView(mContext, booksBean.getImage(), viewHolder.bookImg);
        viewHolder.bookName.setText(booksBean.getTitle());
        String author = "";
        for (int i = 0; i < booksBean.getAuthor().size(); i++) {
            author += booksBean.getAuthor().get(i);
        }
        viewHolder.bookAuthor.setText(author);

        viewHolder.bookSubTitle.setText(booksBean.getSubtitle());
        viewHolder.bookVersion.setText(booksBean.getPubdate());
        viewHolder.bookPage.setText(booksBean.getPages());
        viewHolder.bookPrice.setText(booksBean.getPrice());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.book_bg)
        View bookBg;
        @BindView(R.id.bookImg)
        ImageView bookImg;
        @BindView(R.id.bookName)
        TextView bookName;
        @BindView(R.id.bookAuthor)
        TextView bookAuthor;
        @BindView(R.id.bookSubTitle)
        TextView bookSubTitle;
        @BindView(R.id.bookVersion)
        TextView bookVersion;
        @BindView(R.id.bookPage)
        TextView bookPage;
        @BindView(R.id.bookPrice)
        TextView bookPrice;

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
