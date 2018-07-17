package com.danish.attendance.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKMatching;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.arcsoft.facetracking.AFT_FSDKEngine;
import com.arcsoft.facetracking.AFT_FSDKError;
import com.arcsoft.facetracking.AFT_FSDKFace;
import com.arcsoft.facetracking.AFT_FSDKVersion;
import com.danish.attendance.AttendanceApplication;
import com.danish.attendance.R;
import com.danish.attendance.bean.GetAttendanceStatusBean;
import com.danish.attendance.dao.GetAttendanceStatusDao;
import com.danish.attendance.net.RequestListenerImpl;
import com.danish.attendance.utils.ButtonCountDownTime;
import com.danish.attendance.utils.FaceDB;
import com.danish.attendance.utils.MyLogger;
import com.google.gson.reflect.TypeToken;
import com.guo.android_extend.java.AbsLoop;
import com.guo.android_extend.java.ExtByteArrayOutputStream;
import com.guo.android_extend.tools.CameraHelper;
import com.guo.android_extend.widget.CameraFrameData;
import com.guo.android_extend.widget.CameraGLSurfaceView;
import com.guo.android_extend.widget.CameraSurfaceView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaceRecognitionActivity extends BaseActivity implements View.OnTouchListener, Camera.AutoFocusCallback, CameraSurfaceView.OnCameraListener {

    private final String TAG = this.getClass().getSimpleName();

//    @BindView(R.id.headerImg)
//    ImageView headerImg;

    @BindView(R.id.workNo)
    TextView workNo;
    @BindView(R.id.retryRecognity)
    Button retryRecognity;
    @BindView(R.id.nextStep)
    Button nextStep;
    @BindView(R.id.glsurfaceView)
    CameraGLSurfaceView glsurfaceView;
    @BindView(R.id.confidenceText)
    TextView confidenceText;
    @BindView(R.id.surfaceView)
    CameraSurfaceView surfaceView;
    @BindView(R.id.nameText)
    TextView nameText;
    @BindView(R.id.cameraTitle)
    TextView cameraTitle;
    @BindView(R.id.exitBtn)
    Button exitBtn;
    @BindView(R.id.previewImg)
    ImageView previewImg;
//    @BindView(R.id.trainNo)
//    TextView trainNo;


    private int mWidth, mHeight, mFormat;


    AFT_FSDKVersion version = new AFT_FSDKVersion();
    AFT_FSDKEngine engine = new AFT_FSDKEngine();
    List<AFT_FSDKFace> result = new ArrayList<>();

    int mCameraID;
    int mCameraRotate;
    boolean mCameraMirror;
    byte[] mImageNV21 = null;
    FRAbsLoop mFRAbsLoop = null;
    AFT_FSDKFace mAFT_FSDKFace = null;
    Handler mHandler;
    private Camera mCamera;

    private Bitmap faceBitmap;
    private Bitmap originBitmap;

    private Timer faceTimer = new Timer();
    private int faceTime = 0;
    private boolean isRecognized; //是否识别到
    private boolean isRepeateClick; //是否点击了重新识别 主要为了fix 相似人脸的bug
    private String workNoStr;

    AttendanceApplication app;
    String plateNo; // 办理出勤所需 车牌号
    String identifyCode;  //办理退勤所需 设备编号

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mCameraID = getIntent().getIntExtra("Camera", 0) == 0 ? Camera.CameraInfo.CAMERA_FACING_BACK : Camera.CameraInfo.CAMERA_FACING_FRONT;
//        mCameraRotate = getIntent().getIntExtra("Camera", 0) == 0 ? 90 : 270;
//        mCameraMirror = getIntent().getIntExtra("Camera", 0) == 0 ? false : true;

        mCameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
        mCameraRotate = 0;
        mCameraMirror = true;
        mWidth = 1280;
        mHeight = 960;
        mFormat = ImageFormat.NV21;
        mHandler = new Handler();

        setContentView(R.layout.activity_face_recognition);
        ButterKnife.bind(this);

        app = (AttendanceApplication) FaceRecognitionActivity.this.getApplicationContext();


        glsurfaceView.setOnTouchListener(this);
        surfaceView.setOnCameraListener(this);
        surfaceView.setupGLSurafceView(glsurfaceView, true, mCameraMirror, mCameraRotate);
        surfaceView.debug_print_fps(true, false);

        AFT_FSDKError err = engine.AFT_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.ft_key, AFT_FSDKEngine.AFT_OPF_0_HIGHER_EXT, 16, 5);
        Log.d(TAG, "AFT_FSDK_InitialFaceEngine =" + err.getCode());
        err = engine.AFT_FSDK_GetVersion(version);
        Log.d(TAG, "AFT_FSDK_GetVersion:" + version.toString() + "," + err.getCode());

        mFRAbsLoop = new FRAbsLoop();
        mFRAbsLoop.start();


        //下一步
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (originBitmap == null) {
                    Toast.makeText(FaceRecognitionActivity.this, "未识别人脸", Toast.LENGTH_SHORT).show();
                    return;
                } else if (workNo.getText().length() == 3 || nameText.getText().length() == 3) {
                    Toast.makeText(FaceRecognitionActivity.this, "工号或姓名为空", Toast.LENGTH_SHORT).show();
                    return;
                }
