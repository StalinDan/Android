package com.danish.attendance.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;

import com.danish.attendance.AttendanceApplication;
import com.danish.attendance.R;
import com.danish.attendance.utils.FaceDB;
import com.danish.attendance.utils.MyLogger;
import com.guo.android_extend.image.ImageConverter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetecterActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final int REQUEST_CODE_IMAGE_CAMERA = 1;
    private static final int REQUEST_CODE_OP = 3;

    private final String TAG = this.getClass().toString();
    private final static int MSG_CODE = 0x1000;
    private final static int MSG_EVENT_REG = 0x1001;
    private final static int MSG_EVENT_NO_FACE = 0x1002;
    private final static int MSG_EVENT_NO_FEATURE = 0x1003;
    private final static int MSG_EVENT_FD_ERROR = 0x1004;
    private final static int MSG_EVENT_FR_ERROR = 0x1005;
    @BindView(R.id.photoBtn)
    Button photoBtn;
    @BindView(R.id.confirmBtn)
    Button confirmBtn;
    @BindView(R.id.recognizeText)
    TextView recognizeText;
    @BindView(R.id.exitBtn)
    Button exitBtn;

    private UIHandler mUIHandler;
    private String mFilePath;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Bitmap mBitmap;
    private Rect src = new Rect();
    private Rect dst = new Rect();
    private Thread view;

    private AFR_FSDKFace mAFR_FSDKFace;
    Bitmap faceBitmap;
    private Uri mPath;

    private Bitmap photoBitMap; //拍照的照片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detecter);
        ButterKnife.bind(this);

        //initial data.
        if (!getIntentData(getIntent().getExtras())) {
            Log.e(TAG, "getIntentData fail!");
            this.finish();
        }

        //拍摄或确定按钮
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                ((AttendanceApplication) (DetecterActivity.this.getApplicationContext())).setCaptureImage(uri);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_CODE_IMAGE_CAMERA);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                RegisterActivity.mAFR_FSDKFace = mAFR_FSDKFace;
//                RegisterActivity.faceBitmap = photoBitMap;

                DriverLIstActivity.mAFR_FSDKFace = mAFR_FSDKFace;
                DriverLIstActivity.faceBitmap = photoBitMap;

                setResult(10086, intent);
                finish();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateSurfaceView();
    }

    /**
     * 更新surfaceView
     */
    private void updateSurfaceView() {
        mUIHandler = new UIHandler();
        mBitmap = AttendanceApplication.decodeImage(mFilePath);

        photoBitMap = mBitmap;

        src.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
        mSurfaceView.getHolder().addCallback(this);
        view = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mSurfaceHolder == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];
                ImageConverter convert = new ImageConverter();
                convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);
                if (convert.convert(mBitmap, data)) {
                    Log.d(TAG, "convert ok!");
                }
                convert.destroy();

                AFD_FSDKEngine engine = new AFD_FSDKEngine();
                AFD_FSDKVersion version = new AFD_FSDKVersion();
                List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
                AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
                Log.d(TAG, "AFD_FSDK_InitialFaceEngine = " + err.getCode());
                if (err.getCode() != AFD_FSDKError.MOK) {
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_FD_ERROR;
                    reg.arg2 = err.getCode();
                    mUIHandler.sendMessage(reg);
                }
                err = engine.AFD_FSDK_GetVersion(version);
                Log.d(TAG, "AFD_FSDK_GetVersion =" + version.toString() + ", " + err.getCode());
                err = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);
                Log.d(TAG, "AFD_FSDK_StillImageFaceDetection =" + err.getCode() + "<" + result.size());
                while (mSurfaceHolder != null) {
                    Canvas canvas = mSurfaceHolder.lockCanvas();
                    if (canvas != null) {
                        Paint mPaint = new Paint();
                        boolean fit_horizontal = canvas.getWidth() / (float) src.width() < canvas.getHeight() / (float) src.height() ? true : false;
                        float scale = 1.0f;
                        if (fit_horizontal) {
                            scale = canvas.getWidth() / (float) src.width();
                            dst.left = 0;
                            dst.top = (canvas.getHeight() - (int) (src.height() * scale)) / 2;
                            dst.right = dst.left + canvas.getWidth();
                            dst.bottom = dst.top + (int) (src.height() * scale);
                        } else {
                            scale = canvas.getHeight() / (float) src.height();
                            dst.left = (canvas.getWidth() - (int) (src.width() * scale)) / 2;
                            dst.top = 0;
                            dst.right = dst.left + (int) (src.width() * scale);
                            dst.bottom = dst.top + canvas.getHeight();
                        }
                        canvas.drawBitmap(mBitmap, src, dst, mPaint);
                        canvas.save();
                        canvas.scale((float) dst.width() / (float) src.width(), (float) dst.height() / (float) src.height());
                        canvas.translate(dst.left / scale, dst.top / scale);
                        for (AFD_FSDKFace face : result) {
                            mPaint.setColor(Color.RED);
                            mPaint.setStrokeWidth(10.0f);
                            mPaint.setStyle(Paint.Style.STROKE);
                            canvas.drawRect(face.getRect(), mPaint);
                        }
                        canvas.restore();
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                        break;
                    }
                }

                if (!result.isEmpty()) {
                    AFR_FSDKVersion version1 = new AFR_FSDKVersion();
                    AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
                    AFR_FSDKFace result1 = new AFR_FSDKFace();
                    AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
                    Log.d("com.arcsoft", "AFR_FSDK_InitialEngine = " + error1.getCode());
                    if (error1.getCode() != AFD_FSDKError.MOK) {
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_FR_ERROR;
                        reg.arg2 = error1.getCode();
                        mUIHandler.sendMessage(reg);
                    }
                    error1 = engine1.AFR_FSDK_GetVersion(version1);
                    Log.d("com.arcsoft", "FR=" + version.toString() + "," + error1.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
                    error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
                    Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode());
                    if (error1.getCode() == error1.MOK) {
                        mAFR_FSDKFace = result1.clone();
                        int width = result.get(0).getRect().width();
                        int height = result.get(0).getRect().height();
                        Bitmap face_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        Canvas face_canvas = new Canvas(face_bitmap);
                        face_canvas.drawBitmap(mBitmap, result.get(0).getRect(), new Rect(0, 0, width, height), null);
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_REG;
                        reg.obj = face_bitmap;
                        mUIHandler.sendMessage(reg);
                    } else {
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_NO_FEATURE;
                        mUIHandler.sendMessage(reg);
                    }
                    error1 = engine1.AFR_FSDK_UninitialEngine();
                    Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
                } else {
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_NO_FACE;
                    mUIHandler.sendMessage(reg);
                }
                err = engine.AFD_FSDK_UninitialFaceEngine();
                Log.d(TAG, "AFD_FSDK_UninitialFaceEngine =" + err.getCode());
            }
        });
        view.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_IMAGE_CAMERA) {
            mPath = ((AttendanceApplication) (DetecterActivity.this.getApplicationContext())).getCaptureImage();
            String file = getPath(mPath);
            MyLogger.i("拍摄的照片路径---》"+mPath);
            Bitmap bmp = AttendanceApplication.decodeImage(file);
//            startRegister(bmp, file);

            MyLogger.i("mPath====" + mPath);

            Bundle bundle = getIntent().getExtras();
            bundle.putString("imagePath", file);

            MyLogger.i( "bundle====" + bundle);
            this.faceBitmap = bmp;
            if (!getIntentData(bundle)) {
                MyLogger.i("getIntentData fail!");
                this.finish();
            }
            updateSurfaceView();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
        try {
            view.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE) {
                if (msg.arg1 == MSG_EVENT_REG) {
//                    LayoutInflater inflater = LayoutInflater.from(DetecterActivity.this);
//                    View layout = inflater.inflate(R.layout.dialog_register, null);
//                    mEditText = (EditText) layout.findViewById(R.id.editview);
//                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
//                    mExtImageView = (ExtImageView) layout.findViewById(R.id.extimageview);
//                    mExtImageView.setImageBitmap((Bitmap) msg.obj);
//                    final Bitmap face = (Bitmap) msg.obj;
//                    new AlertDialog.Builder(DetecterActivity.this)
//                            .setTitle("请输入注册名字")
//                            .setIcon(android.R.drawable.ic_dialog_info)
//                            .setView(layout)
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    ((DriverLoginApplication)DetecterActivity.this.getApplicationContext()).mFaceDB.addFace(mEditText.getText().toString(), mAFR_FSDKFace);
//                                    mRegisterViewAdapter.notifyDataSetChanged();
//                                    dialog.dismiss();
//                                }
//                            })
//                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            })
//                            .show();

                    faceBitmap = (Bitmap) msg.obj;
                    confirmBtn.setBackgroundResource(R.drawable.btn_corner_blue);
                    confirmBtn.setEnabled(true);
                    recognizeText.setTextColor(Color.parseColor("#518cb8"));
                    recognizeText.setText("已识别到人脸");

                } else if (msg.arg1 == MSG_EVENT_NO_FEATURE) {
                    Toast.makeText(DetecterActivity.this, "人脸特征无法检测，请换一张图片", Toast.LENGTH_SHORT).show();
                    noFaceInfo();
                } else if (msg.arg1 == MSG_EVENT_NO_FACE) {
                    Toast.makeText(DetecterActivity.this, "没有检测到人脸，请换一张图片", Toast.LENGTH_SHORT).show();
                    noFaceInfo();
                } else if (msg.arg1 == MSG_EVENT_FD_ERROR) {
                    Toast.makeText(DetecterActivity.this, "FD初始化失败，错误码：" + msg.arg2, Toast.LENGTH_SHORT).show();
                    noFaceInfo();
                } else if (msg.arg1 == MSG_EVENT_FR_ERROR) {
                    Toast.makeText(DetecterActivity.this, "FR初始化失败，错误码：" + msg.arg2, Toast.LENGTH_SHORT).show();
                    noFaceInfo();
                }
            }
        }
    }

    private void noFaceInfo() {
        confirmBtn.setBackgroundResource(R.drawable.btn_corner_gray);
        confirmBtn.setEnabled(false);
        recognizeText.setText("未识别到人脸");
        recognizeText.setTextColor(Color.parseColor("#fe5354"));
    }

    /**
     * @param bundle
     * @note bundle data :
     * String imagePath
     */
    private boolean getIntentData(Bundle bundle) {
        try {
            mFilePath = bundle.getString("imagePath");
            if (mFilePath == null || mFilePath.isEmpty()) {
                return false;
            }
            MyLogger.i( "getIntentData:" + mFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
