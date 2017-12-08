package com.example.danish.canvasdemo.widget;

import android.animation.PointFEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.danish.canvasdemo.R;

/**
 * Created by danish on 2017/12/8.
 */

public class BallView extends View implements {

    public static final int sRADIUS = 20;
    private Point mCurrentPoint;
    private Paint mPaint;
    //动画持续时间 默认5S
    private int mAnimDuration;
    private int mDefaultAnimDuration = 5;



    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.Ball);
        mAnimDuration = typedArray.getInt(R.styleable.Ball_anim_duration,mDefaultAnimDuration);
        typedArray.recycle();
        init();
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCurrentPoint == null) {
            mCurrentPoint = new Point(sRADIUS,sRADIUS);
            drawCircle(canvas);
            startAnimation();
        } else  {
            drawCircle(canvas);
        }
    }

    //绘制圆球
    private void drawCircle(Canvas canvas) {
        float x = mCurrentPoint.x;
        float y = mCurrentPoint.y;
        canvas.drawCircle(x,y,sRADIUS,mPaint);
    }

    private void startAnimation(){
        Point startPoint = new Point(getWidth() /2,sRADIUS);
        Point endPoint = new Point(getWidth()/2,getHeight()-sRADIUS);
        Log.i("TEST", "startPoint:" + startPoint.x + "-" + startPoint.y);
        Log.i("TEST", "endPoint:" + endPoint.x + "-" + endPoint.y);
        final ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point)animation.getAnimatedValue();
                invalidate();
            }
        });
    }


    public class PointEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            //fraction 与时间有关的系数，该值由差值器计算得出，由ValueAnimator调用 animateValue
            Point startPoint = (Point)startValue;
            Point endPoint = (Point)endValue;
            float x = startPoint.x + fraction*(endPoint.x-startPoint.x);
            float y = startPoint.y + fraction*(endPoint.y - startPoint.y);
            return new Point((int) x,(int) y);
        }
    }

}
