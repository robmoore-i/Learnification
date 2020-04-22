package com.rrm.learnification.logger;

import android.util.Log;

import java.util.ArrayList;

public class AndroidLogger {
    public AndroidLogger() {
    }

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
        i(wrapTag(tag), "USER ACTION - " + message);
    }

    public ArrayList<String> dump() {
        ArrayList<String> logs = new ArrayList<>();
        logs.add("some log 1");
        logs.add("some log 2");
        logs.add("some log 3");
        logs.add("some log 4");
        return logs;
    }
}
