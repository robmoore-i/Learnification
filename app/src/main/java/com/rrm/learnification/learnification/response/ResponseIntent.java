package com.rrm.learnification.learnification.response;

interface ResponseIntent {
    String getStringExtra(String name);

    int getIntExtra(String name);

    CharSequence getRemoteInputText(String key);

    boolean hasRemoteInput();
}
