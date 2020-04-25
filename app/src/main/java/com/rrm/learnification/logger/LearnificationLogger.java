package com.rrm.learnification.logger;

public interface LearnificationLogger {

    static LearnificationLogger noneLogger() {
        return new AndroidLogger() {
            @Override
            public void i(String tag, String message) {
            }

            @Override
            public void v(String tag, String message) {
            }

            @Override
            public void e(String tag, Throwable e) {
            }

            @Override
            public void u(String tag, String message) {
            }
        };
    }

    void i(String tag, String message);

    void v(String tag, String message);

    void e(String tag, Throwable e);

    void u(String tag, String message);
}
