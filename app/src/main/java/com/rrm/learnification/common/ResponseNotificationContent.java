package com.rrm.learnification.common;

import android.support.annotation.NonNull;

class ResponseNotificationContent {
    private final String title;
    private final String text;

    ResponseNotificationContent(String title, String text) {
        this.title = title;
        this.text = text;
    }

    String title() {
        return title;
    }

    String text() {
        return text;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResponseNotificationContent{title='" + title + "'," + "text='" + text + "'}";
    }
}
