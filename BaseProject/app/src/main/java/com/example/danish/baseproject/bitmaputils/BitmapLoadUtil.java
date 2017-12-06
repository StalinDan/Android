package com.example.danish.baseproject.bitmaputils;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

/**
 * Created by LHG on 2017/3/28.
 */

public class BitmapLoadUtil {

    /**
     * 下载网络图片
     * @param context
     * @param iamgeUrl
     * @param callBack
     */
    public static void loadImage(Context context , String iamgeUrl , ImageDownLoadCallBack callBack)
    {

        try {

            Bitmap bitmap = Glide.with(context).load(iamgeUrl).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

            if(bitmap == null)
            {
                callBack.onDownLoadSuccess(bitmap);
            }
            else
            {
                callBack.onDownLoadFailed();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            callBack.onDownLoadFailed();
        } catch (ExecutionException e) {
            e.printStackTrace();
            callBack.onDownLoadFailed();
        }

    }


    public interface ImageDownLoadCallBack {

        void onDownLoadSuccess(Bitmap bitmap);

        void onDownLoadFailed();
    }
}
