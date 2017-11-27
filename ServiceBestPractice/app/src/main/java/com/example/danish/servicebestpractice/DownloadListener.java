package com.example.danish.servicebestpractice;

/**
 * Created by danish on 2017/11/24.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
