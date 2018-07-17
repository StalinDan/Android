package com.danish.attendance.net;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.Log;


import com.danish.attendance.bean.MessageBean;
import com.danish.attendance.utils.MyLogger;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by danish on 2018/3/13.
 */

public class FileUtil {

    public final static int DOWNLOAD_SUCESS = 0XD001;
    public final static int DOWNLOAD_FAIL = 0XD002;
    public final static int DOWNLOAD_PRO = 0XD003;
    public final static String DownLoadUrl = Environment.getExternalStorageDirectory().getAbsolutePath() + "/faceInfo";

    private final static OkHttpClient httpClient = new OkHttpClient();

    private UploadFileCallBack uploadFileCallBack;

    public void setUploadFileCallBack(UploadFileCallBack uploadFileCallBack) {
        this.uploadFileCallBack = uploadFileCallBack;
    }

    public interface UploadFileCallBack {
        public void uploadSuccess(MessageBean messageBean);
        public void uploadFail(String errorMsg);
    }

    private SubmitFaceCallBack submitFaceCallBack;

    public void setSubmitFaceCallBack(SubmitFaceCallBack submitFaceCallBack) {
        this.submitFaceCallBack = submitFaceCallBack;
    }

    public interface SubmitFaceCallBack {
        public void submitSuccess(MessageBean messageBean);
        public void submitFail(String errorMsg);
    }

    /**
     * 上传文件
     * @param url
     * @param fileName
     */
    public void uploadFileData (String url, String filePath, String fileName, final UploadFileCallBack callback) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        //创建File
        File file = new File(filePath);

        //创建RequestBody
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename="+fileName), fileBody);


        //构建请求体
        final RequestBody requestBody = builder.build();

        //构建请求
        Request request = new Request.Builder()
                .url(url)//地址
                .post(requestBody)//添加请求体
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                String errorMsg = e.getLocalizedMessage();
                System.out.println("request = " + request.urlString());
                System.out.println("e.getLocalizedMessage() = " + errorMsg);

                callback.uploadFail(errorMsg);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String jsonStr = response.body().string();
                MyLogger.i("response = " + jsonStr);

                Gson gson = new Gson();
                MessageBean messageBean = gson.fromJson(jsonStr,MessageBean.class);

                callback.uploadSuccess(messageBean);
            }
        });
    }


    /**
     * 下载文件
     * @param url
     * @param destFileDir
     * @param mHandler
     */
    public void downLoadFile (final String url, final String destFileDir, final List workNoList, final Handler mHandler) {

       final File file = new File(destFileDir,"");
        if (file.exists()) {
            Message msg = mHandler.obtainMessage();
            msg.what = DOWNLOAD_SUCESS;
            mHandler.sendMessage(msg);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
//                Request request = new Request.Builder().url(url).build();

                ArrayMap paramsMap = new ArrayMap();
                paramsMap.put("workNos",workNoList);

                Gson gson = new Gson();
                RequestBody formBody = RequestBody.create(MediaType.parse("JSON"), gson.toJson(paramsMap));
                Request.Builder builder = new Request.Builder().url(url).post(formBody);
                Request request = builder.build();

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


    /**
     * 上传图片
     *
     * @param url
     * @param params
     * @return
     */
    public void uploadImage(String url, ArrayMap<String, String> params,String imgPath,String imgName,final SubmitFaceCallBack submitFaceCallBack) {

        //创建File
        File file = new File(imgPath);
        //创建RequestBody
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);


        multipartBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename="+imgName), fileBody);

        if (params != null) {
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String param = params.get(key);
                multipartBuilder.addFormDataPart(key, param);
                MyLogger.i(key + "   " + param);
            }
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                MyLogger.i("request = " + request.urlString());
                MyLogger.i("e.getLocalizedMessage() = " + e.getLocalizedMessage());

                submitFaceCallBack.submitFail(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String jsonStr = response.body().string();
                MyLogger.i("response = " + jsonStr);


                Gson gson = new Gson();
                MessageBean messageBean = gson.fromJson(jsonStr,MessageBean.class);

                MyLogger.i(messageBean.getMsg());
                submitFaceCallBack.submitSuccess(messageBean);
            }
        });

    }

}