//                InputTrainnoActivity.nameStr = nameText.getText().toString();
//                InputTrainnoActivity.workNoStr = workNo.getText().toString();
//                InputTrainnoActivity.ldNameStr = ldNameText.getText().toString();
//
//
//                app.setFaceBitmap(originBitmap);


//                Intent intent = new Intent(FaceRecognitionActivity.this, InputTrainnoActivity.class);

                if (nextStep.getText().toString().equals("下一步")) {

                    MyLogger.i("下一步");
                    Intent intent = new Intent(FaceRecognitionActivity.this, AttendanceActivity.class);
                    intent.putExtra("plateNo", plateNo);
                    intent.putExtra("workNo",workNoStr);
                    startActivity(intent);
                } else if (nextStep.getText().toString().equals("办理退勤")) {

                    MyLogger.i("办理退勤");
                    Intent intent = new Intent(FaceRecognitionActivity.this, RetireActivity.class);
                    intent.putExtra("identifyCode", identifyCode);
                    startActivity(intent);
                } else {

                }
            }
        });

        //重新识别
        retryRecognity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confidenceText.setText("未识别");
                isRecognized = false;
                isRepeateClick = true;
                faceTime = 0;
                nameText.setText("姓名：");
                workNo.setText("工号：");
//                trainNo.setText("车牌：");
                faceBitmap = null;
//                headerImg.setImageResource(R.mipmap.header_img);
                originBitmap = null;
                previewImg.setImageBitmap(null);
                previewImg.setVisibility(View.INVISIBLE);

                nextStep.setEnabled(false);
                nextStep.setBackgroundResource(R.drawable.btn_corner_gray);

//                mFRAbsLoop = new FRAbsLoop();
//                mFRAbsLoop.start();

                MyLogger.i("重新识别---》isRecognized--》" + isRecognized);
            }
        });

        //退出
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        faceTimer.schedule(faceTimerTask, 0, 1000);


