package com.rrm.learnification.common;

import android.util.Log;

public class AndroidLogger {

    public AndroidLogger() {
    }

    public void v(String tag, String message) {
        Log.v(tag, message);
    }

    public void e(String tag, Throwable e) {
        Log.e(tag, e.getMessage(), e);
    }
}
