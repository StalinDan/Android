package com.example.danish.touchdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    private View view1,view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);

//        view1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG,"view1 onClick");
//            }
//        });
//
//        view1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG,"view1 onTouch");
//                return true;
//            }
//        });
//
//        view2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG,"view2 onClick");
//            }
//        });
//
//        view2.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG,"view2 onTouch");
//                return false;
//            }
//        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "[onTouchEvent] -> ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "[onTouchEvent] -> ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "[onTouchEvent] -> ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "[onTouchEvent] -> ACTION_CANCEL");
                break;
            default:
                break;
        }
        boolean superReturn = super.onTouchEvent(event);
        Log.d(TAG, "[onTouchEvent] return " + superReturn);
        return superReturn;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

       switch (ev.getAction()) {
           case MotionEvent.ACTION_DOWN:
               Log.d(TAG, "[dispatchTouchEvent] -> ACTION_DOWN");
               break;
           case MotionEvent.ACTION_MOVE:
               Log.d(TAG, "[dispatchTouchEvent] -> ACTION_MOVE");
               break;
           case MotionEvent.ACTION_UP:
               Log.d(TAG, "[dispatchTouchEvent] -> ACTION_UP");
               break;
           case MotionEvent.ACTION_CANCEL:
               Log.d(TAG, "[dispatchTouchEvent] -> ACTION_CANCEL");
               break;
       }
       boolean superReturn = super.dispatchTouchEvent(ev);
        Log.d(TAG, "[dispatchTouchEvent] return " + superReturn);
       return superReturn;
    }
}
