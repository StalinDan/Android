package com.danish.attendance.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danish.attendance.AttendanceApplication;
import com.danish.attendance.R;
import com.danish.attendance.bean.FaceInfoBean;
import com.danish.attendance.bean.LoginBean;
import com.danish.attendance.dao.DownLoadFaceDao;
import com.danish.attendance.dao.LoginDao;
import com.danish.attendance.net.AsyncRequestRunner;
import com.danish.attendance.net.RequestListenerImpl;
import com.danish.attendance.popWindow.LoginPopWindow;
import com.danish.attendance.popWindow.PopConfigWidow;
import com.danish.attendance.utils.MD5Util;
import com.danish.attendance.utils.MyLogger;
import com.danish.attendance.utils.SharedPreferenceManager;
import com.google.gson.reflect.TypeToken;
import com.guo.android_extend.java.ExtOutputStream;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.mainBtn)
    Button mainBtn;
    //    @BindView(R.id.deleteFaceInfo)
//    Button deleteFaceInfo;
    @BindView(R.id.titleText)
    TextView titleText;
    @BindView(R.id.login)
    Button login;

    LoginPopWindow loginPopWindow;

    public static boolean isAlterHost = false;
    AttendanceApplication app;
    Timer timer = new Timer();
    int updateTime = 0;

    public interface DownLoadCallBack {
        public void downLoadSuccess(byte[] bytes);

        public void downLoadFail();
    }

    DownLoadCallBack downLoadCallBack;

    public void setDownLoadCallBack(DownLoadCallBack downLoadCallBack) {
        this.downLoadCallBack = downLoadCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (SharedPreferenceManager.getHost().equals("")) {
            SharedPreferenceManager.setHost("http://192.168.0.33:80");
        }

        AsyncRequestRunner.HOST = SharedPreferenceManager.getHost();

        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FaceRecognitionActivity.class);
                startActivity(intent);
            }
        });

        final ColorDrawable drawable = new ColorDrawable(0x00000000);
        loginPopWindow = new LoginPopWindow(LayoutInflater.from(this).inflate(R.layout.popup_login,null,false),1000,600,false,this);
        loginPopWindow.setLoginCallback(new LoginPopWindow.LoginCallback() {
            @Override
            public void loginClicked(String name,String password) {
                goToLogin(name,password);

            }
        });

        //注册
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPopWindow.showPopupWindow();
                loginPopWindow.setBackgroundDrawable(drawable);
                loginPopWindow.backgroundAlpha(0.5f);
            }
        });



        final PopConfigWidow popConfigWidow = new PopConfigWidow(LayoutInflater.from(this).inflate(R.layout.popup_config, null, false), 1000, 600, false, this);
        titleText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                popConfigWidow.showPopupWindow();

                popConfigWidow.setBackgroundDrawable(drawable);
                popConfigWidow.backgroundAlpha(0.5f);
                return false;
            }
        });
        app = (AttendanceApplication)MainActivity.this.getApplicationContext();

        createFaceFile();

        timer.schedule(timerTask,0,1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAlterHost) {
            isAlterHost = false;
            downLoadFaceData();
        }
    }

    private void createFaceFile () {
        if (app.isFaceInfoExist(app.filePathStr) && app.isFaceTextExist() && app.isFaceImgExist()) {
            MyLogger.i("downLoadFaceData===");
            downLoadFaceData();
        } else {
            MyLogger.i("createFaceFile===");
            app.createFaceTextFile();
            app.createFile();
            app.createFaceImgFile();
            createFaceFile();
        }
    }

    /**
     * 下载人脸
     */
    private void downLoadFaceData() {
        List<FaceInfoBean> faceList = new ArrayList();
        faceList = app.mFaceDB.getFaces();
        List workNoList = new ArrayList();
        for (FaceInfoBean bean : faceList) {
            workNoList.add(bean.getWorkNo().toString());
        }


        DownLoadFaceDao downLoadFaceDao = new DownLoadFaceDao();
        downLoadFaceDao.downLoadFace(workNoList, new DownLoadCallBack() {

            @Override
            public void downLoadSuccess(byte[] bytes) {
//                Log.i("aa","inputStream---->"+inputStream.toString());
                try {
                    MyLogger.i("下载到本地");
                    Logger.d("下载人脸");
                    writeToLocal(app.filePathStr + "/temp.zip", bytes);
                } catch (Exception e) {
                    MyLogger.i("写入失败");
                }

                try {
                    MyLogger.i("路径--》" + app.filePathStr);
                    unzip(app.filePathStr + "/temp.zip", app.filePathStr + "/");

                    File delfile = new File(app.filePathStr + "/" + "temp.zip");
                    if (delfile.exists()) {
                        MyLogger.i( "temp存在");
                        delfile.delete();

                        MyLogger.i("注册的人脸数-->" + app.mFaceDB.mRegister.size());

                    }
                } catch (Exception e) {
                    MyLogger.i("解压错误---》" + e.getLocalizedMessage());
                }
            }

            @Override
            public void downLoadFail() {
//                Log.i("MainActivity","下载失败");
            }
        });


    }


    /**
     * 将InputStream写入本地文件
     *
     * @param destination 写入本地目录
     * @param input       输入流
     * @throws IOException
     */
    private static void writeToLocal(String destination, byte[] input)
            throws IOException {

        FileOutputStream downloadFile = new FileOutputStream(destination);
        downloadFile.write(input, 0, input.length);

        downloadFile.flush();

        downloadFile.close();
    }


    /*
    * 这个是解压ZIP格式文件的方法
    *
    * @zipFileName：是传进来你要解压的文件路径，包括文件的名字；
    *
    * @outputDirectory:选择你要保存的路劲；
    *
    */
    private void unzip(String zipFileName, String outputDirectory)
            throws Exception {

        final AttendanceApplication app = (AttendanceApplication) MainActivity.this.getApplicationContext();

        ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry z;
        String name = "";
        String extractedFile = "";
        int counter = 0;

        while ((z = in.getNextEntry()) != null) {
            name = z.getName();
            MyLogger.i("unzipping file: " + name);
            if (z.isDirectory()) {
                MyLogger.i(name + "is a folder");
                // get the folder name of the widget
                name = name.substring(0, name.length() - 1);
                File folder = new File(outputDirectory + File.separator + name);
                folder.mkdirs();
                if (counter == 0) {
                    extractedFile = folder.toString();
                }
                counter++;
                MyLogger.i("mkdir " + outputDirectory + File.separator + name);
            } else {
                MyLogger.i(name + "is a normal file");


                File file = new File(outputDirectory + File.separator + name);
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int ch;
                byte[] buffer = new byte[1024];
                // read (ch) bytes into buffer
                while ((ch = in.read(buffer)) != -1) {
                    // write (ch) byte from buffer at the position 0
                    out.write(buffer, 0, ch);
                    out.flush();
                }
                out.close();


                //update all names
                MyLogger.i("=======111111111========");
                FileOutputStream fs = new FileOutputStream(app.filePathStr + "/face.txt", true);
                ExtOutputStream bos = new ExtOutputStream(fs);
                String[] array = name.split(".data");
                bos.writeString(array[0].toString());
                bos.close();
                fs.close();
//
//                FaceDB faceDB = app.mFaceDB;
//                FaceDB.FaceRegist faceRegist = faceDB.new FaceRegist(array[0]);
////                faceRegist.mFaceList.add(face);
//
////                app.mFaceDB.mRegister.add(faceRegist);
////                Log.i("aa","app.mFaceDB.mRegister------>size--==" + app.mFaceDB.mRegister.size());
//
//                FileInputStream fsIn = new FileInputStream(app.filePathStr + "/face.txt");
//                ExtInputStream bosIn = new ExtInputStream(fsIn);
//                //load version
//                String version_saved = bosIn.readString();
//
//                //load all regist name.
////                String nameStr = array[0];
//                if (version_saved != null) {
//                    for (String nameStr = bosIn.readString(); nameStr != null; nameStr = bosIn.readString()){
//                        if (new File(app.filePathStr + "/" + name + ".data").exists()) {
//                            MyLogger.i("name---->"+name);
//                            app.mFaceDB.mRegister.add(faceRegist);
//                        }
//                    }
//                }
//                bos.close();
//                fs.close();

            }
        }

        in.close();

        MyLogger.i("app.mFaceDB.mRegister------>size--==" + app.mFaceDB.mRegister.size());
        Iterator it = app.mFaceDB.mRegister.iterator();
        while (it.hasNext()) {
            it.next();
            if (true) {
                it.remove();
            }
        }
        MyLogger.i("app.mFaceDB.mRegister------>size--==" + app.mFaceDB.mRegister.size());
        app.mFaceDB.loadFaces();

    }

    /**
     * 登录
     * @param name
     * @param password
     */
    private void goToLogin (String name,String password) {

        String md5Pwd = MD5Util.getMD5(password);
        LoginDao loginDao = new LoginDao();
        loginDao.login(name, md5Pwd, new RequestListenerImpl<LoginBean>() {
            @Override
            public Type getTypeReference() {
                return new TypeToken<LoginBean>(){}.getType();
            }

            @Override
            public void onSuccess(LoginBean response) {
                if (response.getType().equals("1")) {
                    loginPopWindow.dismissPopupWindow();
                    loginPopWindow.clearLoginInfo();
                    Intent intent = new Intent(MainActivity.this,DriverLIstActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFail(String type, String errMsg) {
                MyLogger.i("errMsg-->" + errMsg);
                showToast(errMsg);
            }

            @Override
            public void onNetError(Throwable e) {
                MyLogger.i("Throwable errMsg" + e.toString());
            }
        });
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            updateTime ++;
            if (updateTime >= 60) {
                downLoadFaceData();
                updateTime = 0;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }


}
