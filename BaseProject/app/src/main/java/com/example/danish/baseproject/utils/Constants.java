package com.example.danish.baseproject.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by L on 2015/12/11 ,16:40.
 */
public class Constants {

    public static final String APP_NAME            = "ASIMS";
    public static String EXTERNAL_DIR        = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+APP_NAME;
    public static String LOG_DIR             = EXTERNAL_DIR+ File.separator+"log";
    public static String IMAGE_DIR             = EXTERNAL_DIR+ File.separator+"image";
}
