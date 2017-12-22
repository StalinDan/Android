package com.example.danish.paddemotest.widget;

import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by danish on 2017/12/13.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount; //列数
    private int spacing; //间隔
    private boolean includeEdge; //是否包含边缘

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom

        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }

    }


    /**
     * 是否是最后一行
     * @param parent
     * @param position
     * @return
     */
    public boolean isLastRow(RecyclerView parent,int position) {
        int itemCount = parent.getAdapter().getItemCount();  //itemView的总数
        GridLayoutManager gridLayoutManager = (GridLayoutManager)parent.getLayoutManager();
        int spanCount = gridLayoutManager.getSpanCount(); //gridLayoutManager有多少列
        itemCount = itemCount - itemCount % spanCount;
        if (position >= itemCount) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * 是否是最后一列
     * @param parent
     * @param position
     * @return
     */
    public boolean isLastColunm(RecyclerView parent,int position) {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = gridLayoutManager.getSpanCount();
        if ((position + 1) % spanCount ==0) {
            return true;
        } else {
            return false;
        }
    }


    }
