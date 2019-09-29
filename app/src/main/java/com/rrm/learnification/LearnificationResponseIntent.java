package com.rrm.learnification;

interface LearnificationResponseIntent {
    boolean isSkipped();

    boolean hasRemoteInput();

    String actualUserResponse();

    String expectedUserResponse();
}
