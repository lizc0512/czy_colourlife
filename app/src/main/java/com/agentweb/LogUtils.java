package com.agentweb;

import android.util.Log;

import static com.agentweb.AgentWebConfig.DEBUG;

/**
 * Created by cenxiaozhong on 2017/5/28.
 */

public class LogUtils {

    private static final String PREFIX = " agentweb ---> "; //


    static boolean isDebug() {
        return DEBUG;
    }

    static void i(String tag, String message) {

        if (isDebug())
            Log.i(PREFIX.concat(tag), message);
    }

    static void v(String tag, String message) {

        if (isDebug())
            Log.v(PREFIX.concat(tag), message);

    }

    static void safeCheckCrash(String tag, String msg, Throwable tr) {
        if (isDebug()) {
            throw new RuntimeException(PREFIX.concat(tag) + " " + msg, tr);
        } else {
            Log.e(PREFIX.concat(tag), msg, tr);
        }
    }

    static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    static void e(String tag, String message) {

        if (DEBUG)
            Log.e(PREFIX.concat(tag), message);
    }
}
