package com.danish.threaddemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //1. 这种方式仅仅是起动了一个新的线程，没有任务的概念，不能做状态的管理。
        // start之后，run当中的代码就一定会执行到底，无法中途取消。
        // Runnable作为匿名内部类还持有了外部类的引用，在线程退出之前，
        // 该引用会一直存在，阻碍外部类对象被GC回收，在一段时间内造成内存泄漏。
        //没有线程切换的接口，要传递处理结果到UI线程的话，需要写额外的线程切换代码。

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

    }


    //2. 和使用Thread()不同的是，多了几处API回调来严格规范工作线程与UI线程之间的交互。
    // 我们大部分的业务场景几乎都符合这种规范，比如去磁盘读取图片，缩放处理需要在工作线程执行，
    // 最后绘制到ImageView控件需要切换到UI线程。
    public class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    }

    //3. HandlerThread
    //
}
