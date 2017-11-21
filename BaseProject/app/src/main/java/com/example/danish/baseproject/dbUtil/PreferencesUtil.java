package com.example.danish.baseproject.dbUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesUtil {

    private static SharedPreferences.Editor mEditor = null;
    private static SharedPreferences mdPreferences = null;

    private PreferencesUtil() {

    }

    private static SharedPreferences.Editor getEditor(Context paramContext) {
        if (mEditor == null)
            mEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
        return mEditor;
    }

    public static int getSharedPreferences(Context paramContext, String paramString, int paramInt) {
        return getSharedPreferences(paramContext).getInt(paramString, paramInt);
    }

    public static long getSharedPreferences(Context paramContext, String paramString, long paramLong) {
        return getSharedPreferences(paramContext).getLong(paramString, paramLong);
    }

    public static float getSharedPreferences(Context paramContext, String paramString, float paramFloat) {
        return getSharedPreferences(paramContext).getFloat(paramString, paramFloat);
    }

    public static Boolean getSharedPreferences(Context paramContext, String paramString, Boolean paramBoolean) {
        return getSharedPreferences(paramContext).getBoolean(paramString, paramBoolean);
    }

    public static String getSharedPreferences(Context paramContext, String paramString1, String paramString2) {
        return getSharedPreferences(paramContext).getString(paramString1, paramString2);
    }

    private static SharedPreferences getSharedPreferences(Context paramContext) {
        if (mdPreferences == null)
            mdPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        return mdPreferences;
    }

    public static void setEditor(Context paramContext, String paramString, int paramInt) {
        getEditor(paramContext).putInt(paramString, paramInt).commit();
    }

    public static void setEditor(Context paramContext, String paramString, long paramLong) {
        getEditor(paramContext).putLong(paramString, paramLong).commit();
    }
    public static void setEditor(Context paramContext, String paramString, float paramFloat) {
        getEditor(paramContext).putFloat(paramString, paramFloat).commit();
    }

    public static void setEditor(Context paramContext, String paramString, Boolean paramBoolean) {
        getEditor(paramContext).putBoolean(paramString, paramBoolean).commit();
    }

    public static void setEditor(Context paramContext, String paramString1, String paramString2) {
        getEditor(paramContext).putString(paramString1, paramString2).commit();
    }
}
