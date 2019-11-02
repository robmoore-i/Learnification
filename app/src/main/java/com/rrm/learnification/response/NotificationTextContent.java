package com.rrm.learnification.response;

import android.support.annotation.NonNull;

public class NotificationTextContent {
    private final String title;
    private final String text;

    public NotificationTextContent(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String title() {
        return title;
    }

    public String text() {
        return text;
    }

    @NonNull
    @Override
    public String toString() {
        return "NotificationTextContent{title='" + title + "'," + "text='" + text + "'}";
    }
}
