package com.youle.unityproject.manufacturer;


import android.app.Activity;

import com.youle.unityproject.core.AbstractNotch;

/**
 * Google标准P版本刘海屏
 * Created by wangchunlong on 2018/10/24.
 */

public class GooglePNotch extends AbstractNotch {

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return false;
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        return new int[0];
    }

    @Override
    protected boolean isNotchEnable_P(Activity activity) {
        return super.isNotchEnable_P(activity);
    }

    @Override
    protected int[] getNotchSize_P(Activity activity) {
        return super.getNotchSize_P(activity);
    }
}
