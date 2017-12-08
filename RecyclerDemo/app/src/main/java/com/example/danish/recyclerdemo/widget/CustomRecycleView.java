package com.example.danish.recyclerdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Scroller;


/**
 * Created by danish on 2017/12/7.
 */

public class CustomRecycleView extends RecyclerView {

    //item的根布局
    private LinearLayout itemRoot;
    //上一次滑动的Item根布局
    private LinearLayout itemRootLast;
    //上次X轴的滑动坐标
    private int mLastX;
    //上次Y轴的滑动坐标
    private int mLastY;
    //滑动的最大距离
    private final int MAX_WIDTH = 100;
    private Context mContext;
    private Scroller mScroller;

    public CustomRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context,new LinearInterpolator(context,null));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int maxLength = dipToPx(mContext, MAX_WIDTH);
        int x = (int) e.getX();
        int y = (int) e.getY();
        final int position;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //恢复上一次侧滑的ITEM
                if (itemRootLast != null) {
                    itemRootLast.scrollTo(0, 0);
                    invalidate();
                }

                //根据点击的坐标获取那个Item被点击了
                View view = findChildViewUnder(x, y);
                if (view == null) {
                    return false;
                }

                final com.example.danish.recyclerdemo.adapter.ListAdapter.MyViewHolder myViewHolder
                        = (com.example.danish.recyclerdemo.adapter.ListAdapter.MyViewHolder) getChildViewHolder(view);

                itemRootLast = itemRoot = (LinearLayout) myViewHolder.itemView.getParent();
                position = myViewHolder.getAdapterPosition();
                if (mOnItemClickListener != null) {
                    myViewHolder.itemView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onClick(myViewHolder.itemView, position);
                        }
                    });
                }

            }
            break;

            case MotionEvent.ACTION_MOVE: {
                if (itemRoot == null) {
                    return false;
                }
                if (Math.abs(mLastX - x) > 0 && Math.abs(mLastX - x) > Math.abs(mLastY - y)) {
                    int scrollX = itemRoot.getScrollX();
                    int newScrollX = scrollX + mLastX - x;
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > maxLength) {
                        newScrollX = maxLength;
                    }
                    itemRoot.scrollTo(newScrollX, 0);
                }

            }
            break;

            case MotionEvent.ACTION_UP: {
                if (itemRoot == null) {
                    return false;
                }
                int scrollX = itemRoot.getScrollX();
                int newScrollX = scrollX + mLastX - x;
                if (scrollX > maxLength / 2) {
                    newScrollX = maxLength;
                } else {
                    newScrollX = 0;
                }
                mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
                invalidate();
            }
            break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);

    }

    private int dipToPx(Context context,int dip){
        return (int)(dip*context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            itemRoot.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if(itemRootLast !=null){
                itemRootLast.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            }
        }
        invalidate();
    }

    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener{
        void onClick(View view,int position);
    }

}
