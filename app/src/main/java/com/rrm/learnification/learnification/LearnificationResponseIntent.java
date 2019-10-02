package com.rrm.learnification.learnification;

public interface LearnificationResponseIntent {
    boolean isSkipped();

    boolean hasRemoteInput();

    String actualUserResponse();

    String expectedUserResponse();
}
