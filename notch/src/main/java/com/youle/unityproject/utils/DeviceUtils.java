package com.youle.unityproject.utils;

import android.os.Build;
import android.text.TextUtils;

import java.util.Locale;

/**
 * 设备工具类
 */
public final class DeviceUtils {

    /**
     * Return the manufacturer of the product/hardware.
     * <p>e.g. Xiaomi</p>
     *
     * @return the manufacturer of the product/hardware
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // 是否是魅族
    public static boolean isMeizu() {
        String meizuFlymeOSFlag = SystemProperties.get("ro.build.display.id");
        String meizuVersionFlag = SystemProperties.get("ro.build.flyme.version");
        boolean meizuVersionFlagIsNullOrEmpty = meizuVersionFlag == null || meizuVersionFlag.isEmpty();
        boolean meizuFlymeOSFlagIsContain = meizuFlymeOSFlag != null && meizuFlymeOSFlag.toLowerCase(Locale.getDefault()).contains("flyme");
        return !meizuVersionFlagIsNullOrEmpty || meizuFlymeOSFlagIsContain;
    }

    // 是否是三星
    public static boolean isSamsung() {
        String fingerPrint = Build.FINGERPRINT;
        if (!TextUtils.isEmpty(fingerPrint)){
            return fingerPrint.toLowerCase(Locale.getDefault()).contains("samsung");
        }
        String manufacturer = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(manufacturer)){
            return manufacturer.toLowerCase(Locale.getDefault()).contains("samsung");
        }
        return false;
    }
}
