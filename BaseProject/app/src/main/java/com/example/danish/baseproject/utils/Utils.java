package com.example.danish.baseproject.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.danish.baseproject.AsimsApplication;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * get the external storage file
     * 
     * @return the file
     */
    public static File getExternalStorageDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * get the external storage file path
     * 
     * @return the file path
     */
    public static String getExternalStoragePath() {
        return getExternalStorageDir().getAbsolutePath();
    }

    /**
     * get the external storage state
     * 
     * @return
     */
    public static String getExternalStorageState() {
        return Environment.getExternalStorageState();
    }

    /**
     * check the usability of the external storage.
     * 
     * @return enable -> true, disable->false
     */
    public static boolean isExternalStorageEnable() {
        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean unMounted = Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTED);

        return !(!canRead || onlyRead || unMounted);
    }

    /**
     * judge the list is null or isempty
     * 
     * @param list
     * @return
     */
    public static boolean isEmpty(final List<? extends Object> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(final Set<? extends Object> sets) {
        if (sets == null || sets.isEmpty()) {
            return true;
        }
        return false;
    }
    public static boolean isEmpty(final Map<? extends Object,? extends Object> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }
        return false;
    }


    /**
     * return true ,if the string is numeric
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(final String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @param phone
     * @return
     */
    public static boolean matchPhone(CharSequence phone) {
        Matcher m = Patterns.MOBILE_PHONE.matcher(phone);
        return m.matches();
    }

    /**
     * 
     * @param email
     * @return
     */
    public static boolean matchEmail(CharSequence email) {
        Matcher m = Patterns.EMAIL_ADDRESS.matcher(email);
        return m.matches();
    }
    public static boolean matchCarPlate(CharSequence plate) {
        Matcher m = Patterns.CAR_PLATE.matcher(plate);
        return m.matches();
    }
    public static boolean matchICNumber(CharSequence ic) {
        Matcher m = Patterns.IC_NUMBER.matcher(ic);
        return m.matches();
    }

    /**
     * get the width of the device screen
     * 
     */
    public static int getSceenWidth() {
        return AsimsApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * get the height of the device screen
     * 
     */
    public static int getSceenHeight() {
        return AsimsApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 
     * @param context
     * @return
     */
    public static float getSceenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * convert the dip value to px value
     * 
     * @return
     */
    public static int dip2px(float dipValue) {
        float scale = AsimsApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 
     * method desc： px -> dip
     * @param pxValue
     * @return
     */
    public static int px2dip(int pxValue) {
        float reSize = AsimsApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) ((pxValue / reSize) + 0.5);
    }

    /**
     * 
     * method desc：sp -> px
     * @param spValue
     * @return
     */
    public static float sp2px(int spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, AsimsApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * hide the input method
     * 
     * @param view
     */
    public static void hideSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * show the input method
     * 
     * @param view
     */
    public static void showSoftInput(View view) {
        if (view == null)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * get the package manaher
     * method desc：
     * @return
     */
    private static PackageManager getPackageInfo() {
        return AsimsApplication.getContext().getPackageManager();
    }

    /**
     * get the package name
     * method desc：
     * @return
     */
    public static String getPackageName() {
        return AsimsApplication.getContext().getPackageName();
    }

    /**
     * get the application meta data
     * method desc：
     * @param key
     * @return
     */
    public static Object getApplicationMeta(String key) {
        Object applicationMeta = null;
        try {
            ApplicationInfo applicationInfo  = getPackageInfo().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            if(applicationInfo != null) {
                applicationMeta = applicationInfo.metaData.get(key);
            }
        } catch (NameNotFoundException e) {
        }
        return applicationMeta;
    }

    /**
     * get the activity meta data
     * method desc：
     * @param componentName
     * @param key
     * @return
     */
    public static Object getActivityMeta(ComponentName componentName, String key) {
        Object activityMeta = null;
        try {
            ActivityInfo activityInfo  = getPackageInfo().getActivityInfo(componentName, PackageManager.GET_META_DATA);
            if(activityInfo != null) {
                activityMeta = activityInfo.metaData.get(key);
            }
        } catch (NameNotFoundException e) {

        }
        return activityMeta;
    }

    public static boolean isIntentSafe(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }
    public static String getPicPathFromUri(Uri uri, Activity activity) {
        String value = uri.getPath();

        if (value.startsWith("/external")) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else {
            return value;
        }
    }

    public static void cleanFile(File file, long maxInterval) {
        if(file==null || !file.exists()) {
            return ;
        }
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                cleanFile(f, maxInterval);
            }
            //delete this directory
            file.delete();
        } else {
            long current = System.currentTimeMillis();
            long lastModifiedTime = file.lastModified();
            if ((current - lastModifiedTime) > maxInterval) {
                // if the file is exist more than a week , so need to delete.
                file.delete();
            }
        }
    }

    /**
     * 
     * method desc： convert the str to the charString
     * @param str
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String toCharString(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "null";
        }
        StringBuilder strBuf = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int a = str.charAt(i);
            strBuf.append(Integer.toHexString(a).toUpperCase());
        }
        return strBuf.toString();
    }
    public static int getScaleByMinute(int minute) {

        if(minute==0) {
            return 15;
        }
        int value = minute /15;
        int remain = minute % 15;
        value = remain==0 ? value:value+1;
        int displayMinute = value * 15;
//        if(displayMinute == 60) {
//            return 0;
//        }
        return displayMinute;
    }
    
    public static boolean isCharatorBegin(String headerStr) {
        if(TextUtils.isEmpty(headerStr)) {
            return false;
        }
        String header = headerStr.substring(0, 1);
        // 正则表达式，判断首字母是否是英文字母  
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(header).matches()) {
            return true;
        } else {
            return false;
        }
    }
    public static String getChractorBegin(String headerStr) {
        if(TextUtils.isEmpty(headerStr)) {
            return "#";
        }
        String header = headerStr.substring(0, 1);
        // 正则表达式，判断首字母是否是英文字母  
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(header).matches()) {
            return header;
        } else {
            return "#";
        }
    }

}
