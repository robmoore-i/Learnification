package com.rrm.learnification.logger;

public class StdOutLogger implements LearnificationLogger {
    private void log(String level, String tag, String message) {
        System.out.println(String.join(" ", level, tag, message));
    }

    @Override
    public void i(String tag, String message) {
        log("I", tag, message);
    }

    @Override
    public void v(String tag, String message) {
        log("V", tag, message);
    }

    @Override
    public void e(String tag, Throwable e) {
        log("E", tag, e.getMessage());
    }

    @Override
    public void u(String tag, String message) {
        log("U", tag, message);
    }
}
