package com.rrm.learnification.notification;

public interface NotificationResponseIntent {
    String getStringExtra(String name);

    int getIntExtra(String name);

    CharSequence getRemoteInputText(String key);

    boolean hasRemoteInput();
}
