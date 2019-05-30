package com.gesturepwd.utils;

import android.content.Context;

/**
 * Created by HX_CHEN on 2016/3/2.
 */
public class GetInstanceUtils {
    private static LockPatternUtils mLockPatternUtils;
    private static GetInstanceUtils mInstance;
    public static GetInstanceUtils getInstance(Context context) {
        mInstance = new GetInstanceUtils();
        mLockPatternUtils = new LockPatternUtils(context);
        return mInstance;
    }


    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }
}
