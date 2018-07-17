package com.danish.attendance;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.danish.attendance.utils.FaceDB;
import com.danish.attendance.utils.MyLogger;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by danish on 2018/6/6.
 */

public class AttendanceApplication extends Application {

    private static AttendanceApplication mLoganApplication;

    public FaceDB mFaceDB;
    Uri mImage;
    public String filePathStr;
    public String faceImgPath;

    private Bitmap faceBitmap; //现场照片或注册照片

    public Bitmap getFaceBitmap() {
        return faceBitmap;
    }

    public void setFaceBitmap(Bitmap faceBitmap) {
        this.faceBitmap = faceBitmap;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);

        mLoganApplication = AttendanceApplication.this;
//        RxWebSocketUtil.getInstance().setClient(new OkHttpClient());
//        RxWebSocketUtil.getInstance().setShowLog(BuildConfig.DEBUG);


//        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        mImage = null;

        filePathStr = getSDCardBaseDir() + "/attendanceFaceInfo";
        faceImgPath = getSDCardBaseDir() + "/attendanceFaceImg";
        MyLogger.i("filePath-->"+filePathStr);
        MyLogger.i("SD卡目录--》"+ getSDCardBaseDir());

        //如果不存在，创建faceInfo文件夹
        if (!isFaceInfoExist(filePathStr)) {
            createFile();
        }

        //判断face.txt 是否存在
        if (!isFaceTextExist()) {
            createFaceTextFile();
        }

        //判断faceImg 是否存在
        if (!isFaceImgExist()) {
            createFaceImgFile();
        }

        mFaceDB = new FaceDB(filePathStr);

    }

    /**
     * 人脸信息文件夹是否存在
     * @param filePathStr
     * @return
     */
    public boolean isFaceInfoExist (String filePathStr) {
        //新建一个File，传入文件夹目录
        File file = new File(filePathStr);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            MyLogger.i("不存在");
            return false;
            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
        } else {
            return true;
        }
    }

    /**
     * face.txt 是否存在
     * @return
     */
    public boolean isFaceTextExist () {
        File file = new File(filePathStr + "/face.txt");
        if (file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFaceImgExist() {
        File file = new File(faceImgPath);
        if (!file.exists()) {
            MyLogger.i("faceImg不存在");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 创建人脸信息文件夹
     */
    public void createFile () {
        //新建一个File，传入文件夹目录
        File file = new File(filePathStr);
        //判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            MyLogger.i("不存在");
            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
            file.mkdirs();
        }
    }

    /**
     * 创建faceImg
     */
    public void createFaceImgFile () {
        File file = new File(faceImgPath);
        if (!file.exists()) {
            MyLogger.i("faceImg不存在");
            file.mkdirs();
        }
    }

    /**
     * 创建face.txt
     */
    public void createFaceTextFile () {
        File file = new File(filePathStr + "/face.txt");
        if (file.isFile()) {

        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private void createFile () {
//        //新建一个File，传入文件夹目录
//        File file = new File(filePathStr);
//        //判断文件夹是否存在，如果不存在就创建，否则不创建
//        if (!file.exists()) {
//            MyLogger.i("不存在");
//            //通过file的mkdirs()方法创建<span style="color:#FF0000;">目录中包含却不存在</span>的文件夹
//            file.mkdirs();
//        }
//    }
//
//    private void createFaceImgFile () {
//        File file = new File(faceImgPath);
//        if (!file.exists()) {
//            MyLogger.i("faceImg不存在");
//            file.mkdirs();
//        }
//    }
//
//    private void createFaceTextFile () {
//        File file = new File(filePathStr + "/face.txt");
//        if (file.isFile()) {
//
//        } else {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 判断SD卡是否被挂载
    public static boolean isSDCardMounted() {
        // return Environment.getExternalStorageState().equals("mounted");
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    // 获取SD卡的根目录
    public static String getSDCardBaseDir() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    // 往SD卡的自定义目录下保存文件
    public static boolean saveFileToSDCardCustomDir(byte[] data, String dir, String fileName) {
        BufferedOutputStream bos = null;
        if (isSDCardMounted()) {
            File file = new File(getSDCardBaseDir() + File.separator + dir);
            if (!file.exists()) {
                file.mkdirs();// 递归创建自定义目录
            }
            try {
                bos = new BufferedOutputStream(new FileOutputStream(new File(file, fileName)));
                bos.write(data);
                bos.flush();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static AttendanceApplication getContext() {
        return mLoganApplication;
    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
