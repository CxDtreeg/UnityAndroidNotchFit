package com.youle.unityproject.utils;

import android.util.Log;

import com.youle.unityproject.BuildConfig;


/**
 * 日志工具类
 * Created by wangchunlong on 2018/10/24.
 */

public class LogUtils {
    private static String TAG = "NotchFit";

    public static void i(String msg){
        if(BuildConfig.DEBUG)
        Log.i(TAG, msg);
    }

    public static void e(String msg){
        if(BuildConfig.DEBUG)
        Log.e(TAG, msg);
    }
}
