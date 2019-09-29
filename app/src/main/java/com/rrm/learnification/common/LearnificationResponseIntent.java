package com.rrm.learnification.common;

interface LearnificationResponseIntent {
    boolean isSkipped();

    boolean hasRemoteInput();

    String actualUserResponse();

    String expectedUserResponse();
}
