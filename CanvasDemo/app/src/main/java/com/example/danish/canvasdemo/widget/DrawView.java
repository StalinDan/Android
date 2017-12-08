package com.example.danish.canvasdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by danish on 2017/12/8.
 */

public class DrawView extends View {


    public DrawView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);


        //画点
        canvas.drawPoint(200,200,mPaint);

        canvas.drawPoints(new float[]{
                300,400,
                300,500,
                300,600
        },mPaint);

        //画文本
        canvas.drawText("文本",10,20,mPaint);

        //画圆
        canvas.drawCircle(600,300,50,mPaint);
        //设置画笔的锯齿效果。 true是去除，
        mPaint.setAntiAlias(true);
        canvas.drawCircle(600,500,50,mPaint);

        //画弧线
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(30,30,300,30,mPaint);

        //画笑脸弧线
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF oval = new RectF(150,30,180,40);
        canvas.drawArc(oval,180,180,false,mPaint);
        oval.set(190,30,220,40);
        canvas.drawArc(oval, 180, 180, false, mPaint);//小弧形
        oval.set(160, 30, 210, 60);
        canvas.drawArc(oval, 0, 180, false, mPaint);//小弧形

        //画矩形
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(60,60,80,80,mPaint);

        //画圆角矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setAntiAlias(true);
        RectF oval1 = new RectF(200,60,300,100);
        canvas.drawRoundRect(oval1,20,15,mPaint);

        //画椭圆
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.YELLOW);
        RectF oval2 = new RectF(300,300,500,700);
        canvas.drawOval(oval2,mPaint);



        for (int i=0;i<10;i++) {
            canvas.drawCircle(300,600+i*100,50,mPaint);
        }


    }
}
