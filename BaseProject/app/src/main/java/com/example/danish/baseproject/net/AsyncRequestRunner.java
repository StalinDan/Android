package com.example.danish.baseproject.net;

import android.support.v4.util.ArrayMap;

import com.example.danish.baseproject.BuildConfig;
import com.example.danish.baseproject.dbUtil.SharedPreferenceManager;
import com.example.danish.baseproject.utils.DeviceUtil;
import com.example.danish.baseproject.utils.MyLogger;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AsyncRequestRunner {

    public final static int RequestGET = 0x0001;
    public final static int RequestPOST = 0x0002;
    public final static int RequestUPLOADIMAGE = 0x0003;
    public final static int RequestPUT = 0x0004;

    //电影，书
//    public final static String HOST = "https://api.douban.com/v2/";

    //视频
    public final static String HOST = "http://114.55.250.37:8080/videos/";
    public final static String WebSocketHOST = "wss://mapi.bm.deayea.com/";
    public final static String DATAHOST = "http://th.bm.deayea.com/";


//    public final static String HOST = "http://192.168.0.244:8180/";
//    public final static String WebSocketHOST = "ws://192.168.0.244:8180/";




    private final static OkHttpClient httpClient = new OkHttpClient();

    static {

        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };

        httpClient.setConnectTimeout(20, TimeUnit.SECONDS);
        httpClient.setReadTimeout(20, TimeUnit.SECONDS);
        httpClient.setSslSocketFactory(sslContext.getSocketFactory());
        httpClient.setHostnameVerifier(DO_NOT_VERIFY);

    }


    public static OkHttpClient getClient() {
        //httpClient.networkInterceptors().add(new StethoInterceptor());
        return httpClient;
    }

    /**
     * @param requestWay 交互方式
     * @param url        交互url
     * @param params     参数
     * @param tag        tag,主要用来取消
     * @param listener   回调监听
     */
    public void request(final int requestWay, final String url, final ArrayMap<String, String> params, final String tag, final RequestListener listener) {


        Observer observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String responseStr) {
                if (listener != null) {
                    listener.onFinish(responseStr);
                }
            }

            @Override
            public void onError(Throwable e2) {
                if (listener != null) {
                    listener.onNetError(e2);
                }
            }

            @Override
            public void onComplete() {

            }

        };

        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

                Response response = null;

                switch (requestWay) {

                    case RequestPOST:
                        response = post(url, params, tag);
                        break;
                    case RequestGET:
                        response = get(url, params, tag);
                        break;
                    case RequestUPLOADIMAGE:
                        response = uploadImage(url, params, tag);
                        break;
                    case RequestPUT:
                        response = doPut(url, params ,tag );
                        break;
                }

                if (response != null && response.isSuccessful()) {
                    try {
                        e.onNext(response.body().string());
                    } catch (IOException e1) {
                        e.onError(e1);
                    }
                } else {
                    //主要因为网络异常
                    e.onError(new Throwable(tag));
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);


