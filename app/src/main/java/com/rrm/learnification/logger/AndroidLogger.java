package com.rrm.learnification.logger;

import android.util.Log;

import java.util.List;

public class AndroidLogger {

    private final LogStore logStore;

    public AndroidLogger() {
        logStore = new LogStore();
    }

    private String wrapTag(String tag) {
        return "Learnification:" + tag;
    }

    public void i(String tag, String message) {
        Log.i(wrapTag(tag), message);
        logStore.i(tag, message);
    }

    public void v(String tag, String message) {
        Log.v(wrapTag(tag), message);
        logStore.v(tag, message);
    }

    public void e(String tag, Throwable e) {
        Log.e(wrapTag(tag), e.getMessage(), e);
        logStore.e(tag, e.getMessage());
    }

    public void u(String tag, String message) {
        Log.i(wrapTag(tag), "USER ACTION - " + message);
        logStore.u(tag, message);
    }

    public List<String> dump() {
        return logStore.dump();
    }
}
