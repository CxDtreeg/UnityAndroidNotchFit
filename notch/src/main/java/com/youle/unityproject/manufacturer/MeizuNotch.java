package com.youle.unityproject.manufacturer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Window;

import com.youle.unityproject.core.AbstractNotch;

import java.lang.reflect.Field;

public class MeizuNotch extends AbstractNotch {

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        boolean fringeDevice = false;
        try{
            Class clazz = Class.forName("flyme.config.FlymeFeature");
            Field field = clazz.getDeclaredField("IS_FRINGE_DEVICE");
            fringeDevice = (boolean) field.get(null);
        }catch (Exception e){

        }
        if (fringeDevice){
            ContentResolver contentResolver = activity.getWindow().getContext().getContentResolver();
            boolean isFringeHidden = Settings.Global.getInt(contentResolver, "mz_fringe_hide", 0) == 1;
            return !isFringeHidden;
        }

        return false;
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        Context context = activity.getWindow().getContext();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        // 获取刘海高度(51px)
        int notchHeight = 0;
        int fhid = context.getResources().getIdentifier("fringe_height","dimen", "android");
        if (fhid >0){
            notchHeight = context.getResources().getDimensionPixelSize(fhid);
        }
        // 获取刘海宽度(534px)
        int notchWidth = 0;
        int fwid = context.getResources().getIdentifier("fringe_width", "dimen", "android");
        if (fwid > 0){
            notchWidth = context.getResources().getDimensionPixelSize(fwid);
        }
        return new int[]{notchWidth, notchHeight};
    }
}
