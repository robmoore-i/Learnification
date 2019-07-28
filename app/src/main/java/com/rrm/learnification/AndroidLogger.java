package com.rrm.learnification;

import android.util.Log;

class AndroidLogger {

    public AndroidLogger() {
    }

    void v(String tag, String message) {
        Log.v(tag, message);
    }

    void e(String tag, Throwable e) {
        Log.e(tag, e.getMessage(), e);
    }
}
