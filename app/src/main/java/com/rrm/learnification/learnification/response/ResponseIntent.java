package com.rrm.learnification.learnification.response;

interface ResponseIntent {
    String getStringExtra(String name);

    CharSequence getRemoteInputText(String key);

    boolean hasRemoteInput();
}
