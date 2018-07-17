package com.danish.attendance.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.danish.attendance.AttendanceApplication;
import com.danish.attendance.R;
import com.danish.attendance.adapter.DriverListAdapter;
import com.danish.attendance.bean.DriverListBean;
import com.danish.attendance.bean.MessageBean;
import com.danish.attendance.dao.DeleteFaceDao;
import com.danish.attendance.dao.DriverListDao;
import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.FileUtil;
import com.danish.attendance.net.RequestListener;
import com.danish.attendance.net.RequestListenerImpl;
import com.danish.attendance.popWindow.RegisterFacePopWindow;
import com.danish.attendance.utils.MyLogger;
import com.danish.attendance.utils.Utils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriverLIstActivity extends BaseActivity {

    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_OP = 3;

    public static AFR_FSDKFace mAFR_FSDKFace;
    public static Bitmap faceBitmap;

    @BindView(R.id.numEditText)
    EditText numEditText;
    @BindView(R.id.queryBtn)
    Button queryBtn;
    @BindView(R.id.driverListRecyclerView)
    RecyclerView driverListRecyclerView;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.ptr)
    PtrClassicFrameLayout ptr;
    @BindView(R.id.driverLL)
    LinearLayout driverLL;

    private int updateIndex; //修改人脸信息的索引
