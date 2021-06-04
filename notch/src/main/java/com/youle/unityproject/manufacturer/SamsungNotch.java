package com.youle.unityproject.manufacturer;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.DisplayCutoutCompat;
import androidx.lifecycle.ViewModelProvider;

import com.unity3d.player.UnityPlayer;
import com.youle.unityproject.core.AbstractNotch;

import java.lang.reflect.Method;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SamsungNotch extends AbstractNotch {
    private WindowInsetsWrapper mWindowInsetsWrapper;

    public class WindowInsetsWrapper {
        public DisplayCutoutWrapper displayCutoutWrapper;

        @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
        WindowInsetsWrapper(WindowInsets insets) {
            displayCutoutWrapper = new DisplayCutoutWrapper(insets);
        }
    }

    public class DisplayCutoutWrapper {
        Rect mSafeInsets = new Rect();
        ArrayList<Rect> mBoundingRects = new ArrayList<Rect>();

        private static final String TAG = "SamsungNotch";
        private static final String GET_DISPLAY_CUTOUT = "getDisplayCutout";
        private static final String GET_SAFE_INSET_TOP = "getSafeInsetTop";
        private static final String GET_SAFE_INSET_BOTTOM = "getSafeInsetBottom";
        private static final String GET_SAFE_INSET_LEFT = "getSafeInsetLeft";
        private static final String GET_SAFE_INSET_RIGHT = "getSafeInsetRight";

        public int getSafeInsetTop() {
            return mSafeInsets.top;
        }

        public int getSafeInsetBottom() {
            return mSafeInsets.bottom;
        }

        public int getSafeInsetLeft() {
            return mSafeInsets.left;
        }

        public int getSafeInsetRight() {
            return mSafeInsets.right;
        }

        public List<Rect> getBoundingRects() {
            return mBoundingRects;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
        DisplayCutoutWrapper(WindowInsets insets) {
            try {
                Method method = WindowInsets.class.getDeclaredMethod(GET_DISPLAY_CUTOUT);
                Object displayCutoutInstance = method.invoke(insets);
                Class cls = displayCutoutInstance.getClass();
                int top = (int) cls.getDeclaredMethod(GET_SAFE_INSET_TOP).invoke(displayCutoutInstance);
                int bottom = (int) cls.getDeclaredMethod(GET_SAFE_INSET_BOTTOM).invoke(displayCutoutInstance);
                int left = (int) cls.getDeclaredMethod(GET_SAFE_INSET_LEFT).invoke(displayCutoutInstance);
                int right = (int) cls.getDeclaredMethod(GET_SAFE_INSET_RIGHT).invoke(displayCutoutInstance);
                mSafeInsets.set(left, top, right, bottom);
                mBoundingRects.add(mSafeInsets);
            } catch (Exception e) {
                Log.e(TAG, "DisplayCutoutWrapper init exception: " + e.getMessage());
            }
        }
    }

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return hasNotchInScreenHardware(activity.getWindow());
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        List<Rect> rects = getSamsungNotchSizeHardware(activity.getWindow());
        if (rects.isEmpty()) return new int[]{};
        Rect rect = rects.get(0);
        return new int[]{0, rect.top};
    }


    /**
     * hasNotchInScreen()
     * getNotchSizeHardware()
     * getNotchSize()
     * setWindowLayoutAroundNotch()
     * setWindowLayoutBlockNotch()
     * <p>
     * 调用以上方法时，必须调用当前方法
     */
    private void checkInit(Window window) {
        if (mWindowInsetsWrapper != null && mWindowInsetsWrapper.displayCutoutWrapper != null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = window.getDecorView();
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets == null) return;
            mWindowInsetsWrapper = new WindowInsetsWrapper(windowInsets);
        }
    }

    public boolean hasNotchInScreen(Window window) {
        try {
            checkInit(window);
            return !getSamsungNotchSize(window).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasNotchInScreenHardware(Window window) {
        try {
            checkInit(window);
            Resources res = window.getContext().getResources();
            int resId = res.getIdentifier("config_mainBuiltInDisplayCutout", "string", "android");
            String spec = null;
            if (resId > 0) spec = res.getString(resId);
            return spec != null && !spec.isEmpty();
        } catch (Exception e) {
            return hasNotchInScreen(window);
        }
    }

    public List<Rect> getSamsungNotchSize(Window window) {
        checkInit(window);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //setWindowLayoutBlockNotch之后 DisplayCutout 可能为null
            //获取当前屏幕 notch size 需要重新建立一个新的 WindowInsetsWrapper 来实时获取 屏幕状态
            WindowInsetsWrapper tempWrapper = new WindowInsetsWrapper(window.getDecorView().getRootWindowInsets());
            if (tempWrapper.displayCutoutWrapper != null) {
                return tempWrapper.displayCutoutWrapper.getBoundingRects();
            }
        }
        return new ArrayList();
    }

    public List<Rect> getSamsungNotchSizeHardware(Window window) {
        checkInit(window);
        //获取硬件层面上的 notch size
        //利用 setWindowLayoutBlockNotch() 执行之前 获取的 notch size 然后保存起来，当做硬件层面上 notch size
        if (mWindowInsetsWrapper != null && mWindowInsetsWrapper.displayCutoutWrapper != null) {
            return mWindowInsetsWrapper.displayCutoutWrapper.getBoundingRects();
        }
        return getSamsungNotchSize(window);
    }
}
