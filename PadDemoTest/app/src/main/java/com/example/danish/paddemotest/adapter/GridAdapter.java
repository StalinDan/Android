package com.example.danish.paddemotest.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danish.paddemotest.R;
import com.example.danish.paddemotest.bean.GridItemBean;
import com.example.danish.paddemotest.widget.PopWindow;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by danish on 2017/12/12.
 */

public class GridAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GridItemBean> mList;
    private int screenW;
    private int screenH;
    private PopupWindow popupWindow;
    PopWindow popWindow;
    public GridAdapter(Context context,List list) {
        mContext = context;
        mList = list;

        popWindow = new PopWindow(mContext,LayoutInflater.from(mContext).inflate(R.layout.item_popup,null,false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,false);

        screenSize();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item,null);



        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder)holder;
        final GridItemBean itemBean = mList.get(position);
        myViewHolder.name.setText(itemBean.getName());
        myViewHolder.trainNo.setText(itemBean.getTrainNo());
        myViewHolder.bed.setText(itemBean.getBed());
//        if (itemBean.getStatus() == 1) {
//            myViewHolder.itemView.setBackgroundColor(Color.RED);
//        } else if (itemBean.getStatus() == 2) {
//            myViewHolder.itemView.setBackgroundColor(Color.YELLOW);
//        } else if (itemBean.getStatus() == 3) {
//            myViewHolder.itemView.setBackgroundColor(Color.GREEN);
//        } else {
//            myViewHolder.itemView.setBackgroundColor(Color.BLUE);
//        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("GridAdapter","itemView====");
//                initPopWindow(v);
                ;
                popWindow.showPopWindow(v);

                popWindow.setOnBtnClickListner(new PopWindow.OnBtnClickListner() {
                    @Override
                    public void leftBtnPress() {
                        Log.d("GridAdapter","leftBtnPress");
                        popWindow.dismiss();
                    }

                    @Override
                    public void rightBtnPress() {
                        Log.d("GridAdapter","rightBtnPress");
                        Toast.makeText(mContext,itemBean.getName()+"\n"+itemBean.getTrainNo()+"\n"+itemBean.getBed(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView bed,trainNo,name;
        public MyViewHolder(View itemView) {
            super(itemView);
            bed = itemView.findViewById(R.id.bed);
            trainNo = itemView.findViewById(R.id.trainNo);
            name = itemView.findViewById(R.id.name);
        }
    }

    private void initPopWindow(View v){
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup,null,false);
        Button xiBtn = view.findViewById(R.id.btn_xixi);
        Button haBtn = view.findViewById(R.id.btn_haha);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,true);
        //这些为了点击非PopupWindow区域，PopupWindow会消失的
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setAnimationStyle(R.style.ContexPopupAnim);

        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
//        popupWindow.showAsDropDown(v,100,0);
//        popupWindow.showAtLocation(v.getRootView(), Gravity.CENTER,0,0);


//        animation(view,v);

        popupWindow.showAtLocation(v,Gravity.CENTER,0,0);



        //设置popupWindow里的按钮的事件
        xiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext,"你点击了嘻嘻",Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        haBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"你点击了哈哈",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void animation(final View view, View itemView) {

        float itemW = itemView.getWidth();
        float itemH = itemView.getHeight();
        int[] location = new int[2];
        itemView.getLocationOnScreen(location);
        int originX = location[0];
        int originY = location[1];
        float centerX = originX + itemW/2;
        float centerY = originY + itemH/2;

//        float curTranslationX = (itemView.getRight()-itemView.getLeft())/2;
//
//        int[] location = new int[2];
//        itemView.getLocationOnScreen(location);
//        int x = location[0];
//        int y = location[1];
//        int w = itemView.getWidth();
//        Log.d("GridAdapter","x---->"+x);
//        Log.d("GridAdapter","y---->"+y);
//        Log.d("GridAdapter","w---->"+w);
//
//        Log.d("GridAdapter","curTranslationX-->getRight-->"+itemView.getRight());
//        Log.d("GridAdapter","curTranslationX-->getLeft-->"+itemView.getLeft());
//
//        float curTranslationY = (itemView.getBottom()-itemView.getTop())/2;
//        Log.d("GridAdapter","curTranslationY-->getTop-->"+itemView.getTop());
//        Log.d("GridAdapter","curTranslationY-->getBottom-->"+itemView.getBottom());
//
//
//        float centerX = curTranslationX + itemView.getLeft();
//        Log.d("GridAdapter","centerX-->"+centerX);
//
//        float centerY = curTranslationY + itemView.getLeft();
//        Log.d("GridAdapter","centerY-->"+centerY);
//
//        Log.d("GridAdapter","screenW-->"+screenW);
//        Log.d("GridAdapter","screenH-->"+screenH);

        view.setPivotX(centerX);
        view.setPivotY(centerY);

        ObjectAnimator translateX = ObjectAnimator.ofFloat(view,"translationX",centerX,0);
        ObjectAnimator translateY = ObjectAnimator.ofFloat(view,"translationY",centerY,0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,"scaleX",0f,1f);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,"scaleY",0f,1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleX).with(scaleY);//.with(translateY).with(translateX);



        animatorSet.setDuration(500);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("GridAdapter","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Log.d("GridAdapter","onAnimationEnd");
//                popupWindow.showAtLocation(view,Gravity.CENTER,0,0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void screenSize(){
        DisplayMetrics dm =new DisplayMetrics();

        dm = mContext.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;// 屏幕宽（像素，如：3200px）

        screenW = screenWidth;
        int screenHeight = dm.heightPixels;// 屏幕高（像素，如：1280px）
        screenH = screenHeight;
        Log.i("GridAdapter","screenW-->"+screenW);
        Log.i("GridAdapter","screenH-->"+screenH);
        Log.i("GridAdapter","屏幕原始尺寸宽度-->"+getDpi(mContext));
        Log.i("GridAdapter","虚拟按键高度-->"+getBottomStatusHeight(mContext));

    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context){
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.widthPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取 虚拟按键的高度
     * @param context
     * @return
     */
    public static  int getBottomStatusHeight(Context context){
        int totalHeight = getDpi(context);

        int contentHeight = getScreenHeight(context);

        return totalHeight  - contentHeight;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
