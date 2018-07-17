package com.danish.attendance.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.danish.attendance.AttendanceApplication;
import com.danish.attendance.R;
import com.danish.attendance.adapter.FaceInfoAdapter;
import com.danish.attendance.bean.FaceInfoBean;
import com.danish.attendance.dao.DeleteFaceDao;
import com.danish.attendance.net.RequestListener;
import com.danish.attendance.utils.MyLogger;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteFaceInfoActivity extends AppCompatActivity {

    @BindView(R.id.faceInfoRecyclerView)
    RecyclerView faceInfoRecyclerView;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.deleteBtn)
    Button deleteBtn;

    List<FaceInfoBean> mList = new ArrayList<>();
    FaceInfoAdapter faceInfoAdapter;

    List<FaceInfoBean> deleteList = new ArrayList();
    String[] workNos ;
    List<String> workList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_face_info);
        ButterKnife.bind(this);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        initData();

        faceInfoAdapter = new FaceInfoAdapter(DeleteFaceInfoActivity.this,mList);
        faceInfoRecyclerView.setAdapter(faceInfoAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(DeleteFaceInfoActivity.this, OrientationHelper.VERTICAL,false);
        faceInfoRecyclerView.setLayoutManager(manager);

        faceInfoAdapter.setFaceInfoCallBack(new FaceInfoAdapter.FaceInfoCallBack() {

            @Override
            public void faceInfoData(List<FaceInfoBean> list) {
                deleteList = list;
//                for (FaceInfoBean faceInfoBean:deleteList) {
//                    Log.i("DeleteFaceInfoActivity","deleteList--->"+faceInfoBean.isSelect()+"\n");
//                }


            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceApplication application = (AttendanceApplication) DeleteFaceInfoActivity.this.getApplicationContext();

                for (int i = 0; i<deleteList.size();i++) {

                    FaceInfoBean bean = deleteList.get(i);
                    if (bean.isSelect()) {
                        String keyStr = bean.getWorkNo()+"_"+bean.getName() + "_" + bean.getLdName();
                        MyLogger.i("bean.isSelect-->"+bean.getWorkNo() + "\n" +"keyStr-->"+keyStr);

                        application.mFaceDB.delete(keyStr);
                        workList.add(bean.getWorkNo());
                        deleteList.remove(i);
                        i--;

                    }
                }
                faceInfoAdapter.notifyDataSetChanged();

                if (workList.size() > 0) {
                    deleteFaceData(workList);
                }

            }
        });

    }

    private void initData () {
        AttendanceApplication application = (AttendanceApplication) DeleteFaceInfoActivity.this.getApplicationContext();
        mList = application.mFaceDB.getFaces();
        MyLogger.i("人脸信息---》"+mList.size());
    }

    private void deleteFaceData (List list) {
        workNos = new String[list.size()];
        for (int i = 0;i < list.size();i++) {
            workNos[i] = list.get(i).toString();
            MyLogger.i("workNos-->" + workNos[i]);
        }

        DeleteFaceDao deleteFaceDao = new DeleteFaceDao();
        deleteFaceDao.deleteFaceData(workNos, new RequestListener() {
            @Override
            public void onFinish(String response) {
                Log.i("DeleteFaceInfoActivity","删除成功");
                Logger.d("删除人脸信息"+ workNos);
            }

            @Override
            public void onFail(String type, String errMsg) {
                Log.i("DeleteFaceInfoActivity","删除失败");
            }

            @Override
            public void onNetError(Throwable e) {
                Log.i("DeleteFaceInfoActivity","网络错误，请稍后再试");
            }
        });
    }
}