//        countDownTimer = new CountDownTimer(30*1000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                MyLogger.i("millisUntilFinished--->"+millisUntilFinished/1000);
//                exitBtn.setText(millisUntilFinished/1000 + "s后退出");
//
//            }
//
//            @Override
//            public void onFinish() {
//                finish();
//            }
//        }.start();
    }

    Runnable hide = new Runnable() {
        @Override
        public void run() {
//            mTextView.setAlpha(0.5f);
//            mImageView.setImageAlpha(128);
        }
    };

    TimerTask faceTimerTask = new TimerTask() {
        @Override
        public void run() {
            faceTime ++;
            if ((faceTime >= 30) && (isRecognized ==false)) {
                faceTimer.cancel();
                finish();
            }
        }
    };

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            Log.d(TAG, "Camera Focus SUCCESS!");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        CameraHelper.touchFocus(mCamera, event, v, this);
        return false;
    }

    @Override
    public Camera setupCamera() {
        mCamera = Camera.open(mCameraID);
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(mWidth, mHeight);
            parameters.setPreviewFormat(mFormat);

            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                Log.d(TAG, "SIZE:" + size.width + "x" + size.height);
            }
            for (Integer format : parameters.getSupportedPreviewFormats()) {
                Log.d(TAG, "FORMAT:" + format);
            }

            List<int[]> fps = parameters.getSupportedPreviewFpsRange();
            for (int[] count : fps) {
                Log.d(TAG, "T:");
                for (int data : count) {
                    Log.d(TAG, "V=" + data);
                }
            }
            //parameters.setPreviewFpsRange(15000, 30000);
            //parameters.setExposureCompensation(parameters.getMaxExposureCompensation());
            //parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            //parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
            //parmeters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            //parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            //parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCamera != null) {
            mWidth = mCamera.getParameters().getPreviewSize().width;
            mHeight = mCamera.getParameters().getPreviewSize().height;
        }
        return mCamera;
    }

    @Override
    public void setupChanged(int format, int width, int height) {

    }

    @Override
    public boolean startPreviewLater() {
        return false;
    }

    @Override
    public Object onPreview(byte[] data, int width, int height, int format, long timestamp) {
        AFT_FSDKError err = engine.AFT_FSDK_FaceFeatureDetect(data, width, height, AFT_FSDKEngine.CP_PAF_NV21, result);
//        MyLogger.i( "AFT_FSDK_FaceFeatureDetect =" + err.getCode());
//        MyLogger.i( "Face=" + result.size());
        for (AFT_FSDKFace face : result) {
//            MyLogger.i("Face:" + face.toString());
        }


        if (mImageNV21 == null) {
            if (!result.isEmpty()) {
                mAFT_FSDKFace = result.get(0).clone();
                mImageNV21 = data.clone();
            } else {
                mHandler.postDelayed(hide, 3000);
            }
        }
        //copy rects
        Rect[] rects = new Rect[result.size()];
        for (int i = 0; i < result.size(); i++) {
            rects[i] = new Rect(result.get(i).getRect());
        }


        //测试
        if (data.length > 0) {

//            MyLogger.i("isRecognized--->" + isRecognized + "\n" + "originBitmap-->" + originBitmap);
            if (isRecognized == true && originBitmap == null) {
                MyLogger.i("=========111===");

                BitmapFactory.Options newOpts = new BitmapFactory.Options();
                newOpts.inJustDecodeBounds = true;
                YuvImage yuvimage = new YuvImage(
                        data,
                        ImageFormat.NV21,
                        mWidth,
                        mHeight,
                        null);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                yuvimage.compressToJpeg(new Rect(0, 0, mWidth, mHeight), 100, baos);// 80--JPG图片的质量[0-100],100最高
                byte[] rawImage = baos.toByteArray();
                //将rawImage转换成bitmap
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);

                //照片反转
                Bitmap modBm = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(modBm);
                Paint paint = new Paint();
                paint.setColor(Color.BLACK);
                paint.setAntiAlias(true);
                Matrix matrix = new Matrix();
                //matrix.setRotate(90, bm.getWidth()/2, bm.getHeight()/2);
                //matrix.setTranslate(20, 20);
                //镜子效果
                matrix.setScale(-1, 1);
                matrix.postTranslate(bitmap.getWidth(), 0);

                canvas.drawBitmap(bitmap, matrix, paint);


                previewImg.setVisibility(View.VISIBLE);
                originBitmap = modBm;
//                originBitmap = bitmap;
                previewImg.setImageBitmap(originBitmap);

            }

            if (isRecognized == false) {
//                previewImg.setImageBitmap(modBm);
                previewImg.setVisibility(View.INVISIBLE);
            }

        }

        //clear result.
        result.clear();
        //return the rects for render.
        return rects;
    }

    @Override
    public void onBeforeRender(CameraFrameData data) {

    }

    @Override
    public void onAfterRender(CameraFrameData data) {
        glsurfaceView.getGLES2Render().draw_rect((Rect[]) data.getParams(), Color.GREEN, 2);
    }

    class FRAbsLoop extends AbsLoop {

        AFR_FSDKVersion version = new AFR_FSDKVersion();
        AFR_FSDKEngine engine = new AFR_FSDKEngine();
        AFR_FSDKFace result = new AFR_FSDKFace();
        List<FaceDB.FaceRegist> mResgist = ((AttendanceApplication) FaceRecognitionActivity.this.getApplicationContext()).mFaceDB.mRegister;

        @Override
        public void setup() {
            AFR_FSDKError error = engine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            Log.d(TAG, "AFR_FSDK_InitialEngine = " + error.getCode());
            error = engine.AFR_FSDK_GetVersion(version);
            Log.d(TAG, "FR=" + version.toString() + "," + error.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
        }

        @Override
        public void loop() {
            if (mImageNV21 != null) {
                AFR_FSDKError error = engine.AFR_FSDK_ExtractFRFeature(mImageNV21, mWidth, mHeight, AFR_FSDKEngine.CP_PAF_NV21, mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree(), result);
//                MyLogger.i("Face=" + result.getFeatureData()[0] + "," + result.getFeatureData()[1] + "," + result.getFeatureData()[2] + "," + error.getCode());
                AFR_FSDKMatching score = new AFR_FSDKMatching();
                float max = 0.0f;
                String name = null;
                for (FaceDB.FaceRegist fr : mResgist) {
                    for (AFR_FSDKFace face : fr.mFaceList) {
                        error = engine.AFR_FSDK_FacePairMatching(result, face, score);
//                        MyLogger.i("Score:" + score.getScore() + ", AFR_FSDK_FacePairMatching=" + error.getCode());
                        if (max < score.getScore()) {
                            max = score.getScore();
                            name = fr.mName;
                            MyLogger.i("max-->" + max + "  name-->" + name);
                        }
                    }
                }


//                if (isRepeateClick && !isRecognized) {
//                    MyLogger.i("00000");
//                    max = 0.0f;
//                    isRepeateClick = false;
//                    return;
//                }
//                MyLogger.i("isRepeateClick-->" + isRepeateClick);

                //crop
                byte[] data = mImageNV21;
                YuvImage yuv = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
                ExtByteArrayOutputStream ops = new ExtByteArrayOutputStream();
                yuv.compressToJpeg(mAFT_FSDKFace.getRect(), 80, ops);
                final Bitmap bmp = BitmapFactory.decodeByteArray(ops.getByteArray(), 0, ops.getByteArray().length);
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (max > 0.8f) {

                    MyLogger.i("isRecognized--》" + isRecognized);

                    //如果已识别到 就不在识别
                    if (isRecognized) {
//                        mFRAbsLoop.shutdown();
                        mImageNV21 = null;
                        return;
                    }
                    //fr success.
                    isRecognized = true;
                    faceTime = 0;

                    final float max_score = max;
                    MyLogger.i("fit Score:" + max + ", NAME:" + name);
                    final String mNameShow = name;
                    mHandler.removeCallbacks(hide);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

//                            mTextView.setAlpha(1.0f);
//                            mTextView.setText(mNameShow);
//                            mTextView.setTextColor(Color.RED);
//                            mTextView.setVisibility(View.VISIBLE);
                            confidenceText.setText("置信度：" + (float) ((int) (max_score * 1000)) / 1000.0);
                            confidenceText.setTextColor(Color.RED);


//                            mImageView.setRotation(mCameraRotate);
//                            if (mCameraMirror) {
//                                mImageView.setScaleY(-1);
//                            }
//                            mImageView.setImageAlpha(255);
//                            mImageView.setImageBitmap(bmp);

//                            headerImg.setImageBitmap(bmp);

                            if (isRecognized == true) {
                                previewImg.setVisibility(View.VISIBLE);
//                                headerImg.setImageBitmap(bmp);
//                                faceBitmap = bmp;
                                String[] array = mNameShow.split("_");
                                nameText.setText("姓名：" + array[1]);
                                workNo.setText("工号：" + array[0]);
                                workNoStr = array[0];

                                MyLogger.i("已识别---checkFace");

//                                checkFace();
                                getAttendanceStatus();
                            }

                        }
                    });
                } else {
                    final String mNameShow = "未识别";
                    if (isRecognized) {
                        return;
                    }
                    isRecognized = false;
//                    isChanged = false;
//                    nameText.setText("姓名：");
//                    workNo.setText("工号：");


                    FaceRecognitionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyLogger.i("未识别");

//                            mTextView.setAlpha(1.0f);
//                            mTextView1.setVisibility(View.INVISIBLE);
//                            mTextView.setText(mNameShow);
//                            mTextView.setTextColor(Color.RED);
//                            mImageView.setImageAlpha(255);
//                            mImageView.setRotation(mCameraRotate);
//                            if (mCameraMirror) {
//                                mImageView.setScaleY(-1);
//                            }
//                            mImageView.setImageBitmap(bmp);

                            confidenceText.setText(mNameShow);
                            confidenceText.setTextColor(Color.RED);

                            MyLogger.i("未识别---》isRecognized--》" + isRecognized);
                            if (isRecognized) {
//                                headerImg.setImageBitmap(faceBitmap);
                                previewImg.setVisibility(View.VISIBLE);
                                previewImg.setImageBitmap(originBitmap);

                            } else {

//                                headerImg.setImageResource(R.mipmap.header_img);
                                previewImg.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                mImageNV21 = null;
            }

        }

        @Override
        public void over() {
            AFR_FSDKError error = engine.AFR_FSDK_UninitialEngine();
            Log.d(TAG, "AFR_FSDK_UninitialEngine : " + error.getCode());
        }
    }

    /**
     * 获取司机出勤状态
     */
    private void getAttendanceStatus() {
        GetAttendanceStatusDao statusDao = new GetAttendanceStatusDao();
        MyLogger.i("工号====" + workNoStr);

        statusDao.getAttendanceStatus(workNoStr, new RequestListenerImpl<GetAttendanceStatusBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<GetAttendanceStatusBean>() {
                }.getType();
            }

            @Override
            public void onSuccess(GetAttendanceStatusBean response) {
                if (response.getType().equals("1")) {

                    nextStep.setBackgroundResource(R.drawable.btn_corner_blue);
                    nextStep.setEnabled(true);

                    if (response.getResult().isStatus()) {
                        MyLogger.i("已出勤");
                        nextStep.setText("办理退勤");
                        identifyCode = response.getResult().getIdentifyCode();
                    } else {
                        MyLogger.i("未出勤");
                        nextStep.setText("下一步");
                    }
                    plateNo = response.getResult().getPlateNo();
                    if (plateNo.length() == 0 ){
                        plateNo = "";
                    }
//                    trainNo.setText("车牌：" + plateNo);
                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
//                showToast(errMsg);
                nextStep.setEnabled(false);
                nextStep.setBackgroundResource(R.drawable.btn_corner_gray);
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
                nextStep.setEnabled(false);
                nextStep.setBackgroundResource(R.drawable.btn_corner_gray);
                showNetErrToast();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFRAbsLoop.shutdown();
        AFT_FSDKError err = engine.AFT_FSDK_UninitialFaceEngine();
        Log.d(TAG, "AFT_FSDK_UninitialFaceEngine =" + err.getCode());

        if (faceTimer != null) {
            faceTimer.cancel();
        }

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