//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//
//                Response response = null;
//
//                switch (requestWay) {
//                    case RequestPOST:
//                        response = post(url, params, tag);
//                        break;
//                    case RequestGET:
//                        response = get(url, params, tag );
//                        break;
//                    case RequestUPLOADIMAGE:
//                        response = uploadImage(url, params, tag);
//                        break;
//                }
//
//                 if(response != null && response.isSuccessful())
//                 {
//                     MyLogger.i("   "+response.code());
//                     try {
//                         listener.onSuccess(response.body().string());
//                     } catch (IOException e) {
//                         listener.onNetError(e);
//                     }
//                 }
//                else
//                 {
//                     //主要因为网络异常
//                     listener.onNetError(null);
//                 }
//            }
//        }.start();
    }

    /**
     * @param url
     * @param params
     * @return
     */
    private Response get(String url, ArrayMap<String, String> params, String tag) {
        String requestURL = generateURL(url, params);
        MyLogger.i(requestURL);
        Request.Builder builder = new Request.Builder().url(requestURL).tag(tag);

        Request request;

        request = packageBuild(builder).build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
        }
        return response;
    }

    private Response post(String url, ArrayMap<String, String> params, String tag) {
        Response response = null;
        RequestBody formBody = null;

        if (tag.equals("login")) {
            SharedPreferenceManager.setUsertoken("");
            FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
            if (params != null) {
                Iterator<String> keys = params.keySet().iterator();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String param = (String) params.get(key);
                    if (key == null || param == null) {
                        return null;
                    }
                    formEncodingBuilder.add(key, param);
                    MyLogger.i(key + " " + param);
                }
            }
            formBody = formEncodingBuilder.build();
        } else {

            Gson gson = new Gson();
            formBody = RequestBody.create(MediaType.parse("JSON"), gson.toJson(params));
            MyLogger.i(gson.toJson(params));

        }
        Request.Builder builder = new Request.Builder().url(url).post(formBody).tag(tag);


        if (SharedPreferenceManager.getUUID().equals("")) {
            SharedPreferenceManager.setUUID(DeviceUtil.getDeviceId());
        }


        if (tag.equals("login")) {
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        } else {
            builder.addHeader("Content-Type", "application/json");
        }

        Request request = packageBuild(builder).build();

        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
            MyLogger.i(e.toString());
        }
        return response;
    }

    /**
     * 上传图片
     *
     * @param url
     * @param params
     * @param tag
     * @return
     */
    private Response uploadImage(String url, ArrayMap<String, String> params, String tag) {

        Response response = null;
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (params != null) {
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                String param = params.get(key);
                if (!key.equals("userImage")) {
                    multipartBuilder.addFormDataPart(key, param);
                } else {
                    multipartBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"userImage\"; filename=\"userImage\""), fileBody);
                }
                MyLogger.i(key + "   " + param);
            }
        }
        RequestBody requestBody = multipartBuilder.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            response = httpClient.newCall(request).execute();
        } catch (IOException e) {
        }
        return response;
    }


    public Response doPut(String url, ArrayMap<String, String> params, String tag) {

        RequestBody formBody = null;

        Gson gson = new Gson();
        formBody = RequestBody.create(MediaType.parse("JSON"), gson.toJson(params));

        Request.Builder builder = new Request.Builder().url(url).put(formBody).tag(tag);
        builder.addHeader("Content-Type", "application/json");


        Request request = packageBuild(builder).build();



        Response response = null;

        try {
                response = httpClient.newCall(request).execute();
            } catch (IOException e) {
        }

        return response;
    }

    private File file = null;

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 根据主url和所需参数params生成相应url请求字符串
     *
     * @param url    要传递的url
     * @param params 调用服务时所需参数
     * @return url请求字符串
     */
    private String generateURL(String url, ArrayMap<String, String> params) {

        StringBuffer urlBuffer = new StringBuffer(url);
        if (null == params || params.keySet().size() == 0)
            return urlBuffer.toString();
        int index = 0;
        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            String param = params.get(key);
            if (index == 0) {
                urlBuffer.append("?").append(key).append("=").append(param);
            } else {
                urlBuffer.append("&").append(key).append("=").append(param);
            }
            index++;
        }
        String completionUrl = urlBuffer.toString();
        return completionUrl;
    }

    public void cancel(String tag) {
        httpClient.cancel(tag);
    }

    /**
     * 添加 header
     * @param builder
     * @return
     */
    private Request.Builder packageBuild(Request.Builder builder) {
        builder.addHeader("mid", SharedPreferenceManager.getUUID());
        builder.addHeader("accessToken", SharedPreferenceManager.getUsertoken());
        builder.addHeader("appVer", BuildConfig.VERSION_NAME);
        builder.addHeader("appType", "Android");
        builder.addHeader("appid", "batman");
        // builder.addHeader("appid", "Android");
        return builder;
    }
}