//    private Bitmap faceBitmap; //人脸图片

    private RegisterFacePopWindow facePopWindow;
    private RecyclerAdapterWithHF ptrAdapter;
    private DriverListAdapter driverListAdapter;
    private int pageNo = 1;

    DriverListDao driverListDao;
    FileUtil fileUtil;
    AttendanceApplication app;
    DeleteFaceDao deleteFaceDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);
        ButterKnife.bind(this);


        driverListDao = new DriverListDao();
        fileUtil = new FileUtil();
        deleteFaceDao = new DeleteFaceDao();

        driverListAdapter = new DriverListAdapter(this, this);

        app = (AttendanceApplication) this.getApplicationContext();
        if (app == null) {
            MyLogger.i("AttendanceApplication为空======");
        } else {
            MyLogger.i("AttendanceApplication不为空======");
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        driverListRecyclerView.setLayoutManager(layoutManager);
        ptrAdapter = new RecyclerAdapterWithHF(driverListAdapter);
        driverListRecyclerView.setAdapter(ptrAdapter);

        ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                getDriverList();
            }
        });

        ptr.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                pageNo++;
                getDriverList();
            }
        });

        getDriverList();


        facePopWindow = new RegisterFacePopWindow(LayoutInflater.from(this).
                inflate(R.layout.popup_register_face, null, false), 1000, 600, false, this);
        facePopWindow.setFacePopUpdateDriverInfo(new RegisterFacePopWindow.FacePopUpdateDriverInfo() {
            @Override
            public void updateFaceInfo(DriverListBean.ResultBean.ResultListBean listBean) {

                MyLogger.i("保存人脸信息");
                inputFaceInfo(listBean);

            }
        });

        driverListAdapter.setDriverListAdapterSelectItem(new DriverListAdapter.DriverListAdapterSelectItem() {
            @Override
            public void selectIndex(int index, List<DriverListBean.ResultBean.ResultListBean> listBeans) {
                updateIndex = index;
                facePopWindow.showPopupWindow();
                facePopWindow.setDriverInfoData(listBeans.get(index));
                facePopWindow.setFaceImg(null);
                ColorDrawable drawable = new ColorDrawable(0x00000000);
                facePopWindow.setBackgroundDrawable(drawable);
                facePopWindow.backgroundAlpha(0.5f);
            }

            @Override
            public void deleteFaceAtIndex(final int index, final List<DriverListBean.ResultBean.ResultListBean> listBeans) {
                updateIndex = index;


                AlertDialog.Builder dialog = new AlertDialog.Builder(DriverLIstActivity.this);
                dialog.setTitle("删除人脸信息");
                dialog.setMessage("确定要删除该司机的人脸信息吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDriverFaceInfo(listBeans.get(index));
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }

        });

        final View view = getWindow().getDecorView();

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideSoftInput(view);
                pageNo = 1;
                getDriverList();
            }
        });


        Utils.hideSoftInput(view);

        numEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                numEditText.setFocusable(true);
                Utils.showSoftInput(view);
            }
        });


        driverLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                numEditText.setFocusable(false);
                Utils.hideSoftInput(view);

            }
        });

        driverLL.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom - oldBottom < -1) {
                    //软键盘弹上去了
                    exitBtn.setVisibility(View.INVISIBLE);
                } else if (bottom - oldBottom > 1) {
                    //软键盘弹下去了
                    exitBtn.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * 查询司机列表
     */
    private void getDriverList() {

        driverListDao.getDriverListData(pageNo, 10, numEditText.getText().toString(), new RequestListenerImpl<DriverListBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<DriverListBean>() {
                }.getType();
            }

            @Override
            public void onSuccess(DriverListBean response) {
                if (pageNo == 1) {
                    driverListAdapter.removeData();
                    ptr.refreshComplete();
                } else {
                    ptr.loadMoreComplete(true);
                }

                if (response.getResult().getResultList().size() == 0) {
                    showToast("暂无数据");
                } else {
                    driverListAdapter.addData(response);
                    if (response.getResult().getTotalPages() <= pageNo) {
                        ptr.setLoadMoreEnable(false);
                    } else {
                        ptr.setLoadMoreEnable(true);
                    }
                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
                showNetErrToast();
            }
        });
    }

    /**
     * 录入司机人脸信息
     *
     * @param listBean
     */
    public void inputFaceInfo(final DriverListBean.ResultBean.ResultListBean listBean) {

        MyLogger.i("app.filePathStr--->" + app.filePathStr);
        final String nameStr = listBean.getWorkNo() + "_" + listBean.getName();

        if (mAFR_FSDKFace == null) {
            showToast("请录入人脸信息");
            return;
        }
        facePopWindow.dismissPopupWindow();
        app.mFaceDB.addFace(nameStr, mAFR_FSDKFace);

        fileUtil.uploadFileData(AsyncRequestRunner.HOST + "/face-feature/input", app.filePathStr + "/" + nameStr + ".data", nameStr + ".data", new FileUtil.UploadFileCallBack() {
            @Override
            public void uploadSuccess(final MessageBean messageBean) {
                if (messageBean.getType().equals("1")) {
                    MyLogger.i("注册成功--->" + nameStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast("注册成功");
                            driverListAdapter.updateFaceData(updateIndex, listBean);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showToast(messageBean.getMsg());
                        }
                    });
                }
            }

            @Override
            public void uploadFail(String errorMsg) {
                MyLogger.i("注册失败-->" + errorMsg);
                app.mFaceDB.delete(nameStr);
            }
        });
    }

    private void deleteDriverFaceInfo(DriverListBean.ResultBean.ResultListBean listBean) {
        String[] workNos = new String[1];
        workNos[0] = listBean.getWorkNo().toString();
        MyLogger.i("删除人脸信息--->" + workNos[0]);

        String nameStr = listBean.getWorkNo() + "_" + listBean.getName();
        app.mFaceDB.delete(nameStr);
        listBean.setFaceFeatureExist(false);
        driverListAdapter.updateFaceData(updateIndex, listBean);

        deleteFaceDao.deleteFaceData(workNos, new RequestListener() {
            @Override
            public void onFinish(String response) {
                Log.i("DeleteFaceInfoActivity", "删除成功");

            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("删除失败");
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("网络错误，请稍后再试-->" + e.getLocalizedMessage());
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLogger.i("===========onActivityResult");
        if (requestCode == REQUEST_CODE_IMAGE_CAMERA && resultCode == RESULT_OK) {
            Uri mPath = ((AttendanceApplication) (DriverLIstActivity.this.getApplicationContext())).getCaptureImage();
            MyLogger.i("拍摄的照片路径---》" + mPath);
            String file = getPath(mPath);
            Bitmap bmp = AttendanceApplication.decodeImage(file);
            startRegister(bmp, file);
        } else if (requestCode == REQUEST_CODE_OP) {
            MyLogger.i("RESULT =" + resultCode);
            if (data == null) {
                return;
            }
//            Bundle bundle = data.getExtras();
//            String path = bundle.getString("imagePath");
//            Log.i("RegisterActivity", "path="+path);

        }
        if (resultCode == 10086) {

            MyLogger.i("faceBitmap-->" + faceBitmap + "\n" + "mAFR_FSDKFace-->" + mAFR_FSDKFace);
//            headerImg.setImageBitmap(faceBitmap);
            facePopWindow.setFaceImg(faceBitmap);


            Intent intent = getIntent();
            MyLogger.i("10086=====" + intent);
            if (intent != null) {
//                Bitmap faceBitmap = intent.getParcelableExtra("faceBitmap");
//                headerImg.setImageBitmap(faceBitmap);
//                AFR_FSDKFace mAFR_FSDKFace = intent.getParcelableExtra("mAFR_FSDKFace");
//                this.mAFR_FSDKFace = mAFR_FSDKFace;

//                Log.i("RegisterActivity","faceBitmap-->"+faceBitmap +"\n"+"mAFR_FSDKFace-->"+mAFR_FSDKFace);
            }
        }
    }

    /**
     * @param mBitmap
     */
    private void startRegister(Bitmap mBitmap, String file) {
        Intent it = new Intent(DriverLIstActivity.this, DetecterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("imagePath", file);
        it.putExtras(bundle);
        startActivityForResult(it, REQUEST_CODE_OP);
    }

    /**
     * @param uri
     * @return
     */
    private String getPath(Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this, uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    return null;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    return null;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(this, contentUri, selection, selectionArgs);
            }
        }
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = this.getContentResolver().query(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
