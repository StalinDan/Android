package com.example.danish.baseproject.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.danish.baseproject.R;
import com.example.danish.baseproject.adapter.QBVideoLIstAdapter;
import com.example.danish.baseproject.bean.QBVideoListBean;
import com.example.danish.baseproject.dao.VideoListDao;
import com.example.danish.baseproject.net.RequestListenerImpl;
import com.example.danish.baseproject.net.base.BaseBean;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QBVideoListActivity extends AppCompatActivity {

    @BindView(R.id.videoLIst_Recycler)
    RecyclerView videoLIstRecycler;

    private List<QBVideoListBean.ContentBean> mList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbvideo_list);
        ButterKnife.bind(this);

        getVideoData(this);

    }

    private void getVideoData(final Context context) {
        new VideoListDao().videoListData(1, 1, 10, new RequestListenerImpl<QBVideoListBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<QBVideoListBean>(){}.getType();
            }

            @Override
            public void onSuccess(QBVideoListBean response) {
                Log.d("QBVideoListActivity","视频数据"+response);
                mList = response.getContent();

                LinearLayoutManager manager = new LinearLayoutManager(context, OrientationHelper.VERTICAL,false);
                videoLIstRecycler.setLayoutManager(manager);

                QBVideoLIstAdapter adapter = new QBVideoLIstAdapter(context,mList);
                videoLIstRecycler.setAdapter(adapter);
            }

            @Override
            public void onFail(String errMsg) {
                Log.d("QBVideoListActivity","请求失败---"+errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                Log.d("QBVideoListActivity","请求失败---"+e.toString());
            }
        });
    }
}
