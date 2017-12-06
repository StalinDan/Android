package com.example.danish.baseproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.danish.baseproject.R;

import org.w3c.dom.Text;

/**
 * Created by danish on 2017/12/5.
 */

public class BookCardItem extends RelativeLayout {
    private ImageView bookImg;
    private TextView bookName,bookAuthor,bookSubTitle,bookVersionDate,bookPage,bookPrice;

    public BookCardItem(Context context) {
        super(context);
    }

    public BookCardItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.book_item,null);
        initContext(context);
        addView(view);
    }

    public void initContext (Context context) {
        bookImg = findViewById(R.id.bookImg);
        bookName = findViewById(R.id.bookName);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookSubTitle = findViewById(R.id.bookSubTitle);
        bookPage = findViewById(R.id.bookPage);
        bookPrice = findViewById(R.id.bookPrice);
    }

}
