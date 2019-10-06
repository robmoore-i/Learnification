package com.rrm.learnification.response;

public interface LearnificationResponseIntent {
    boolean isSkipped();

    boolean hasRemoteInput();

    String actualUserResponse();

    String expectedUserResponse();
}
