package com.rrm.learnification.response;

public interface LearnificationResponseIntent {
    boolean isShowMeResponse();

    boolean hasRemoteInput();

    String actualUserResponse();

    String expectedUserResponse();

    String givenPrompt();
}
