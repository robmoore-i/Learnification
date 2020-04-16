package com.rrm.learnification.intent;

public interface ResponseIntent {
    String getStringExtra(String name);

    CharSequence getRemoteInputText(String key);

    boolean hasRemoteInput();
}
