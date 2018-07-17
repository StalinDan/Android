package com.danish.attendance.net;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.danish.attendance.utils.MyLogger;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LHG on 2017/7/7.
 */

public class DownloadUtil {


    public final static int DOWNLOAD_SUCESS = 0XD001;
    public final static int DOWNLOAD_FAIL = 0XD002;
    public final static int DOWNLOAD_PRO = 0XD003;
    public final static String DOWNLOAD_URL = Environment.getExternalStorageDirectory().getAbsolutePath();
    public final static String DOWNLOAD_NAME = "tongan.apk";

    /**
     * 下载文件
     * @param url
     * @param mHandler
     */
    public void download(final String url , final Handler mHandler)
    {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                AsyncRequestRunner.getClient().newCall(request).enqueue(new Callback() {

                    @SuppressWarnings("resource")
                    @Override
                    public void onResponse(Response response) throws IOException {
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len = 0;
                        FileOutputStream fos = null;

                        try {
                            is = response.body().byteStream();
                            long total = response.body().contentLength();
                            File file = new File(DOWNLOAD_URL, DOWNLOAD_NAME);
                            fos = new FileOutputStream(file);
                            long sum = 0;
                            while ((len = is.read(buf)) != -1) {
                                fos.write(buf, 0, len);
                                sum += len;
                                int progress = (int) (sum * 1.0f / total * 100);
                                MyLogger.i(progress+"");
                                Message msg = mHandler.obtainMessage();
                                msg.what = DOWNLOAD_PRO;
                                msg.arg1 = progress;
                                mHandler.sendMessage(msg);
                            }
                            fos.flush();

                            Message msg = mHandler.obtainMessage();
                            msg.what = DOWNLOAD_SUCESS;
                            mHandler.sendMessage(msg);

                        } catch (Exception e) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = DOWNLOAD_FAIL;
                            mHandler.sendMessage(msg);
                        } finally {
                            try {
                                if (is != null)
                                    is.close();
                            } catch (IOException e) {
                            }
                            try {
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                            }
                        }
                    }

                    @Override
                    public void onFailure(Request arg0, IOException arg1) {

                        Message msg = mHandler.obtainMessage();
                        msg.what = DOWNLOAD_FAIL;
                        mHandler.sendMessage(msg);

                    }
                });
            }
        }).start();


    }
}
