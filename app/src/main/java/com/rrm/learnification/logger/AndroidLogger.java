package com.rrm.learnification.logger;

import android.util.Log;

public class AndroidLogger implements LearnificationLogger {
    private String wrapTag(String tag) {
        return "Learnification:" + tag;
    }

    public void i(String tag, String message) {
        Log.i(wrapTag(tag), message);
    }

    public void v(String tag, String message) {
        Log.v(wrapTag(tag), message);
    }

    public void e(String tag, Throwable e) {
        Log.e(wrapTag(tag), e.getMessage(), e);
    }

    public void u(String tag, String message) {
        Log.i(wrapTag(tag), "USER ACTION - " + message);
    }
}
