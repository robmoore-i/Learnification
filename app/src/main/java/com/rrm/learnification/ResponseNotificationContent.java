package com.rrm.learnification;

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
}
