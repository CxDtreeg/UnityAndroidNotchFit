package com.youle.unityproject.core;


import android.text.TextUtils;

import com.youle.unityproject.manufacturer.GooglePNotch;
import com.youle.unityproject.manufacturer.HuaweiNotch;
import com.youle.unityproject.manufacturer.MeizuNotch;
import com.youle.unityproject.manufacturer.OppoNotch;
import com.youle.unityproject.manufacturer.SamsungNotch;
import com.youle.unityproject.manufacturer.SmartisanNotch;
import com.youle.unityproject.manufacturer.VivoNotch;
import com.youle.unityproject.manufacturer.XiaomiNotch;
import com.youle.unityproject.utils.DeviceUtils;
import com.youle.unityproject.utils.SystemProperties;

/**
 * Notch设备参数获取工厂
 * Created by wangchunlong on 2018/10/24.
 */

public class NotchFactory {
    private static NotchFactory instance;

    private NotchFactory() {
    }

    public static NotchFactory getInstance() {
        if (instance == null) {
            synchronized (NotchFactory.class) {
                instance = new NotchFactory();
            }
        }
        return instance;
    }

    AbstractNotch notch;

    public AbstractNotch getNotch() {
        if (notch != null) {
            return notch;
        }
        String manufacturer = DeviceUtils.getManufacturer().toLowerCase();
        if (TextUtils.equals(manufacturer, "huawei")) {
            notch = new HuaweiNotch();
        } else if (TextUtils.equals(manufacturer, "xiaomi")) {
            notch = new XiaomiNotch();
        } else if (TextUtils.equals(manufacturer, "oppo")) {
            notch = new OppoNotch();
        } else if (TextUtils.equals(manufacturer, "vivo")) {
            notch = new VivoNotch();
        } else if (TextUtils.equals(manufacturer, "smartisan")) {
            notch = new SmartisanNotch();
        } else if (DeviceUtils.isMeizu()) {
            notch = new MeizuNotch();
        } else if (DeviceUtils.isSamsung()) {
            notch = new SamsungNotch();
        } else {
            notch = new GooglePNotch();
        }
        return notch;
    }


}
